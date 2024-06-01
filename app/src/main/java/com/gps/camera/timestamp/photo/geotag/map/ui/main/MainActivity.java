package com.gps.camera.timestamp.photo.geotag.map.ui.main;

import static com.gps.camera.timestamp.photo.geotag.map.utils.Utils.getCameraPermission;
import static com.gps.camera.timestamp.photo.geotag.map.utils.Utils.getLocationPermission;
import static com.gps.camera.timestamp.photo.geotag.map.utils.Utils.getStoragePermissions;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivityMainBinding;
import com.gps.camera.timestamp.photo.geotag.map.ui.area_calc.AreaCalcActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.gps_camera.GpsCameraActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.language.LanguageActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.live_location.LiveLocationActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.my_photos.MyPhotoActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.permission.PermissionActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.template.TemplateActivity;
import com.gps.camera.timestamp.photo.geotag.map.utils.ExtsKt;
import com.gps.camera.timestamp.photo.geotag.map.utils.Utils;

public class MainActivity extends BaseActivity<ActivityMainBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        listener();
    }

    private void listener(){
        AdsManager.INSTANCE.loadAndShowNativeSmallHome(this, AdsManager.INSTANCE.getNATIVE_HOME(), binding.frNative,binding.cvAds);
        AdsManager.INSTANCE.showAdBannerCollapsible(this,AdsManager.INSTANCE.getBANNER_COLLAP_HOME(),binding.frBanner,binding.line);

        AdsManager.INSTANCE.loadInter(this,AdsManager.INSTANCE.getINTER_VIEW_PHOTO());
        ExtsKt.setOnClickListener500MilliSeconds(binding.btnCameraGps, () -> {
            if (!Utils.allPermissionGrant(this,getCameraPermission())
                    || !Utils.allPermissionGrant(this,getLocationPermission())
                    || !Utils.allPermissionGrant(this,getStoragePermissions())){
                Intent intent = new Intent(this, PermissionActivity.class);
                startActivityForResult(intent,1);
            }else startActivityForResult(new Intent(MainActivity.this, GpsCameraActivity.class),1);
            return null;
        });
        ExtsKt.setOnClickListener500MilliSeconds(binding.btnAreaCalc, () -> {
            if (!Utils.allPermissionGrant(this,getCameraPermission())
                    || !Utils.allPermissionGrant(this,getLocationPermission())
                    || !Utils.allPermissionGrant(this,getStoragePermissions())){
                Intent intent = new Intent(this, PermissionActivity.class);
                startActivityForResult(intent,1);

            }else{
                Intent intent = new Intent(MainActivity.this, AreaCalcActivity.class);
                startActivityForResult(intent,1);

            }
            return null;
        });

        ExtsKt.setOnClickListener500MilliSeconds(binding.btnTemplate, () -> {
            if (!Utils.allPermissionGrant(this,getCameraPermission())
                    || !Utils.allPermissionGrant(this,getLocationPermission())
                    || !Utils.allPermissionGrant(this,getStoragePermissions())){
                Intent intent = new Intent(this, PermissionActivity.class);
                startActivityForResult(intent,1);


            }else{
                Intent intent = new Intent(MainActivity.this, TemplateActivity.class);
                startActivityForResult(intent,1);

            }

            return null;
        });

        ExtsKt.setOnClickListener500MilliSeconds(binding.btnMyPhotos, () -> {
            if (!Utils.allPermissionGrant(this,getCameraPermission())
                    || !Utils.allPermissionGrant(this,getLocationPermission())
                    || !Utils.allPermissionGrant(this,getStoragePermissions())){
                Intent intent = new Intent(this, PermissionActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(MainActivity.this, MyPhotoActivity.class);
                startActivityForResult(intent,1);

            }
            return null;
        });

        ExtsKt.setOnClickListener500MilliSeconds(binding.btnLanguage, () -> {
            Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
            intent.putExtra("SCREEN_LANGUAGE",false);
            startActivityForResult(intent,1);
            return null;
        });

        ExtsKt.setOnClickListener500MilliSeconds(binding.btnLiveLocation, () -> {
            if (!Utils.allPermissionGrant(this,getCameraPermission())
                    || !Utils.allPermissionGrant(this,getLocationPermission())
                    || !Utils.allPermissionGrant(this,getStoragePermissions())){
                Intent intent = new Intent(this, PermissionActivity.class);
                startActivity(intent);
                startActivityForResult(intent,1);

            }else{
                startActivityForResult(new Intent(MainActivity.this, LiveLocationActivity.class),1111);
            }
            return null;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdsManager.INSTANCE.setShowAdsEnable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            AdsManager.INSTANCE.loadAndShowNativeSmallHome(this, AdsManager.INSTANCE.getNATIVE_HOME(), binding.frNative, binding.cvAds);
            AdsManager.INSTANCE.showAdBannerCollapsible(this,AdsManager.INSTANCE.getBANNER_COLLAP_HOME(),binding.frBanner,binding.line);
    }
}
