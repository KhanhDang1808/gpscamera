package com.gps.camera.timestamp.photo.geotag.map.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.data.models.LanguageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommonUtils {

    private static long timeLast = 0;
    public static boolean singleClick500mls(){
        long time = System.currentTimeMillis() - timeLast;
        if (time>500){
            timeLast = System.currentTimeMillis();
            return true;
        }
        return false;
    }
    public static boolean singleClick250mls(){
        long time = System.currentTimeMillis() - timeLast;
        if (time>250){
            timeLast = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public static boolean singleClick100mls(){
        long time = System.currentTimeMillis() - timeLast;
        if (time>100){
            timeLast = System.currentTimeMillis();
            return true;
        }
        return false;
    }
    public static boolean singleClick1s(){
        long time = System.currentTimeMillis() - timeLast;
        if (time>1000){
            timeLast = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public static ArrayList<LanguageModel> getLanguageList() {
        List languageArrayList = new ArrayList<LanguageModel>();
        languageArrayList.add(new LanguageModel(R.drawable.ic_england_flag, R.string.english, "en"));
        languageArrayList.add(new LanguageModel(R.drawable.ic_india_flag, R.string.hindi, "hi"));
        languageArrayList.add(new LanguageModel(R.drawable.ic_spanish_flag, R.string.spanish, "es"));
        languageArrayList.add(new LanguageModel(R.drawable.ic_french_flag, R.string.french, "fr"));
        languageArrayList.add(new LanguageModel(R.drawable.ic_arabic_flag, R.string.arabic, "ar"));
        languageArrayList.add(new LanguageModel(R.drawable.ic_bengal_flag, R.string.bengali, "bn"));
        languageArrayList.add(new LanguageModel(R.drawable.ic_russian_flag, R.string.russian, "ru"));
        languageArrayList.add(new LanguageModel(R.drawable.ic_portugal, R.string.portuguese, "pt"));
        languageArrayList.add(new LanguageModel(R.drawable.ic_indo_flag, R.string.indonesian, "in"));
        languageArrayList.add(new LanguageModel(R.drawable.ic_german_flag, R.string.german, "de"));
        languageArrayList.add(new LanguageModel(R.drawable.ic_itali_flag, R.string.italian, "it"));
        languageArrayList.add(new LanguageModel(R.drawable.ic_korean_flag, R.string.korean, "ko"));
        return (ArrayList<LanguageModel>) languageArrayList;
    }

    public static void setLocale(Context context, LanguageModel language) {
        Locale myLocale = new Locale(language.getKey());
        Resources resource = context.getResources();
        DisplayMetrics displayMetrics = resource.getDisplayMetrics();
        Configuration configuration = resource.getConfiguration();
        configuration.setLocale(myLocale);
        resource.updateConfiguration(configuration, displayMetrics);
    }
}
