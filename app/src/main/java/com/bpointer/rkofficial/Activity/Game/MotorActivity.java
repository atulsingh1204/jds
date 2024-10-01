package com.bpointer.rkofficial.Activity.Game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

public class MotorActivity extends AppCompatActivity implements View.OnClickListener, ItemClickListener {
   ImageView iv_back;
   CustomDialog customDialog;
   PreferenceManager preferenceManager;
   TextView tv_title, tv_wallet, tv_date, tv_type, tv_motor;
   EditText et_digit, et_point;
   int company_id, userId, game_id, played_points = 0, wallet_amount = 0, dummy_wallet_amount = 0;
   String company_name, game_name, open_time, close_time;
   Button bt_add, bt_submit;
   boolean visibility_open = true, visibility_close = true;
   RecyclerView rv_cart;
   List<CartModel> cartModelList;
   List<String> arrangenumberlist = new ArrayList<>();
   List<NumbersModel> numberModelList = new ArrayList<>();
   List<NumbersModel> numbersModelList = new ArrayList<>();
   List<NumbersModel> tmpnumberModelList = new ArrayList<>();
   CountDownTimer openCountDownTimer, closeCountDownTimer;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_motor);
      
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
                  Toast.makeText(MotorActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
      tv_motor = findViewById(R.id.tv_motor);
      tv_wallet = findViewById(R.id.tv_wallet);
      tv_date = findViewById(R.id.tv_date);
      tv_type = findViewById(R.id.tv_type);
      et_digit = findViewById(R.id.et_digit);
      et_point = findViewById(R.id.et_point);
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
      tv_motor.setText(game_name.toUpperCase());
      
      rv_cart.setLayoutManager(new LinearLayoutManager(this));
      cartModelList = new ArrayList<>();
      clearCart();
      
      et_digit.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
         
         }
         
         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
            String digit = removeDuplicates(et_digit.getText().toString());
            if (!digit.equals(et_digit.getText().toString())) {
               et_digit.setText(digit);
               et_digit.setSelection(et_digit.getText().length());
            }
         }
         
         @Override
         public void afterTextChanged(Editable s) {
         
         }
      });
      
      iv_back.setOnClickListener(this);
      tv_type.setOnClickListener(this);
      bt_add.setOnClickListener(this);
      bt_submit.setOnClickListener(this);
   }
   
   public String removeDuplicates(String str) {
      String str2 = "";
      for (int i = 0; i < str.length(); i++) {
         if (!str2.contains(String.valueOf(str.charAt(i)))) {
            str2 = str2 + String.valueOf(str.charAt(i));
         }
      }
      return str2;
   }
   
   @SuppressLint("NonConstantResourceId")
   @Override
   public void onClick(View v) {
      switch (v.getId()) {
         case R.id.iv_back:
            onBackPressed();
            break;
         
         case R.id.tv_type:
            showTypeDialog();
            break;
         
         case R.id.bt_add:
            hideKeyboard();
            if (visibility_open || visibility_close) {
               if (tv_date.getText().toString().trim().equals("SELECT DATE")) {
                  customDialog.showFailureDialog("Please Select Date");
               } else if (tv_type.getText().toString().trim().equals("SELECT GAME TYPE")) {
                  customDialog.showFailureDialog("Please Select Game Type");
               } else if (et_digit.getText().toString().isEmpty()) {
                  et_digit.setError("Digits Required !");
                  et_digit.requestFocus();
               } else if (et_digit.getText().toString().length() < 4) {
                  et_digit.setError("Minimum 4 Digits Required !");
                  et_digit.requestFocus();
               } else if (et_point.getText().toString().isEmpty()) {
                  et_point.setError("Point Required !");
                  et_point.requestFocus();
               } else if (Integer.parseInt(et_point.getText().toString().trim()) < 10) {
                  et_point.setError("Minimum 10 Rs Play !");
                  et_point.requestFocus();
               } else if (!checkBalance(et_point.getText().toString().trim())) {
                  customDialog.showFailureDialog("Insufficient Balance Check Your Wallet");
               } else {
                  insertGame(tv_date.getText().toString().trim(), tv_type.getText().toString().trim(), et_digit.getText().toString().trim(), et_point.getText().toString().trim());
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
   
   private void insertGame(String date, String type, String digit, String point) {
      if (cartModelList.size() > 0) {
         customDialog.showFailureDialog("Please Submit Game before playing again");
      } else {
         if (type.equals("Open")) {
            type = "1";
         } else {
            type = "2";
         }
         
         for (int i2 = 0; i2 < digit.length(); i2++) {
            numberModelList.add(new NumbersModel("", String.valueOf(digit.charAt(i2)), ""));
         }
         Collections.sort(numberModelList, new Comparator<NumbersModel>() {
            public int compare(NumbersModel numbersModel, NumbersModel numbersModel2) {
               return numbersModel.getNum().compareTo(numbersModel2.getNum());
            }
         });
         if (numberModelList.size() > 0) {
            for (int i3 = 0; i3 < numberModelList.size(); i3++) {
               arrangenumberlist.add(numberModelList.get(i3).getNum());
            }
         }
         if (game_name.equals("SP Motor")) {
            for (int i4 = 0; i4 <= arrangenumberlist.size() - 2; i4++) {
               for (int i5 = 0; i5 < arrangenumberlist.size() - 2; i5++) {
                  for (int i6 = 0; i6 < arrangenumberlist.size() - 2; i6++) {
                     int i7 = i4 + i5;
                     int i8 = i7 + i6 + 2;
                     if (arrangenumberlist.size() > i8) {
                        List<NumbersModel> list = tmpnumberModelList;
                        list.add(new NumbersModel("", "" + arrangenumberlist.get(i4) + "" + arrangenumberlist.get(i7 + 1) + "" + arrangenumberlist.get(i8), ""));
                     }
                  }
               }
            }
            for (int i9 = 0; i9 < tmpnumberModelList.size(); i9++) {
               String valueOf = String.valueOf(tmpnumberModelList.get(i9).getNum());
               String valueOf2 = String.valueOf(valueOf.charAt(0));
               String valueOf3 = String.valueOf(valueOf.charAt(1));
               String valueOf4 = String.valueOf(valueOf.charAt(2));
               if (valueOf2.equals("0")) {
                  List<NumbersModel> list2 = numbersModelList;
                  list2.add(new NumbersModel("", valueOf3 + valueOf4 + valueOf2, ""));
               } else {
                  numbersModelList.add(new NumbersModel("", valueOf, ""));
               }
            }
            Collections.sort(numbersModelList, new Comparator<NumbersModel>() {
               public int compare(NumbersModel numbersModel, NumbersModel numbersModel2) {
                  return numbersModel.getNum().compareTo(numbersModel2.getNum());
               }
            });
         } else {
            for (int i10 = 0; i10 <= arrangenumberlist.size() - 1; i10++) {
               for (int i11 = 0; i11 <= arrangenumberlist.size() - 1; i11++) {
                  int i12 = i10 + i11 + 1;
                  if (arrangenumberlist.size() > i12) {
                     List<NumbersModel> list3 = tmpnumberModelList;
                     list3.add(new NumbersModel("", "" + arrangenumberlist.get(i10) + "" + arrangenumberlist.get(i10) + "" + arrangenumberlist.get(i12), ""));
                  }
               }
            }
            for (int i13 = 0; i13 <= arrangenumberlist.size() - 1; i13++) {
               for (int i14 = 0; i14 <= arrangenumberlist.size() - 1; i14++) {
                  int i15 = i13 + i14 + 1;
                  if (arrangenumberlist.size() > i15) {
                     List<NumbersModel> list4 = tmpnumberModelList;
                     list4.add(new NumbersModel("", "" + arrangenumberlist.get(i13) + "" + arrangenumberlist.get(i15) + "" + arrangenumberlist.get(i15), ""));
                  }
               }
            }
            for (int i16 = 0; i16 < tmpnumberModelList.size(); i16++) {
               String valueOf5 = String.valueOf(tmpnumberModelList.get(i16).getNum());
               String valueOf6 = String.valueOf(valueOf5.charAt(0));
               String valueOf7 = String.valueOf(valueOf5.charAt(1));
               String valueOf8 = String.valueOf(valueOf5.charAt(2));
               if (valueOf6.equals("0") && valueOf7.equals("0")) {
                  List<NumbersModel> list5 = numbersModelList;
                  list5.add(new NumbersModel("", valueOf8 + valueOf7 + valueOf6, ""));
               } else if (valueOf6.equals("0")) {
                  List<NumbersModel> list6 = numbersModelList;
                  list6.add(new NumbersModel("", valueOf7 + valueOf8 + valueOf6, ""));
               } else {
                  numbersModelList.add(new NumbersModel("", valueOf5, ""));
               }
            }
            Collections.sort(numbersModelList, new Comparator<NumbersModel>() {
               public int compare(NumbersModel numbersModel, NumbersModel numbersModel2) {
                  return numbersModel.getNum().compareTo(numbersModel2.getNum());
               }
            });
         }
         if (checkBalance(String.valueOf(numbersModelList.size() * Integer.parseInt(point)))) {
            String newGame = "";
            if (game_name.equals("SP Motor")) {
               newGame = "SP";
            } else {
               newGame = "DP";
            }
            for (int i17 = 0; i17 < numbersModelList.size(); i17++) {
               String current_time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
               CartModel cartModel = new CartModel(userId, company_id, game_id, newGame, type, point, numbersModelList.get(i17).getNum(), changeToServerDate(date), current_time);
               cartModelList.add(cartModel);
            }
            numbersModelList.clear();
            tmpnumberModelList.clear();
            numberModelList.clear();
            arrangenumberlist.clear();
            updateCart();
         } else {
            customDialog.showFailureDialog("Insufficient Balance Check Your Wallet");
         }
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
                  Toast.makeText(MotorActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
      et_digit.setText("");
      et_point.setText("");
      et_digit.requestFocus();
   }
   
   public boolean checkBalance(String str) {
      if (wallet_amount < Integer.parseInt(str)) {
         return false;
      }
      wallet_amount -= Integer.parseInt(str);
      dummy_wallet_amount += Integer.parseInt(str);
      return true;
   }
   
   private void updateDummyWallet(int i) {
      int i2 = dummy_wallet_amount;
      if (i2 != i) {
         int i3 = i2 - i;
         wallet_amount += i3;
         dummy_wallet_amount = i2 - i3;
      }
   }
   
   private void showTypeDialog() {
      Dialog dialog = new Dialog(this, R.style.alert_dialog_light);
      dialog.setContentView(R.layout.dialog_select_game_type);
      dialog.setCancelable(true);
      dialog.setCanceledOnTouchOutside(true);
      dialog.show();
      
      LinearLayout ll_open = dialog.findViewById(R.id.ll_open);
      LinearLayout ll_close = dialog.findViewById(R.id.ll_close);
      
      if (visibility_open) {
         ll_open.setVisibility(View.VISIBLE);
      } else {
         ll_open.setVisibility(View.GONE);
      }
      
      if (visibility_close) {
         ll_close.setVisibility(View.VISIBLE);
      } else {
         ll_close.setVisibility(View.GONE);
      }
      
      ll_open.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            tv_type.setText("Open");
            dialog.dismiss();
         }
      });
      
      ll_close.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            tv_type.setText("Close");
            dialog.dismiss();
         }
      });
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
   
   @Override
   public void itemClick(Object object) {
      int i = (int) object;
      cartModelList.remove(i);
      updateCart();
   }
   
   public void clearCart() {
      cartModelList.clear();
      played_points = 0;
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
                  showMarketCloseDialog("Sorry ! Open type Market is Closed !");
               }
            }.start();
         } else {
            showMarketCloseDialog("Sorry ! Open type Market is Closed !");
         }
         
         long remainCloseTime = closeTime.getTime() - currentTime.getTime();
         if (remainCloseTime > 0) {
            closeCountDownTimer = new CountDownTimer(remainCloseTime, 1000) {
               @Override
               public void onTick(long millisUntilFinished) {
                  printLog("Close", millisUntilFinished);
               }
               
               @Override
               public void onFinish() {
                  showMarketCloseDialog("Sorry ! Market is Closed !");
               }
            }.start();
         } else {
            showMarketCloseDialog("Sorry ! Market is Closed !");
         }
      } else if (currentTime.before(closeTime)) {
         long remainCloseTime = closeTime.getTime() - currentTime.getTime();
         if (remainCloseTime > 0) {
            closeCountDownTimer = new CountDownTimer(remainCloseTime, 1000) {
               @Override
               public void onTick(long millisUntilFinished) {
                  printLog("Close", millisUntilFinished);
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
      if (closeCountDownTimer != null) {
         closeCountDownTimer.cancel();
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
                  Intent i = new Intent(MotorActivity.this, MainActivity.class);
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