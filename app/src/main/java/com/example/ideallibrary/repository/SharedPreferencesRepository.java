package com.example.ideallibrary.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ideallibrary.utilities.Constants;

public class SharedPreferencesRepository {

    public static Boolean insertSharedPreferences(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);

        return editor.commit();

    }

    public static String getSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);

        return sharedPreferences.getString(key, "");
    }

    public static Boolean removeSharedPreferences(Context context, String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(key)) {
            editor.remove(key);
        }

        return editor.commit();
    }
}
