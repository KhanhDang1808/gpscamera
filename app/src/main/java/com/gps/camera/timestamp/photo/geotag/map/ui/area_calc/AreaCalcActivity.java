package com.gps.camera.timestamp.photo.geotag.map.ui.area_calc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.gps.camera.timestamp.photo.geotag.map.area_caculator.DrawingOption;
import com.gps.camera.timestamp.photo.geotag.map.area_caculator.DrawingOptionBuilder;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivityAreaCalcBinding;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;

public class AreaCalcActivity extends BaseActivity<ActivityAreaCalcBinding> {

    private DrawingOption.DrawingType currentDrawingType;
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            activityResult -> AdsManager.INSTANCE.loadAndShowNative(AreaCalcActivity.this,binding.frNative,AdsManager.INSTANCE.getNATIVE_AREA_CALC()));
    @Override
    public int getLayoutId() {
        return R.layout.activity_area_calc;
    }

    @Override
    public void initView() {
        binding.btnBack.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        AdsManager.INSTANCE.loadAndShowNative(this,binding.frNative,AdsManager.INSTANCE.getNATIVE_AREA_CALC());

        binding.btnDrawPoint.setOnClickListener(view -> {
            AdsManager.INSTANCE.setStateTitleDraw(2);
            currentDrawingType = DrawingOption.DrawingType.POINT;
            someActivityResultLauncher.launch(new DrawingOptionBuilder().withLocation(35.744502d, 51.368966d).withFillColor(Color.argb(60, 0, 0, 255)).withStrokeColor(Color.argb(100, 0, 0, 0)).withStrokeWidth(3).withRequestGPSEnabling(false).withDrawingType(currentDrawingType).build(getApplicationContext()));
        });

        binding.btnDrawPolygon.setOnClickListener(view -> {
            AdsManager.INSTANCE.setStateTitleDraw(0);
            currentDrawingType = DrawingOption.DrawingType.POLYGON;
            someActivityResultLauncher.launch(new DrawingOptionBuilder().withLocation(35.744502d, 51.368966d).withFillColor(Color.argb(60, 0, 0, 255)).withStrokeColor(Color.argb(100, 255, 0, 0)).withStrokeWidth(3).withRequestGPSEnabling(false).withDrawingType(currentDrawingType).build(getApplicationContext()));

        });

        binding.btnDrawPolyline.setOnClickListener(view -> {
            AdsManager.INSTANCE.setStateTitleDraw(1);
            currentDrawingType = DrawingOption.DrawingType.POLYLINE;
            someActivityResultLauncher.launch(new DrawingOptionBuilder().withLocation(35.744502d, 51.368966d).withFillColor(Color.argb(60, 0, 0, 255)).withStrokeColor(Color.argb(100, 255, 0, 0)).withStrokeWidth(3).withRequestGPSEnabling(false).withDrawingType(currentDrawingType).build(getApplicationContext()));
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}