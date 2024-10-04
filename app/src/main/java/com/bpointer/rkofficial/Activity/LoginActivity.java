package com.bpointer.rkofficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Common.SessionManager;
import com.bpointer.rkofficial.Fragments.ForgotPasswordFragment;
import com.bpointer.rkofficial.Fragments.LoginMPINFragment;
import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.AdminDetailsResponseModel.AdminDetailsResponseModel;
import com.bpointer.rkofficial.Model.Response.LoginResponseModel.LoginResponseModel;
import com.bpointer.rkofficial.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_EMAIL;
import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_MOBILE;
import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_WHATSAPP;
import static com.bpointer.rkofficial.Common.AppConstant.APP_STATUS;
import static com.bpointer.rkofficial.Common.AppConstant.ID;
import static com.bpointer.rkofficial.Common.AppConstant.INSTRUCTION;
import static com.bpointer.rkofficial.Common.AppConstant.MANUAL_QR_IMG;
import static com.bpointer.rkofficial.Common.AppConstant.MOBILE;
import static com.bpointer.rkofficial.Common.AppConstant.NAME;
import static com.bpointer.rkofficial.Common.AppConstant.PAYMENT_METHOD;
import static com.bpointer.rkofficial.Common.AppConstant.USER_ID;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_new_user, tv_forgot_password, tv_title, tv_mobile, tv_email;
    Button bt_login, bt_login_mpin;
    EditText et_mobile, et_password;
    PreferenceManager mPreferenceManager;
    SessionManager sessionManager;
    CustomDialog customDialog;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        getAdminDetailsAPI();


         deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
         Log.e("LoginActivity","deviceId--->"+deviceId);
    }

    private void getAdminDetailsAPI() {
        customDialog.showLoader();

        Call<AdminDetailsResponseModel> call = Api.getClient().create(Authentication.class).getAdminDetails();
        call.enqueue(new Callback<AdminDetailsResponseModel>() {
            @Override
            public void onResponse(Call<AdminDetailsResponseModel> call, Response<AdminDetailsResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        mPreferenceManager.setPreference(ADMIN_WHATSAPP, response.body().getUser().getWhatsappNumber());
                        mPreferenceManager.setPreference(ADMIN_EMAIL, response.body().getUser().getEmail());
                        mPreferenceManager.setPreference(ADMIN_MOBILE, response.body().getUser().getContactNumber());
                        mPreferenceManager.setPreference(APP_STATUS, response.body().getAppStatus());
                        mPreferenceManager.setPreference(PAYMENT_METHOD, response.body().getUser().getPayment_method());
                        mPreferenceManager.setPreference(MANUAL_QR_IMG, response.body().getUser().getManual_qr_img());
                        mPreferenceManager.setPreference(INSTRUCTION, response.body().getUser().getInstruction());

                        tv_title.setText("JDS " + response.body().getUser().getWhatsappNumber());
                        tv_email.setText("Email: " + response.body().getUser().getEmail());
                        tv_mobile.setText("Mo. " + response.body().getUser().getContactNumber());
                    } else {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AdminDetailsResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        bt_login = findViewById(R.id.bt_login);
        bt_login_mpin = findViewById(R.id.bt_login_mpin);
        et_mobile = findViewById(R.id.et_mobile);
        et_password = findViewById(R.id.et_password);
        tv_new_user = findViewById(R.id.tv_new_user);
        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        tv_title = findViewById(R.id.tv_title);
        tv_email = findViewById(R.id.tv_email);
        tv_mobile = findViewById(R.id.tv_mobile);

        customDialog = new CustomDialog(this);
        sessionManager = new SessionManager(LoginActivity.this);
        mPreferenceManager = new PreferenceManager(LoginActivity.this);

        if (!sessionManager.getMobile().isEmpty()) {
            et_mobile.setText(sessionManager.getMobile());
        }

        bt_login.setOnClickListener(this);
        bt_login_mpin.setOnClickListener(this);
        tv_new_user.setOnClickListener(this);
        tv_forgot_password.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.bt_login:
                if (et_mobile.getText().toString().trim().isEmpty() || et_mobile.getText().toString().trim().length() < 10) {
                    et_mobile.setError("Mobile No. Required !");
                } else if (et_password.getText().toString().trim().isEmpty()) {
                    et_password.setError("Password Required !");
                } else {
                    loginAPI();
                }
                break;

            case R.id.bt_login_mpin:
                fragment = new LoginMPINFragment();
                fragmentTransaction.add(R.id.content, fragment);
                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.commit();
                break;

            case R.id.tv_forgot_password:
                fragment = new ForgotPasswordFragment();
                fragmentTransaction.add(R.id.content, fragment);
                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.commit();
                break;

            case R.id.tv_new_user:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    void loginAPI() {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setContact_number(et_mobile.getText().toString().trim());
        requestBody.setPassword(et_password.getText().toString().trim());
        requestBody.setDevice_id(deviceId);
        Call<LoginResponseModel> call = Api.getClient().create(Authentication.class).login(requestBody);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        mPreferenceManager.setIntPreference(ID, response.body().getUser().getId());
                        mPreferenceManager.setPreference(USER_ID, response.body().getUser().getUserId());
                        Log.e("check", "id: " +response.body().getUser().getId() + " User_id: " +response.body().getUser().getUserId());
                        mPreferenceManager.setPreference(MOBILE, response.body().getUser().getContactNumber());
                        mPreferenceManager.setPreference(NAME, response.body().getUser().getName());
                        sessionManager.setLogin(false);
                        sessionManager.setMobile(response.body().getUser().getContactNumber());

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }
}