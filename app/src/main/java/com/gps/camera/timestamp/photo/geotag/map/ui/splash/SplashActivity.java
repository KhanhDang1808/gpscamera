package com.gps.camera.timestamp.photo.geotag.map.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;

import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.Util;
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.KeysConstants;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Activity.SharePref;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivitySplashBinding;
import com.gps.camera.timestamp.photo.geotag.map.ui.area_calc.map.AreaMapsActivityCar;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.language.LanguageActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.live_location.LiveLocationActivity;
import com.gps.camera.timestamp.photo.geotag.map.utils.GoogleMobileAdsConsentManager;
import com.gps.camera.timestamp.photo.geotag.map.utils.Utils;
import com.facebook.appevents.AppEventsConstants;
import com.vapp.admoblibrary.ads.AdmobUtils;
import com.vapp.admoblibrary.ads.AppOpenManager;

import java.util.concurrent.atomic.AtomicBoolean;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    private static final Long timeDelay = 2000L;
    private final Handler handlerStartScreen = new Handler();
    private final Runnable runnableStartScreen = this::startLanguageScreen;
    C1281SP mSP;


    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)
        ) {
            finish();
            return;
        }

        if (AdmobUtils.isNetworkConnected(this)) {
            setupCMP();
        } else {
          //  binding.textView.setVisibility(View.INVISIBLE);
            handlerStartScreen.postDelayed(runnableStartScreen,timeDelay);
        }
        this.mSP = new C1281SP(this);
        SharePref sharePref = new SharePref(this);
        if (!sharePref.getFirstOpen()){
            SavePreferencesnew("Numbering", "1");
            SavePreferencesnew("Pos", AppEventsConstants.EVENT_PARAM_VALUE_NO);
            SavePreferencesnew("CameraSounf", "ON");
            this.mSP.setString(this, KeysConstants.CAMERA_POSITION0, "ON");
            SavePreferencesnew("Layout", "Advance");
            SavePreferencesnew("DateTime_Temp0", "Yes");
            SavePreferencesnew("Maptype_Temp0", "Yes");
            SavePreferencesnew("Address_Temp0", "Yes");
            SavePreferencesnew("laglog_Temp0", "Yes");
            SavePreferencesnew("timezone_Temp0", "Yes");
            SavePreferencesnew("logo_Temp0", "Yes");
            SavePreferencesnew("wind_Temp0", "Yes");
            SavePreferencesnew("weather_Temp0", "Yes");
            SavePreferencesnew("humidity_Temp0", "Yes");
            SavePreferencesnew("pressure_Temp0", "Yes");
            SavePreferencesnew("magnetic_Temp0", "Yes");
            SavePreferencesnew("compass_Temp0", "Yes");
            SavePreferencesnew("altitude_Temp0", "Yes");
            SavePreferencesnew("accurancy_Temp0", "yes");
            SavePreferencesnew("hashtag_Temp0", "Yes");
            SavePreferencesnew("number_Temp0", "Yes");
            SavePreferencesnew("Fonttype", "quicksand_bold.otf");
            SavePreferencesnew("DateTime_Temp2", "Yes");
            SavePreferencesnew("Maptype_Temp2", "Yes");
            SavePreferencesnew("Address_Temp2", "Yes");
            SavePreferencesnew("laglog_Temp2", "Yes");
            SavePreferencesnew("timezone_Temp2", "Yes");
            SavePreferencesnew("logo_Temp2", "Yes");
            SavePreferencesnew("wind_Temp2", "Yes");
            SavePreferencesnew("weather_Temp2", "Yes");
            SavePreferencesnew("humidity_Temp2", "Yes");
            SavePreferencesnew("pressure_Temp2", "Yes");
            SavePreferencesnew("magnetic_Temp2", "Yes");
            SavePreferencesnew("compass_Temp2", "Yes");
            SavePreferencesnew("altitude_Temp2", "Yes");
            SavePreferencesnew("accurancy_Temp2", "Yes");
            SavePreferencesnew("hashtag_Temp2", "Yes");
            SavePreferencesnew("number_Temp2", "Yes");
            Util util = new Util(this);
            util.SavePreferences("positionX1", String.valueOf(1));
            util.SavePreferences("positionX2", String.valueOf(1));
            this.mSP.setString(this, "pos_type_temp", "Bottom");
            this.mSP.setString(this, "pos_type_temp1", "Bottom");
            this.mSP.setString(this, "pos_type_temp2", "Bottom");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdsManager.INSTANCE.setInitVariable();
    }

    private void startLanguageScreen() {
        Intent intent = new Intent(SplashActivity.this, LanguageActivity.class);
        intent.putExtra("SCREEN_LANGUAGE",true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    private void SavePreferencesnew(String str, String str2) {
        SharedPreferences.Editor edit = getSharedPreferences(str, 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }
    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;
    private AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);

    private void setupCMP(){
        googleMobileAdsConsentManager = new GoogleMobileAdsConsentManager(this);
        googleMobileAdsConsentManager.gatherConsent(error -> {
            if (error != null){
                initializeMobileAdsSdk();
            }
            if (googleMobileAdsConsentManager.getCanRequestAds()){
                initializeMobileAdsSdk();
            }
        });
    }

    private void initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.get()) {
            return;
        }
        isMobileAdsInitializeCalled.set(true);
        initAdmob();
    }

    private void initAdmob() {
        AdmobUtils.initAdmob(this,10000, false, true);
        if (Utils.isAndroid9()){
            AppOpenManager.getInstance().init(this.getApplication(), AdsManager.INSTANCE.getONRESUME());
        }
        AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity.class);
        AppOpenManager.getInstance().disableAppResumeWithActivity(LiveLocationActivity.class);
        AppOpenManager.getInstance().disableAppResumeWithActivity(AreaMapsActivityCar.class);
        loadAndShowInter();
    }

    private void loadAndShowInter(){
        AdsManager.INSTANCE.loadNative(this,AdsManager.INSTANCE.getNATIVE_LANGUAGE());
        AdsManager.INSTANCE.loadNative(this,AdsManager.INSTANCE.getNATIVE_INTRO());
        AdsManager.INSTANCE.loadNative(this,AdsManager.INSTANCE.getNATIVE_HOME());
        AdsManager.INSTANCE.loadAndShowInter(this, AdsManager.INSTANCE.getINTER_SPLASH(), new AdsManager.AdListener2() {
            @Override
            public void onAdClose() {
                startLanguageScreen();
            }

            @Override
            public void onAdFail(@Nullable String error) {
                startLanguageScreen();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

}