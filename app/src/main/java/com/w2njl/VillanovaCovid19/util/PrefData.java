package com.w2njl.VillanovaCovid19.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefData {

    public static String MY_PREFS = "healthPref";
    public static String AGE = "healthAge";
    public static int DEFAULT_AGE = 25;


    public static void setAge(Context context, int age) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AGE, age);
        editor.apply();
        editor.commit();
    }

    public static int getAge(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(AGE, DEFAULT_AGE);

    }


}
