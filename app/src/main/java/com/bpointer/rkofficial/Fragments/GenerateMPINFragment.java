package com.bpointer.rkofficial.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.MpinGenerateResponseModel;
import com.bpointer.rkofficial.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ID;

public class GenerateMPINFragment extends Fragment implements View.OnClickListener {
    View view;
    ImageView iv_back;
    TextView tv_title;
    CardView cv_generate_mpin;
    AlertDialog generateDialog;
    PreferenceManager preferenceManager;
    int userId;
    CustomDialog customDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_generate_mpin, container, false);
    
        initView();


        return view;
    }

    private void initView() {
        iv_back = view.findViewById(R.id.iv_back);
        tv_title = view.findViewById(R.id.tv_title);
        cv_generate_mpin = view.findViewById(R.id.cv_generate_mpin);

        tv_title.setText("MPIN");

        customDialog = new CustomDialog(getContext());
        preferenceManager = new PreferenceManager(getContext());
        userId = preferenceManager.getIntPreference(ID);

        iv_back.setOnClickListener(this);
        cv_generate_mpin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.cv_generate_mpin:
                generateMPINDialog();
                break;
        }
    }

    private void generateMPINDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_genrate_mpin, viewGroup, false);
        builder.setView(dialogView);
        generateDialog = builder.create();
        generateDialog.setCanceledOnTouchOutside(false);

        Button bt_submit = dialogView.findViewById(R.id.bt_submit);
        EditText et_mpin = dialogView.findViewById(R.id.et_mpin);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_mpin.getText().toString().trim().isEmpty() || et_mpin.getText().toString().trim().length() < 4) {
                    Toast.makeText(getContext(), R.string.enter_4_digit_mpin, Toast.LENGTH_SHORT).show();
                } else {
                    generateMpinAPI(et_mpin.getText().toString().trim());
                }
            }
        });

        generateDialog.show();
    }

    private void generateMpinAPI(String mpin) {
     customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));
        requestBody.setMpin(mpin);

        Call<MpinGenerateResponseModel> call = Api.getClient().create(Authentication.class).generateMpin(requestBody);
        call.enqueue(new Callback<MpinGenerateResponseModel>() {
            @Override
            public void onResponse(Call<MpinGenerateResponseModel> call, Response<MpinGenerateResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        generateDialog.dismiss();
                        customDialog.showSuccessDialog(response.body().getMessage());
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MpinGenerateResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });

    }
}
