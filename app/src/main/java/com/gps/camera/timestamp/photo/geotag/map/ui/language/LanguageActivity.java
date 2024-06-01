package com.gps.camera.timestamp.photo.geotag.map.ui.language;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Activity.SharePref;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivityLanguageBinding;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.language.adapter.AdapterLanguage;
import com.gps.camera.timestamp.photo.geotag.map.ui.main.MainActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.onboarding.OnboardingActivity;
import com.gps.camera.timestamp.photo.geotag.map.utils.ExtsKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class LanguageActivity extends BaseActivity<ActivityLanguageBinding> {
    private AdapterLanguage adapter = null;
    private SharePref sharePref;
    private boolean isScreenSplash = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    public void initView() {
        isScreenSplash = getIntent().getBooleanExtra("SCREEN_LANGUAGE", false);
        sharePref = new SharePref(this);
        getLanguage();
        listener();
    }

    private void getLanguage() {
        adapter = new AdapterLanguage(this);
        binding.rcvLanguage.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvLanguage.setAdapter(adapter);
        adapter.setSelectedPositionLanguage(sharePref.getLanguagePosition());
    }

    private void listener() {
        ExtsKt.setOnClickListener500MilliSeconds(binding.ivDone, () -> {
            sharePref.setLanguagePosition(adapter.getSelectedPositionLanguage());
            Intent intent;
            if (isScreenSplash) {
                intent = new Intent(LanguageActivity.this, OnboardingActivity.class);
            } else {
                intent = new Intent(LanguageActivity.this, MainActivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return null;
        });
        ExtsKt.setOnClickListener500MilliSeconds(binding.ivBack, () -> {
            finish();
            return null;
        });

        if (isScreenSplash) {
            binding.ivBack.setVisibility(View.GONE);
            AdsManager.INSTANCE.showAdNative(this, binding.frNative, AdsManager.INSTANCE.getNATIVE_LANGUAGE());
        } else {
            binding.ivBack.setVisibility(View.VISIBLE);
            binding.ivBack.setOnClickListener(view -> finish());
            AdsManager.INSTANCE.loadAndShowNative(this, binding.frNative, AdsManager.INSTANCE.getNATIVE_LANGUAGE());

        }
    }
}