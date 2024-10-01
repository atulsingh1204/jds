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
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ID;

public class RedBracketActivity extends AppCompatActivity implements View.OnClickListener, ItemClickListener {
   ImageView iv_back;
   CustomDialog customDialog;
   PreferenceManager preferenceManager;
   TextView tv_title, tv_wallet, tv_date;
   CheckBox cb_all;
   EditText et_point;
   AutoCompleteTextView actv_digit;
   LinearLayout ll_digit;
   int company_id, userId, game_id, counter = 0, played_points = 0, wallet_amount = 0, dummy_wallet_amount = 0;
   String company_name, game_name, open_time, close_time;
   Button bt_add, bt_submit;
   RecyclerView rv_cart;
   List<CartModel> cartModelList;
   List<Object> numbers = new ArrayList();
   List<NumbersModel> numbersModelList = new ArrayList();
   List<String> jodiList = new ArrayList<>();
   boolean visibility_open = true, visibility_close = true;
   CountDownTimer openCountDownTimer;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_red_bracket);
      
      addJodi();
      getAllNumbers();
      initView();
      startCountdownTimer();
      getSingleDigitDetails();
   }
   
   private void getAllNumbers() {
      for (int i = 0; i < 20; i++) {
         if (i < 10) {
            List<NumbersModel> list = numbersModelList;
            String valueOf = String.valueOf(i);
            list.add(new NumbersModel(valueOf, "" + i + i, "s"));
         } else {
            List<NumbersModel> list2 = numbersModelList;
            String valueOf2 = String.valueOf(i);
            list2.add(new NumbersModel(valueOf2, "" + counter + jodiList.get(counter), "s"));
            counter = counter + 1;
            if (i == 19) {
               counter = 0;
            }
         }
      }
      arrangeNumbers(numbersModelList);
   }
   
   private void arrangeNumbers(List<NumbersModel> list) {
      Collections.sort(list, new Comparator<NumbersModel>() {
         public int compare(NumbersModel numbersModel, NumbersModel numbersModel2) {
            return numbersModel.getNum().compareTo(numbersModel2.getNum());
         }
      });
      for (int i = 0; i < list.size(); i++) {
         numbers.add(list.get(i).getNum());
      }
   }
   
   private void addJodi() {
      jodiList.clear();
      for (int i = 0; i < 10; i++) {
         if (i >= 5) {
            jodiList.add(String.valueOf(i - 5));
         } else {
            jodiList.add(String.valueOf(i + 5));
         }
      }
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
                  Toast.makeText(RedBracketActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
      cb_all = findViewById(R.id.cb_all);
      ll_digit = findViewById(R.id.ll_digit);
      et_point = findViewById(R.id.et_point);
      bt_add = findViewById(R.id.bt_add);
      bt_submit = findViewById(R.id.bt_submit);
      actv_digit = findViewById(R.id.actv_digit);
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
      
      actv_digit.setAdapter(new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, numbers));
      rv_cart.setLayoutManager(new LinearLayoutManager(this));
      cartModelList = new ArrayList<>();
      clearCart();
      
      cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
               ll_digit.setVisibility(View.GONE);
            } else {
               ll_digit.setVisibility(View.VISIBLE);
            }
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
                  customDialog.showFailureDialog("Please Select Date");
               } else if (et_point.getText().toString().isEmpty()) {
                  et_point.setError("Point Required !");
                  et_point.requestFocus();
               } else if (Integer.parseInt(et_point.getText().toString().trim()) < 10) {
                  et_point.setError("Minimum 10 Rs Play !");
                  et_point.requestFocus();
               } else if (cb_all.isChecked()) {
                  insertAllGame(tv_date.getText().toString().trim(), et_point.getText().toString().trim());
               } else if (actv_digit.getText().toString().isEmpty()) {
                  actv_digit.setError("Bracket Digits Required ! !");
                  actv_digit.requestFocus();
               } else if (!isDigitExist(actv_digit.getText().toString().trim())) {
                  actv_digit.setError("Invalid Digit! Please Correct It!");
                  actv_digit.requestFocus();
               } else if (!checkBalance(et_point.getText().toString().trim())) {
                  customDialog.showFailureDialog("Insufficient Balance Check Your Wallet");
               } else {
                  insertGame(tv_date.getText().toString().trim(), actv_digit.getText().toString().trim(), et_point.getText().toString().trim());
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
                  Toast.makeText(RedBracketActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
   
   private void insertAllGame(String date, String point) {
      if (cartModelList.size() > 0) {
         customDialog.showFailureDialog("Please Submit Game before playing again");
      } else {
         if (checkBalance(String.valueOf(Integer.parseInt(point) * numbersModelList.size()))) {
            for (int i = 0; i < numbersModelList.size(); i++) {
               String current_time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
               CartModel cartModel = new CartModel(userId, company_id, game_id, game_name, "2", point, numbersModelList.get(i).getNum(), changeToServerDate(date), current_time);
               cartModelList.add(cartModel);
            }
            updateCart();
         } else {
            customDialog.showFailureDialog("Insufficient Balance Check Your Wallet");
         }
      }
   }
   
   private void insertGame(String date, String digit, String point) {
      boolean alreadyPlayed = false;
      for (CartModel model : cartModelList) {
         if (digit.equals(model.getNumber())) {
            alreadyPlayed = true;
            break;
         }
      }
      if (alreadyPlayed) {
         customDialog.showFailureDialog("This Bracket already played!");
      } else {
         String current_time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
         CartModel cartModel = new CartModel(userId, company_id, game_id, game_name, "2", point, digit, changeToServerDate(date), current_time);
         cartModelList.add(cartModel);
         updateCart();
      }
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
      actv_digit.setText("");
      et_point.setText("");
      actv_digit.requestFocus();
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
   
   private boolean isDigitExist(String digit) {
      for (int i = 0; i < numbersModelList.size(); i++) {
         if (numbersModelList.get(i).getNum().equals(digit)) {
            return true;
         }
      }
      return false;
   }
   
   public boolean checkBalance(String str) {
      if (wallet_amount < Integer.parseInt(str)) {
         return false;
      }
      wallet_amount -= Integer.parseInt(str);
      dummy_wallet_amount += Integer.parseInt(str);
      return true;
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
                  Intent i = new Intent(RedBracketActivity.this, MainActivity.class);
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