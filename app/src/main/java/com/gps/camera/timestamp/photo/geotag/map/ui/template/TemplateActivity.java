package com.gps.camera.timestamp.photo.geotag.map.ui.template;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.annotation.Nullable;

import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.Util;
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.DataBase.DatabaseHelper;
import com.gps.camera.timestamp.photo.geotag.map.camera.Default;
import com.gps.camera.timestamp.photo.geotag.map.camera.HelperClass;
import com.gps.camera.timestamp.photo.geotag.map.camera.KeysConstants;
import com.gps.camera.timestamp.photo.geotag.map.camera.adapter.DateAdapterCamera;
import com.gps.camera.timestamp.photo.geotag.map.camera.adapter.TimeAdapter;
import com.gps.camera.timestamp.photo.geotag.map.data.database.DateTimeDB;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivityTemplateBinding;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.template.edit_template.EditTemplateActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.template.edit_template.EditTemplateActivity2;
import com.facebook.appevents.AppEventsConstants;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TemplateActivity extends BaseActivity<ActivityTemplateBinding> {

    String Address;
    String Address2;
    String DateTime;
    String timer;
    String DateTime2;
    String Logo2;
    String Logo1;
    String timer2;

    int Temp_type = 0;

    String compass;
    String compass2;
    String cureent_temp = AppEventsConstants.EVENT_PARAM_VALUE_NO;
    DateTimeDB dateTimeDB;
    String font_style_classic;
    String lagitud;
    String lagitud2;
    String[] mDateArray;
    FirebaseAnalytics mFirebaseAnalytics;
    HelperClass mHelperClass;
    private C1281SP mSP;
    TypedArray mWeatherIcon;
    String magnetic;
    String magnetic2;
    String timeZone2;
    String timeZone;
    String note;
    String note2;
    String numbering;
    String numbering2;

    SharedPreferences sharedPreferences;
    Util util;

    private boolean isEdit = false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_template;
    }

    @Override
    public void initView() {

        AdsManager.INSTANCE.loadAndShowNativeSmallHome(this, AdsManager.INSTANCE.getNATIVE_TEMPLATE(), binding.frNative, binding.cvAds);

        this.util = new Util(this);
        this.cureent_temp = getIntent().getStringExtra("Images");
        this.mDateArray = getResources().getStringArray(R.array.datetime_format_arry);
        this.mSP = new C1281SP(this);
        this.dateTimeDB = new DateTimeDB(this);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        init();
        binding.btnBack.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        binding.btnEdit.setOnClickListener(view -> {
            binding.btnEdit.setVisibility(View.INVISIBLE);
            binding.btnCancel.setVisibility(View.VISIBLE);
            if (showPreferences("Layout").equals("Advance")) {
                binding.icTemplateAdvance.setVisibility(View.VISIBLE);
                binding.icTemplateClassic.setVisibility(View.INVISIBLE);
//                binding.llAdvance.setBackgroundResource(R.drawable.bg_selected_remplate_cancel);
//                binding.llClassic.setBackgroundResource(R.drawable.bg_un_selected_remplate);
            }else {
                binding.icTemplateAdvance.setVisibility(View.INVISIBLE);
                binding.icTemplateClassic.setVisibility(View.VISIBLE);
//                binding.llAdvance.setBackgroundResource(R.drawable.bg_un_selected_remplate);
//                binding.llClassic.setBackgroundResource(R.drawable.bg_selected_remplate_cancel);
            }
            isEdit = true;
        });
        binding.btnCancel.setOnClickListener(view -> {
            actionCancel();
        });

        binding.llAdvance.setOnClickListener(view -> {
            SavePreferences("Layout", "Advance");
            if (isEdit){
//                binding.llAdvance.setBackgroundResource(R.drawable.bg_selected_remplate_cancel);
//                binding.llClassic.setBackgroundResource(R.drawable.bg_un_selected_remplate);
                binding.icTemplateAdvance.setVisibility(View.VISIBLE);
                binding.icTemplateClassic.setVisibility(View.INVISIBLE);
            }else {
//                binding.llAdvance.setBackgroundResource(R.drawable.bg_selected_remplate);
//                binding.llClassic.setBackgroundResource(R.drawable.bg_un_selected_remplate);
                binding.icTemplateAdvance.setVisibility(View.INVISIBLE);
                binding.icTemplateClassic.setVisibility(View.INVISIBLE);
            }
        });

        binding.llClassic.setOnClickListener(view -> {
            SavePreferences("Layout", "Classic");
            if (isEdit){
//                binding.llAdvance.setBackgroundResource(R.drawable.bg_un_selected_remplate);
//                binding.llClassic.setBackgroundResource(R.drawable.bg_selected_remplate_cancel);
                binding.icTemplateClassic.setVisibility(View.VISIBLE);
                binding.icTemplateAdvance.setVisibility(View.INVISIBLE);
            }else {
//                binding.llAdvance.setBackgroundResource(R.drawable.bg_un_selected_remplate);
//                binding.llClassic.setBackgroundResource(R.drawable.bg_selected_remplate);
                binding.icTemplateClassic.setVisibility(View.INVISIBLE);
                binding.icTemplateAdvance.setVisibility(View.INVISIBLE);
            }
        });

        binding.icTemplateAdvance.setOnClickListener(view -> {
            SavePreferences("Layout", "Advance");
            Intent intent = new Intent(TemplateActivity.this, EditTemplateActivity.class);
            intent.putExtra("form", "Temp1");
            startActivityForResult(intent,1);
        });

        binding.icTemplateClassic.setOnClickListener(view -> {
            SavePreferences("Layout", "Classic");
            Intent intent = new Intent(TemplateActivity.this, EditTemplateActivity2.class);
            intent.putExtra("form", "Temp2");
            startActivityForResult(intent,1);
        });
    }

    private void actionCancel(){
        binding.btnCancel.setVisibility(View.INVISIBLE);
        binding.btnEdit.setVisibility(View.VISIBLE);
        binding.icTemplateAdvance.setVisibility(View.INVISIBLE);
        binding.icTemplateClassic.setVisibility(View.INVISIBLE);
        isEdit = false;

        if (showPreferences("Layout").equals("Advance")) {
            binding.icTemplateAdvance.setVisibility(View.VISIBLE);
            binding.icTemplateClassic.setVisibility(View.INVISIBLE);
//            binding.llAdvance.setBackgroundResource(R.drawable.bg_selected_remplate);
//            binding.llClassic.setBackgroundResource(R.drawable.bg_un_selected_remplate);
        }else {
            binding.icTemplateAdvance.setVisibility(View.INVISIBLE);
            binding.icTemplateClassic.setVisibility(View.VISIBLE);
//            binding.llAdvance.setBackgroundResource(R.drawable.bg_un_selected_remplate);
//            binding.llClassic.setBackgroundResource(R.drawable.bg_selected_remplate);
        }
    }
    private void init() {
        Intent intent = getIntent();
        int i = 0;
        if (intent != null) {
            this.Temp_type = intent.getIntExtra("temp_type", 0);
        }
        this.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        if (this.dateTimeDB.getNormalDates().size() == 0) {
            while (true) {
                String[] strArr = this.mDateArray;
                if (i >= strArr.length) {
                    break;
                }
                this.dateTimeDB.insetDate(strArr[i], "", 0, 0, 0, 0);
                i++;
            }
        }
        this.mHelperClass = new HelperClass(this);
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = getSharedPreferences(str, 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }
    private String showPreferences(String str) {
        return getSharedPreferences(str, 0).getString(str, "null");
    }

    private String getdate() {
        String string = this.mSP.getString(this, KeysConstants.DATE_POSITION0, DateAdapterCamera.DATE_FORMAT_1);
        String str = "EEEE, " + string;
        if (string == DateAdapterCamera.DATE_FORMAT_0) {
            str = "EEEE, dd-MM-yyyy";
        } else if (string == DateAdapterCamera.DATE_FORMAT_1) {
            str = "EEEE, MM-dd-yyyy";
        } else if (string == DateAdapterCamera.DATE_FORMAT_2) {
            str = "EEEE, yyyy-MM-dd";
        }
        return new SimpleDateFormat(str).format(Calendar.getInstance().getTime());
    }

    private String getLocalTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.mSP.getString(this, KeysConstants.TIME_POSITION0, TimeAdapter.TIME_FORMAT_1));
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        return simpleDateFormat.format(instance.getTime());
    }

    public void AdvanceTemplate() {
        this.DateTime = showPreferences("DateTime_Temp0");
        this.timer = showPreferences("timezone_Temp0");
        this.timeZone = showPreferences("timezone_Temp0");
        this.Address = showPreferences("Address_Temp0");
        this.lagitud = showPreferences("laglog_Temp0");
        this.magnetic = showPreferences("magnetic_Temp0");
        this.compass = showPreferences("compass_Temp0");
        this.note = showPreferences("hashtag_Temp0");
        this.numbering = showPreferences("number_Temp0");
        this.Logo1 = showPreferences("logo_Temp0");

        binding.layoutTemplate1.timeTv.setText(getLocalTimeZone());

        if (this.DateTime.equals("Yes")) {
           binding.layoutTemplate1.lnDate.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate1.lnDate.setVisibility(View.INVISIBLE);
        }
        if (this.timeZone.equals("Yes")) {
            binding.layoutTemplate1.lnTime.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate1.lnTime.setVisibility(View.INVISIBLE);
        }

        if (this.Logo1.equals("Yes")) {
            binding.layoutTemplate1.imgLogo.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate1.imgLogo.setVisibility(View.INVISIBLE);
        }

        if (this.Address.equals("Yes")) {
            binding.layoutTemplate1.liAddresss.setVisibility(View.VISIBLE);
            binding.layoutTemplate1.addressTv.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate1.liAddresss.setVisibility(View.INVISIBLE);
            binding.layoutTemplate1.addressTv.setVisibility(View.INVISIBLE);
        }
        if (this.lagitud.equals("Yes")) {
            binding.layoutTemplate1.lagitude.setVisibility(View.VISIBLE);
            binding.layoutTemplate1.LatitudeName.setVisibility(View.VISIBLE);
            binding.layoutTemplate1.latitudeTv.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate1.lagitude.setVisibility(View.INVISIBLE);
            binding.layoutTemplate1.LatitudeName.setVisibility(View.INVISIBLE);
            binding.layoutTemplate1.latitudeTv.setVisibility(View.INVISIBLE);
        }

        if (this.magnetic.equals("Yes")) {
            binding.layoutTemplate1.liMagneticField.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate1.liMagneticField.setVisibility(View.INVISIBLE);
        }
        if (this.compass.equals("Yes")) {
            binding.layoutTemplate1.liCompass.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate1.liCompass.setVisibility(View.INVISIBLE);
        }

        if (this.note.equals("Yes")) {
            binding.layoutTemplate1.lyNote.setVisibility(View.VISIBLE);
            binding.layoutTemplate1.hastag.setText(getnotes());
        } else {
            binding.layoutTemplate1.lyNote.setVisibility(View.INVISIBLE);
            binding.layoutTemplate1.hastag.setText(getnotes());
        }
        if (this.numbering.equals("Yes")) {
            binding.layoutTemplate1.lyNumbering.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate1.lyNumbering.setVisibility(View.INVISIBLE);
        }
        if (!showPreferences("Current_Latitude").equals("null"))  binding.layoutTemplate1.LatitudeName.setText(showPreferences("Current_Latitude"));
        if (!showPreferences("Current_Longitude").equals("null"))  binding.layoutTemplate1.latitudeTv.setText(showPreferences("Current_Longitude"));
        binding.layoutTemplate1.addressTv.setText(this.mSP.getString(this, "address", ""));
        if (!showPreferences("Compasss_Data").equals("null")) binding.layoutTemplate1.tvCompass.setText(showPreferences("Compasss_Data"));
        binding.layoutTemplate1.timeLocalTv.setText(String.valueOf(getLocalTime()));
        binding.layoutTemplate1.dateTv.setText(getdate());
        if (!showPreferences("Current_MagField").equals("null")) binding.layoutTemplate1.smapTxtMagnetic.setText(showPreferences("Current_MagField"));
        binding.layoutTemplate1.txtNumbering.setText(showPreferences("Numbering"));


        Typeface typeface = HelperClass.getFontStyle(this, showPreferences("Fonttype"));
        binding.layoutTemplate1.txtWatermark.setTypeface(typeface);
        binding.layoutTemplate1.hastag.setTypeface(typeface);
        binding.layoutTemplate1.txtNumbering.setTypeface(typeface);
        binding.layoutTemplate1.dateTv.setTypeface(typeface);
        binding.layoutTemplate1.timeTv.setTypeface(typeface);
        binding.layoutTemplate1.LongitudeName.setTypeface(typeface);
        binding.layoutTemplate1.LatitudeName.setTypeface(typeface);
        binding.layoutTemplate1.longitudeTv.setTypeface(typeface);
        binding.layoutTemplate1.latitudeTv.setTypeface(typeface);
        binding.layoutTemplate1.addressTv.setTypeface(typeface);
        binding.layoutTemplate1.tvCompass.setTypeface(typeface);
        binding.layoutTemplate1.smapTxtMagnetic.setTypeface(typeface);
        binding.layoutTemplate1.txtTitleAddr.setTypeface(typeface);
    }

    private String getnotes() {
        if (!this.util.showPreferences("key").equals("")) {
            return this.util.showPreferences("key");
        }
        return this.mSP.getString(this, DatabaseHelper.TABLE_NOTE, Default.notes);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isEdit){
            if (showPreferences("Layout").equals("Advance")) {
                binding.icTemplateAdvance.setVisibility(View.VISIBLE);
                binding.icTemplateClassic.setVisibility(View.INVISIBLE);
//                binding.llClassic.setBackgroundResource(R.drawable.bg_un_selected_remplate);
//                binding.llAdvance.setBackgroundResource(R.drawable.bg_selected_remplate_cancel);
            } else {
                binding.icTemplateAdvance.setVisibility(View.INVISIBLE);
                binding.icTemplateClassic.setVisibility(View.VISIBLE);
//                binding.llClassic.setBackgroundResource(R.drawable.bg_selected_remplate_cancel);
//                binding.llAdvance.setBackgroundResource(R.drawable.bg_un_selected_remplate);
            }
        }else {
            binding.icTemplateAdvance.setVisibility(View.INVISIBLE);
            binding.icTemplateClassic.setVisibility(View.INVISIBLE);
            if (showPreferences("Layout").equals("Advance")) {
//                binding.llClassic.setBackgroundResource(R.drawable.bg_un_selected_remplate);
//                binding.llAdvance.setBackgroundResource(R.drawable.bg_selected_remplate);
            } else {
//                binding.llClassic.setBackgroundResource(R.drawable.bg_selected_remplate);
//                binding.llAdvance.setBackgroundResource(R.drawable.bg_un_selected_remplate);
            }
        }

        AdvanceTemplate();
        ClassicTemplate();
        AppsetFont();
    }
    public void AppsetFont() {
        new HelperClass(this);
    }

    public void ClassicTemplate() {
        this.font_style_classic = this.mSP.getString(this, C1281SP.STAMP_FONT_STYLE_CLASSIC, Default.DEFAULT_FONT_STYLE);
        this.DateTime2 = showPreferences("DateTime_Temp2");
        this.Logo2 = showPreferences("logo_Temp2");
        this.timer2 = showPreferences("DateTime_Temp2");
        this.Address2 = showPreferences("Address_Temp2");
        this.lagitud2 = showPreferences("laglog_Temp2");
        this.magnetic2 = showPreferences("magnetic_Temp2");
        this.timeZone2 = showPreferences("timezone_Temp2");
        this.compass2 = showPreferences("compass_Temp2");
        this.note2 = showPreferences("hashtag_Temp2");
        this.numbering2 = showPreferences("number_Temp2");

        if (this.Logo2.equals("Yes")) {
            binding.layoutTemplate2.imgLogo.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate2.imgLogo.setVisibility(View.INVISIBLE);
        }

        binding.layoutTemplate2.timeTv.setText(getLocalTimeZone());
        if (this.DateTime2.equals("Yes")) {
            binding.layoutTemplate2.lnDate.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate2.lnDate.setVisibility(View.INVISIBLE);
        }
        if (this.timeZone2.equals("Yes")) {
            binding.layoutTemplate2.lnTime.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate2.lnTime.setVisibility(View.INVISIBLE);
        }

        if (this.Address2.equals("Yes")) {
            binding.layoutTemplate2.liAddresss.setVisibility(View.VISIBLE);
            binding.layoutTemplate2.addressTv.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate2.liAddresss.setVisibility(View.INVISIBLE);
            binding.layoutTemplate2.addressTv.setVisibility(View.INVISIBLE);
        }
        if (this.lagitud2.equals("Yes")) {
            binding.layoutTemplate2.lagitude.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate2.lagitude.setVisibility(View.INVISIBLE);
        }

        if (this.magnetic2.equals("Yes")) {
            binding.layoutTemplate2.liMagneticField.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate2.liMagneticField.setVisibility(View.INVISIBLE);
        }
        if (this.compass2.equals("Yes")) {
            binding.layoutTemplate2.liCompass.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate2.liCompass.setVisibility(View.INVISIBLE);
        }

        if (this.note2.equals("Yes")) {
            binding.layoutTemplate2.lyNote.setVisibility(View.VISIBLE);
            binding.layoutTemplate2.hastag.setText(getnotes1());
        } else {
            binding.layoutTemplate2.lyNote.setVisibility(View.INVISIBLE);
            binding.layoutTemplate2.hastag.setText(getnotes1());
        }
        if (this.numbering2.equals("Yes")) {
            binding.layoutTemplate2.lyNumbering.setVisibility(View.VISIBLE);
        } else {
            binding.layoutTemplate2.lyNumbering.setVisibility(View.INVISIBLE);
        }
        if (!showPreferences("Current_Latitude").equals("null")) binding.layoutTemplate2.LatitudeName.setText(showPreferences("Current_Latitude"));
        if (!showPreferences("Current_Longitude").equals("null")) binding.layoutTemplate2.latitudeTv.setText(showPreferences("Current_Longitude"));
        binding.layoutTemplate2.addressTv.setText(this.mSP.getString(this, "address", ""));

        if (!showPreferences("Compasss_Data").equals("null")) binding.layoutTemplate2.tvCompass.setText(showPreferences("Compasss_Data"));
        binding.layoutTemplate2.timeLocalTv.setText(String.valueOf(getLocalTime()));
        binding.layoutTemplate2.dateTv.setText(getdate());
        if (!showPreferences("Current_MagField").equals("null")) binding.layoutTemplate2.smapTxtMagnetic.setText(showPreferences("Current_MagField"));
        binding.layoutTemplate2.txtNumbering.setText(showPreferences("Numbering"));

        Typeface typeface = HelperClass.getFontStyle(this, showPreferences("Fonttype"));
        binding.layoutTemplate2.txtWatermark.setTypeface(typeface);
        binding.layoutTemplate2.hastag.setTypeface(typeface);
        binding.layoutTemplate2.txtNumbering.setTypeface(typeface);
        binding.layoutTemplate2.dateTv.setTypeface(typeface);
        binding.layoutTemplate2.timeTv.setTypeface(typeface);
        binding.layoutTemplate2.LongitudeName.setTypeface(typeface);
        binding.layoutTemplate2.LatitudeName.setTypeface(typeface);
        binding.layoutTemplate2.longitudeTv.setTypeface(typeface);
        binding.layoutTemplate2.latitudeTv.setTypeface(typeface);
        binding.layoutTemplate2.addressTv.setTypeface(typeface);
        binding.layoutTemplate2.tvCompass.setTypeface(typeface);
        binding.layoutTemplate2.smapTxtMagnetic.setTypeface(typeface);
        binding.layoutTemplate2.txtTitleAddr.setTypeface(typeface);
    }

    private String getnotes1() {
        if (!this.util.showPreferences("key").equals("")) {
            return this.util.showPreferences("key");
        }
        return this.mSP.getString(this, DatabaseHelper.TABLE_NOTE, Default.notes);
    }
    private String getLocalTimeZone() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.mSP.getString(this, KeysConstants.TIME_POSITION0, TimeAdapter.TIME_FORMAT_1));
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        return simpleDateFormat.format(instance.getTime());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AdsManager.INSTANCE.loadAndShowNativeSmallHome(this, AdsManager.INSTANCE.getNATIVE_TEMPLATE(), binding.frNative, binding.cvAds);
        if (resultCode == Activity.RESULT_FIRST_USER){
            actionCancel();
        }
    }
}