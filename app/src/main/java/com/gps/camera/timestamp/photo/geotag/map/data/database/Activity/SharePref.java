package com.gps.camera.timestamp.photo.geotag.map.data.database.Activity;

import android.content.Context;
import android.content.SharedPreferences;

import com.gps.camera.timestamp.photo.geotag.map.data.database.Model.DateTime;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class SharePref {
    public static final String arryDates = "arrayDate";
    public static final String arryDates_horizontal = "arrayDate_horizontal";
    private final String Horizontal = "horizontal";
    SharedPreferences.Editor moEditor;
    public SharedPreferences moSharedPreferences;

    public SharePref(Context context) {
        if (context != null) {
            this.moSharedPreferences = context.getSharedPreferences("setformate", 0);
        }
    }
    public void setLanguagePosition(int position) {
        SharedPreferences.Editor editor = moSharedPreferences.edit();
        editor.putInt("KEY_LANGUAGE_POSITION", position);
        editor.apply();
    }

    public int getLanguagePosition() {
        return moSharedPreferences.getInt("KEY_LANGUAGE_POSITION", 0);
    }

    public boolean getHorizontal() {
        return this.moSharedPreferences.getBoolean("horizontal", true);
    }

    public void setHorizontal(boolean z) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putBoolean("horizontal", z);
        this.moEditor.commit();
    }

    public boolean getShowRate4s() {
        return this.moSharedPreferences.getBoolean("getShowRate4s", false);
    }

    public void setShowRate4s() {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putBoolean("getShowRate4s", true);
        this.moEditor.commit();
    }
    public boolean getFirstOpen() {
        return this.moSharedPreferences.getBoolean("getFirstOpen", false);
    }

    public void setFirstOpen() {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putBoolean("getFirstOpen", true);
        this.moEditor.commit();
    }


    public boolean getVertical() {
        return this.moSharedPreferences.getBoolean("Vertical", true);
    }

    public void setVertical(boolean z) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putBoolean("Vertical", z);
        this.moEditor.commit();
    }

    public String getH1() {
        return this.moSharedPreferences.getString("Horizontal1", null);
    }

    public void setH1(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal1", str);
        this.moEditor.commit();
    }

    public String getH2() {
        return this.moSharedPreferences.getString("Horizontal2", null);
    }

    public void setH2(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal2", str);
        this.moEditor.commit();
    }

    public String getH3() {
        return this.moSharedPreferences.getString("Horizontal3", null);
    }

    public void setH3(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal3", str);
        this.moEditor.commit();
    }

    public String getH4() {
        return this.moSharedPreferences.getString("Horizontal4", null);
    }

    public void setH4(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal4", str);
        this.moEditor.commit();
    }

    public String getH5() {
        return this.moSharedPreferences.getString("Horizontal5", null);
    }

    public void setH5(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("", str);
        this.moEditor.commit();
    }

    public String getH6() {
        return this.moSharedPreferences.getString("Horizontal6", null);
    }

    public void setH6(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal6", str);
        this.moEditor.commit();
    }

    public String getH7() {
        return this.moSharedPreferences.getString("Horizontal7", null);
    }

    public void setH7(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal7", str);
        this.moEditor.commit();
    }

    public String getH8() {
        return this.moSharedPreferences.getString("Horizontal8", null);
    }

    public void setH8(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal8", str);
        this.moEditor.commit();
    }

    public String getH9() {
        return this.moSharedPreferences.getString("Horizontal9", null);
    }

    public void setH9(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal9", str);
        this.moEditor.commit();
    }

    public String getH10() {
        return this.moSharedPreferences.getString("Horizonta110", null);
    }

    public void setH10(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizonta110", str);
        this.moEditor.commit();
    }

    public String getH11() {
        return this.moSharedPreferences.getString("Horizontal11", null);
    }

    public void setH11(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal11", str);
        this.moEditor.commit();
    }

    public String getH12() {
        return this.moSharedPreferences.getString("Horizontal12", null);
    }

    public void setH12(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal12", str);
        this.moEditor.commit();
    }

    public String getH13() {
        return this.moSharedPreferences.getString("Horizontal13", null);
    }

    public void setH13(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal13", str);
        this.moEditor.commit();
    }

    public String getH14() {
        return this.moSharedPreferences.getString("Horizontal14", null);
    }

    public void setH14(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal14", str);
        this.moEditor.commit();
    }

    public String getH15() {
        return this.moSharedPreferences.getString("", null);
    }

    public void setH15(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal15", str);
        this.moEditor.commit();
    }

    public String getH16() {
        return this.moSharedPreferences.getString("Horizontal16", null);
    }

    public void setH16(String str) {
        SharedPreferences.Editor edit = this.moSharedPreferences.edit();
        this.moEditor = edit;
        edit.putString("Horizontal16", str);
        this.moEditor.commit();
    }

    public ArrayList<DateTime> getDates(Context context, String str) {
        Gson gson = new Gson();
        if (this.moSharedPreferences == null) {
            this.moSharedPreferences = context.getSharedPreferences("setformate", 0);
        }
        return (ArrayList) gson.fromJson(this.moSharedPreferences.getString(str, null), new TypeToken<ArrayList<DateTime>>() {
            
        }.getType());
    }
}
