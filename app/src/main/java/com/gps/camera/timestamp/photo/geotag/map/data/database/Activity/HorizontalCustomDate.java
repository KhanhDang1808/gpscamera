package com.gps.camera.timestamp.photo.geotag.map.data.database.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;

import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Model.DateTime;
import com.gps.camera.timestamp.photo.geotag.map.data.database.fragment.BottomSheetDate;
import com.gps.camera.timestamp.photo.geotag.map.data.database.p006UI.CommonFunction;
import com.gps.camera.timestamp.photo.geotag.map.data.database.DateTimeDB;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HorizontalCustomDate extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    String Htextview1;
    String Htextview10;
    public String Htextview10formate;
    String Htextview11;
    public String Htextview11formate;
    String Htextview12;
    public String Htextview12formate;
    String Htextview13;
    public String Htextview13formate;
    String Htextview14;
    public String Htextview14formate;
    String Htextview15;
    public String Htextview15formate;
    String Htextview16;
    public String Htextview16formate;
    public String Htextview1formate;
    String Htextview2;
    public String Htextview2formate;
    String Htextview3;
    public String Htextview3formate;
    String Htextview4;
    public String Htextview4formate;
    String Htextview5;
    public String Htextview5formate;
    String Htextview6;
    public String Htextview6formate;
    String Htextview7;
    public String Htextview7formate;
    String Htextview8;
    public String Htextview8formate;
    String Htextview9;
    public String Htextview9formate;
    ArrayList<String> arrayList;
    ArrayList<String> arrayListFormat;
    RelativeLayout clear_line;
    DateTime dateTimeCurrent;
    DateTimeDB dateTimeDB;
    int date_id;
    String intialDateFormat = "";
    boolean isEdit = false;
    String[] list;
    String[] list1;
    CommonFunction mCommonFunction;
    RelativeLayout mainrl_horizontal;
    CommonFunction moCommonFunction;
    Spinner moHSpinner1;
    Spinner moHSpinner10;
    Spinner moHSpinner11;
    Spinner moHSpinner12;
    Spinner moHSpinner13;
    Spinner moHSpinner14;
    Spinner moHSpinner15;
    Spinner moHSpinner16;
    Spinner moHSpinner2;
    Spinner moHSpinner3;
    Spinner moHSpinner4;
    Spinner moHSpinner5;
    Spinner moHSpinner6;
    Spinner moHSpinner7;
    Spinner moHSpinner8;
    Spinner moHSpinner9;
    public SharePref moSharePref;
    RelativeLayout mrlProgresslayout;
    List<String> plantsList = null;
    List<String> plantsList1 = null;
    public SharePref sharePref;
    public String stCustomFormatHorizontalCustom = "Not_Set";
    public String stCustomFormatHorizontalCustom_Dollar = "Not_Set";
    RelativeLayout toolbar_back;
    RelativeLayout toolbar_done;
    TextView toolbar_title;
    TextView tvDateString1;

    @Override 
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.car_activity_horizontal_custom_date);
        this.dateTimeDB = new DateTimeDB(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.isEdit = extras.getBoolean("isEdit");
            int i = extras.getInt("id_of_date", 1);
            this.date_id = i;
            this.dateTimeCurrent = this.dateTimeDB.getDateById(i);
        }
        this.tvDateString1 = (TextView) findViewById(R.id.tvDateString1);
        this.moHSpinner1 = (Spinner) findViewById(R.id.Vspinner1);
        this.moHSpinner2 = (Spinner) findViewById(R.id.Vspinner2);
        this.moHSpinner3 = (Spinner) findViewById(R.id.Vspinner3);
        this.moHSpinner4 = (Spinner) findViewById(R.id.Vspinner4);
        this.moHSpinner5 = (Spinner) findViewById(R.id.Vspinner5);
        this.moHSpinner6 = (Spinner) findViewById(R.id.Vspinner6);
        this.moHSpinner7 = (Spinner) findViewById(R.id.Vspinner7);
        this.moHSpinner8 = (Spinner) findViewById(R.id.Vspinner8);
        this.moHSpinner9 = (Spinner) findViewById(R.id.Vspinner9);
        this.moHSpinner10 = (Spinner) findViewById(R.id.Vspinner10);
        this.moHSpinner11 = (Spinner) findViewById(R.id.Vspinner11);
        this.moHSpinner12 = (Spinner) findViewById(R.id.Vspinner12);
        this.moHSpinner13 = (Spinner) findViewById(R.id.Vspinner13);
        this.moHSpinner14 = (Spinner) findViewById(R.id.Vspinner14);
        this.moHSpinner15 = (Spinner) findViewById(R.id.Vspinner15);
        this.moHSpinner16 = (Spinner) findViewById(R.id.Vspinner16);
        this.toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        this.toolbar_back = (RelativeLayout) findViewById(R.id.toolbar_back);
        this.clear_line = (RelativeLayout) findViewById(R.id.toolbar_refresh);
        this.toolbar_done = (RelativeLayout) findViewById(R.id.toolbar_done);
        this.mainrl_horizontal = (RelativeLayout) findViewById(R.id.mainrl_horizontal);
        this.mrlProgresslayout = (RelativeLayout) findViewById(R.id.rl_progresslayout);
        this.moSharePref = new SharePref(this);
        this.sharePref = new SharePref(this);
        this.mCommonFunction = new CommonFunction();
        this.moCommonFunction = new CommonFunction();
        this.clear_line.setVisibility(View.VISIBLE);
        this.toolbar_done.setVisibility(View.VISIBLE);
        this.arrayList = new ArrayList<>();
        this.arrayListFormat = new ArrayList<>();
        if (this.isEdit) {
            this.toolbar_title.setText(getString(R.string.edit_date_time));
        } else {
            this.toolbar_title.setText(getString(R.string.create_date_time));
        }
        DateTime dateTime = this.dateTimeCurrent;
        if (dateTime != null) {
            this.intialDateFormat = dateTime.getDate_format();
            if (this.dateTimeCurrent.getDate_format_().equals("null")) {
                this.stCustomFormatHorizontalCustom_Dollar = "dd#/#MM#/#yyyy# #hh#:#mm# #a";
                this.stCustomFormatHorizontalCustom = "dd/MM/yyyy hh:mm  a";
                setprefDate("dd#/#MM#/#yyyy# #hh#:#mm# #a");
            } else {
                this.stCustomFormatHorizontalCustom = this.intialDateFormat;
                this.stCustomFormatHorizontalCustom_Dollar = this.dateTimeCurrent.getDate_format_();
                setprefDate(this.dateTimeCurrent.getDate_format_());
            }
        } else {
            this.stCustomFormatHorizontalCustom_Dollar = "dd#/#MM#/#yyyy# #hh#:#mm# #a";
            this.stCustomFormatHorizontalCustom = "dd/MM/yyyy hh:mm  a";
            setprefDate("dd#/#MM#/#yyyy# #hh#:#mm# #a");
        }
        this.list = new String[]{"", "Days", "E(" + this.moCommonFunction.getDate(System.currentTimeMillis(), ExifInterface.LONGITUDE_EAST) + ")", "EEEE(" + this.moCommonFunction.getDate(System.currentTimeMillis(), "EEEE") + ")", "EEE(" + this.moCommonFunction.getDate(System.currentTimeMillis(), "EEE") + ")", "Date", "date(" + this.mCommonFunction.getDate(System.currentTimeMillis(), "dd") + ")", "date(" + this.mCommonFunction.getDate(System.currentTimeMillis(), "ddth") + ")", "Months", "MM(" + this.moCommonFunction.getDate(System.currentTimeMillis(), "MM") + ")", "MMM(" + this.moCommonFunction.getDate(System.currentTimeMillis(), "MMM") + ")", "MMMM(" + this.moCommonFunction.getDate(System.currentTimeMillis(), "MMMM") + ")", "Years", "yy(" + this.moCommonFunction.getDate(System.currentTimeMillis(), "yy") + ")", "yyyy(" + this.moCommonFunction.getDate(System.currentTimeMillis(), "yyyy") + ")", "Times", " Hour(" + this.mCommonFunction.getDate(System.currentTimeMillis(), "HH") + ")", "hour(" + this.mCommonFunction.getDate(System.currentTimeMillis(), "hh") + ")", "Minute(" + this.mCommonFunction.getDate(System.currentTimeMillis(), "mm") + ")", "second(" + this.mCommonFunction.getDate(System.currentTimeMillis(), "ss") + ")", "a(AM/PM)", ":", "/", ",", ".", "-", "SPACE", "CLEAR", "CLEAR ALL"};
        this.list1 = new String[]{"", "Days", ExifInterface.LONGITUDE_EAST, "EEEE", "EEE", "Date", "dd", "", "Months", "MM", "MMM", "MMMM", "Years", "yy", "yyyy", "Times", "HH", "hh", "mm", "ss", "a", ":", "/", ",", ".", "-", "SPACE", "CLEAR", "CLEAR ALL"};
        this.plantsList = new ArrayList(Arrays.asList(this.list));
        this.plantsList1 = new ArrayList(Arrays.asList(this.list1));
        this.mrlProgresslayout.setVisibility(View.VISIBLE);
        onClicksH();
        initialH();
        onclicks();
        new Handler().postDelayed(new Runnable() {
            

            public void run() {
                HorizontalCustomDate.this.setSpinnerAdaptersH();
            }
        }, 300);
    }

    public void onclicks() {
        this.toolbar_back.setOnClickListener(this);
        this.toolbar_done.setOnClickListener(this);
        this.clear_line.setOnClickListener(this);
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void discardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.discard_message));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            

            public void onClick(DialogInterface dialogInterface, int i) {
                HorizontalCustomDate.this.save();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            

            public void onClick(DialogInterface dialogInterface, int i) {
                HorizontalCustomDate.this.finish();
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void doBack() {
        if (!this.isEdit) {
            finish();
        } else if (!this.intialDateFormat.equals(this.stCustomFormatHorizontalCustom)) {
            discardDialog();
        } else {
            finish();
        }
    }

    public void save() {
        if (!TextUtils.isEmpty(this.stCustomFormatHorizontalCustom) && !TextUtils.isEmpty(this.stCustomFormatHorizontalCustom_Dollar)) {
            new C1281SP(this).setString(this, "date_format_temp", this.stCustomFormatHorizontalCustom);
            BottomSheetDate.selected_pos = this.stCustomFormatHorizontalCustom;
        }
        if (this.isEdit) {
            if (TextUtils.isEmpty(this.stCustomFormatHorizontalCustom) || TextUtils.isEmpty(this.stCustomFormatHorizontalCustom_Dollar)) {
                Snackbar.make(this.mainrl_horizontal, getResources().getString(R.string.error_null_dates), 0).show();
            } else if (!this.dateTimeDB.IsDateAvailable(this.stCustomFormatHorizontalCustom)) {
                this.dateTimeDB.updateDate(this.date_id, this.stCustomFormatHorizontalCustom, this.stCustomFormatHorizontalCustom_Dollar);
                for (int i = 0; i < BottomSheetDate.dateTimes_horizontal.size(); i++) {
                    if (BottomSheetDate.dateTimes_horizontal.get(i).getDate_id() == this.date_id) {
                        BottomSheetDate.dateTimes_horizontal.get(i).setDate_format(this.stCustomFormatHorizontalCustom);
                        BottomSheetDate.dateTimes_horizontal.get(i).setDate_format_(this.stCustomFormatHorizontalCustom_Dollar);
                        BottomSheetDate.dateTimes_horizontal.get(i).setDate_type(1);
                        BottomSheetDate.dateTimes_horizontal.get(i).setDate_custom(1);
                        if (this.dateTimeCurrent != null) {
                            BottomSheetDate.dateTimes_horizontal.get(i).setIsSelect(this.dateTimeCurrent.getIsSelect());
                        } else {
                            BottomSheetDate.dateTimes_horizontal.get(i).setIsSelect(0);
                        }
                        BottomSheetDate.dateTimes_horizontal.get(i).setDate_pos(-1);
                    }
                }
                finish();
            } else {
                finish();
            }
        } else if (TextUtils.isEmpty(this.stCustomFormatHorizontalCustom) || TextUtils.isEmpty(this.stCustomFormatHorizontalCustom_Dollar)) {
            Snackbar.make(this.mainrl_horizontal, getResources().getString(R.string.error_null_dates), 0).show();
        } else if (!this.dateTimeDB.IsDateAvailable(this.stCustomFormatHorizontalCustom)) {
            this.dateTimeDB.insetDate(this.stCustomFormatHorizontalCustom, this.stCustomFormatHorizontalCustom_Dollar, 1, 1, 0, -1);
            int idFromFormat = this.dateTimeDB.getIdFromFormat(this.stCustomFormatHorizontalCustom);
            DateTime dateTime = new DateTime();
            if (idFromFormat != -1) {
                dateTime.setDate_id(idFromFormat);
            }
            dateTime.setDate_format(this.stCustomFormatHorizontalCustom);
            dateTime.setDate_format_(this.stCustomFormatHorizontalCustom_Dollar);
            dateTime.setDate_type(1);
            dateTime.setDate_custom(1);
            dateTime.setIsSelect(0);
            dateTime.setDate_pos(-1);
            BottomSheetDate.dateTimes_horizontal.add(0, dateTime);
            finish();
        } else {
            finish();
        }
    }

    public void clearALLH() {
        this.Htextview1 = "";
        this.Htextview2 = "";
        this.Htextview3 = "";
        this.Htextview4 = "";
        this.Htextview5 = "";
        this.Htextview6 = "";
        this.Htextview7 = "";
        this.Htextview8 = "";
        this.Htextview9 = "";
        this.Htextview10 = "";
        this.Htextview11 = "";
        this.Htextview12 = "";
        this.Htextview13 = "";
        this.Htextview14 = "";
        this.Htextview15 = "";
        this.Htextview16 = "";
        this.Htextview1formate = "";
        this.Htextview2formate = "";
        this.Htextview3formate = "";
        this.Htextview4formate = "";
        this.Htextview5formate = "";
        this.Htextview6formate = "";
        this.Htextview7formate = "";
        this.Htextview8formate = "";
        this.Htextview9formate = "";
        this.Htextview10formate = "";
        this.Htextview11formate = "";
        this.Htextview12formate = "";
        this.Htextview13formate = "";
        this.Htextview14formate = "";
        this.Htextview15formate = "";
        this.Htextview16formate = "";
        this.arrayList.add(0, "");
        this.arrayList.add(1, this.Htextview2);
        this.arrayList.add(2, this.Htextview3);
        this.arrayList.add(3, this.Htextview4);
        this.arrayList.add(4, this.Htextview5);
        this.arrayList.add(5, this.Htextview6);
        this.arrayList.add(6, this.Htextview7);
        this.arrayList.add(7, this.Htextview8);
        this.arrayList.add(8, this.Htextview9);
        this.arrayList.add(9, this.Htextview10);
        this.arrayList.add(10, this.Htextview11);
        this.arrayList.add(11, this.Htextview12);
        this.arrayList.add(12, this.Htextview13);
        this.arrayList.add(13, this.Htextview14);
        this.arrayList.add(14, this.Htextview15);
        this.arrayList.add(15, this.Htextview16);
        this.arrayListFormat.add(0, this.Htextview1formate);
        this.arrayListFormat.add(1, this.Htextview2formate);
        this.arrayListFormat.add(2, this.Htextview3formate);
        this.arrayListFormat.add(3, this.Htextview4formate);
        this.arrayListFormat.add(4, this.Htextview5formate);
        this.arrayListFormat.add(5, this.Htextview6formate);
        this.arrayListFormat.add(6, this.Htextview7formate);
        this.arrayListFormat.add(7, this.Htextview8formate);
        this.arrayListFormat.add(8, this.Htextview9formate);
        this.arrayListFormat.add(9, this.Htextview10formate);
        this.arrayListFormat.add(10, this.Htextview11formate);
        this.arrayListFormat.add(11, this.Htextview12formate);
        this.arrayListFormat.add(12, this.Htextview13formate);
        this.arrayListFormat.add(13, this.Htextview14formate);
        this.arrayListFormat.add(14, this.Htextview15formate);
        this.arrayListFormat.add(15, this.Htextview16formate);
        printDateH();
        this.moHSpinner1.setSelection(0);
        this.moHSpinner2.setSelection(0);
        this.moHSpinner3.setSelection(0);
        this.moHSpinner4.setSelection(0);
        this.moHSpinner5.setSelection(0);
        this.moHSpinner6.setSelection(0);
        this.moHSpinner7.setSelection(0);
        this.moHSpinner8.setSelection(0);
        this.moHSpinner9.setSelection(0);
        this.moHSpinner10.setSelection(0);
        this.moHSpinner11.setSelection(0);
        this.moHSpinner12.setSelection(0);
        this.moHSpinner13.setSelection(0);
        this.moHSpinner14.setSelection(0);
        this.moHSpinner15.setSelection(0);
        this.moHSpinner16.setSelection(0);
    }

    public void onClicksH() {
        this.moHSpinner1.setOnItemSelectedListener(this);
        this.moHSpinner2.setOnItemSelectedListener(this);
        this.moHSpinner3.setOnItemSelectedListener(this);
        this.moHSpinner4.setOnItemSelectedListener(this);
        this.moHSpinner5.setOnItemSelectedListener(this);
        this.moHSpinner6.setOnItemSelectedListener(this);
        this.moHSpinner7.setOnItemSelectedListener(this);
        this.moHSpinner8.setOnItemSelectedListener(this);
        this.moHSpinner9.setOnItemSelectedListener(this);
        this.moHSpinner10.setOnItemSelectedListener(this);
        this.moHSpinner11.setOnItemSelectedListener(this);
        this.moHSpinner12.setOnItemSelectedListener(this);
        this.moHSpinner13.setOnItemSelectedListener(this);
        this.moHSpinner14.setOnItemSelectedListener(this);
        this.moHSpinner15.setOnItemSelectedListener(this);
        this.moHSpinner16.setOnItemSelectedListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.toolbar_back) {
            doBack();
        } else if (id == R.id.toolbar_done) {
            save();
        } else if (id == R.id.toolbar_refresh) {
            clearALLH();
        }
    }

    public void printDateH() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 15; i++) {
            if (!TextUtils.isEmpty(this.arrayList.get(i))) {
                sb.append(this.arrayList.get(i));
            }
        }
        if (!TextUtils.isEmpty(sb.toString())) {
            this.tvDateString1.setText(sb.toString());
        } else {
            this.tvDateString1.setText("");
        }
    }

    public void initialH() {
        this.arrayList.add(this.Htextview1);
        this.arrayList.add(this.Htextview2);
        this.arrayList.add(this.Htextview3);
        this.arrayList.add(this.Htextview4);
        this.arrayList.add(this.Htextview5);
        this.arrayList.add(this.Htextview6);
        this.arrayList.add(this.Htextview7);
        this.arrayList.add(this.Htextview8);
        this.arrayList.add(this.Htextview9);
        this.arrayList.add(this.Htextview10);
        this.arrayList.add(this.Htextview11);
        this.arrayList.add(this.Htextview12);
        this.arrayList.add(this.Htextview13);
        this.arrayList.add(this.Htextview14);
        this.arrayList.add(this.Htextview15);
        this.arrayList.add(this.Htextview16);
        this.arrayListFormat.add(this.Htextview1formate);
        this.arrayListFormat.add(this.Htextview2formate);
        this.arrayListFormat.add(this.Htextview3formate);
        this.arrayListFormat.add(this.Htextview4formate);
        this.arrayListFormat.add(this.Htextview5formate);
        this.arrayListFormat.add(this.Htextview6formate);
        this.arrayListFormat.add(this.Htextview7formate);
        this.arrayListFormat.add(this.Htextview8formate);
        this.arrayListFormat.add(this.Htextview9formate);
        this.arrayListFormat.add(this.Htextview10formate);
        this.arrayListFormat.add(this.Htextview11formate);
        this.arrayListFormat.add(this.Htextview12formate);
        this.arrayListFormat.add(this.Htextview13formate);
        this.arrayListFormat.add(this.Htextview14formate);
        this.arrayListFormat.add(this.Htextview15formate);
        this.arrayListFormat.add(this.Htextview16formate);
    }

    @Override 
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        Spinner spinner = (Spinner) adapterView;
        if (this.toolbar_done.getVisibility() == View.GONE) {
            this.toolbar_done.setVisibility(View.VISIBLE);
        }
        int id = spinner.getId();
        List<String> list2 = this.plantsList;
        int selectedItemPosition = spinner.getSelectedItemPosition();
        if (id == R.id.Vspinner1) {
            if (list2.get(selectedItemPosition).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview1 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview1formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(0, this.Htextview1);
            setlistHFormat(0, this.Htextview1formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner10) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview10 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview10formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(9, this.Htextview10);
            setlistHFormat(9, this.Htextview10formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner11) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview11 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview11formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(10, this.Htextview11);
            setlistHFormat(10, this.Htextview11formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner12) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview12 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview12formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(11, this.Htextview12);
            setlistHFormat(11, this.Htextview12formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner13) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview13 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview13formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(12, this.Htextview13);
            setlistHFormat(12, this.Htextview13formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner14) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview14 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview14formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(13, this.Htextview14);
            setlistHFormat(13, this.Htextview14formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner15) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview15 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview15formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(14, this.Htextview15);
            setlistHFormat(14, this.Htextview15formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner16) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview16 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview16formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(15, this.Htextview16);
            setlistHFormat(15, this.Htextview16formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner2) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview2 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview2formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(1, this.Htextview2);
            setlistHFormat(1, this.Htextview2formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner3) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview3 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview3formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(2, this.Htextview3);
            setlistHFormat(2, this.Htextview3formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner4) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview4 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview4formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(3, this.Htextview4);
            setlistHFormat(3, this.Htextview4formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner5) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview5 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview5formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(4, this.Htextview5);
            setlistHFormat(4, this.Htextview5formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner6) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview6 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview6formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(5, this.Htextview6);
            setlistHFormat(5, this.Htextview6formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner7) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview7 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview7formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(6, this.Htextview7);
            setlistHFormat(6, this.Htextview7formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id == R.id.Vspinner8) {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview8 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview8formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(7, this.Htextview8);
            setlistHFormat(7, this.Htextview8formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        } else if (id != R.id.Vspinner9) {
        } else {
            if (list2.get(i).equals("CLEAR ALL")) {
                clearALLH();
                return;
            }
            this.Htextview9 = this.moCommonFunction.setdate(list2.get(i));
            this.Htextview9formate = this.moCommonFunction.getFormat(list2.get(i));
            setlistH(8, this.Htextview9);
            setlistHFormat(8, this.Htextview9formate);
            printDateH();
            this.stCustomFormatHorizontalCustom = setformatH();
            this.stCustomFormatHorizontalCustom_Dollar = setformatH_dollar();
        }
    }

    public void setlistH(int i, String str) {
        this.arrayList.set(i, str);
    }

    public void setlistHFormat(int i, String str) {
        this.arrayListFormat.set(i, str);
    }

    public String setformatH() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 15; i++) {
            if (!TextUtils.isEmpty(this.arrayListFormat.get(i))) {
                sb.append(this.arrayListFormat.get(i));
            }
        }
        return sb.toString();
    }

    public String setformatH_dollar() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 15; i++) {
            if (!TextUtils.isEmpty(this.arrayListFormat.get(i))) {
                sb.append(this.arrayListFormat.get(i) + "#");
            }
        }
        return sb.toString();
    }

    @SuppressLint("ResourceType")
    public void setSpinnerAdaptersH() {
        ArrayAdapter<String> r0 = new ArrayAdapter<String>(this, R.layout.car_spinner_item, this.plantsList) {
            

            public boolean isEnabled(int i) {
                return (i == 0 || i == 1 || i == 5 || i == 8 || i == 12 || i == 15) ? false : true;
            }

            public View getDropDownView(int i, View view, ViewGroup viewGroup) {
                View dropDownView = super.getDropDownView(i, view, viewGroup);
                TextView textView = (TextView) dropDownView;
                if (i == 0 || i == 1 || i == 5 || i == 8 || i == 12 || i == 15) {
                    textView.setTextColor(-7829368);
                } else {
                    textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                }
                return dropDownView;
            }

            public int getCount() {
                return super.getCount();
            }
        };
        r0.setDropDownViewResource(R.layout.car_spinner_item);
        new ArrayAdapter(this, 17367048, this.list).setDropDownViewResource(17367049);
        this.moHSpinner1.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH1())) {
            if (this.moSharePref.getH1().equals(" ")) {
                this.moHSpinner1.setSelection(26);
                this.arrayList.set(0, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner1.setSelection(this.plantsList1.indexOf(this.moSharePref.getH1()));
                this.arrayList.set(0, this.moCommonFunction.setdate(this.moSharePref.getH1()));
            }
        }
        this.moHSpinner2.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH2())) {
            if (this.moSharePref.getH2().equals(" ")) {
                this.moHSpinner2.setSelection(26);
                this.arrayList.set(1, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner2.setSelection(this.plantsList1.indexOf(this.moSharePref.getH2()));
                this.arrayList.set(1, this.moCommonFunction.setdate(this.moSharePref.getH2()));
            }
        }
        this.moHSpinner3.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH3())) {
            if (this.moSharePref.getH3().equals(" ")) {
                this.moHSpinner3.setSelection(26);
                this.arrayList.set(2, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner3.setSelection(this.plantsList1.indexOf(this.moSharePref.getH3()));
                this.arrayList.set(2, this.moCommonFunction.setdate(this.moSharePref.getH3()));
            }
        }
        this.moHSpinner4.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH4())) {
            if (this.moSharePref.getH4().equals(" ")) {
                this.moHSpinner4.setSelection(26);
                this.arrayList.set(3, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner4.setSelection(this.plantsList1.indexOf(this.moSharePref.getH4()));
                this.arrayList.set(3, this.moCommonFunction.setdate(this.moSharePref.getH4()));
            }
        }
        this.moHSpinner5.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH5())) {
            if (this.moSharePref.getH5().equals(" ")) {
                this.moHSpinner5.setSelection(26);
                this.arrayList.set(4, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner5.setSelection(this.plantsList1.indexOf(this.moSharePref.getH5()));
                this.arrayList.set(4, this.moCommonFunction.setdate(this.moSharePref.getH5()));
            }
        }
        this.moHSpinner6.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH6())) {
            if (this.moSharePref.getH6().equals(" ")) {
                this.moHSpinner6.setSelection(26);
                this.arrayList.set(5, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner6.setSelection(this.plantsList1.indexOf(this.moSharePref.getH6()));
                this.arrayList.set(5, this.moCommonFunction.setdate(this.moSharePref.getH6()));
            }
        }
        this.moHSpinner7.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH7())) {
            if (this.moSharePref.getH7().equals(" ")) {
                this.moHSpinner7.setSelection(26);
                this.arrayList.set(6, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner7.setSelection(this.plantsList1.indexOf(this.moSharePref.getH7()));
                this.arrayList.set(6, this.moCommonFunction.setdate(this.moSharePref.getH7()));
            }
        }
        this.moHSpinner8.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH8())) {
            if (this.moSharePref.getH8().equals(" ")) {
                this.moHSpinner8.setSelection(26);
                this.arrayList.set(7, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner8.setSelection(this.plantsList1.indexOf(this.moSharePref.getH8()));
                this.arrayList.set(7, this.moCommonFunction.setdate(this.moSharePref.getH8()));
            }
        }
        this.moHSpinner9.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH9())) {
            if (this.moSharePref.getH9().equals(" ")) {
                this.moHSpinner9.setSelection(26);
                this.arrayList.set(8, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner9.setSelection(this.plantsList1.indexOf(this.moSharePref.getH9()));
                this.arrayList.set(8, this.moCommonFunction.setdate(this.moSharePref.getH9()));
            }
        }
        this.moHSpinner10.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH10())) {
            if (this.moSharePref.getH10().equals(" ")) {
                this.moHSpinner10.setSelection(26);
                this.arrayList.set(9, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner10.setSelection(this.plantsList1.indexOf(this.moSharePref.getH10()));
                this.arrayList.set(9, this.moCommonFunction.setdate(this.moSharePref.getH10()));
            }
        }
        this.moHSpinner11.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH11())) {
            if (this.moSharePref.getH11().equals(" ")) {
                this.moHSpinner11.setSelection(26);
                this.arrayList.set(10, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner11.setSelection(this.plantsList1.indexOf(this.moSharePref.getH11()));
                this.arrayList.set(10, this.moCommonFunction.setdate(this.moSharePref.getH11()));
            }
        }
        this.moHSpinner12.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH12())) {
            if (this.moSharePref.getH12().equals(" ")) {
                this.moHSpinner12.setSelection(26);
                this.arrayList.set(11, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner12.setSelection(this.plantsList1.indexOf(this.moSharePref.getH12()));
                this.arrayList.set(11, this.moCommonFunction.setdate(this.moSharePref.getH12()));
            }
        }
        this.moHSpinner13.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH13())) {
            if (this.moSharePref.getH13().equals(" ")) {
                this.moHSpinner13.setSelection(26);
                this.arrayList.set(12, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner13.setSelection(this.plantsList1.indexOf(this.moSharePref.getH13()));
                this.arrayList.set(12, this.moCommonFunction.setdate(this.moSharePref.getH13()));
            }
        }
        this.moHSpinner14.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH14())) {
            if (this.moSharePref.getH14().equals(" ")) {
                this.moHSpinner14.setSelection(26);
                this.arrayList.set(13, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner14.setSelection(this.plantsList1.indexOf(this.moSharePref.getH14()));
                this.arrayList.set(13, this.moCommonFunction.setdate(this.moSharePref.getH4()));
            }
        }
        this.moHSpinner15.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH15())) {
            if (this.moSharePref.getH15().equals(" ")) {
                this.moHSpinner15.setSelection(26);
                this.arrayList.set(14, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner15.setSelection(this.plantsList1.indexOf(this.moSharePref.getH15()));
                this.arrayList.set(14, this.moCommonFunction.setdate(this.moSharePref.getH15()));
            }
        }
        this.moHSpinner16.setAdapter((SpinnerAdapter) r0);
        if (!TextUtils.isEmpty(this.moSharePref.getH16())) {
            if (this.moSharePref.getH16().equals(" ")) {
                this.moHSpinner16.setSelection(26);
                this.arrayList.set(15, this.moCommonFunction.setdate("SPACE"));
            } else {
                this.moHSpinner16.setSelection(this.plantsList1.indexOf(this.moSharePref.getH16()));
                this.arrayList.set(15, this.moCommonFunction.setdate(this.moSharePref.getH16()));
            }
        }
        firstinitH();
        this.mrlProgresslayout.setVisibility(View.GONE);
    }

    public void firstinitH() {
        if (this.moSharePref.getHorizontal()) {
            if (!TextUtils.isEmpty(this.moSharePref.getH1()) || !TextUtils.isEmpty(this.moSharePref.getH2()) || !TextUtils.isEmpty(this.moSharePref.getH3()) || !TextUtils.isEmpty(this.moSharePref.getH4()) || !TextUtils.isEmpty(this.moSharePref.getH5()) || !TextUtils.isEmpty(this.moSharePref.getH6()) || !TextUtils.isEmpty(this.moSharePref.getH7()) || !TextUtils.isEmpty(this.moSharePref.getH8()) || !TextUtils.isEmpty(this.moSharePref.getH9()) || !TextUtils.isEmpty(this.moSharePref.getH10()) || !TextUtils.isEmpty(this.moSharePref.getH11()) || !TextUtils.isEmpty(this.moSharePref.getH12()) || !TextUtils.isEmpty(this.moSharePref.getH13()) || !TextUtils.isEmpty(this.moSharePref.getH14()) || !TextUtils.isEmpty(this.moSharePref.getH15()) || !TextUtils.isEmpty(this.moSharePref.getH16())) {
                this.moSharePref.setHorizontal(false);
            }
            if (this.moSharePref.getHorizontal()) {
                this.moHSpinner1.setSelection(6);
                this.moHSpinner5.setSelection(14);
                this.moHSpinner7.setSelection(16);
                this.moHSpinner8.setSelection(21);
                this.moHSpinner9.setSelection(18);
                this.moHSpinner11.setSelection(20);
                this.moHSpinner10.setSelection(26);
                this.moHSpinner6.setSelection(26);
                this.moHSpinner4.setSelection(22);
                this.moHSpinner3.setSelection(9);
                this.moHSpinner2.setSelection(22);
            }
        }
    }

    public void setprefDate(String str) {
        String[] split = str.split("#");
        int length = split.length;
        this.moSharePref.setH1(null);
        this.moSharePref.setH2(null);
        this.moSharePref.setH3(null);
        this.moSharePref.setH4(null);
        this.moSharePref.setH5(null);
        this.moSharePref.setH6(null);
        this.moSharePref.setH7(null);
        this.moSharePref.setH8(null);
        this.moSharePref.setH9(null);
        this.moSharePref.setH10(null);
        this.moSharePref.setH11(null);
        this.moSharePref.setH12(null);
        this.moSharePref.setH13(null);
        this.moSharePref.setH14(null);
        this.moSharePref.setH15(null);
        this.moSharePref.setH16(null);
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                this.moSharePref.setH1(split[0]);
            }
            if (i == 1) {
                this.moSharePref.setH2(split[1]);
            }
            if (i == 2) {
                this.moSharePref.setH3(split[2]);
            }
            if (i == 3) {
                this.moSharePref.setH4(split[3]);
            }
            if (i == 4) {
                this.moSharePref.setH5(split[4]);
            }
            if (i == 5) {
                this.moSharePref.setH6(split[5]);
            }
            if (i == 6) {
                this.moSharePref.setH7(split[6]);
            }
            if (i == 7) {
                this.moSharePref.setH8(split[7]);
            }
            if (i == 8) {
                this.moSharePref.setH9(split[8]);
            }
            if (i == 9) {
                this.moSharePref.setH10(split[9]);
            }
            if (i == 10) {
                this.moSharePref.setH11(split[10]);
            }
            if (i == 11) {
                this.moSharePref.setH12(split[11]);
            }
            if (i == 12) {
                this.moSharePref.setH13(split[12]);
            }
            if (i == 13) {
                this.moSharePref.setH14(split[13]);
            }
            if (i == 14) {
                this.moSharePref.setH15(split[14]);
            }
            if (i == 15) {
                this.moSharePref.setH16(split[15]);
            }
        }
    }
}
