package com.bpointer.rkofficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Common.SessionManager;
import com.bpointer.rkofficial.Fragments.GameRateFragment;
import com.bpointer.rkofficial.Fragments.GenerateMPINFragment;
import com.bpointer.rkofficial.Fragments.HowToPlayFragment;
import com.bpointer.rkofficial.Fragments.NoticeBoardFragment;
import com.bpointer.rkofficial.Fragments.ProfileDetailsFragment;
import com.bpointer.rkofficial.R;

import static com.bpointer.rkofficial.Common.AppConstant.MOBILE;
import static com.bpointer.rkofficial.Common.AppConstant.NAME;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_title, tv_name, tv_mobile;
    ImageView iv_back;
    CardView cv_profile, cv_generate_mpin, cv_how_to_play, cv_game_rate, cv_notice_board, cv_logout;
    PreferenceManager preferenceManager;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    
        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_name = findViewById(R.id.tv_name);
        tv_mobile = findViewById(R.id.tv_mobile);
        cv_profile = findViewById(R.id.cv_profile);
        cv_generate_mpin = findViewById(R.id.cv_generate_mpin);
        cv_how_to_play = findViewById(R.id.cv_how_to_play);
        cv_game_rate = findViewById(R.id.cv_game_rate);
        cv_notice_board = findViewById(R.id.cv_notice_board);
        cv_logout = findViewById(R.id.cv_logout);

        tv_title.setText("Profile");
        preferenceManager = new PreferenceManager(this);
        sessionManager = new SessionManager(this);

        tv_name.setText("Name : " + preferenceManager.getStringPreference(NAME));
        tv_mobile.setText("Mobile No : " + preferenceManager.getStringPreference(MOBILE));

        iv_back.setOnClickListener(this);
        cv_profile.setOnClickListener(this);
        cv_generate_mpin.setOnClickListener(this);
        cv_how_to_play.setOnClickListener(this);
        cv_game_rate.setOnClickListener(this);
        cv_notice_board.setOnClickListener(this);
        cv_logout.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.cv_profile:
                fragment = new ProfileDetailsFragment();
                fragmentTransaction.add(R.id.content, fragment);
                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.commit();
                break;

            case R.id.cv_generate_mpin:
                fragment = new GenerateMPINFragment();
                fragmentTransaction.add(R.id.content, fragment);
                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.commit();
                break;

            case R.id.cv_how_to_play:
                fragment = new HowToPlayFragment();
                fragmentTransaction.add(R.id.content, fragment);
                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.commit();
                break;

            case R.id.cv_game_rate:
                fragment = new GameRateFragment();
                fragmentTransaction.add(R.id.content, fragment);
                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.commit();
                break;

            case R.id.cv_notice_board:
                fragment = new NoticeBoardFragment();
                fragmentTransaction.add(R.id.content, fragment);
                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.commit();
                break;

            case R.id.cv_logout:
                showLogoutDialog();
                break;
        }
    }

    private void showLogoutDialog() {
        preferenceManager.cleasrprefernce();
        sessionManager.setLogin(false);
        Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}