package com.bpointer.rkofficial.Activity.Game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpointer.rkofficial.Activity.MainActivity;
import com.bpointer.rkofficial.Adapter.CartAdapter;
import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Interface.ItemClickListener;
import com.bpointer.rkofficial.Model.CartModel;
import com.bpointer.rkofficial.Model.NumbersModel;
import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.GetSingleDigitResponseModel.GetSingleDigitResponse;
import com.bpointer.rkofficial.Model.Response.PlayGameResponseModel.PlayGameResponseModel;
import com.bpointer.rkofficial.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ID;

public class DigitBasedJodiActivity extends AppCompatActivity implements View.OnClickListener, ItemClickListener {
   ImageView iv_back;
   CustomDialog customDialog;
   PreferenceManager preferenceManager;
   TextView tv_title, tv_wallet, tv_date;
   EditText et_point;
   AutoCompleteTextView actv_left_digit, actv_right_digit;
   int company_id, userId, game_id, played_points = 0, wallet_amount = 0, dummy_wallet_amount = 0;
   String company_name, game_name, open_time, close_time;
   Button bt_add, bt_submit;
   boolean visibility_open = true, visibility_close = true;
   List<CartModel> cartModelList;
   List<NumbersModel> numbersModelList = new ArrayList();
   RecyclerView rv_cart;
   CountDownTimer openCountDownTimer;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_digit_based_jodi);
      
      initView();
      startCountdownTimer();
      getSingleDigitDetails();
   }
   
   private void getSingleDigitDetails() {
      customDialog.showLoader();
      
      RequestBody requestBody = new RequestBody();
      requestBody.setUser_id(String.valueOf(userId));
      requestBody.setCompany_id(String.valueOf(company_id));
      
      Call<GetSingleDigitResponse> call = Api.getClient().create(Authentication.class).getSingleDigitDetails(requestBody);
      call.enqueue(new Callback<GetSingleDigitResponse>() {
         @Override
         public void onResponse(Call<GetSingleDigitResponse> call, Response<GetSingleDigitResponse> response) {
            customDialog.closeLoader();
            if (response.body() != null) {
               if (response.body().getStatus().equals("true")) {
                  wallet_amount = Integer.parseInt(response.body().getWallet());
                  visibility_open = Boolean.parseBoolean(response.body().getCurrentlyOpen());
                  visibility_close = Boolean.parseBoolean(response.body().getCurrentlyClose());
                  
                  tv_date.setText(getDate(response.body().getCurrentDate()));
                  tv_wallet.setText(wallet_amount + " /-");
               } else {
                  Toast.makeText(DigitBasedJodiActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
         }
         
         @Override
         public void onFailure(Call<GetSingleDigitResponse> call, Throwable t) {
            customDialog.closeLoader();
            Log.e("LoginResponse", "onFailure: " + t.getMessage());
         }
      });
   }
   
   private void initView() {
      iv_back = findViewById(R.id.iv_back);
      tv_title = findViewById(R.id.tv_title);
      tv_wallet = findViewById(R.id.tv_wallet);
      tv_date = findViewById(R.id.tv_date);
      et_point = findViewById(R.id.et_point);
      actv_left_digit = findViewById(R.id.actv_left_digit);
      actv_right_digit = findViewById(R.id.actv_right_digit);
      bt_add = findViewById(R.id.bt_add);
      bt_submit = findViewById(R.id.bt_submit);
      rv_cart = findViewById(R.id.rv_cart);
      
      preferenceManager = new PreferenceManager(this);
      userId = preferenceManager.getIntPreference(ID);
      customDialog = new CustomDialog(this);
      
      Bundle extras = getIntent().getExtras();
      if (extras != null) {
         company_id = extras.getInt("company_id");
         company_name = extras.getString("company_name");
         game_name = extras.getString("game_name");
         game_id = extras.getInt("game_id");
         open_time = extras.getString("open_time");
         close_time = extras.getString("close_time");
      }
      
      tv_title.setText(company_name.toUpperCase() + " (" + game_name.toUpperCase() + ")");
      rv_cart.setLayoutManager(new LinearLayoutManager(this));
      cartModelList = new ArrayList<>();
      clearCart();
      
      actv_left_digit.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
         
         }
         
         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!actv_left_digit.getText().toString().equals("")) {
               actv_right_digit.setText("");
            }
         }
         
         @Override
         public void afterTextChanged(Editable s) {
         
         }
      });
      
      actv_right_digit.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
         
         }
         
         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!actv_right_digit.getText().toString().equals("")) {
               actv_left_digit.setText("");
            }
         }
         
         @Override
         public void afterTextChanged(Editable s) {
         
         }
      });
      
      iv_back.setOnClickListener(this);
      bt_add.setOnClickListener(this);
      bt_submit.setOnClickListener(this);
   }
   
   @SuppressLint("NonConstantResourceId")
   @Override
   public void onClick(View v) {
      switch (v.getId()) {
         case R.id.iv_back:
            onBackPressed();
            break;
         
         case R.id.bt_add:
            hideKeyboard();
            if (visibility_open) {
               if (tv_date.getText().toString().trim().equals("SELECT DATE")) {
                  customDialog.showFailureDialog("Please Select Game Play Date!");
               } else if (actv_left_digit.getText().toString().isEmpty() && actv_right_digit.getText().toString().isEmpty()) {
                  customDialog.showFailureDialog("Any One Digit Required !");
               } else if (et_point.getText().toString().isEmpty()) {
                  et_point.setError("Point Required !");
                  et_point.requestFocus();
               } else if (Integer.parseInt(et_point.getText().toString().trim()) < 10) {
                  et_point.setError("Minimum 10 Rs Play !");
                  et_point.requestFocus();
               } else {
                  insertGame(tv_date.getText().toString().trim(), et_point.getText().toString().trim());
               }
            } else {
               customDialog.showFailureDialog("Sorry ! Market is Closed !");
            }
            break;
         
         case R.id.bt_submit:
            hideKeyboard();
            if (played_points == 0) {
               customDialog.showFailureDialog("Please Play Games !");
            } else {
               playGameAPI();
            }
            break;
      }
   }
   
   private void insertGame(String date, String point) {
      if (cartModelList.size() > 0) {
         customDialog.showFailureDialog("Please Submit Game before playing again");
      } else {
         for (int i = 0; i < 10; i++) {
            if (actv_left_digit.getText().toString().equals("")) {
               List<NumbersModel> list = numbersModelList;
               list.add(new NumbersModel("", "" + i + actv_right_digit.getText().toString(), ""));
            } else {
               List<NumbersModel> list2 = numbersModelList;
               list2.add(new NumbersModel("", "" + actv_left_digit.getText().toString() + i, ""));
            }
         }
         if (checkBalance(String.valueOf(numbersModelList.size() * Integer.parseInt(point)))) {
            for (int i2 = 0; i2 < numbersModelList.size(); i2++) {
               String current_time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
               CartModel cartModel = new CartModel(userId, company_id, game_id, game_name, "2", point, numbersModelList.get(i2).getNum(), changeToServerDate(date), current_time);
               cartModelList.add(cartModel);
            }
            numbersModelList.clear();
            updateCart();
         } else {
            customDialog.showFailureDialog("Insufficient Balance Check Your Wallet");
         }
      }
   }
   
   public boolean checkBalance(String str) {
      if (wallet_amount < Integer.parseInt(str)) {
         return false;
      }
      wallet_amount -= Integer.parseInt(str);
      dummy_wallet_amount += Integer.parseInt(str);
      return true;
   }
   
   private void playGameAPI() {
      customDialog.showLoader();
      
      String current_time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
      for (int i = 0; i < cartModelList.size(); i++) {
         cartModelList.get(i).setPlay_time(current_time);
      }
      
      RequestBody requestBody = new RequestBody();
      requestBody.setPlays(cartModelList);
      
      Call<PlayGameResponseModel> call = Api.getClient().create(Authentication.class).playGame(requestBody);
      call.enqueue(new Callback<PlayGameResponseModel>() {
         @Override
         public void onResponse(Call<PlayGameResponseModel> call, Response<PlayGameResponseModel> response) {
            customDialog.closeLoader();
            if (response.body() != null) {
               if (response.body().getStatus().equals("true")) {
                  cartModelList.clear();
                  getSingleDigitDetails();
                  updateCart();
                  clearCart();
                  customDialog.showSuccessDialog(response.body().getMessage());
               } else {
                  Toast.makeText(DigitBasedJodiActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
         }
         
         @Override
         public void onFailure(Call<PlayGameResponseModel> call, Throwable t) {
            customDialog.closeLoader();
            Log.e("Response", "onFailure: " + t.getMessage());
         }
      });
      
   }
   
   @SuppressLint("SetTextI18n")
   private void updateCart() {
      CartAdapter cartAdapter = new CartAdapter(this, cartModelList, this);
      rv_cart.setAdapter(cartAdapter);
      played_points = 0;
      for (CartModel model : cartModelList) {
         played_points += Integer.parseInt(model.getPoint());
      }
      
      if (played_points != 0) {
         bt_submit.setText("SUBMIT BID (" + played_points + ")");
      } else {
         bt_submit.setText("SUBMIT BID");
      }
      
      updateDummyWallet(played_points);
      actv_left_digit.setText("");
      actv_right_digit.setText("");
      et_point.setText("");
      actv_left_digit.requestFocus();
   }
   
   private void updateDummyWallet(int i) {
      int i2 = dummy_wallet_amount;
      if (i2 != i) {
         int i3 = i2 - i;
         wallet_amount += i3;
         dummy_wallet_amount = i2 - i3;
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
   
   
   @SuppressLint("SimpleDateFormat")
   private String getDate(String date) {
      try {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         Date dateObj = sdf.parse(date);
         Log.e("TAG", "getDate: " + date + " :- " + new SimpleDateFormat("dd-MM-yyyy").format(dateObj));
         return (new SimpleDateFormat("dd-MM-yyyy").format(dateObj));
      } catch (ParseException e) {
         e.printStackTrace();
         return null;
      }
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
   
   public void clearCart() {
      cartModelList.clear();
      played_points = 0;
   }
   
   @Override
   public void itemClick(Object object) {
      int i = (int) object;
      cartModelList.remove(i);
      updateCart();
   }
   
   public void startCountdownTimer() {
      Calendar now = Calendar.getInstance();
      int hour = now.get(Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
      int minute = now.get(Calendar.MINUTE);
      int second = now.get(Calendar.SECOND);
      Date currentTime = parseDate(hour + ":" + minute + ":" + second);
      Date closeTime = parseDate(close_time);
      Date openTime = parseDate(open_time);
      
      if (currentTime.before(openTime)) {
         long remainOpenTime = openTime.getTime() - currentTime.getTime();
         if (remainOpenTime > 0) {
            openCountDownTimer = new CountDownTimer(remainOpenTime, 1000) {
               @Override
               public void onTick(long millisUntilFinished) {
                  printLog("Open", millisUntilFinished);
               }
               
               @Override
               public void onFinish() {
                  showMarketCloseDialog("Sorry ! Market is Closed !");
               }
            }.start();
         } else {
            showMarketCloseDialog("Sorry ! Market is Closed !");
         }
      } else {
         showMarketCloseDialog("Sorry ! Market is Closed !");
      }
   }
   
   private void printLog(String string, long millisUntilFinished) {
      // Used for formatting digit to be in 2 digits only
      NumberFormat f = new DecimalFormat("00");
      long hour = (millisUntilFinished / 3600000) % 24;
      long min = (millisUntilFinished / 60000) % 60;
      long sec = (millisUntilFinished / 1000) % 60;
      Log.w(string, "onTick: " + f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
   }
   
   @Override
   protected void onDestroy() {
      super.onDestroy();
      if (openCountDownTimer != null) {
         openCountDownTimer.cancel();
      }
   }
   
   private void showMarketCloseDialog(String msg) {
      SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
      sweetAlertDialog.setCancelable(false);
      sweetAlertDialog.setTitleText(msg)
            .setConfirmText("OK")
            .setConfirmButtonBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
               @Override
               public void onClick(SweetAlertDialog sDialog) {
                  sDialog.dismissWithAnimation();
                  Intent i = new Intent(DigitBasedJodiActivity.this, MainActivity.class);
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  startActivity(i);
               }
            })
            .show();
   }
   
   private Date parseDate(String date) {
      final String inputFormat = "HH:mm:ss";
      SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
      try {
         return inputParser.parse(date);
      } catch (java.text.ParseException e) {
         return new Date(0);
      }
   }
}