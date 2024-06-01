package com.gps.camera.timestamp.photo.geotag.map.ui.template.edit_template;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.gps.camera.timestamp.photo.geotag.map.fragment.MapTypeBottomSheetfragment;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.Util;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.DataBase.DatabaseHelper;
import com.gps.camera.timestamp.photo.geotag.map.camera.Default;
import com.gps.camera.timestamp.photo.geotag.map.camera.HelperClass;
import com.gps.camera.timestamp.photo.geotag.map.camera.KeysConstants;
import com.gps.camera.timestamp.photo.geotag.map.camera.adapter.DateAdapterCamera;
import com.gps.camera.timestamp.photo.geotag.map.camera.adapter.TimeAdapter;
import com.gps.camera.timestamp.photo.geotag.map.data.database.DateTimeDB;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivityEditTemplateBinding;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;
import com.facebook.appevents.AppEventsConstants;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTemplateActivity extends BaseActivity<ActivityEditTemplateBinding> {

    String Address;
    int Count = 0;
    String DateTime;
    String Logo;
    int Temp_type = 0;
    String Timezone;

    String compass;
    String cureent_temp = AppEventsConstants.EVENT_PARAM_VALUE_NO;
    DateTimeDB dateTimeDB;
    HelperClass helperClass;
    String humidity;
    boolean isAddress = true;
    boolean isCompass = true;
    boolean isDateTime = false;
    boolean isHumidity = true;
    boolean isLatLng = false;
    boolean isMagneticField = true;
    boolean isNumbering = true;
    boolean isWeather = false;
    boolean isaccuracy = true;
    boolean isaltitude = true;
    boolean islogo = true;
    boolean isnotes = true;
    boolean ispressure = true;
    boolean istimezone = false;
    boolean iswind = false;
    String lagitud;
    String[] mDateArray;
    FirebaseAnalytics mFirebaseAnalytics;
    HelperClass mHelperClass;
    private C1281SP mSP;
    TypedArray mWeatherIcon;
    String magnetic;
    MapTypeBottomSheetfragment mapTypeBottomSheetfragment;
    String note;
    String numbering;
    String presurre;
    SharedPreferences sharedPreferences;
    Util util;

    public String positionStrText = "Top";
    public int positionStr = 1;

    private String typeStr;
    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_template;
    }

    @Override
    public void initView() {
        this.util = new Util(this);
        this.cureent_temp = getIntent().getStringExtra("Images");
        this.mDateArray = getResources().getStringArray(R.array.datetime_format_arry);
        this.mSP = new C1281SP(this);
        this.dateTimeDB = new DateTimeDB(this);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.mWeatherIcon = getResources().obtainTypedArray(R.array.wI);

        binding.txtTitleTemplate.setText(getString(R.string.Template1_new));
        init();
        listener();
        setTeplateAll();

    }

    public void setTeplateAll() {
        typeStr = showPreferences("Fonttype");
        this.helperClass = new HelperClass(this);
        Typeface typeface = HelperClass.getFontStyle(this, showPreferences("Fonttype"));

        binding.layoutTemplate.hastag.setTypeface(typeface);
        binding.layoutTemplate.addressTv.setTypeface(typeface);
        binding.layoutTemplate.txtWatermark.setTypeface(typeface);
        binding.layoutTemplate.txtNumbering.setTypeface(typeface);
        binding.layoutTemplate.dateTv.setTypeface(typeface);
        binding.layoutTemplate.timeTv.setTypeface(typeface);
        binding.layoutTemplate.LongitudeName.setTypeface(typeface);
        binding.layoutTemplate.LatitudeName.setTypeface(typeface);
        binding.layoutTemplate.longitudeTv.setTypeface(typeface);
        binding.layoutTemplate.latitudeTv.setTypeface(typeface);
        binding.layoutTemplate.txtAddressTitle.setTypeface(typeface);
        binding.layoutTemplate.tvCompass.setTypeface(typeface);
        binding.layoutTemplate.smapTxtMagnetic.setTypeface(typeface);
        binding.a.setTypeface(typeface);
        binding.tvAddress.setTypeface(typeface);
        binding.txtAddress.setTypeface(typeface);
        binding.ttLotLog.setTypeface(typeface);
        binding.txtLatlog.setTypeface(typeface);
        binding.ttDatetime.setTypeface(typeface);
        binding.txtDatetime.setTypeface(typeface);
        binding.ttTimezone.setTypeface(typeface);
        binding.txtTimezone.setTypeface(typeface);
        binding.ttNumbering.setTypeface(typeface);
        binding.tvNumbering.setTypeface(typeface);
        binding.ttLogo.setTypeface(typeface);
        binding.ttHastag.setTypeface(typeface);
        binding.txtNotes.setTypeface(typeface);
        binding.ttComapss.setTypeface(typeface);
        binding.txtCompass.setTypeface(typeface);
        binding.ttMagneticf.setTypeface(typeface);
        binding.txtMagnetic.setTypeface(typeface);
        binding.txtFont.setTypeface(typeface);
        binding.ttStampposition.setTypeface(typeface);

    }

    @Override
    public void onResume() {
        super.onResume();
        setTeplateApp();
    }

    //TODO: CÁI THÔNG TIN NÓ ĐANG DEMO NHIỀU
    public void setTeplateApp() {
        binding.layoutTemplate.LatitudeName.setText(showPreferences("Current_Latitude"));
        binding.layoutTemplate.latitudeTv.setText(showPreferences("Current_Longitude"));
        binding.txtLatlog.setText(showPreferences("Current_Latitude")+"/"+showPreferences("Current_Longitude"));
        binding.layoutTemplate.addressTv.setText(this.mSP.getString(this, "address", ""));
        binding.txtAddress.setText(this.mSP.getString(this, "address", ""));
        binding.txtCompass.setText(showPreferences("Compasss_Data"));
        binding.layoutTemplate.tvCompass.setText(showPreferences("Compasss_Data"));
        binding.layoutTemplate.timeTv.setText(String.valueOf(getLocalTimeZone()));
        binding.layoutTemplate.dateTv.setText(getdate());
        binding.txtDatetime.setText(getdate());
        binding.layoutTemplate.smapTxtMagnetic.setText(showPreferences("Current_MagField"));
        binding.txtMagnetic.setText(showPreferences("Current_MagField"));
        binding.layoutTemplate.txtNumbering.setText(showPreferences("Numbering"));
        binding.tvNumbering.setText(getString(R.string.travel_diaries)+" "+showPreferences("Numbering"));
    }

    private String getLocalTimeZone() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.mSP.getString(this, KeysConstants.TIME_POSITION0, TimeAdapter.TIME_FORMAT_1));
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        return simpleDateFormat.format(instance.getTime());
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private String Str = "01";
    public void listener() {
        binding.btnBack.setOnClickListener(v -> {
            HelperClass.hideSoftKeyboard(EditTemplateActivity.this, v);
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        });

        binding.btnSave.setOnClickListener(v -> {
            mSP.setString(EditTemplateActivity.this, "pos_type_temp1", positionStrText);
            util.SavePreferences("positionX1", String.valueOf(positionStr));
            if (istimezone){
                SavePreferences("timezone_Temp0", "Yes");
            }else {
                SavePreferences("timezone_Temp0", "No");
            }
            if (isDateTime){
                SavePreferences("DateTime_Temp0", "Yes");
            }else {
                SavePreferences("DateTime_Temp0", "No");
            }
            if (isAddress){
                SavePreferences("Address_Temp0", "Yes");
            }else {
                SavePreferences("Address_Temp0", "No");
            }
            if (isLatLng){
                SavePreferences("laglog_Temp0", "Yes");
            }else {
                SavePreferences("laglog_Temp0", "No");
            }
            if (islogo){
                SavePreferences("logo_Temp0", "Yes");
            }else {
                SavePreferences("logo_Temp0", "No");
            }
            if (isMagneticField){
                SavePreferences("magnetic_Temp0", "Yes");
            }else {
                SavePreferences("magnetic_Temp0", "No");
            }
            if (isCompass){
                SavePreferences("compass_Temp0", "Yes");
            }else {
                SavePreferences("compass_Temp0", "No");
            }
            if (isnotes){
                SavePreferences("hashtag_Temp0", "Yes");
            }else {
                SavePreferences("hashtag_Temp0", "No");
            }
            if (isNumbering){
                SavePreferences("number_Temp0", "Yes");
            }else {
                SavePreferences("number_Temp0", "No");
            }
            SavePreferences("Fonttype", typeStr);
            SavePreferences("Pos", Str);
            Intent intent = new Intent();
            setResult(Activity.RESULT_FIRST_USER, intent);
            finish();
        });

        binding.linTimezone.setOnClickListener(v -> {
            if (!istimezone) {
                binding.layoutTemplate.lnTimeClock.setVisibility(View.VISIBLE);
                Count++;
                istimezone = true;
                binding.imgTimezone.setImageResource(R.drawable.ic_select_btn);
                return;
            }

            binding.layoutTemplate.lnTimeClock.setVisibility(View.INVISIBLE);
            Count--;
            istimezone = false;
            binding.imgTimezone.setImageResource(R.drawable.ic_uncheck_box_tple);
        });
        binding.linFontstyle.setOnClickListener(v -> {
            mapTypeBottomSheetfragment = new MapTypeBottomSheetfragment(binding.layoutTemplate.hastag, binding.layoutTemplate.addressTv,
                    binding.layoutTemplate.txtWatermark, binding.layoutTemplate.txtNumbering, binding.layoutTemplate.dateTv, binding.layoutTemplate.timeTv,
                    binding.layoutTemplate.LongitudeName,
                    binding.layoutTemplate.LatitudeName, binding.layoutTemplate.longitudeTv, binding.layoutTemplate.latitudeTv,
                    binding.layoutTemplate.txtAddressTitle, binding.layoutTemplate.tvCompass, binding.layoutTemplate.smapTxtMagnetic,
                    binding.a, binding.tvAddress, binding.txtAddress, binding.ttLotLog, binding.txtLatlog,
                    binding.ttDatetime, binding.txtDatetime, binding.ttTimezone, binding.txtTimezone, binding.ttNumbering, binding.tvNumbering,
                    binding.ttLogo, binding.ttHastag, binding.txtNotes, binding.ttComapss, binding.txtCompass, binding.ttMagneticf, binding.txtMagnetic, binding.txtFont,
                    binding.ttStampposition);
            mapTypeBottomSheetfragment.setListener(new MapTypeBottomSheetfragment.ListenerData() {
                @Override
                public void listenerData(String i, String data) {
                    typeStr = data;
                    Str = i;
                }

                @Override
                public void listenerDataPosition(String i, int position) {

                }
            });

            if (!mapTypeBottomSheetfragment.isAdded() && mapTypeBottomSheetfragment != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 9);
                bundle.putString("data", typeStr);
                mapTypeBottomSheetfragment.setArguments(bundle);
                mapTypeBottomSheetfragment.show(getSupportFragmentManager(), MapTypeBottomSheetfragment.TAG);
            }
        });

        binding.linStampposition.setOnClickListener(v -> {
            mapTypeBottomSheetfragment = new MapTypeBottomSheetfragment(getIntent().getStringExtra("form"));
            mapTypeBottomSheetfragment.setListener(new MapTypeBottomSheetfragment.ListenerData() {
                @Override
                public void listenerData(String i, String data) {

                }

                @Override
                public void listenerDataPosition(String i, int position) {
                    positionStrText = i;
                    positionStr = position;
                }
            });
            if (!mapTypeBottomSheetfragment.isAdded() && mapTypeBottomSheetfragment != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 4);
                bundle.putString("data", typeStr);
                bundle.putString("strStamp",positionStrText);
                mapTypeBottomSheetfragment.setArguments(bundle);
                mapTypeBottomSheetfragment.show(getSupportFragmentManager(), MapTypeBottomSheetfragment.TAG);
            }
        });

        binding.linDatetime.setOnClickListener(v -> {
            if (!isDateTime) {
                binding.layoutTemplate.lnDate.setVisibility(View.VISIBLE);
                Count++;
                isDateTime = true;
                binding.imgDatetime.setImageResource(R.drawable.ic_select_btn);
                return;
            }
            binding.layoutTemplate.lnDate.setVisibility(View.INVISIBLE);
            Count--;
            isDateTime = false;
            binding.imgDatetime.setImageResource(R.drawable.ic_uncheck_box_tple);
        });

        binding.btnAddress.setOnClickListener(v -> {
            if (!isAddress) {
                binding.layoutTemplate.liAddress.setVisibility(View.VISIBLE);
                binding.layoutTemplate.addressTv.setVisibility(View.VISIBLE);
                Count++;
                isAddress = true;
                binding.imgAddress.setImageResource(R.drawable.ic_select_btn);
                return;
            }
            binding.layoutTemplate.liAddress.setVisibility(View.INVISIBLE);
            binding.layoutTemplate.addressTv.setVisibility(View.INVISIBLE);
            Count--;
            isAddress = false;
            binding.imgAddress.setImageResource(R.drawable.ic_uncheck_box_tple);
        });

        binding.linLatlog.setOnClickListener(v -> {
            if (!isLatLng) {
                binding.layoutTemplate.LatitudeName.setVisibility(View.VISIBLE);
                binding.layoutTemplate.lagitude.setVisibility(View.VISIBLE);
                Count++;
                isLatLng = true;
                binding.imgLat.setImageResource(R.drawable.ic_select_btn);
                return;
            }
            binding.layoutTemplate.LatitudeName.setVisibility(View.INVISIBLE);
            binding.layoutTemplate.lagitude.setVisibility(View.INVISIBLE);
            Count--;
            isLatLng = false;
            binding.imgLat.setImageResource(R.drawable.ic_uncheck_box_tple);
        });

        binding.btnLogo.setOnClickListener(v -> {
            if (!islogo) {
                binding.layoutTemplate.imgLogo.setVisibility(View.VISIBLE);
                Count++;
                islogo = true;
                binding.imgLogo.setImageResource(R.drawable.ic_select_btn);
                return;
            }
            binding.layoutTemplate.imgLogo.setVisibility(View.INVISIBLE);
            Count--;
            islogo = false;
            binding.imgLogo.setImageResource(R.drawable.ic_uncheck_box_tple);
        });

        binding.btnMagnetic.setOnClickListener(v -> {
            if (!isMagneticField) {
                binding.layoutTemplate.liMagneticField.setVisibility(View.VISIBLE);
                Count++;
                isMagneticField = true;
                binding.imgMagneticc.setImageResource(R.drawable.ic_select_btn);
                return;
            }
            binding.layoutTemplate.liMagneticField.setVisibility(View.INVISIBLE);
            Count--;
            isMagneticField = false;
            binding.imgMagneticc.setImageResource(R.drawable.ic_uncheck_box_tple);
        });

        binding.linCompass.setOnClickListener(v -> {
            if (!isCompass) {
                binding.layoutTemplate.liCompass.setVisibility(View.VISIBLE);
                Count++;
                isCompass = true;
                binding.imgCompass.setImageResource(R.drawable.ic_select_btn);
                return;
            }
            binding.layoutTemplate.liCompass.setVisibility(View.INVISIBLE);
            Count--;
            isCompass = false;
            binding.imgCompass.setImageResource(R.drawable.ic_uncheck_box_tple);
        });

        binding.btnNotes.setOnClickListener(v -> {
            if (!isnotes) {
                binding.layoutTemplate.lyNote.setVisibility(View.VISIBLE);
                Count++;
                isnotes = true;
                binding.imgNotes.setImageResource(R.drawable.ic_select_btn);
                return;
            }
            binding.layoutTemplate.lyNote.setVisibility(View.INVISIBLE);
            Count--;
            isnotes = false;
            binding.imgNotes.setImageResource(R.drawable.ic_uncheck_box_tple);
        });

        binding.linNumbering.setOnClickListener(v -> {
            if (!isNumbering) {
                binding.layoutTemplate.lyNumbering.setVisibility(View.VISIBLE);
                Count++;
                isNumbering = true;
                binding.imgNumbering.setImageResource(R.drawable.ic_select_btn);
                return;
            }
            binding.layoutTemplate.lyNumbering.setVisibility(View.INVISIBLE);
            Count--;
            isNumbering = false;
            binding.imgNumbering.setImageResource(R.drawable.ic_uncheck_box_tple);
        });

    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null) {
            this.Temp_type = intent.getIntExtra("temp_type", 0);
        }
        this.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        if (this.dateTimeDB.getNormalDates().size() == 0) {
            int i = 0;
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
        binding.txtTitleTemplate.setText(getString(R.string.Template1_new));

        setNotes();
        this.DateTime = showPreferences("DateTime_Temp0");
        this.Address = showPreferences("Address_Temp0");
        this.lagitud = showPreferences("laglog_Temp0");
        this.Timezone = showPreferences("timezone_Temp0");
        this.Logo = showPreferences("logo_Temp0");
        this.humidity = showPreferences("humidity_Temp0");
        this.presurre = showPreferences("pressure_Temp0");
        this.magnetic = showPreferences("magnetic_Temp0");
        this.compass = showPreferences("compass_Temp0");
        this.note = showPreferences("hashtag_Temp0");
        this.numbering = showPreferences("number_Temp0");

        positionStrText = mSP.getString(EditTemplateActivity.this, "pos_type_temp1", "Top");;

        if (Timezone.equals("Yes")){
            istimezone = true;
            binding.layoutTemplate.lnTimeClock.setVisibility(View.VISIBLE);
            binding.imgTimezone.setImageResource(R.drawable.ic_select_btn);
        }else {
            istimezone = false;
            binding.layoutTemplate.lnTimeClock.setVisibility(View.INVISIBLE);
            binding.imgTimezone.setImageResource(R.drawable.ic_uncheck_box_tple);
        }
        if (this.Address.equals("Yes")) {
            this.isAddress = true;
            binding.layoutTemplate.liAddress.setVisibility(View.VISIBLE);
            binding.imgAddress.setImageResource(R.drawable.ic_select_btn);
        } else {
            this.isAddress = false;
            binding.layoutTemplate.liAddress.setVisibility(View.INVISIBLE);
            binding.imgAddress.setImageResource(R.drawable.ic_uncheck_box_tple);
        }
        if (this.compass.equals("Yes")) {
            this.isCompass = true;
            binding.layoutTemplate.liCompass.setVisibility(View.VISIBLE);
            binding.imgCompass.setImageResource(R.drawable.ic_select_btn);
        } else {
            this.isCompass = false;
            binding.layoutTemplate.liCompass.setVisibility(View.INVISIBLE);
            binding.imgCompass.setImageResource(R.drawable.ic_uncheck_box_tple);
        }
        if (this.DateTime.equals("Yes")) {
            this.isDateTime = true;
            binding.layoutTemplate.lnDate.setVisibility(View.VISIBLE);
            binding.imgDatetime.setImageResource(R.drawable.ic_select_btn);
        } else {
            this.isDateTime = false;
            binding.layoutTemplate.lnDate.setVisibility(View.INVISIBLE);
            binding.imgDatetime.setImageResource(R.drawable.ic_uncheck_box_tple);
        }

        if (this.numbering.equals("Yes")) {
            this.isNumbering = true;
            binding.layoutTemplate.lyNumbering.setVisibility(View.VISIBLE);
            binding.imgNumbering.setImageResource(R.drawable.ic_select_btn);
        } else {
            this.isNumbering = false;
            binding.layoutTemplate.lyNumbering.setVisibility(View.INVISIBLE);
            binding.imgNumbering.setImageResource(R.drawable.ic_uncheck_box_tple);
        }
        if (this.lagitud.equals("Yes")) {
            this.isLatLng = true;
            binding.layoutTemplate.lagitude.setVisibility(View.VISIBLE);
            binding.imgLat.setImageResource(R.drawable.ic_select_btn);
        } else {
            this.isLatLng = false;
            binding.layoutTemplate.lagitude.setVisibility(View.INVISIBLE);
            binding.imgLat.setImageResource(R.drawable.ic_uncheck_box_tple);
        }
        if (this.magnetic.equals("Yes")) {
            this.isMagneticField = true;
            binding.layoutTemplate.liMagneticField.setVisibility(View.VISIBLE);
            binding.imgMagneticc.setImageResource(R.drawable.ic_select_btn);
        } else {
            this.isMagneticField = false;
            binding.layoutTemplate.liMagneticField.setVisibility(View.INVISIBLE);
            binding.imgMagneticc.setImageResource(R.drawable.ic_uncheck_box_tple);
        }

        if (this.Logo.equals("Yes")) {
            this.islogo = true;
            binding.layoutTemplate.imgLogo.setVisibility(View.VISIBLE);
            binding.imgLogo.setImageResource(R.drawable.ic_select_btn);
        } else {
            this.islogo = false;
            binding.layoutTemplate.imgLogo.setVisibility(View.INVISIBLE);
            binding.imgLogo.setImageResource(R.drawable.ic_uncheck_box_tple);
        }
        if (this.note.equals("Yes")) {
            this.isnotes = true;
            binding.layoutTemplate.lyNote.setVisibility(View.VISIBLE);
            binding.layoutTemplate.hastag.setText(getnotes());
            binding.imgNotes.setImageResource(R.drawable.ic_select_btn);
            return;
        }
        this.isnotes = false;
        binding.layoutTemplate.lyNote.setVisibility(View.INVISIBLE);
        binding.layoutTemplate.hastag.setText(getnotes());
        binding.imgNotes.setImageResource(R.drawable.ic_uncheck_box_tple);
    }

    private void setNotes() {
        binding.txtNotes.setText(getnotes());
    }

    private String getnotes() {
        if (!this.util.showPreferences("key").equals("")) {
            return this.util.showPreferences("key");
        }
        return this.mSP.getString(this, DatabaseHelper.TABLE_NOTE, Default.notes);
    }

    private String showPreferences(String str) {
        return getSharedPreferences(str, 0).getString(str, "Images");
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = getSharedPreferences(str, 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    private String getLocalTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.mSP.getString(this, KeysConstants.TIME_POSITION0, TimeAdapter.TIME_FORMAT_1));
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        return simpleDateFormat.format(instance.getTime());
    }
}