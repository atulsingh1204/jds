package com.bpointer.rkofficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpointer.rkofficial.Adapter.NotificationAdapter;
import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Model.Response.NotificationResponseModel.NotificationResponseModel;
import com.bpointer.rkofficial.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_back;
    TextView tv_title;
    RecyclerView rv_notification;
    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    
        initView();

        getNotificationAPI();
    }

    private void getNotificationAPI() {
        customDialog.showLoader();

        Call<NotificationResponseModel> call = Api.getClient().create(Authentication.class).getNotification();
        call.enqueue(new Callback<NotificationResponseModel>() {
            @Override
            public void onResponse(Call<NotificationResponseModel> call, Response<NotificationResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this, response.body().getData());
                        rv_notification.setAdapter(notificationAdapter);
                    } else {
                        Toast.makeText(NotificationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });

    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        rv_notification = findViewById(R.id.rv_notification);

        tv_title.setText("Notification");

        customDialog = new CustomDialog(this);
        rv_notification.setLayoutManager(new LinearLayoutManager(this));

        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            onBackPressed();
        }
    }
}