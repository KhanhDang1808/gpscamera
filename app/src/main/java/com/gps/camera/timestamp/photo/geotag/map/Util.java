package com.gps.camera.timestamp.photo.geotag.map;

import android.content.Context;
import android.content.SharedPreferences;

public class Util {
    Context context;

    public Util(Context context2) {
        this.context = context2;
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = this.context.getSharedPreferences(str, 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public String showPreferences(String str) {
        return this.context.getSharedPreferences(str, 0).getString(str, "");
    }
}
