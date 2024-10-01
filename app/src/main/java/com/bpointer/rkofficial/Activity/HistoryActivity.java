package com.bpointer.rkofficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpointer.rkofficial.R;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_title;
    CardView cv_bid_history, cv_deposit_history, cv_withdraw_history,cv_winning_history;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    
        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        cv_bid_history = findViewById(R.id.cv_bid_history);
        cv_deposit_history = findViewById(R.id.cv_deposit_history);
        cv_withdraw_history = findViewById(R.id.cv_withdraw_history);
        cv_winning_history = findViewById(R.id.cv_winning_history);

        tv_title.setText("History");

        iv_back.setOnClickListener(this);
        cv_bid_history.setOnClickListener(this);
        cv_deposit_history.setOnClickListener(this);
        cv_withdraw_history.setOnClickListener(this);
        cv_winning_history.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.cv_bid_history:
                startActivity(new Intent(HistoryActivity.this, GameHistoryActivity.class));
                break;

            case R.id.cv_withdraw_history:
                startActivity(new Intent(HistoryActivity.this, WithdrawHistoryActivity.class));
                break;

            case R.id.cv_deposit_history:
                startActivity(new Intent(HistoryActivity.this, DepositHistoryActivity.class));
                break;

            case R.id.cv_winning_history:
                startActivity(new Intent(HistoryActivity.this,WinHistoryActivity.class));
                break;
        }
    }
}