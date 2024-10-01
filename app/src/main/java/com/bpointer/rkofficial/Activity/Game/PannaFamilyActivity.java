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
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.bpointer.rkofficial.Model.FamilyNumberModel;
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

public class PannaFamilyActivity extends AppCompatActivity implements View.OnClickListener, ItemClickListener {
   ImageView iv_back;
   CustomDialog customDialog;
   PreferenceManager preferenceManager;
   TextView tv_title, tv_wallet, tv_date, tv_type;
   EditText et_point;
   AutoCompleteTextView actv_panna;
   int company_id, userId, game_id, played_points = 0, wallet_amount = 0, dummy_wallet_amount = 0;
   String company_name, game_name, open_time,close_time;
   Button bt_add, bt_submit;
   boolean visibility_open = true, visibility_close = true;
   List<CartModel> cartModelList;
   List<String> jodiList = new ArrayList<>();
   List<FamilyNumberModel> familyNumberModelList = new ArrayList<>();
   List<Object> numbers = new ArrayList<>();
   List<NumbersModel> numbersModelList = new ArrayList<>();
   List<NumbersModel> getnumberlist = new ArrayList<>();
   List<NumbersModel> tmp_number_list = new ArrayList<>();
   RecyclerView rv_cart;
   CountDownTimer closeCountDownTimer, openCountDownTimer;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_panna_family);
      
      addJodi();
      getAllNumbers();
      initView();
      startCountdownTimer();
      getSingleDigitDetails();
   }
   
   private void getAllNumbers() {
      this.numbersModelList.clear();
      for (int i = 100; i < 1000; i++) {
         String str = "" + i + "";
         String valueOf = String.valueOf(str.charAt(0));
         String valueOf2 = String.valueOf(str.charAt(1));
         String valueOf3 = String.valueOf(str.charAt(2));
         if (Integer.parseInt(valueOf) < Integer.parseInt(valueOf2) && Integer.parseInt(valueOf2) < Integer.parseInt(valueOf3)) {
            this.numbersModelList.add(new NumbersModel(String.valueOf(i), String.valueOf(i), "s"));
         } else if (Integer.parseInt(valueOf) < Integer.parseInt(valueOf2) && valueOf3.equals("0")) {
            this.numbersModelList.add(new NumbersModel(String.valueOf(i), String.valueOf(i), "s"));
         } else if (valueOf.equals(valueOf2) || valueOf2.equals(valueOf3)) {
            if (Integer.parseInt(valueOf) < Integer.parseInt(valueOf2) || Integer.parseInt(valueOf2) < Integer.parseInt(valueOf3)) {
               this.numbersModelList.add(new NumbersModel(String.valueOf(i), String.valueOf(i), "s"));
            } else if (valueOf2.equals("0") && valueOf3.equals("0")) {
               this.numbersModelList.add(new NumbersModel(String.valueOf(i), String.valueOf(i), "s"));
            } else if (valueOf.equals(valueOf2) && valueOf3.equals("0")) {
               this.numbersModelList.add(new NumbersModel(String.valueOf(i), String.valueOf(i), "s"));
            }
         } else if (valueOf.equals(valueOf2) && valueOf.equals(valueOf3)) {
            this.numbersModelList.add(new NumbersModel(String.valueOf(i), String.valueOf(i), "s"));
         }
      }
      numbersModelList.add(new NumbersModel("000", "000", "s"));
      numbersModelList.add(new NumbersModel("111", "111", "s"));
      numbersModelList.add(new NumbersModel("222", "222", "s"));
      numbersModelList.add(new NumbersModel("333", "333", "s"));
      numbersModelList.add(new NumbersModel("444", "444", "s"));
      numbersModelList.add(new NumbersModel("555", "555", "s"));
      numbersModelList.add(new NumbersModel("666", "666", "s"));
      numbersModelList.add(new NumbersModel("777", "777", "s"));
      numbersModelList.add(new NumbersModel("888", "888", "s"));
      numbersModelList.add(new NumbersModel("999", "999", "s"));
      
      addNumberList(this.numbersModelList);
   }
   
   private void addNumberList(List<NumbersModel> list) {
      Collections.sort(list, new Comparator<NumbersModel>() {
         public int compare(NumbersModel numbersModel, NumbersModel numbersModel2) {
            return numbersModel.getNum().compareTo(numbersModel2.getNum());
         }
      });
      if (list.size() > 0) {
         for (int i = 0; i < list.size(); i++) {
            this.numbers.add(list.get(i).getNum());
         }
      }
   }
   
   private void addJodi() {
      this.jodiList.clear();
      for (int i = 0; i < 10; i++) {
         if (i >= 5) {
            this.jodiList.add(String.valueOf(i - 5));
         } else {
            this.jodiList.add(String.valueOf(i + 5));
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
                  Toast.makeText(PannaFamilyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
      tv_type = findViewById(R.id.tv_type);
      tv_date = findViewById(R.id.tv_date);
      et_point = findViewById(R.id.et_point);
      actv_panna = findViewById(R.id.actv_panna);
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
      
      actv_panna.setAdapter(new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, numbers));
      rv_cart.setLayoutManager(new LinearLayoutManager(this));
      cartModelList = new ArrayList<>();
      clearCart();
      
      iv_back.setOnClickListener(this);
      tv_type.setOnClickListener(this);
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
               } else if (actv_panna.getText().toString().isEmpty()) {
                  actv_panna.setError("Panna Family Required ! !");
                  actv_panna.requestFocus();
               } else if (et_point.getText().toString().isEmpty()) {
                  et_point.setError("Point Required !");
                  et_point.requestFocus();
               } else if (Integer.parseInt(et_point.getText().toString().trim()) < 5) {
                  et_point.setError("Minimum 5 Rs Play !");
                  et_point.requestFocus();
               } else if (!isDigitExist(actv_panna.getText().toString().trim())) {
                  actv_panna.setError("Invalid Jodi Digit !");
                  actv_panna.requestFocus();
               } else if (!checkBalance(et_point.getText().toString().trim())) {
                  customDialog.showFailureDialog("Insufficient Balance Check Your Wallet");
               } else {
                  insertGame(tv_date.getText().toString().trim(), tv_type.getText().toString().trim(), actv_panna.getText().toString().trim(), et_point.getText().toString().trim());
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
                  Toast.makeText(PannaFamilyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
   
   private void insertGame(String date, String type, String digit, String point) {
      if (cartModelList.size() > 0) {
         customDialog.showFailureDialog("Please Submit Game before playing again");
      } else {
         String str6 = digit; //enter 3 digit
         addJodi();   //
         this.getnumberlist.clear();
         this.tmp_number_list.clear();
         String valueOf = String.valueOf(str6.charAt(0));
         String valueOf2 = String.valueOf(str6.charAt(1));
         String valueOf3 = String.valueOf(str6.charAt(2));
         int i2 = 0;
         while (i2 < 8) {
            if (i2 == 0) {
               List<NumbersModel> list = this.getnumberlist;
               list.add(new NumbersModel("", valueOf + valueOf2 + valueOf3, ""));
            } else if (i2 == 1) {
               List<NumbersModel> list2 = this.getnumberlist;
               list2.add(new NumbersModel("", valueOf + valueOf2 + this.jodiList.get(Integer.parseInt(valueOf3)), ""));
            } else if (i2 == 2) {
               List<NumbersModel> list3 = this.getnumberlist;
               list3.add(new NumbersModel("", valueOf + this.jodiList.get(Integer.parseInt(valueOf2)) + this.jodiList.get(Integer.parseInt(valueOf3)), ""));
            } else if (i2 == 3) {
               List<NumbersModel> list4 = this.getnumberlist;
               list4.add(new NumbersModel("", this.jodiList.get(Integer.parseInt(valueOf)) + this.jodiList.get(Integer.parseInt(valueOf2)) + this.jodiList.get(Integer.parseInt(valueOf3)), ""));
               if (valueOf.equals(valueOf2)) {
                  if (!valueOf.equals(valueOf3)) {
                  }
               }
            } else if (i2 == 4) {
               List<NumbersModel> list5 = this.getnumberlist;
               list5.add(new NumbersModel("", this.jodiList.get(Integer.parseInt(valueOf)) + valueOf2 + valueOf3, ""));
            } else if (i2 == 5) {
               List<NumbersModel> list6 = this.getnumberlist;
               list6.add(new NumbersModel("", this.jodiList.get(Integer.parseInt(valueOf)) + this.jodiList.get(Integer.parseInt(valueOf2)) + valueOf3, ""));
               if (!valueOf.equals(valueOf2) && !valueOf2.equals(valueOf3)) {
               }
            } else if (i2 == 6) {
               List<NumbersModel> list7 = this.getnumberlist;
               list7.add(new NumbersModel("", valueOf + this.jodiList.get(Integer.parseInt(valueOf2)) + valueOf3, ""));
            } else if (i2 == 7) {
               List<NumbersModel> list8 = this.getnumberlist;
               list8.add(new NumbersModel("", this.jodiList.get(Integer.parseInt(valueOf)) + valueOf2 + this.jodiList.get(Integer.parseInt(valueOf3)), ""));
            } else {
               i2 = 9;
            }
            i2++;
         }
         
         for (int i3 = 0; i3 < this.getnumberlist.size(); i3++) {
            this.tmp_number_list.clear();
            for (int i4 = 0; i4 < 3; i4++) {
               List<NumbersModel> list9 = this.tmp_number_list;
               list9.add(new NumbersModel("", "" + this.getnumberlist.get(i3).getNum().charAt(i4), ""));
            }
            Collections.sort(this.tmp_number_list, new Comparator<NumbersModel>() {
               public int compare(NumbersModel numbersModel, NumbersModel numbersModel2) {
                  return numbersModel.getNum().compareTo(numbersModel2.getNum());
               }
            });
            if (this.tmp_number_list.get(0).getNum().equals("0") && this.tmp_number_list.get(1).getNum().equals("0")) {
               List<FamilyNumberModel> list10 = this.familyNumberModelList;
               list10.add(new FamilyNumberModel("" + this.tmp_number_list.get(2).getNum() + this.tmp_number_list.get(0).getNum() + this.tmp_number_list.get(1).getNum(), "s"));
            } else if (this.tmp_number_list.get(0).getNum().equals("0")) {
               List<FamilyNumberModel> list11 = this.familyNumberModelList;
               list11.add(new FamilyNumberModel("" + this.tmp_number_list.get(1).getNum() + this.tmp_number_list.get(2).getNum() + this.tmp_number_list.get(0).getNum(), "s"));
            } else {
               List<FamilyNumberModel> list12 = this.familyNumberModelList;
               list12.add(new FamilyNumberModel("" + this.tmp_number_list.get(0).getNum() + this.tmp_number_list.get(1).getNum() + this.tmp_number_list.get(2).getNum(), "s"));
            }
         }
         
         Collections.sort(familyNumberModelList, new Comparator<FamilyNumberModel>() {
            @Override
            public int compare(FamilyNumberModel familyNumberModel, FamilyNumberModel familyNumberModel2) {
               return familyNumberModel.getNumber().compareTo(familyNumberModel2.getNumber());
            }
         });
         
         List<FamilyNumberModel> modelList = removeDuplicates(familyNumberModelList);  //final list after removing duplicates
         
         familyNumberModelList.clear();
         
         addToCart(date, type, point, modelList);
      }
   }
   
   private List<FamilyNumberModel> removeDuplicates(List<FamilyNumberModel> oldList) {
      List<FamilyNumberModel> newList = new ArrayList<>();
      
      boolean add = true;
      for (FamilyNumberModel model : oldList) {
         for (int j = 0; j < newList.size(); j++) {
            add = !newList.get(j).getNumber().equals(model.getNumber());
         }
         if (add) {
            newList.add(model);
         }
      }
      return newList;
   }
   
   
   private void addToCart(String date, String type, String point, List<FamilyNumberModel> list) {
      if (type.equals("Open")) {
         type = "1";
      } else {
         type = "2";
      }
      
      if (checkBalance(String.valueOf(Integer.parseInt(point) * list.size()))) {
         String newGame = "Panna";
         int i2 = 0;
         int i3 = 0;
         while (i3 < list.size()) {
            String numbers2 = list.get(i3).getNumber();
            String valueOf = String.valueOf(numbers2.charAt(i2));
            String valueOf2 = String.valueOf(numbers2.charAt(1));
            String valueOf3 = String.valueOf(numbers2.charAt(2));
            List<CartModel> list2 = this.cartModelList;
            String current_time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            list2.add(new CartModel(userId, company_id, game_id, newGame, type, point, list.get(i3).getNumber(), changeToServerDate(date), current_time));
            i3++;
            i2 = 0;
         }
         list.clear();
         updateCart();
      } else {
         customDialog.showFailureDialog("Insufficient Balance Check Your Wallet");
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
      actv_panna.setText("");
      et_point.setText("");
      actv_panna.requestFocus();
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
                  Intent i = new Intent(PannaFamilyActivity.this, MainActivity.class);
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