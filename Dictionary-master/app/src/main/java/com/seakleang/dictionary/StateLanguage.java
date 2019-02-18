package com.seakleang.dictionary;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class StateLanguage {

    public static void saveState(Activity activity, String key, String value){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSate(Activity activity, String key){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

}
