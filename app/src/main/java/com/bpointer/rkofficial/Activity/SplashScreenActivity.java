package com.bpointer.rkofficial.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Common.SessionManager;
import com.bpointer.rkofficial.Model.Response.AdminDetailsResponseModel.AdminDetailsResponseModel;
import com.bpointer.rkofficial.Model.Response.GetAppVersionResponse.GetAppVersionResponse;
import com.bpointer.rkofficial.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.bpointer.rkofficial.Common.AppConstant.TOKEN_ID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {
   SessionManager sessionManager;
   PreferenceManager preferenceManager;
   SweetAlertDialog sweetAlertDialog;
   int version;
   CustomDialog customDialog;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_splash_screen);
   
      customDialog = new CustomDialog(this);
      sessionManager = new SessionManager(SplashScreenActivity.this);
      preferenceManager = new PreferenceManager(SplashScreenActivity.this);
      
      FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
               @Override
               public void onComplete(@NonNull Task<String> task) {
                  if (!task.isSuccessful()) {
                     return;
                  }
                  // Get new FCM registration token
                  String token = task.getResult();
                  preferenceManager.setPreference(TOKEN_ID, token);
                  Log.e("check_token", "onComplete: " + token);
               }
            });
   }
   
   @Override
   protected void onResume() {
      super.onResume();
      try {
         PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
         version = pInfo.versionCode;
         
         checkAppVersion(version);
         Log.e("App Version", "App Version: " + version);
      } catch (PackageManager.NameNotFoundException e) {
         e.printStackTrace();
      }
   }
   
   private void checkAppVersion(int version) {
      customDialog.showLoader();
      Call<GetAppVersionResponse> call = Api.getClient().create(Authentication.class).getAppVersion();
      call.enqueue(new Callback<GetAppVersionResponse>() {
         @Override
         public void onResponse(Call<GetAppVersionResponse> call, Response<GetAppVersionResponse> response) {
            customDialog.closeLoader();
            if (response.body() != null) {
               if (response.body().getStatus().equals("true")) {
                  int versionFromBackend = Integer.parseInt(response.body().getData().getAppVersion());
                  if (version < versionFromBackend) {
                     updateApp();
                  } else {
                     callIntent();
                  }
               } else {
                  Toast.makeText(SplashScreenActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
         }
         
         @Override
         public void onFailure(Call<GetAppVersionResponse> call, Throwable t) {
            customDialog.closeLoader();
            Log.e("LoginResponse", "onFailure: " + t.getMessage());
         }
      });
   }
   
   private void callIntent() {
      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
            if (sessionManager.isLoggedIn()) {
               Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
               startActivity(i);
               finish();
            } else {
               Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
               startActivity(i);
               finish();
            }
         }
      }, 2000);
   }
   
   private void updateApp() {
      sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
      sweetAlertDialog.setCancelable(false);
      sweetAlertDialog.setTitleText("Update Available")
            .setContentText("You are using older version, please update!")
            .setConfirmText("Yes, update it!")
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
               @Override
               public void onClick(SweetAlertDialog sDialog) {
                  sDialog.dismissWithAnimation();
                  final String appPackageName = getPackageName(); // package name of the app
                  try {
                     startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                  } catch (android.content.ActivityNotFoundException anfe) {
                     startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                  }
               }
            })
            .show();
   }
}