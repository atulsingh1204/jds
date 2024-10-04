package com.bpointer.rkofficial.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bpointer.rkofficial.Activity.MainActivity;
import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Common.SessionManager;
import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.MPINLoginResponseModel.MPINLoginResponseModel;
import com.bpointer.rkofficial.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_EMAIL;
import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_WHATSAPP;
import static com.bpointer.rkofficial.Common.AppConstant.ID;
import static com.bpointer.rkofficial.Common.AppConstant.MOBILE;
import static com.bpointer.rkofficial.Common.AppConstant.NAME;
import static com.bpointer.rkofficial.Common.AppConstant.USER_ID;

public class LoginMPINFragment extends Fragment {
    View view;
    EditText et_mpin;
    Button bt_login;
    PreferenceManager mPreferenceManager;
    SessionManager sessionManager;
    CustomDialog customDialog;
    TextView tv_title, tv_email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_mpin, container, false);
    
        initView();
        return view;
    }

    private void initView() {
        et_mpin = view.findViewById(R.id.et_mpin);
        bt_login = view.findViewById(R.id.bt_login);
        tv_title = view.findViewById(R.id.tv_title);
        tv_email = view.findViewById(R.id.tv_email);

        sessionManager = new SessionManager(getContext());
        mPreferenceManager = new PreferenceManager(getContext());
        customDialog = new CustomDialog(getContext());

        tv_title.setText("JDS " + mPreferenceManager.getStringPreference(ADMIN_WHATSAPP));
        tv_email.setText("Email: " + mPreferenceManager.getStringPreference(ADMIN_EMAIL));


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_mpin.getText().toString().trim().isEmpty() || et_mpin.getText().toString().trim().length() < 4) {
                    et_mpin.setError("Mpin Required !");
                } else {
                    loginAPI();
                }
            }
        });
    }

    private void loginAPI() {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setContact_number(sessionManager.getMobile());
        requestBody.setMpin(et_mpin.getText().toString().trim());

        Call<MPINLoginResponseModel> call = Api.getClient().create(Authentication.class).mpinLogin(requestBody);
        call.enqueue(new Callback<MPINLoginResponseModel>() {
            @Override
            public void onResponse(Call<MPINLoginResponseModel> call, Response<MPINLoginResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        mPreferenceManager.setIntPreference(ID, response.body().getUser().getId());
                        mPreferenceManager.setPreference(USER_ID, response.body().getUser().getUserId());
                        mPreferenceManager.setPreference(MOBILE, response.body().getUser().getContactNumber());
                        mPreferenceManager.setPreference(NAME, response.body().getUser().getName());
                        sessionManager.setLogin(false);
                        Intent i = new Intent(getContext(), MainActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MPINLoginResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }
}
