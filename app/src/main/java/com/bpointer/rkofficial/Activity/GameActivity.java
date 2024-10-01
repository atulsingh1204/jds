package com.bpointer.rkofficial.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpointer.rkofficial.Activity.Game.DigitBasedJodiActivity;
import com.bpointer.rkofficial.Activity.Game.DoublePannaActivity;
import com.bpointer.rkofficial.Activity.Game.FullSangamActivity;
import com.bpointer.rkofficial.Activity.Game.HalfSangamActivity;
import com.bpointer.rkofficial.Activity.Game.JodiDigitActivity;
import com.bpointer.rkofficial.Activity.Game.JodiFamilyActivity;
import com.bpointer.rkofficial.Activity.Game.MotorActivity;
import com.bpointer.rkofficial.Activity.Game.OddEvenActivity;
import com.bpointer.rkofficial.Activity.Game.PannaFamilyActivity;
import com.bpointer.rkofficial.Activity.Game.RedBracketActivity;
import com.bpointer.rkofficial.Activity.Game.SingleDigitActivity;
import com.bpointer.rkofficial.Activity.Game.SinglePannaActivity;
import com.bpointer.rkofficial.Activity.Game.SpDpTpActivity;
import com.bpointer.rkofficial.Activity.Game.TriplePannaActivity;
import com.bpointer.rkofficial.Activity.Game.TwoDigitPannaActivity;
import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Model.Response.GetGamesResponseModel.Game;
import com.bpointer.rkofficial.Model.Response.GetGamesResponseModel.GetGamesResponseModel;
import com.bpointer.rkofficial.R;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ImgURL;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
   ImageView iv_back, single_digit, jodi_digit, single_panna, double_panna, triple_panna, odd_even, jodi_family, panna_family, red_bracket, half_sangam, full_sangam, sp_motor, dp_motor, digit_based_jodi, two_digit_panna, sp_dp_tp;
   TextView tv_title;
   int company_id;
   String company_name, open_time, close_time;
   CustomDialog customDialog;
   List<Game> gameList;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_game);
      
      initView();
      
      getGamesAPI();
   }
   
   private void initView() {
      iv_back = findViewById(R.id.iv_back);
      tv_title = findViewById(R.id.tv_title);
      single_digit = findViewById(R.id.single_digit);
      jodi_digit = findViewById(R.id.jodi_digit);
      single_panna = findViewById(R.id.single_panna);
      double_panna = findViewById(R.id.double_panna);
      triple_panna = findViewById(R.id.triple_panna);
      odd_even = findViewById(R.id.odd_even);
      jodi_family = findViewById(R.id.jodi_family);
      panna_family = findViewById(R.id.panna_family);
      red_bracket = findViewById(R.id.red_bracket);
      half_sangam = findViewById(R.id.half_sangam);
      full_sangam = findViewById(R.id.full_sangam);
      sp_motor = findViewById(R.id.sp_motor);
      dp_motor = findViewById(R.id.dp_motor);
      digit_based_jodi = findViewById(R.id.digit_based_jodi);
      two_digit_panna = findViewById(R.id.two_digit_panna);
      sp_dp_tp = findViewById(R.id.sp_dp_tp);
      
      Bundle extras = getIntent().getExtras();
      if (extras != null) {
         company_id = extras.getInt("company_id");
         company_name = extras.getString("company_name");
         open_time = extras.getString("open_time");
         close_time = extras.getString("close_time");
      }
      
      tv_title.setText(company_name.toUpperCase() + " DASHBOARD");
      
      customDialog = new CustomDialog(this);
      
      iv_back.setOnClickListener(this);
      single_digit.setOnClickListener(this);
      jodi_digit.setOnClickListener(this);
      single_panna.setOnClickListener(this);
      double_panna.setOnClickListener(this);
      triple_panna.setOnClickListener(this);
      odd_even.setOnClickListener(this);
      jodi_family.setOnClickListener(this);
      panna_family.setOnClickListener(this);
      red_bracket.setOnClickListener(this);
      half_sangam.setOnClickListener(this);
      full_sangam.setOnClickListener(this);
      sp_motor.setOnClickListener(this);
      dp_motor.setOnClickListener(this);
      digit_based_jodi.setOnClickListener(this);
      two_digit_panna.setOnClickListener(this);
      sp_dp_tp.setOnClickListener(this);

   }
   
   @Override
   public void onBackPressed() {
      super.onBackPressed();
      startActivity(new Intent(GameActivity.this, MainActivity.class));
      finish();
   }
   
   @SuppressLint("NonConstantResourceId")
   @Override
   public void onClick(View v) {
      Intent intent;
      switch (v.getId()) {
         case R.id.iv_back:
            onBackPressed();
            break;
         
         case R.id.single_digit:
            if (checkCloseMarketStatus()) {
               intent = new Intent(this, SingleDigitActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(0).getGameName());
               intent.putExtra("game_id", gameList.get(0).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.jodi_digit:
            if (checkOpenMarketStatus()) {
               intent = new Intent(this, JodiDigitActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(1).getGameName());
               intent.putExtra("game_id", gameList.get(1).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.odd_even:
            if (checkCloseMarketStatus()) {
               intent = new Intent(this, OddEvenActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(2).getGameName());
               intent.putExtra("game_id", gameList.get(2).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         
         case R.id.single_panna:
            if (checkCloseMarketStatus()) {
               intent = new Intent(this, SinglePannaActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(3).getGameName());
               intent.putExtra("game_id", gameList.get(3).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.double_panna:
            if (checkCloseMarketStatus()) {
               intent = new Intent(this, DoublePannaActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(4).getGameName());
               intent.putExtra("game_id", gameList.get(4).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.triple_panna:
            if (checkCloseMarketStatus()) {
               intent = new Intent(this, TriplePannaActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(5).getGameName());
               intent.putExtra("game_id", gameList.get(5).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         
         case R.id.jodi_family:
            if (checkOpenMarketStatus()) {
               intent = new Intent(this, JodiFamilyActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(6).getGameName());
               intent.putExtra("game_id", gameList.get(6).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.panna_family:
            if (checkCloseMarketStatus()) {
               intent = new Intent(this, PannaFamilyActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(7).getGameName());
               intent.putExtra("game_id", gameList.get(7).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.red_bracket:
            if (checkOpenMarketStatus()) {
               intent = new Intent(this, RedBracketActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(8).getGameName());
               intent.putExtra("game_id", gameList.get(8).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.half_sangam:
            if (checkOpenMarketStatus()) {
               intent = new Intent(this, HalfSangamActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(9).getGameName());
               intent.putExtra("game_id", gameList.get(9).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.full_sangam:
            if (checkOpenMarketStatus()) {
               intent = new Intent(this, FullSangamActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(10).getGameName());
               intent.putExtra("game_id", gameList.get(10).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.sp_motor:
            if (checkCloseMarketStatus()) {
               intent = new Intent(this, MotorActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(11).getGameName());
               intent.putExtra("game_id", gameList.get(11).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.dp_motor:
            if (checkCloseMarketStatus()) {
               intent = new Intent(this, MotorActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(12).getGameName());
               intent.putExtra("game_id", gameList.get(12).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.digit_based_jodi:
            if (checkOpenMarketStatus()) {
               intent = new Intent(this, DigitBasedJodiActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(13).getGameName());
               intent.putExtra("game_id", gameList.get(13).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.two_digit_panna:
            if (checkCloseMarketStatus()) {
               intent = new Intent(this, TwoDigitPannaActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(14).getGameName());
               intent.putExtra("game_id", gameList.get(14).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.sp_dp_tp:
            if (checkCloseMarketStatus()) {
               intent = new Intent(this, SpDpTpActivity.class);
               intent.putExtra("company_id", company_id);
               intent.putExtra("company_name", company_name);
               intent.putExtra("open_time", open_time);
               intent.putExtra("close_time", close_time);
               intent.putExtra("game_name", gameList.get(15).getGameName());
               intent.putExtra("game_id", gameList.get(15).getGameId());
               startActivity(intent);
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
      }
   }
   
   private void getGamesAPI() {
      customDialog.showLoader();
      
      Call<GetGamesResponseModel> call = Api.getClient().create(Authentication.class).getGames();
      call.enqueue(new Callback<GetGamesResponseModel>() {
         @Override
         public void onResponse(Call<GetGamesResponseModel> call, Response<GetGamesResponseModel> response) {
            customDialog.closeLoader();
            if (response.body() != null) {
               if (response.body().getStatus().equals("true")) {
                  gameList = new ArrayList<>();
                  gameList = response.body().getGame();
                  
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(0).getGameImage()).into(single_digit);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(1).getGameImage()).into(jodi_digit);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(2).getGameImage()).into(odd_even);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(3).getGameImage()).into(single_panna);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(4).getGameImage()).into(double_panna);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(5).getGameImage()).into(triple_panna);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(6).getGameImage()).into(jodi_family);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(7).getGameImage()).into(panna_family);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(8).getGameImage()).into(red_bracket);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(9).getGameImage()).into(half_sangam);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(10).getGameImage()).into(full_sangam);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(11).getGameImage()).into(sp_motor);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(12).getGameImage()).into(dp_motor);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(13).getGameImage()).into(digit_based_jodi);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(14).getGameImage()).into(two_digit_panna);
                  Glide.with(GameActivity.this).load(ImgURL + gameList.get(15).getGameImage()).into(sp_dp_tp);
               } else {
                  Toast.makeText(GameActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
         }
         
         @Override
         public void onFailure(Call<GetGamesResponseModel> call, Throwable t) {
            customDialog.closeLoader();
            Log.e("LoginResponse", "onFailure: " + t.getMessage());
         }
      });
   }
   
   private boolean checkOpenMarketStatus() {
      Calendar now = Calendar.getInstance();
      int hour = now.get(Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
      int minute = now.get(Calendar.MINUTE);
      int second = now.get(Calendar.SECOND);
      Date currentTime = parseDate(hour + ":" + minute + ":" + second);
      Date openTime = parseDate(open_time);
      
      return currentTime.before(openTime);
   }
   
   private boolean checkCloseMarketStatus() {
      Calendar now = Calendar.getInstance();
      int hour = now.get(Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
      int minute = now.get(Calendar.MINUTE);
      int second = now.get(Calendar.SECOND);
      Date currentTime = parseDate(hour + ":" + minute + ":" + second);
      Date closeTime = parseDate(close_time);
      
      return currentTime.before(closeTime);
   }
   
   private Date parseDate(String date) {
      final String inputFormat = "HH:mm:ss";
      SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.getDefault());
      try {
         return inputParser.parse(date);
      } catch (java.text.ParseException e) {
         return new Date(0);
      }
   }
}