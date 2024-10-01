package com.bpointer.rkofficial.Fragments;

import android.app.AlertDialog;
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

import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.SendOtpResponseModel.SendOtpResponseModel;
import com.bpointer.rkofficial.Model.Response.UpdatePasswordResponseModel.UpdatePasswordResponseModel;
import com.bpointer.rkofficial.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_EMAIL;
import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_WHATSAPP;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordFragment extends Fragment {
    View view;
    EditText et_mobile;
    Button bt_submit;
    TextView tv_title, tv_email;
    PreferenceManager preferenceManager;
    CustomDialog customDialog;
    AlertDialog alertDialog;
    String id, mobile, otp;
    String verificationId;

    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        firebaseAuth = FirebaseAuth.getInstance();


    
        initView();



        return view;
    }



    private void initView() {
        et_mobile = view.findViewById(R.id.et_mobile);
        bt_submit = view.findViewById(R.id.bt_submit);
        tv_title = view.findViewById(R.id.tv_title);
        tv_email = view.findViewById(R.id.tv_email);

        preferenceManager = new PreferenceManager(getContext());
        customDialog = new CustomDialog(getContext());

        tv_title.setText("DRS Group " + preferenceManager.getStringPreference(ADMIN_WHATSAPP));
        tv_email.setText("Email: " + preferenceManager.getStringPreference(ADMIN_EMAIL));

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_mobile.getText().toString().trim().isEmpty() || et_mobile.getText().toString().trim().length() < 10) {
                    et_mobile.setError("Mobile No. Required !");
                } else {
//                    sendOtpAPI();

                    sendFirebaseOTP(et_mobile.getText().toString().trim());
                }
            }
        });
    }

    private void sendFirebaseOTP(String mobile_number) {

        mobile = mobile_number;
        customDialog.showLoader();

        Log.e("check", "mobile_number: " +mobile_number);
//        Toast.makeText(getActivity(), "" +mobile_number, Toast.LENGTH_SHORT).show();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile_number,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        Toast.makeText(getActivity(), "onVerificationCompleted", Toast.LENGTH_SHORT).show();
                        customDialog.closeLoader();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(getActivity(), "onVerificationFailed", Toast.LENGTH_SHORT).show();
                        customDialog.closeLoader();

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                        customDialog.closeLoader();
                        verificationId = s;
                        Toast.makeText(getContext(), "Verifications code sent!", Toast.LENGTH_SHORT).show();
                        showVerifyDialog();


                    }
                }
        );

    }

    private void sendOtpAPI() {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setContact_number(et_mobile.getText().toString());

        Call<SendOtpResponseModel> call = Api.getClient().create(Authentication.class).sendOtp(requestBody);
        call.enqueue(new Callback<SendOtpResponseModel>() {
            @Override
            public void onResponse(Call<SendOtpResponseModel> call, Response<SendOtpResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        id = String.valueOf(response.body().getUser().getId());
                        otp = String.valueOf(response.body().getUser().getOtp());
                        mobile = response.body().getUser().getContactNumber();
                        showVerifyDialog();
                    } else {
                        customDialog.showFailureDialog("" + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SendOtpResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });

    }

    private void showVerifyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_verify_otp, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();

        Button bt_submit = dialogView.findViewById(R.id.bt_submit);
        EditText et_otp = dialogView.findViewById(R.id.et_otp);
        TextView tv_mobile = dialogView.findViewById(R.id.tv_mobile);
        tv_mobile.setText("Enter the verification code we sent you on " + mobile);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_otp.getText().toString().isEmpty()) {
                    et_otp.setError("Please enter Otp !");
                } else if (et_otp.getText().toString().length() < 4) {
                    et_otp.setError("Please enter correct Otp !");
                }
//                else if (et_otp.getText().toString().equals(otp)) {
//                    alertDialog.dismiss();
//                    showUpdatePasswordDialog();
//                }
                else {
//                    Toast.makeText(getContext(), "Please enter correct Otp !", Toast.LENGTH_SHORT).show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, et_otp.getText().toString().trim());
                    signInWithPhoneAuthCredential(credential);
                }
            }

        });
        alertDialog.show();
    }

    private void showUpdatePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_password, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();

        Button bt_submit = dialogView.findViewById(R.id.bt_submit);
        EditText et_password = dialogView.findViewById(R.id.et_password);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    updatePasswordAPI(et_password.getText().toString());
                }
            }
        });
        alertDialog.show();
    }

    private void updatePasswordAPI(String password) {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
//        requestBody.setUser_id(id);
        requestBody.setMobile_number(mobile);
        requestBody.setPassword(password);

        Call<UpdatePasswordResponseModel> call = Api.getClient().create(Authentication.class).updatePassword(requestBody);
        call.enqueue(new Callback<UpdatePasswordResponseModel>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponseModel> call, Response<UpdatePasswordResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        alertDialog.dismiss();
                        getActivity().onBackPressed();
                    } else {
                        customDialog.showFailureDialog("" + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdatePasswordResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Authentication Successful!", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        showUpdatePasswordDialog();
                    }else {
                        Toast.makeText(getContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
      
    }

}
