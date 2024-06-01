package com.gps.camera.timestamp.photo.geotag.map.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.mgrs.Angle;
import com.gps.camera.timestamp.photo.geotag.map.mgrs.MGRSCoord;
import com.gps.camera.timestamp.photo.geotag.map.camera.utils.CommonCoordinates;
import com.gps.camera.timestamp.photo.geotag.map.ui.gps_camera.GpsCameraActivity;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.otaliastudios.cameraview.size.AspectRatio;
import com.otaliastudios.cameraview.size.Size;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import cz.msebera.android.httpclient.protocol.HTTP;

public class HelperClass {
    public static final String FONT_STYLE_OPEN_TIME = "FONT_STYLE_OPEN_TIME";
    public static String IS_PURCHESH_OR_NOT = "isPurcheshOrNot";
    public static final String MAP_DATA_OPEN_TIME = "MAP_DATA_OPEN_TIME";
    public static final String STAMP_POS_OPEN_TIME = "STAMP_POS_OPEN_TIME";
    public static Context activity = null;

    

    public static GpsCameraActivity main_activity;
    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public HelperClass(GpsCameraActivity cAR_CameraActivity) {
        main_activity = cAR_CameraActivity;
    }

    public HelperClass(Context context) {
        activity = context;
    }

    public static Boolean check_internet(Context context) {
        boolean z = false;
        if (context == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        return pattern.matcher(str).matches();
    }

    public static String LatLngLocation(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && isNumeric(str) && isNumeric(str2)) {
            double parseDouble = Double.parseDouble(str);
            double parseDouble2 = Double.parseDouble(str2);
            str2 = (parseDouble2 >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'E' : 'W') + " " + CommonCoordinates.replaceDelimiters(Location.convert(parseDouble2, Location.FORMAT_SECONDS), 8);
            str = (parseDouble >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'N' : 'S') + " " + CommonCoordinates.replaceDelimiters(Location.convert(parseDouble, Location.FORMAT_SECONDS), 8);
        }
        return str + "_" + str2;
    }

