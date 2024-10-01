package com.bpointer.rkofficial.Common;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import static android.content.Context.MODE_PRIVATE;
import static com.bpointer.rkofficial.Common.AppConstant.MYPREF;


public class PreferenceManager {

    Context mContext;
    SharedPreferences mSharedPreferences;

    public PreferenceManager(Context mContext) {
        this.mContext = mContext;
        getSharedPreferences();
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {

        return null;
    }


    public SharedPreferences getSharedPreferences() {
        mSharedPreferences = mContext.getSharedPreferences(MYPREF, MODE_PRIVATE);
        return mSharedPreferences;
    }



    SharedPreferences.Editor getEditor() {
        return mSharedPreferences.edit();
    }

    public void setPreference(String key, String value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.apply();

    }

    public void cleasrprefernce() {
        SharedPreferences.Editor editor = getEditor();
        editor.clear();
        editor.apply();

    }

    public void setPreference(String key, boolean value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.apply();

    }


    public void setPreference(String key, long value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putLong(key, value);
        editor.apply();

    }

    public void setPreference(String key, int value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(key, value);
        editor.apply();

    }

    public void setIntPreference(String key, int value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(key, value);
        editor.apply();

    }
    public void setModelPreference(String key, Object value) {
        SharedPreferences.Editor editor = getEditor();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.apply();
    }

    public String getStringPreference(String key) {
        if (mSharedPreferences.contains(key)) {
            return mSharedPreferences.getString(key, "");
        }
        return "";
    }
    public Integer getIntPreference(String key) {
        if (mSharedPreferences.contains(key)) {
            return mSharedPreferences.getInt(key, 0);
        }
        return 0;
    }
}


