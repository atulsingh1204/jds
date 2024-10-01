package com.bpointer.rkofficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpointer.rkofficial.R;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_title;
    ImageView iv_back;
    CardView cv_withdraw_funds, cv_add_funds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
    
        initView();
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        iv_back = findViewById(R.id.iv_back);
        cv_withdraw_funds = findViewById(R.id.cv_withdraw_funds);
        cv_add_funds = findViewById(R.id.cv_add_funds);

        tv_title.setText("Wallets");

        iv_back.setOnClickListener(this);
        cv_withdraw_funds.setOnClickListener(this);
        cv_add_funds.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.cv_withdraw_funds:
                startActivity(new Intent(WalletActivity.this, WithdrawPoints.class));
                break;

            case R.id.cv_add_funds:
                startActivity(new Intent(WalletActivity.this, AddPointActivity.class));
                break;
        }
    }
}