package com.gps.camera.timestamp.photo.geotag.map.ui.permission;

import static com.gps.camera.timestamp.photo.geotag.map.utils.Utils.getCameraPermission;
import static com.gps.camera.timestamp.photo.geotag.map.utils.Utils.getLocationPermission;
import static com.gps.camera.timestamp.photo.geotag.map.utils.Utils.getStoragePermissions;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Activity.SharePref;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivityPermissionBinding;
import com.gps.camera.timestamp.photo.geotag.map.dialog.SettingPermissionDialog;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.main.MainActivity;
import com.gps.camera.timestamp.photo.geotag.map.utils.ExtsKt;
import com.gps.camera.timestamp.photo.geotag.map.utils.Utils;
import com.vapp.admoblibrary.ads.AppOpenManager;

public class PermissionActivity extends BaseActivity<ActivityPermissionBinding> {

    private final ActivityResultLauncher<String[]> permissionCamera = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
        if (Utils.allPermissionGrant(this,getCameraPermission())){
            binding.imgCameraAccess.setImageResource(R.drawable.ic_switch_on);
        }else{
            showDialogPermission();
        }
    });

    private final ActivityResultLauncher<String[]> permissionLocation = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
        if (Utils.allPermissionGrant(this,getLocationPermission())){
            binding.imgLocationAccess.setImageResource(R.drawable.ic_switch_on);
        }else{
            showDialogPermission();
        }
    });

    private final ActivityResultLauncher<String[]> permissionStorage = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
        if (Utils.allPermissionGrant(this,getStoragePermissions())){
            binding.imgPhotoAccess.setImageResource(R.drawable.ic_switch_on);
        }else{
            showDialogPermission();
        }
    });

/*    private final ActivityResultLauncher<String[]> permissionRecord = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
        if (Utils.allPermissionGrant(this,getRecordPermission())){
            binding.imgRecordAudioAccess.setImageResource(R.drawable.ic_switch_on);
        }else{
            showDialogPermission();
        }
    });*/

    @Override
    public int getLayoutId() {
        return R.layout.activity_permission;
    }


    @Override
    public void initView() {
        SharePref sharePref = new SharePref(this);
        if (!sharePref.getFirstOpen()) {
            sharePref.setFirstOpen();
        }
        listener();
    }

    private void listener(){

        ExtsKt.setOnClickListener500MilliSeconds(binding.btnCameraAccess, () -> {
            permissionCamera.launch(getCameraPermission());
            return null;
        });

        ExtsKt.setOnClickListener500MilliSeconds(binding.btnLocationAccess, () -> {
            permissionLocation.launch(getLocationPermission());
            return null;
        });

        ExtsKt.setOnClickListener500MilliSeconds(binding.btnPhotoAccess, () -> {
            permissionStorage.launch(getStoragePermissions());
          /*  Dexter.withContext(this).withPermissions("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.READ_MEDIA_IMAGES").withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    multiplePermissionsReport.areAllPermissionsGranted();
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        binding.imgPhotoAccess.setImageResource(R.drawable.ic_switch_on);
                    }
                    multiplePermissionsReport.isAnyPermissionPermanentlyDenied();
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                    showDialogPermission();

                }
            }).check();*/
            return null;
        });

        /*ExtsKt.setOnClickListener500MilliSeconds(binding.btnRecordAudioAccess, () -> {
            permissionRecord.launch(getRecordPermission());
            return null;
        });*/

        ExtsKt.setOnClickListener500MilliSeconds(binding.btnLetGo, () -> {
            if (Utils.allPermissionGrant(PermissionActivity.this,getCameraPermission())
                    && Utils.allPermissionGrant(PermissionActivity.this,getLocationPermission())
                    && Utils.allPermissionGrant(PermissionActivity.this,getStoragePermissions())){
                Intent intent = new Intent(PermissionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return null;
        });

        ExtsKt.setOnClickListener500MilliSeconds(binding.btnSkip, () -> {
            Intent intent = new Intent(PermissionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return null;
        });
    }

    private void showDialogPermission(){
        new SettingPermissionDialog(PermissionActivity.this, () -> {
            checkStatePermission();
            return null;
        });
    }

    private void checkStatePermission(){
        if (Utils.allPermissionGrant(this,getCameraPermission())
        && Utils.allPermissionGrant(this,getLocationPermission())
        && Utils.allPermissionGrant(this,getStoragePermissions())){
            binding.btnLetGo.setBackgroundResource(R.drawable.corner_100);
        }else{
            binding.btnLetGo.setBackgroundResource(R.drawable.corner_100_un_per);
        }

        if (Utils.allPermissionGrant(this,getCameraPermission())){
            binding.imgCameraAccess.setImageResource(R.drawable.ic_switch_on);
        }else{
            binding.imgCameraAccess.setImageResource(R.drawable.ic_switch_off);
        }

        if (Utils.allPermissionGrant(this,getLocationPermission())){
            binding.imgLocationAccess.setImageResource(R.drawable.ic_switch_on);
        }else{
            binding.imgLocationAccess.setImageResource(R.drawable.ic_switch_off);
        }

        if (Utils.allPermissionGrant(this,getStoragePermissions())){
            binding.imgPhotoAccess.setImageResource(R.drawable.ic_switch_on);
        }else{
            binding.imgPhotoAccess.setImageResource(R.drawable.ic_switch_off);
        }

     /*   if (Utils.allPermissionGrant(this,getRecordPermission())){
            binding.imgRecordAudioAccess.setImageResource(R.drawable.ic_switch_on);
        }else{
            binding.imgRecordAudioAccess.setImageResource(R.drawable.ic_switch_off);
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkStatePermission();
        AppOpenManager.getInstance().enableAppResumeWithActivity(PermissionActivity.class);

    }
}