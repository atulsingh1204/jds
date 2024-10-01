package com.bpointer.rkofficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bpointer.rkofficial.Adapter.GameCategoryAdapter;
import com.bpointer.rkofficial.Adapter.SliderAdapter;
import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;
import com.bpointer.rkofficial.BuildConfig;
import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.AdminDetailsResponseModel.AdminDetailsResponseModel;
import com.bpointer.rkofficial.Model.Response.HomeResponseModel.HomeResponseModel;
import com.bpointer.rkofficial.Model.Response.PostTokenResponseModel.PostTokenResponseModel;
import com.bpointer.rkofficial.R;
import com.smarteist.autoimageslider.SliderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_EMAIL;
import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_MOBILE;
import static com.bpointer.rkofficial.Common.AppConstant.ADMIN_WHATSAPP;
import static com.bpointer.rkofficial.Common.AppConstant.APP_STATUS;
import static com.bpointer.rkofficial.Common.AppConstant.ID;
import static com.bpointer.rkofficial.Common.AppConstant.TOKEN_ID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int userId;
    PreferenceManager preferenceManager;
    SliderView sliderView;
    RecyclerView rv_category;
    RelativeLayout rl_notification, rl_wallet, rl_profile, rl_history, rl_share, rl_whats_app, rl_call;
    TextView tv_wallet, tv_mobile, tv_whatsapp;
    CustomDialog customDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    AlertDialog alertDialog;
    String title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        initView();

        getAdminDetailsAPI();
        postTokenAPI();
        getHomeDataAPI();
    }

    private void showNotificationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.notification_dialog, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();

        Button bt_submit = dialogView.findViewById(R.id.bt_submit);
        TextView tv_title = dialogView.findViewById(R.id.tv_title);
        TextView tv_body = dialogView.findViewById(R.id.tv_body);

        tv_title.setText(title);
        tv_body.setText(description);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void postTokenAPI() {
        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));
        requestBody.setToken(preferenceManager.getStringPreference(TOKEN_ID));

        Call<PostTokenResponseModel> call = Api.getClient().create(Authentication.class).postToken(requestBody);
        call.enqueue(new Callback<PostTokenResponseModel>() {
            @Override
            public void onResponse(Call<PostTokenResponseModel> call, Response<PostTokenResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {

                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostTokenResponseModel> call, Throwable t) {
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    private void getAdminDetailsAPI() {
        Call<AdminDetailsResponseModel> call = Api.getClient().create(Authentication.class).getAdminDetails();
        call.enqueue(new Callback<AdminDetailsResponseModel>() {
            @Override
            public void onResponse(Call<AdminDetailsResponseModel> call, Response<AdminDetailsResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        preferenceManager.setPreference(ADMIN_WHATSAPP, response.body().getUser().getWhatsappNumber());
                        preferenceManager.setPreference(ADMIN_EMAIL, response.body().getUser().getEmail());
                        preferenceManager.setPreference(ADMIN_MOBILE, response.body().getUser().getContactNumber());
                        preferenceManager.setPreference(APP_STATUS, response.body().getAppStatus());

                        tv_whatsapp.setText("" + response.body().getUser().getWhatsappNumber());
                        tv_mobile.setText("" + response.body().getUser().getContactNumber());
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AdminDetailsResponseModel> call, Throwable t) {
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    private void getHomeDataAPI() {
        customDialog.showLoader();

        RequestBody requestBody = new RequestBody();
        requestBody.setUser_id(String.valueOf(userId));

        Call<HomeResponseModel> call = Api.getClient().create(Authentication.class).getHomeData(requestBody);
        call.enqueue(new Callback<HomeResponseModel>() {
            @Override
            public void onResponse(Call<HomeResponseModel> call, Response<HomeResponseModel> response) {
                customDialog.closeLoader();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("true")) {
                        SliderAdapter sliderAdapter = new SliderAdapter(response.body().getSlider());
                        sliderView.setSliderAdapter(sliderAdapter);

                        tv_wallet.setText("" + response.body().getWallet());

                        GameCategoryAdapter gameCategoryAdapter = new GameCategoryAdapter(MainActivity.this, response.body().getCompany());
                        rv_category.setAdapter(gameCategoryAdapter);

                        title = response.body().getTitle();
                        description = response.body().getDescription();
                        if (title.equals("null") || description.equals("null")) {

                        } else {
                            showNotificationDialog();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeResponseModel> call, Throwable t) {
                customDialog.closeLoader();
                Log.e("LoginResponse", "onFailure: " + t.getMessage());
            }
        });
    }

    private void initView() {
        sliderView = findViewById(R.id.imageSlider);
        rv_category = findViewById(R.id.rv_category);
        rl_notification = findViewById(R.id.rl_notification);
        rl_wallet = findViewById(R.id.rl_wallet);
        rl_profile = findViewById(R.id.rl_profile);
        rl_history = findViewById(R.id.rl_history);
        rl_share = findViewById(R.id.rl_share);
        rl_call = findViewById(R.id.rl_call);
        rl_whats_app = findViewById(R.id.rl_whats_app);
        tv_wallet = findViewById(R.id.tv_wallet);
        tv_whatsapp = findViewById(R.id.tv_whatsapp);
        tv_mobile = findViewById(R.id.tv_mobile);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        customDialog = new CustomDialog(this);
        preferenceManager = new PreferenceManager(this);
        userId = preferenceManager.getIntPreference(ID);

        if(5 == preferenceManager.getIntPreference(ID)){
            sliderView.setVisibility(View.INVISIBLE);
            rl_profile.setVisibility(View.INVISIBLE);
            rl_history.setVisibility(View.INVISIBLE);
            rl_wallet.setVisibility(View.INVISIBLE);

        }else {
            sliderView.setVisibility(View.VISIBLE);
            rl_profile.setVisibility(View.VISIBLE);
            rl_wallet.setVisibility(View.VISIBLE);
            rl_history.setVisibility(View.VISIBLE);
        }
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(5);
        // to set it scrollable automatically
        sliderView.setAutoCycle(true);
        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        rv_category.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHomeDataAPI();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        
        rl_notification.setOnClickListener(this);
        rl_wallet.setOnClickListener(this);
        rl_profile.setOnClickListener(this);
        rl_history.setOnClickListener(this);
        rl_share.setOnClickListener(this);
        rl_call.setOnClickListener(this);
        rl_whats_app.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (preferenceManager.getStringPreference(APP_STATUS).equalsIgnoreCase("1")) {
            switch (v.getId()) {
                case R.id.rl_notification:
                    startActivity(new Intent(this, NotificationActivity.class));
                    break;

                case R.id.rl_wallet:
                    startActivity(new Intent(this, WalletActivity.class));
                    break;

                case R.id.rl_share:
                    share();
                    break;

                case R.id.rl_whats_app:
                    openWhatsAPP();
                    break;

                case R.id.rl_call:
                    callNumber();
                    break;

                case R.id.rl_profile:
                    startActivity(new Intent(this, ProfileActivity.class));
                    break;

                case R.id.rl_history:
                    startActivity(new Intent(this, HistoryActivity.class));
                    break;
            }
        }
    }

    private void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n");
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void callNumber() {
        String sMobile = preferenceManager.getStringPreference(ADMIN_MOBILE);
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        Log.e("Phone", "Phone" + sMobile);
        callIntent.setData(Uri.parse("tel:" + sMobile));
        startActivity(callIntent);
    }

    private void openWhatsAPP() {
        String sMobile = preferenceManager.getStringPreference(ADMIN_WHATSAPP);
        String url = "https://api.whatsapp.com/send?phone=91" + sMobile;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}