package com.bpointer.rkofficial.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Model.Response.RegisterResponseModel.RegisterResponseModel;
import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.VerifyNumberResponseModel.VerifyNumberResponseModel;
import com.bpointer.rkofficial.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_WHATSAPP;

public class RegisterActivity extends AppCompatActivity {
    EditText et_first_name, et_last_name, et_mobile, et_password, et_confirm_password;
    Button bt_register;
    CustomDialog customDialog;
    PreferenceManager preferenceManager;
    TextView tv_title;
    String otp;
    private String deviceId;

    private FirebaseAuth firebaseAuth;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("RegisterActivity", "deviceId--->" + deviceId);
        initView();


        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                customDialog.closeLoader();
                Log.e("check", "phoneAuthCredential: " + phoneAuthCredential);

                signInWithPhoneAuthCredential(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("check", "FirebaseException: " + e);
                customDialog.closeLoader();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                customDialog.closeLoader();
                Log.e("check", "forceResendingToken: " + forceResendingToken);
                verificationId = s;
                Toast.makeText(RegisterActivity.this, "Verification code sent", Toast.LENGTH_SHORT).show();
                showVerifyDialog();

            }
        };

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Authentication successful!", Toast.LENGTH_SHORT).show();
                        register();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                        showVerifyDialog();
                    }
                });

    }

    private void verifyOTP() {

        String otp = "";

        String verificationId = getIntent().getStringExtra("verificationId");
        firebaseAuth.signInWithCredential(PhoneAuthProvider.getCredential(verificationId, otp))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "OTP Verified Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initView() {
        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        bt_register = findViewById(R.id.bt_register);
        tv_title = findViewById(R.id.tv_title);

        customDialog = new CustomDialog(this);
        preferenceManager = new PreferenceManager(this);

        tv_title.setText("DRS Group " + preferenceManager.getStringPreference(ADMIN_WHATSAPP));

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_first_name.getText().toString().trim().isEmpty()) {
                    et_first_name.setError("First Name Required !");
                } else if (et_last_name.getText().toString().trim().isEmpty()) {
                    et_last_name.setError("Last Name Required !");
                } else if (et_mobile.getText().toString().trim().isEmpty() || et_mobile.getText().toString().trim().length() < 10) {
                    et_mobile.setError("Mobile No. Required !");
                } else if (et_password.getText().toString().trim().isEmpty()) {
                    et_password.setError("Password Required !");
                } else if (et_confirm_password.getText().toString().trim().isEmpty()) {
                    et_confirm_password.setError("Password Required !");
                } else if (!et_password.getText().toString().trim().equals(et_confirm_password.getText().toString().trim())) {
                    et_confirm_password.setError("Wrong Password !");
                } else {
                    Random random = new Random();
                    otp = String.format("%04d", random.nextInt(10000));
                    Log.e("otp", "onClick: " + otp);
//                    verifyNumberAPI();


                    sendVerificationCode(et_mobile.getText().toString().trim());
                }
            }
        });
    }

    private void sendVerificationCode(String number) {

        customDialog.showLoader();

        String phoneNumber = "+91" + number;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks
        );
    }


    private void verifyNumberAPI() {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setContact_number(et_mobile.getText().toString());
        requestBody.setOtp(otp);

        Call<VerifyNumberResponseModel> call = Api.getClient().create(Authentication.class).verifyNumber(requestBody);
        call.enqueue(new Callback<VerifyNumberResponseModel>() {
            @Override
            public void onResponse(Call<VerifyNumberResponseModel> call, Response<VerifyNumberResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        showVerifyDialog();
                    } else {
                        customDialog.showFailureDialog("" + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyNumberResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });

    }

    private void showVerifyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_verify_otp, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        Button bt_submit = dialogView.findViewById(R.id.bt_submit);
        EditText et_otp = dialogView.findViewById(R.id.et_otp);
        TextView tv_mobile = dialogView.findViewById(R.id.tv_mobile);
        tv_mobile.setText("Enter the verification code we sent you on " + et_mobile.getText().toString().trim());

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_otp.getText().toString().isEmpty()) {
                    et_otp.setError("Please enter Otp !");
                } else if (et_otp.getText().toString().length() < 4) {
                    et_otp.setError("Please enter correct Otp !");
                } else {
                    alertDialog.dismiss();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, et_otp.getText().toString().trim());
                    signInWithPhoneAuthCredential(credential);

                }
//                else {
//                    Toast.makeText(RegisterActivity.this, "Please enter correct Otp !", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        alertDialog.show();
    }

    void register() {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setFirst_name(et_first_name.getText().toString().trim());
        requestBody.setLast_name(et_last_name.getText().toString().trim());
        requestBody.setContact_number(et_mobile.getText().toString().trim());
        requestBody.setPassword(et_password.getText().toString().trim());
        requestBody.setDevice_id(deviceId);
        Call<RegisterResponseModel> call = Api.getClient().create(Authentication.class).register(requestBody);
        call.enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        Toast.makeText(RegisterActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
//                        onBackPressed();
                    } else {
                        Toast.makeText(RegisterActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("RegisterResponse", "onFailure: " + t.getMessage());
            }
        });
    }
}