    public static String encodeTobase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
    }

    public static String getCelcius(float f) {
        return "" + f + "° C";
    }

    public static String getFahrenheit(float f) {
        return "" + (((Math.round(f) * 9) / 5) + 32) + "° F";
    }

    public static Bitmap decodeBase64(String str) {
        byte[] decode = Base64.decode(str, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    public static Typeface getFontStyle(Context context, String str) {
        File file = new File(context.getFilesDir(), "font/" + str);
        if (!file.exists()) {
            return Typeface.createFromAsset(context.getResources().getAssets(), str);
        }
        try {
            return Typeface.createFromFile(file);
        } catch (Exception unused) {
            return Typeface.createFromAsset(context.getResources().getAssets(), Default.DEFAULT_FONT_STYLE);
        }
    }

    public static String getConvertPressure(Context context, int i) {
        double floatValue = (double) new C1281SP(context).getFloat(context, C1281SP.PRESSURE_VALUE).floatValue();
        if (i == 0) {
            return new DecimalFormat("##.##").format(floatValue) + " hpa";
        }
        if (i == 1) {
            return new DecimalFormat("##.##").format(floatValue * 0.75d) + " mmhg";
        }
        if (i != 2) {
            return "";
        }
        return new DecimalFormat("##.##").format(floatValue * 0.03d) + " inHg";
    }

    public static String getwindConvert(Context context, int i) {
        double floatValue = (double) new C1281SP(context).getFloat(context, C1281SP.WIND_VALUE).floatValue();
        if (i == 0) {
            return new DecimalFormat("##.##").format(floatValue * 3.6d) + " km/h";
        }
        if (i == 1) {
            return new DecimalFormat("##.##").format(floatValue * 2.23694d) + " mph";
        }
        if (i == 2) {
            return new DecimalFormat("##.##").format(floatValue) + " m/s";
        }
        if (i != 3) {
            return "";
        }
        return new DecimalFormat("##.##").format(floatValue * 1.94384d) + " kt";
    }

    public int setposratio(Context context, List<Size> list) {
        int i = 0;
        if (list != null) {
            int i2 = 20;
            while (i < list.size()) {
                if (getAspectRatioMPString(context.getResources(), list.get(i).getWidth(), list.get(i).getHeight(), true).contains("4:3")) {
                    i2 = i;
                }
                i++;
            }
            i = i2;
        }
        return i == 20 ? list.size() - 1 : i;
    }

    public static String getAspectRatioMPString(Resources resources, int i, int i2, boolean z) {
        return AspectRatio.of(i, i2).flip() + " | " + getMPString(i, i2) + getBurstString(resources, z);
    }
    public static String getAspectRatioMPStringNew( int i, int i2) {
        return AspectRatio.of(i, i2).flip().toString();
    }
    private static String getMPString(int i, int i2) {
        return formatFloatToString(((float) (i * i2)) / 1000000.0f) + "MP";
    }

    private static String formatFloatToString(float f) {
        int i = (int) f;
        if (f == ((float) i)) {
            return Integer.toString(i);
        }
        return String.format(Locale.getDefault(), "%.2f", Float.valueOf(f));
    }

    private static String getBurstString(Resources resources, boolean z) {
        return z ? "" : ", " + resources.getString(R.string.no_burst);
    }

    public static void showRateDialogs(Activity activity2) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + activity2.getPackageName()));
        activity2.startActivity(intent);
    }

    public static void showShareDialogs(Activity activity2) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", "Hey check out this Awesome " + activity2.getResources().getString(R.string.app_name) + " App here \n\nhttp://play.google.com/store/apps/details?id=" + activity2.getPackageName());
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        activity2.startActivity(intent);
        Toast.makeText(activity2, "Thank You for Sharing", Toast.LENGTH_LONG).show();
    }

    public static String getAltitudeconvert(Context context, int i) {
        double floatValue = (double) new C1281SP(context).getFloat(context, C1281SP.ALTITUDE_VALUE).floatValue();
        if (i == 0) {
            return new DecimalFormat("##.##").format(floatValue) + " m";
        }
        if (i != 1) {
            return "";
        }
        double d = floatValue * 39.370078d;
        return (((int) (d - ((double) (((int) (d / 63360.0d)) * 63360)))) / 12) + " ft";
    }

    public static String getAccuracy(Context context, int i) {
        double floatValue = (double) new C1281SP(context).getFloat(context, C1281SP.ACCURACY_VALUE).floatValue();
        if (i == 0) {
            return new DecimalFormat("##.##").format(floatValue) + " m";
        }
        if (i != 1) {
            return "";
        }
        double d = floatValue * 39.370078d;
        return (((int) (d - ((double) (((int) (d / 63360.0d)) * 63360)))) / 12) + " ft";
    }

    private static String replaceDelimins(String str, int i) {
        String replaceFirst = str.replaceFirst(":", "");
        int indexOf = replaceFirst.indexOf(".") + 1 + i;
        return indexOf < replaceFirst.length() ? replaceFirst.substring(0, indexOf) : replaceFirst;
    }

    private static String replaceDelimiterss(String str, int i) {
        String replaceFirst = str.replaceFirst(":", "").replaceFirst(":", "");
        int indexOf = replaceFirst.indexOf(".") + 1 + i;
        return indexOf < replaceFirst.length() ? replaceFirst.substring(0, indexOf) : replaceFirst;
    }

    public String getLatLong(Context context, int i) {
        char c;
        C1281SP sp = new C1281SP(context);
        String string = sp.getString(context, C1281SP.LATITUDE, "");
        String string2 = sp.getString(context, C1281SP.LONGITUDE, "");
        Double.valueOf((double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        Double.valueOf((double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        if (string == null || string.isEmpty() || string2 == null || string2.isEmpty()) {
            return null;
        }
        if (string.contains(",")) {
            string = string.replace(",", ".");
        }
        if (string2.contains(",")) {
            string2 = string2.replace(",", ".");
        }
        Double valueOf = Double.valueOf(string);
        Double valueOf2 = Double.valueOf(string2);
        if (valueOf.doubleValue() == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE || valueOf2.doubleValue() == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            return null;
        }
        switch (i) {
            case 0:
                return "Lat " + string + " Long " + string2;
            case 1:
                return "Lat " + string + "° Long " + string2 + "°";
            case 2:
                return "Lat " + string + " " + (valueOf.doubleValue() >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'N' : 'S') + " Long " + string2 + " " + (valueOf2.doubleValue() >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'E' : 'W');
            case 3:
                return "Lat " + replaceDelimins(Location.convert(valueOf.doubleValue(), Location.FORMAT_MINUTES), 8) + " Long " + replaceDelimins(Location.convert(valueOf2.doubleValue(), Location.FORMAT_MINUTES), 8);
            case 4:
                return "Lat " + ((valueOf.doubleValue() >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'N' : 'S') + " " + CommonCoordinates.replaceDelimiters(Location.convert(valueOf.doubleValue(), Location.FORMAT_SECONDS), 8)) + " Long " + (valueOf2.doubleValue() >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'E' : 'W') + " " + CommonCoordinates.replaceDelimiters(Location.convert(valueOf2.doubleValue(), Location.FORMAT_SECONDS), 8);
            case 5:
                return "Lat " + (replaceDelimiterss(Location.convert(valueOf.doubleValue(), Location.FORMAT_SECONDS), 8) + " " + (valueOf.doubleValue() >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'N' : 'S')) + " Long " + replaceDelimiterss(Location.convert(valueOf2.doubleValue(), Location.FORMAT_SECONDS), 8) + " " + (valueOf2.doubleValue() >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'E' : 'W');
            case 6:
                int floor = (int) Math.floor((valueOf2.doubleValue() / 6.0d) + 31.0d);
                if (valueOf.doubleValue() < -72.0d) {
                    c = 'C';
                } else if (valueOf.doubleValue() < -64.0d) {
                    c = 'D';
                } else if (valueOf.doubleValue() < -56.0d) {
                    c = 'E';
                } else if (valueOf.doubleValue() < -48.0d) {
                    c = 'F';
                } else if (valueOf.doubleValue() < -40.0d) {
                    c = 'G';
                } else if (valueOf.doubleValue() < -32.0d) {
                    c = 'H';
                } else if (valueOf.doubleValue() < -24.0d) {
                    c = 'J';
                } else if (valueOf.doubleValue() < -16.0d) {
                    c = 'K';
                } else if (valueOf.doubleValue() < -8.0d) {
                    c = 'L';
                } else if (valueOf.doubleValue() < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    c = 'M';
                } else if (valueOf.doubleValue() < 8.0d) {
                    c = 'N';
                } else if (valueOf.doubleValue() < 16.0d) {
                    c = 'P';
                } else if (valueOf.doubleValue() < 24.0d) {
                    c = 'Q';
                } else if (valueOf.doubleValue() < 32.0d) {
                    c = 'R';
                } else if (valueOf.doubleValue() < 40.0d) {
                    c = 'S';
                } else if (valueOf.doubleValue() < 48.0d) {
                    c = 'T';
                } else if (valueOf.doubleValue() < 56.0d) {
                    c = 'U';
                } else if (valueOf.doubleValue() < 64.0d) {
                    c = 'V';
                } else {
                    c = valueOf.doubleValue() < 72.0d ? 'W' : 'X';
                }
                double d = (((double) ((floor * 6) - 183)) * 3.141592653589793d) / 180.0d;
                double round = ((double) Math.round(((((((Math.log(((Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d) * Math.sin(((valueOf2.doubleValue() * 3.141592653589793d) / 180.0d) - d)) + 1.0d) / (1.0d - (Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d) * Math.sin(((valueOf2.doubleValue() * 3.141592653589793d) / 180.0d) - d)))) * 0.5d) * 0.9996d) * 6399593.62d) / Math.pow((Math.pow(0.0820944379d, 2.0d) * Math.pow(Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d), 2.0d)) + 1.0d, 0.5d)) * (((((Math.pow(0.0820944379d, 2.0d) / 2.0d) * Math.pow(Math.log(((Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d) * Math.sin(((valueOf2.doubleValue() * 3.141592653589793d) / 180.0d) - d)) + 1.0d) / (1.0d - (Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d) * Math.sin(((valueOf2.doubleValue() * 3.141592653589793d) / 180.0d) - d)))) * 0.5d, 2.0d)) * Math.pow(Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d), 2.0d)) / 3.0d) + 1.0d)) + 500000.0d) * 100.0d)) * 0.01d;
                double atan = (((((Math.atan(Math.tan((valueOf.doubleValue() * 3.141592653589793d) / 180.0d) / Math.cos(((valueOf2.doubleValue() * 3.141592653589793d) / 180.0d) - d)) - ((valueOf.doubleValue() * 3.141592653589793d) / 180.0d)) * 0.9996d) * 6399593.625d) / Math.sqrt((Math.pow(Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d), 2.0d) * 0.006739496742d) + 1.0d)) * ((Math.pow(Math.log(((Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d) * Math.sin(((valueOf2.doubleValue() * 3.141592653589793d) / 180.0d) - d)) + 1.0d) / (1.0d - (Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d) * Math.sin(((valueOf2.doubleValue() * 3.141592653589793d) / 180.0d) - d)))) * 0.5d, 2.0d) * 0.003369748371d * Math.pow(Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d), 2.0d)) + 1.0d)) + ((((((valueOf.doubleValue() * 3.141592653589793d) / 180.0d) - ((((valueOf.doubleValue() * 3.141592653589793d) / 180.0d) + (Math.sin(((valueOf.doubleValue() * 2.0d) * 3.141592653589793d) / 180.0d) / 2.0d)) * 0.005054622556d)) + (((((((valueOf.doubleValue() * 3.141592653589793d) / 180.0d) + (Math.sin(((valueOf.doubleValue() * 2.0d) * 3.141592653589793d) / 180.0d) / 2.0d)) * 3.0d) + (Math.sin(((valueOf.doubleValue() * 2.0d) * 3.141592653589793d) / 180.0d) * Math.pow(Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d), 2.0d))) * 4.258201531E-5d) / 4.0d)) - ((((((((((valueOf.doubleValue() * 3.141592653589793d) / 180.0d) + (Math.sin(((valueOf.doubleValue() * 2.0d) * 3.141592653589793d) / 180.0d) / 2.0d)) * 3.0d) + (Math.sin(((valueOf.doubleValue() * 2.0d) * 3.141592653589793d) / 180.0d) * Math.pow(Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d), 2.0d))) * 5.0d) / 4.0d) + ((Math.sin(((valueOf.doubleValue() * 2.0d) * 3.141592653589793d) / 180.0d) * Math.pow(Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d), 2.0d)) * Math.pow(Math.cos((valueOf.doubleValue() * 3.141592653589793d) / 180.0d), 2.0d))) * 1.674057895E-7d) / 3.0d)) * 6397033.7875500005d);
                if (c < 'M') {
                    atan += 1.0E7d;
                }
                return floor + "" + c + "  " + String.valueOf(((double) Math.round(atan * 100.0d)) * 0.01d) + " " + (valueOf2.doubleValue() >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'E' : 'W') + " " + (round + " " + (valueOf.doubleValue() >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'N' : 'S'));
            case 7:
                String mGRSCoord = MGRSCoord.fromLatLon(Angle.fromDegrees(valueOf.doubleValue()), Angle.fromDegrees(valueOf2.doubleValue())).toString();
                String[] split = mGRSCoord.split(" ");
                if (split.length <= 2) {
                    return mGRSCoord;
                }
                return split[0] + " " + split[1] + " " + split[2];
            default:
                return null;
        }
    }

    public static String setDateTimeFormat(String str) {
        String str2;
        Calendar instance = Calendar.getInstance();
        if (str.contains("ddth")) {
            str2 = addExtention(str);
        } else {
            str2 = new SimpleDateFormat(str, Locale.getDefault()).format(instance.getTime());
        }
        return str2.replace("am", "AM").replace("pm", "PM");
    }

    private static String addExtention(String str) {
        String str2;
        Calendar instance = Calendar.getInstance();
        String[] split = str.split("th");
        String format = new SimpleDateFormat(split[0], Locale.getDefault()).format(instance.getTime());
        char charAt = format.charAt(format.length() - 1);
        if (format.substring(format.length() - 2).equalsIgnoreCase("13") || format.equalsIgnoreCase("13") || format.substring(format.length() - 2).equalsIgnoreCase("12") || format.equalsIgnoreCase("12") || format.substring(format.length() - 2).equalsIgnoreCase("11") || format.equalsIgnoreCase("11")) {
            str2 = format + "th";
        } else if (charAt == '1') {
            str2 = format + "st";
        } else if (charAt == '2') {
            str2 = format + "nd";
        } else {
            str2 = charAt == '3' ? format + "rd" : format + "th";
        }
        return str2 + new SimpleDateFormat(split[1], Locale.getDefault()).format(instance.getTime());
    }

    public static void hideSoftKeyboard(Activity activity2, View view) {
        ((InputMethodManager) activity2.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
