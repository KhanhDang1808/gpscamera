package com.gps.camera.timestamp.photo.geotag.map.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.LogLevel;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.data.models.LanguageModel;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Activity.SharePref;
import com.gps.camera.timestamp.photo.geotag.map.utils.CommonUtils;

public class App extends Application {

    private SharePref sharePref;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initAdjust();
        changLanguage();
    }
    private void initAdjust() {
        AdjustConfig config = new AdjustConfig(
                this,
                getString(R.string.adjust_token_key),
                AdjustConfig.ENVIRONMENT_PRODUCTION
        );
        config.setLogLevel(LogLevel.WARN);
        Adjust.onCreate(config);
        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
    }
    public void changLanguage(){
        if (sharePref == null) sharePref = new SharePref(getApplicationContext());
        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
    }

    private class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
            LanguageModel language = CommonUtils.getLanguageList().get(sharePref.getLanguagePosition());
            CommonUtils.setLocale(activity, language);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }
    }
}
