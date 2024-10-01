package com.bpointer.rkofficial.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.HomeResponseModel.HomeResponseModel;
import com.bpointer.rkofficial.Model.Response.WithdrawAmountResponseModel.WithdrawAmountResponseModel;
import com.bpointer.rkofficial.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_WHATSAPP;
import static com.bpointer.rkofficial.Common.AppConstant.ID;

public class WithdrawPoints extends AppCompatActivity implements View.OnClickListener {
    EditText et_point;
    Button bt_submit;
    TextView tv_title, tv_whatsapp, tv_wallet, tv_video, tv_transaction_history;
    PreferenceManager preferenceManager;
    int userId, wallet_amount = 0;
    ImageView iv_back;
    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_points);
    
        initView();

        getHomeDataAPI();
    }

    private void getHomeDataAPI() {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));

        Call<HomeResponseModel> call = Api.getClient().create(Authentication.class).getHomeData(requestBody);
        call.enqueue(new Callback<HomeResponseModel>() {
            @Override
            public void onResponse(Call<HomeResponseModel> call, Response<HomeResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        wallet_amount = Integer.parseInt(response.body().getWallet());
                        tv_wallet.setText("" + wallet_amount);
                    } else {
                        Toast.makeText(WithdrawPoints.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }


    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_wallet = findViewById(R.id.tv_wallet);
        tv_whatsapp = findViewById(R.id.tv_whatsapp);
        tv_video = findViewById(R.id.tv_video);
        tv_transaction_history = findViewById(R.id.tv_transaction_history);
        bt_submit = findViewById(R.id.bt_submit);
        et_point = findViewById(R.id.et_point);

        preferenceManager = new PreferenceManager(this);
        userId = preferenceManager.getIntPreference(ID);
        customDialog = new CustomDialog(this);

        tv_title.setText("Withdraw Funds");
        tv_whatsapp.setText(preferenceManager.getStringPreference(ADMIN_WHATSAPP));

        iv_back.setOnClickListener(this);
        tv_video.setOnClickListener(this);
        tv_transaction_history.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.bt_submit:
                if (et_point.getText().toString().isEmpty()) {
                    et_point.setError("Point Required !");
                    et_point.requestFocus();
                } else if (Integer.parseInt(et_point.getText().toString()) < 100) {
                    et_point.setError("Minimum 100/- point Accepted !");
                    et_point.requestFocus();
                } else if (Integer.parseInt(et_point.getText().toString()) > wallet_amount) {
                    et_point.setError("Insufficient Balance !");
                    et_point.requestFocus();
                } else {
                    withdrawAmountAPI();
                }
                break;

            case R.id.tv_video:
                openLink();
                break;

            case R.id.tv_transaction_history:
                startActivity(new Intent(WithdrawPoints.this, WithdrawHistoryActivity.class));
                break;
        }
    }

    private void openLink() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=QM2-MX1Lz-A"));
        startActivity(intent);
    }

    private void withdrawAmountAPI() {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));
        requestBody.setWithdraw_amount(et_point.getText().toString());

        Call<WithdrawAmountResponseModel> call = Api.getClient().create(Authentication.class).withdrawAmount(requestBody);
        call.enqueue(new Callback<WithdrawAmountResponseModel>() {
            @Override
            public void onResponse(Call<WithdrawAmountResponseModel> call, Response<WithdrawAmountResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        customDialog.showSuccessDialog(response.body().getMessage());
                        getHomeDataAPI();
                    } else {
                        Toast.makeText(WithdrawPoints.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<WithdrawAmountResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }
}