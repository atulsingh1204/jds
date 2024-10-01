package com.bpointer.rkofficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpointer.rkofficial.Adapter.WithdrawHistoryAdapter;
import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.WithdrawHistoryResponseModel.WithdrawHistoryResponseModel;
import com.bpointer.rkofficial.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ID;

public class WithdrawHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rv_history;
    PreferenceManager preferenceManager;
    int userId;
    ImageView iv_back;
    TextView tv_title, tv_date;
    CustomDialog customDialog;
    Button bt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_history);
    
        initView();
        getWithdrawHistoryAPI("");
    }

    private void getWithdrawHistoryAPI(String date) {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));

        if (date.isEmpty()) {
            requestBody.setCreated_at(date);
        } else {
            requestBody.setCreated_at(changeToServerDate(date));
        }

        Call<WithdrawHistoryResponseModel> call = Api.getClient().create(Authentication.class).getWithdrawHistory(requestBody);
        call.enqueue(new Callback<WithdrawHistoryResponseModel>() {
            @Override
            public void onResponse(Call<WithdrawHistoryResponseModel> call, Response<WithdrawHistoryResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {

                        WithdrawHistoryAdapter withdrawHistoryAdapter = new WithdrawHistoryAdapter(WithdrawHistoryActivity.this,response.body().getWithdrawHistory());
                        rv_history.setAdapter(withdrawHistoryAdapter);
                    } else {
                        Toast.makeText(WithdrawHistoryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        WithdrawHistoryAdapter withdrawHistoryAdapter = new WithdrawHistoryAdapter(WithdrawHistoryActivity.this,response.body().getWithdrawHistory());
                        rv_history.setAdapter(withdrawHistoryAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<WithdrawHistoryResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private String changeToServerDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date dateObj = sdf.parse(date);
            Log.e("TAG", "getDate: " + date + " :- " + new SimpleDateFormat("yyyy-MM-dd").format(dateObj));
            return (new SimpleDateFormat("yyyy-MM-dd").format(dateObj));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        rv_history = findViewById(R.id.rv_history);
        tv_date = findViewById(R.id.tv_date);
        bt_search = findViewById(R.id.bt_search);

        preferenceManager = new PreferenceManager(this);
        userId = preferenceManager.getIntPreference(ID);
        customDialog = new CustomDialog(this);

        tv_title.setText("Withdraw History");

        rv_history.setLayoutManager(new LinearLayoutManager(this));
        iv_back.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        bt_search.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.tv_date:
                openCalender();
                break;

            case R.id.bt_search:
                if (tv_date.getText().toString().equals("Select Date")){
                    customDialog.showFailureDialog("Please Select Date");
                }else {
                    getWithdrawHistoryAPI(tv_date.getText().toString());
                }
                break;
        }
    }

    public void openCalender() {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}