package com.bpointer.rkofficial.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpointer.rkofficial.Adapter.NotificationAdapter;
import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.AddFundResponseModel.AddFundResponseModel;
import com.bpointer.rkofficial.Model.Response.GetActiveUpiModel;
import com.bpointer.rkofficial.Model.Response.HomeResponseModel.HomeResponseModel;
import com.bpointer.rkofficial.Model.Response.NotificationResponseModel.NotificationResponseModel;
import com.bpointer.rkofficial.R;
import com.gpfreetech.IndiUpi.IndiUpi;
import com.gpfreetech.IndiUpi.entity.TransactionResponse;
import com.gpfreetech.IndiUpi.listener.PaymentStatusListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_WHATSAPP;
import static com.bpointer.rkofficial.Common.AppConstant.ID;
import static com.bpointer.rkofficial.Common.AppConstant.UPI_ID;

public class AddPointActivity extends AppCompatActivity implements View.OnClickListener, PaymentStatusListener {

    EditText et_point;
    Button bt_submit;
    TextView tv_title, tv_whatsapp, tv_wallet;
    PreferenceManager preferenceManager;
    int userId, wallet_amount = 0;
    ImageView iv_back;
    CustomDialog customDialog;
    String finalAmount;
    String upi_id, display_name = "R K Group";
   // private TextView tv_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
    
        initView();

        // this is just for testing purpose

        getHomeDataAPI();
        getActiveUpiAPI();
    }

    private void getHomeDataAPI() {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));

        Call<HomeResponseModel> call = Api.getClient().create(Authentication.class).getHomeData(requestBody);
        call.enqueue(new Callback<HomeResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<HomeResponseModel> call, @NonNull Response<HomeResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        wallet_amount = Integer.parseInt(response.body().getWallet());
                        tv_wallet.setText("" + wallet_amount);
                    } else {
                        Toast.makeText(AddPointActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        bt_submit = findViewById(R.id.bt_submit);
        et_point = findViewById(R.id.et_point);
        //tv_response = findViewById(R.id.tv_response);


        preferenceManager = new PreferenceManager(this);
        userId = preferenceManager.getIntPreference(ID);
        customDialog = new CustomDialog(this);

        tv_title.setText("Add Funds");
        tv_whatsapp.setText(preferenceManager.getStringPreference(ADMIN_WHATSAPP));

        iv_back.setOnClickListener(this);
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
                hideKeyboard();
                if (et_point.getText().toString().isEmpty()) {
                    et_point.setError("Point Required !");
                    et_point.requestFocus();
                } else if (Integer.parseInt(et_point.getText().toString()) < 100) {
                    et_point.setError("Minimum 100/- point Accepted !");
                    et_point.requestFocus();
                } else {
                    paymentIndi(Integer.parseInt(et_point.getText().toString().trim()));
                }
                break;
        }
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void paymentIndi(int amt) {
        if(TextUtils.isEmpty(upi_id))
            upi_id=UPI_ID;

        if(TextUtils.isEmpty(display_name))
            upi_id= "R K Group";

        String transactionId = "TID" + System.currentTimeMillis();
        String desc = "Add to wallet";
        double amount = amt * 1.00;
        finalAmount = String.valueOf(amount);

        IndiUpi indiUpi = new IndiUpi.Builder()
                .with(this)
                .setPayeeVpa(upi_id)
                .setAmount(finalAmount)
                .setPayeeName(display_name)
                .setDescription(desc)
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionId)
                //internal parameter automatically add in URL same as above UPI request
                .build();

        indiUpi.pay("Pay With");
        indiUpi.setPaymentStatusListener(this);

    }

    @Override
    public void onTransactionCompleted(TransactionResponse transactionResponse) {
        // Transaction Completed
        Log.e("TransactionResponse", transactionResponse.toString());
        //tv_response.setText("onTransactionCompleted--->"+transactionResponse);
    }

    @Override
    public void onTransactionSuccess(TransactionResponse transactionResponse) {
        // Payment Success
        Log.e("AddPointActivity", "transactionResponse--->"+transactionResponse);
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();

        //tv_response.setText("onTransactionSuccess--->"+transactionResponse);
        addFundAPI(finalAmount, transactionResponse.getTransactionId());
    }

    @Override
    public void onTransactionSubmitted() {
        // Payment Pending
        Toast.makeText(this, "Payment Pending Or Submitted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionFailed() {
        // Payment Failed
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionCancelled() {
        // Payment Process Cancelled by User
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
    }

    private void addFundAPI(String point, String txnumber) {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));
        requestBody.setDeposit_amount(point);
        requestBody.setTransaction_number(txnumber);

        Call<AddFundResponseModel> call = Api.getClient().create(Authentication.class).addFund(requestBody);
        call.enqueue(new Callback<AddFundResponseModel>() {
            @Override
            public void onResponse(Call<AddFundResponseModel> call, Response<AddFundResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        customDialog.showSuccessDialog(response.body().getMessage());
                        getHomeDataAPI();
                    } else {
                        Toast.makeText(AddPointActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddFundResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }



    private void getActiveUpiAPI() {


        Call<GetActiveUpiModel> call = Api.getClient().create(Authentication.class).getActiveUpiId();
        call.enqueue(new Callback<GetActiveUpiModel>() {
            @Override
            public void onResponse(Call<GetActiveUpiModel> call, Response<GetActiveUpiModel> response) {
                customDialog.closeLoader();
                if (response.body() != null  ) {
                    GetActiveUpiModel result = response.body();
                    if (result.isStatus() && result.getData() != null && result.getData().getPaymentOption() != null) {
                       upi_id = result.getData().getPaymentOption().getPaymentId();
                       display_name = result.getData().getPaymentOption().getPayeeName();
                    } else {
                       // Toast.makeText(NotificationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetActiveUpiModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });

    }

}