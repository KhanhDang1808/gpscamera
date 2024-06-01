package com.gps.camera.timestamp.photo.geotag.map.camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import java.util.ArrayList;


public class C1281SP {
    public static final String ACCURACY_VALUE = "Accuracy_value";
    public static final String ADS_STATUS = "ads_status";
    public static final String ALTITUDE_VALUE = "Altitude_value";
    public static final String COMPASS_FOUND = "compass_found";
    public static final String DATE_FORMAT = "date_format";
    public static final String FOLDER_NAME = "folder_naame";
    public static final String FOLDER_PATH = "folder_path";
    public static final String IMAGE_URI = "image_uri";
    public static final String IS_LOCATION_CHANGED = "is_location_chnaged";
    public static final String IS_ORIGINAL_IMAGE = "is_original_image";
    public static final String LATITUDE = "latitude_1";
    public static final String LOCATION_SELECTION = "loction_selection";
    public static final String LOCATION_SELECTION_TITLE = "loction_selection_title";
    public static final String LOCATION_TYPE = "location_type";
    public static final String LOC_LINE_1_ADDRESS = "loc_address_line_1";
    public static final String LOC_LINE_2_CITY = "loc_city";
    public static final String LOC_LINE_3_STATE = "loc_state";
    public static final String LOC_LINE_4_COUNTRY = "loc_country";
    public static final String LONGITUDE = "longitude_1";
    public static final String MAGNETIC_FIELD_VALUE = "magnetic_field_value";
    public static final String MAP_TYPE = "map_type";
    public static final String OPEN_TIME = "open_time";
    public static final String PRESSURE_VALUE = "Pressure_value";
    public static final String STAMP_FONT_STYLE_CLASSIC = "stamp_font_style_classic";
    public static final String TEMPRETURE_VALUE = "temprature_value";
    public static final String WIND_VALUE = "wind_value";
    SharedPreferences myPreference;

    public C1281SP(Context context) {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        if (context != null) {
            try {
                this.myPreference = PreferenceManager.getDefaultSharedPreferences(context);
            } catch (Throwable th) {
                StrictMode.setThreadPolicy(allowThreadDiskReads);
                throw th;
            }
        }
        StrictMode.setThreadPolicy(allowThreadDiskReads);
    }

    public static String getRatioPos_Key(String str) {
        return "resolution_pos" + str;
    }

    public static String getRatio_Key(String str) {
        return "camera_resolution_" + str;
    }

    public void setString(Context context, String str, String str2) {
        if (this.myPreference == null) {
            this.myPreference = context.getSharedPreferences("COMMON_SHARED", 0);
        }
        SharedPreferences.Editor edit = this.myPreference.edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public String getString(Context context, String str, String str2) {
        if (this.myPreference == null) {
            this.myPreference = context.getSharedPreferences("COMMON_SHARED", 0);
        }
        return this.myPreference.getString(str, str2);
    }

    public void setInteger(Context context, String str, Integer num) {
        if (this.myPreference == null) {
            this.myPreference = context.getSharedPreferences("COMMON_SHARED", 0);
        }
        SharedPreferences.Editor edit = this.myPreference.edit();
        edit.putInt(str, num.intValue());
        edit.apply();
    }

    public Integer getInteger(Context context, String str) {
        if (this.myPreference == null) {
            this.myPreference = context.getSharedPreferences("COMMON_SHARED", 0);
        }
        return Integer.valueOf(this.myPreference.getInt(str, 0));
    }

    public Float getFloat(Context context, String str) {
        if (this.myPreference == null) {
            this.myPreference = context.getSharedPreferences("COMMON_SHARED", 0);
        }
        return Float.valueOf(this.myPreference.getFloat(str, 0.0f));
    }

    public Integer getInteger(Context context, String str, int i) {
        if (this.myPreference == null) {
            this.myPreference = context.getSharedPreferences("COMMON_SHARED", 0);
        }
        return Integer.valueOf(this.myPreference.getInt(str, i));
    }

    public void setBoolean(Context context, String str, Boolean bool) {
        if (this.myPreference == null) {
            this.myPreference = context.getSharedPreferences("COMMON_SHARED", 0);
        }
        SharedPreferences.Editor edit = this.myPreference.edit();
        edit.putBoolean(str, bool.booleanValue());
        edit.apply();
    }

    public Boolean getBoolean(Context context, String str, Boolean bool) {
        if (this.myPreference == null) {
            this.myPreference = context.getSharedPreferences("COMMON_SHARED", 0);
        }
        return Boolean.valueOf(this.myPreference.getBoolean(str, bool.booleanValue()));
    }

    public ArrayList<String> loadImageuriArray(String str, Context context) {
        int i = this.myPreference.getInt(str + "_size", 0);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(this.myPreference.getString(str + "_" + i2, null));
        }
        return arrayList;
    }

    public boolean saveImageuriArray(ArrayList<String> arrayList, String str, Context context) {
        SharedPreferences.Editor edit = this.myPreference.edit();
        edit.putInt(str + "_size", arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            edit.putString(str + "_" + i, arrayList.get(i));
        }
        return edit.commit();
    }
}
