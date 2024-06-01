package com.gps.camera.timestamp.photo.geotag.map.ui.setting;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import com.gps.camera.timestamp.photo.geotag.map.bottom_sheet_fragment.BottomSheetDateFormat;
import com.gps.camera.timestamp.photo.geotag.map.bottom_sheet_fragment.BottomSheetTimeFormat;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.KeysConstants;
import com.gps.camera.timestamp.photo.geotag.map.camera.adapter.DateAdapterCamera;
import com.gps.camera.timestamp.photo.geotag.map.camera.adapter.TimeAdapter;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivitySettingBinding;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> implements BottomSheetDateFormat.onClickDate, BottomSheetTimeFormat.onClickTime {

    C1281SP mSP;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        this.mSP = new C1281SP(this);
        if (this.mSP.getString(this, KeysConstants.CAMERA_POSITION0, "ON").equals("ON")) {
            binding.imgSwitch.setImageResource(R.drawable.ic_switch_on);
        } else {
            binding.imgSwitch.setImageResource(R.drawable.ic_switch_off);
        }
        binding.txtContentDateFormat.setText(getCurrentDate());
        binding.txtContentTimeFormat.setText(getCurrentTime());

        binding.btnBack.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });

        binding.btnCameraSound.setOnClickListener(view -> {
            if (this.mSP.getString(this, KeysConstants.CAMERA_POSITION0, "ON").equals("ON")) {
                SavePreferences("CameraSounf", "OFF");
                mSP.setString(this, KeysConstants.CAMERA_POSITION0, "OFF");
                binding.imgSwitch.setImageResource(R.drawable.ic_switch_off);
            } else {
                SavePreferences("CameraSounf", "ON");
                binding.imgSwitch.setImageResource(R.drawable.ic_switch_on);
                mSP.setString(this, KeysConstants.CAMERA_POSITION0, "ON");
            }
        });

        binding.btnDateFormate.setOnClickListener(view -> {
            BottomSheetDateFormat.newInstance().show(this.getSupportFragmentManager(), "Bottomsheet3 Fragment");
        });

        binding.btnTimeFormat.setOnClickListener(view -> {
            BottomSheetTimeFormat.newInstance().show(this.getSupportFragmentManager(), "Bottomsheet4 Fragment");
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.mSP.getString(this, KeysConstants.DATE_POSITION0, DateAdapterCamera.DATE_FORMAT_0));
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        return simpleDateFormat.format(instance.getTime());
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof BottomSheetDateFormat) {
            ((BottomSheetDateFormat) fragment).setonDateListner(this);
        }
        if (fragment instanceof BottomSheetTimeFormat) {
            ((BottomSheetTimeFormat) fragment).setOntimeListner(this);
        }
    }

    private String getCurrentTime() {
//        return new SimpleDateFormat(this.mSP.getString(this.mContext, SP_Keys.DATE_POSITION0, CAR_DateAdapter.DATE_FORMAT_0)).format(Calendar.getInstance().getTime());
        return new SimpleDateFormat(this.mSP.getString(this, KeysConstants.TIME_POSITION0, TimeAdapter.TIME_FORMAT_1)).format(Calendar.getInstance().getTime());
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = this.getSharedPreferences(str, 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    @Override
    public void setOnDate(int i) {
        binding.txtContentDateFormat.setText(getCurrentDate());

    }

    @Override
    public void setonTime(int i) {
        binding.txtContentTimeFormat.setText(getCurrentTime());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}