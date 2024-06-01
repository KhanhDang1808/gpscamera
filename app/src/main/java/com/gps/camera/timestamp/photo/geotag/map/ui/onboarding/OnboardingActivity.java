package com.gps.camera.timestamp.photo.geotag.map.ui.onboarding;


import android.content.Intent;
import android.view.View;


import androidx.viewpager2.widget.ViewPager2;

import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager;
import com.gps.camera.timestamp.photo.geotag.map.data.models.PhotoModel;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Activity.SharePref;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivityOnboardingBinding;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.main.MainActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.onboarding.adapter.PhotoModelAdapter;
import com.gps.camera.timestamp.photo.geotag.map.ui.permission.PermissionActivity;
import com.gps.camera.timestamp.photo.geotag.map.utils.ExtsKt;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class OnboardingActivity extends BaseActivity<ActivityOnboardingBinding> {
    private List<PhotoModel> listPhoto;

    @Override
    public int getLayoutId() {
        return R.layout.activity_onboarding;
    }

    @Override
    public void initView() {
        listPhoto = getListPhoto();
        PhotoModelAdapter photoModelAdapter = new PhotoModelAdapter(listPhoto, this);
        binding.viewPager2Intro.setAdapter(photoModelAdapter);
        binding.viewPager2Intro.setUserInputEnabled(false);
        AdsManager.INSTANCE.showNativeSmall1(this, AdsManager.INSTANCE.getNATIVE_INTRO(), binding.frNative);
        binding.viewPager2Intro.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 2){
                    binding.frNative.setVisibility(View.GONE);
                }
            }
        });
        ExtsKt.setOnClickListener100MilliSeconds(binding.btnContinue, new Function0<Unit>() {
            @Override
            public Unit invoke() {
                if (binding.viewPager2Intro.getCurrentItem() == listPhoto.size() - 1) {
                    SharePref sharePref = new SharePref(OnboardingActivity.this);
                    if (!sharePref.getFirstOpen()){
                        Intent intent = new Intent(OnboardingActivity.this, PermissionActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    binding.viewPager2Intro.setCurrentItem(binding.viewPager2Intro.getCurrentItem() + 1);
                }
                return null;
            }
        });
    }

    private List<PhotoModel> getListPhoto() {
        List<PhotoModel> list = new ArrayList<>();
        list.add(new PhotoModel(R.drawable.image_intro_1,R.string.content_onboarding_1,R.string.content_onboarding_1,1));
        list.add(new PhotoModel(R.drawable.image_intro_2,R.string.content_onboarding_2,R.string.content_onboarding_2,2));
        list.add(new PhotoModel(R.drawable.image_intro_3,R.string.content_onboarding_3,R.string.content_onboarding_3,3));
        return list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}