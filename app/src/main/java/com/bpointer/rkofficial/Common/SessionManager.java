package com.bpointer.rkofficial.Common;

import android.content.Context;
import android.content.SharedPreferences;

import static com.bpointer.rkofficial.Common.AppConstant.MOBILE;

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Session";
    private static final String KEY_IS_LOGGED_IN = "IS_LOGGED_IN";
    private static final String USER_ID = "USER_ID";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setMobile(String value) {
        editor.putString(MOBILE, value);
        editor.apply();
    }

    public String getMobile() {
        if (sharedPreferences.contains(MOBILE)) {
            return sharedPreferences.getString(MOBILE, "");
        }
        return "";
    }

}
