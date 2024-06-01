package com.gps.camera.timestamp.photo.geotag.map.data.database.p006UI;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.exifinterface.media.ExifInterface;

import com.gps.camera.timestamp.photo.geotag.map.data.database.Model.DateTime;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Activity.SharePref;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CommonFunction {
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;

    private void copyFile(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            FileOutputStream fileOutputStream = new FileOutputStream(context.getFilesDir() + "/font/" + str);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    open.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (IOException unused) {
        }
    }

    public String getThFormat(long j, String str) {
        String str2;
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        String dayNumberSuffix = getDayNumberSuffix(instance.get(5));
        if (str.contains("ddth")) {
            str2 = str.replace("ddth", "d'" + dayNumberSuffix + "'");
        } else {
            str2 = "d'" + dayNumberSuffix + "'";
        }
        return new SimpleDateFormat(str2).format(instance.getTime());
    }

    private String getDayNumberSuffix(int i) {
        if (i >= 11 && i <= 13) {
            return "th";
        }
        int i2 = i % 10;
        if (i2 == 1) {
            return "st";
        }
        if (i2 != 2) {
            return i2 != 3 ? "th" : "rd";
        }
        return "nd";
    }

    public String getDate(long j, String str) {
        if (str.contains("ddth")) {
            return getThFormat(System.currentTimeMillis(), str);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        System.out.println(j);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return simpleDateFormat.format(instance.getTime());
    }

    public String setdate(String str) {
        Log.e("LLLL 1", "" + str);
        Log.e("LLLL 2", "" + str.contains("th"));
        if (str.equals("CLEAR")) {
            return "";
        }
        if ((str.contains("th)") || str.contains("st)") || str.contains("nd)") || str.contains("rd)")) && !str.contains("MMMM(")) {
            return getThFormat(System.currentTimeMillis(), str);
        }
        if (str.equals("hh(12)")) {
            return getDate(System.currentTimeMillis(), "hh");
        }
        if (str.equals("hh(24)")) {
            return getDate(System.currentTimeMillis(), "HH");
        }
        if (str.equals("") || str.equalsIgnoreCase("select one") || str.equalsIgnoreCase("select")) {
            return "";
        }
        if (str.equals("AM")) {
            return "AM";
        }
        if (str.equals("am")) {
            return "am";
        }
        if (str.equals("PM")) {
            return "PM";
        }
        if (str.equals("pm")) {
            return "pm";
        }
        if (str.equals(":")) {
            return ":";
        }
        if (str.equals("/")) {
            return "/";
        }
        if (str.equals(",")) {
            return ",";
        }
        if (str.equals(".")) {
            return ".";
        }
        if (str.equals("-")) {
            return "-";
        }
        if (str.contains("second(")) {
            return getDate(System.currentTimeMillis(), "ss");
        }
        if (str.contains("date(")) {
            return getDate(System.currentTimeMillis(), "dd");
        }
        if (str.contains("EEEE(")) {
            return getDate(System.currentTimeMillis(), "EEEE");
        }
        if (str.contains("EEE(")) {
            return getDate(System.currentTimeMillis(), "EEE");
        }
        if (str.contains("yyyy(")) {
            return getDate(System.currentTimeMillis(), "yyyy");
        }
        if (str.contains("yy(")) {
            return getDate(System.currentTimeMillis(), "yy");
        }
        if (str.contains("E(")) {
            return getDate(System.currentTimeMillis(), ExifInterface.LONGITUDE_EAST);
        }
        if (str.contains("MMMM(")) {
            return getDate(System.currentTimeMillis(), "MMMM");
        }
        if (str.contains("MMM(")) {
            return getDate(System.currentTimeMillis(), "MMM");
        }
        if (str.contains("MM(")) {
            return getDate(System.currentTimeMillis(), "MM");
        }
        if (str.equalsIgnoreCase("SPACE")) {
            return " ";
        }
        if (str.contains("Hour")) {
            return getDate(System.currentTimeMillis(), "HH");
        }
        if (str.contains("hour")) {
            return getDate(System.currentTimeMillis(), "hh");
        }
        if (str.contains("Minute(")) {
            return getDate(System.currentTimeMillis(), "mm");
        }
        return getDate(System.currentTimeMillis(), str);
    }

    public String getFormat(String str) {
        if (str.equals("CLEAR")) {
            return "";
        }
        if (str.equals(":")) {
            return ":";
        }
        if ((str.contains("th)") || str.contains("st)") || str.contains("nd)") || str.contains("rd)")) && !str.contains("MMMM(")) {
            return "ddth";
        }
        if (str.equals("/")) {
            return "/";
        }
        if (str.equals("")) {
            return "";
        }
        if (str.equals(".")) {
            return ".";
        }
        if (str.equals("-")) {
            return "-";
        }
        if (str.contains("date(")) {
            return "dd";
        }
        if (str.equals("hh(12)")) {
            return "hh";
        }
        if (str.equals("hh(24)")) {
            return "HH";
        }
        if (str.equals("") || str.equalsIgnoreCase("select one") || str.equalsIgnoreCase("select")) {
            return "";
        }
        if (str.contains("second(")) {
            return "ss";
        }
        if (str.equals("AM")) {
            return "AM";
        }
        if (str.equals("am")) {
            return "am";
        }
        if (str.equals("PM")) {
            return "PM";
        }
        if (str.equals("pm")) {
            return "pm";
        }
        if (str.contains("EEEE(")) {
            return "EEEE";
        }
        if (str.contains("EEE(")) {
            return "EEE";
        }
        if (str.contains("E(")) {
            return ExifInterface.LONGITUDE_EAST;
        }
        if (str.contains("yyyy(")) {
            return "yyyy";
        }
        if (str.contains("yy(")) {
            return "yy";
        }
        if (str.contains("MMMM(")) {
            return "MMMM";
        }
        if (str.contains("MMM(")) {
            return "MMM";
        }
        if (str.contains("MM(")) {
            return "MM";
        }
        if (str.contains("Hour")) {
            return "HH";
        }
        if (str.contains("hour")) {
            return "hh";
        }
        if (str.contains("Minute(")) {
            return "mm";
        }
        return str.equalsIgnoreCase("SPACE") ? " " : str;
    }

    public String stringWithTextNoSpace(int i, String str) {
        String str2;
        StringBuilder sb;
        String str3;
        StringBuilder sb2;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String[] split = str.split("\n");
        float f = 0.0f;
        if (split.length == 3) {
            float length = (float) split[0].length();
            float length2 = (float) split[1].length();
            float length3 = (float) split[2].length();
            if (length > length2 && length > length3) {
                float f2 = (length - length3) / 2.0f;
                float f3 = (length - length2) / 2.0f;
                StringBuilder sb3 = new StringBuilder();
                StringBuilder sb4 = new StringBuilder();
                if (((double) f3) == 0.5d) {
                    f3 = 0.0f;
                }
                if (((double) f2) != 0.5d) {
                    f = f2;
                }
                for (int i2 = 0; ((float) i2) < f3; i2++) {
                    sb4.append("");
                }
                for (int i3 = 0; ((float) i3) < f; i3++) {
                    sb3.append("");
                }
                if (i == 0 || i == 2 || i == 5 || i == 6) {
                    str9 = sb4.toString() + split[1];
                    str8 = sb3.toString() + split[2];
                } else {
                    str9 = split[1] + sb4.toString();
                    str8 = split[2] + sb3.toString();
                }
                return split[0] + "\n" + str9 + "\n" + str8;
            } else if (length2 > length && length2 > length3) {
                float f4 = (length2 - length) / 2.0f;
                float f5 = (length2 - length3) / 2.0f;
                StringBuilder sb5 = new StringBuilder();
                StringBuilder sb6 = new StringBuilder();
                if (((double) f4) == 0.5d) {
                    f4 = 0.0f;
                }
                if (((double) f5) != 0.5d) {
                    f = f5;
                }
                for (int i4 = 0; ((float) i4) < f4; i4++) {
                    sb5.append("");
                }
                for (int i5 = 0; ((float) i5) < f; i5++) {
                    sb6.append("");
                }
                if (i == 0 || i == 2 || i == 5 || i == 6) {
                    str7 = sb5.toString() + split[0];
                    str6 = sb6.toString() + split[2];
                } else {
                    str7 = split[0] + sb5.toString();
                    str6 = split[2] + sb6.toString();
                }
                return str7 + "\n" + split[1] + "\n" + str6;
            } else if (length3 <= length2 || length3 <= length) {
                return split[0] + "\n" + split[1] + "\n" + split[2];
            } else {
                float f6 = (length3 - length) / 2.0f;
                float f7 = (length3 - length2) / 2.0f;
                StringBuilder sb7 = new StringBuilder();
                StringBuilder sb8 = new StringBuilder();
                if (((double) f6) == 0.5d) {
                    f6 = 0.0f;
                }
                if (((double) f7) != 0.5d) {
                    f = f7;
                }
                for (int i6 = 0; ((float) i6) < f6; i6++) {
                    sb7.append("");
                }
                for (int i7 = 0; ((float) i7) < f; i7++) {
                    sb8.append("");
                }
                if (i == 0 || i == 2 || i == 5 || i == 6) {
                    str5 = sb7.toString() + split[0];
                    str4 = sb8.toString() + split[1];
                } else {
                    str5 = split[0] + sb7.toString();
                    str4 = split[1] + sb8.toString();
                }
                return str5 + "\n" + str4 + "\n" + split[2];
            }
        } else if (split.length != 2) {
            return str;
        } else {
            float length4 = (float) split[0].length();
            float length5 = (float) split[1].length();
            if (length4 > length5) {
                float f8 = (length4 - length5) / 2.0f;
                if (((double) f8) != 0.5d) {
                    f = f8;
                }
                StringBuilder sb9 = new StringBuilder();
                for (int i8 = 0; ((float) i8) < f; i8++) {
                    sb9.append("");
                }
                StringBuilder append = new StringBuilder().append(split[0]).append("\n");
                if (i == 0 || i == 2 || i == 5 || i == 6) {
                    sb2 = new StringBuilder().append(sb9.toString());
                    str3 = split[1];
                } else {
                    sb2 = new StringBuilder().append(split[1]);
                    str3 = sb9.toString();
                }
                return append.append(sb2.append(str3).toString()).toString();
            } else if (length5 <= length4) {
                return split[0] + "\n" + split[1];
            } else {
                float f9 = (length5 - length4) / 2.0f;
                if (((double) f9) != 0.5d) {
                    f = f9;
                }
                StringBuilder sb10 = new StringBuilder();
                for (int i9 = 0; ((float) i9) < f; i9++) {
                    sb10.append("");
                }
                StringBuilder sb11 = new StringBuilder();
                if (i == 0 || i == 2 || i == 5 || i == 6) {
                    sb = new StringBuilder().append(sb10.toString());
                    str2 = split[0];
                } else {
                    sb = new StringBuilder().append(split[0]);
                    str2 = sb10.toString();
                }
                return sb11.append(sb.append(str2).toString()).append("\n").append(split[1]).toString();
            }
        }
    }

    public String stringWithText(int i, String str) {
        String str2;
        StringBuilder sb;
        String str3;
        StringBuilder sb2;
        String str4;
        String str5;
        String str6;
        StringBuilder sb3;
        String str7;
        String str8;
        String str9;
        StringBuilder sb4;
        String str10;
        String str11;
        String str12;
        StringBuilder sb5;
        String str13;
        String str14;
        String str15;
        String str16;
        String str17;
        String str18;
        String[] split = str.split("\n");
        float f = 0.0f;
        if (split.length == 3) {
            float length = (float) split[0].length();
            float length2 = (float) split[1].length();
            float length3 = (float) split[2].length();
            int i2 = (length > length2 ? 1 : (length == length2 ? 0 : -1));
            char c = i2 > 0 ? 1 : i2 == 0 ? (char) 0 : 65535;
            if (c <= 0 || length <= length3) {
                int i3 = (length2 > length ? 1 : (length2 == length ? 0 : -1));
                char c2 = i3 > 0 ? 1 : i3 == 0 ? (char) 0 : 65535;
                if (c2 > 0 && length2 > length3) {
                    float f2 = (length2 - length) / 2.0f;
                    float f3 = (length2 - length3) / 2.0f;
                    StringBuilder sb6 = new StringBuilder();
                    StringBuilder sb7 = new StringBuilder();
                    if (((double) f2) == 0.5d) {
                        f2 = 0.0f;
                    }
                    if (((double) f3) != 0.5d) {
                        f = f3;
                    }
                    for (int i4 = 0; ((float) i4) < f2; i4++) {
                        sb6.append("  ");
                    }
                    for (int i5 = 0; ((float) i5) < f; i5++) {
                        sb7.append("  ");
                    }
                    if (i == 0 || i == 2 || i == 5 || i == 6) {
                        str16 = sb6.toString() + split[0];
                        str15 = sb7.toString() + split[2];
                    } else {
                        str16 = split[0] + sb6.toString();
                        str15 = split[2] + sb7.toString();
                    }
                    return str16 + "\n" + split[1] + "\n" + str15;
                } else if (length3 > length2 && length3 > length) {
                    float f4 = (length3 - length) / 2.0f;
                    float f5 = (length3 - length2) / 2.0f;
                    StringBuilder sb8 = new StringBuilder();
                    StringBuilder sb9 = new StringBuilder();
                    if (((double) f4) == 0.5d) {
                        f4 = 0.0f;
                    }
                    if (((double) f5) != 0.5d) {
                        f = f5;
                    }
                    for (int i6 = 0; ((float) i6) < f4; i6++) {
                        sb8.append("  ");
                    }
                    for (int i7 = 0; ((float) i7) < f; i7++) {
                        sb9.append("  ");
                    }
                    if (i == 0 || i == 2 || i == 5 || i == 6) {
                        str14 = sb8.toString() + split[0];
                        str13 = sb9.toString() + split[1];
                    } else {
                        str14 = split[0] + sb8.toString();
                        str13 = split[1] + sb9.toString();
                    }
                    return str14 + "\n" + str13 + "\n" + split[2];
                } else if (c == 0) {
                    if (length > length3) {
                        float f6 = (length - length3) / 2.0f;
                        StringBuilder sb10 = new StringBuilder();
                        if (((double) f6) != 0.5d) {
                            f = f6;
                        }
                        for (int i8 = 0; ((float) i8) < f; i8++) {
                            sb10.append("  ");
                        }
                        StringBuilder append = new StringBuilder().append(split[0]).append("\n").append(split[1]).append("\n");
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            sb5 = new StringBuilder().append(sb10.toString());
                            str12 = split[2];
                        } else {
                            sb5 = new StringBuilder().append(split[2]);
                            str12 = sb10.toString();
                        }
                        return append.append(sb5.append(str12).toString()).toString();
                    } else if (length3 <= length) {
                        return "";
                    } else {
                        float f7 = (length3 - length) / 2.0f;
                        float f8 = (length3 - length2) / 2.0f;
                        StringBuilder sb11 = new StringBuilder();
                        StringBuilder sb12 = new StringBuilder();
                        if (((double) f7) == 0.5d) {
                            f7 = 0.0f;
                        }
                        if (((double) f8) != 0.5d) {
                            f = f8;
                        }
                        for (int i9 = 0; ((float) i9) < f7; i9++) {
                            sb11.append("  ");
                        }
                        for (int i10 = 0; ((float) i10) < f; i10++) {
                            sb12.append("  ");
                        }
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            str11 = sb11.toString() + split[0];
                            str10 = sb12.toString() + split[1];
                        } else {
                            str11 = split[0] + sb11.toString();
                            str10 = split[1] + sb12.toString();
                        }
                        return str11 + "\n" + str10 + "\n" + split[2];
                    }
                } else if (length2 == length3) {
                    if (c2 > 0) {
                        float f9 = (length2 - length) / 2.0f;
                        StringBuilder sb13 = new StringBuilder();
                        if (((double) f9) != 0.5d) {
                            f = f9;
                        }
                        for (int i11 = 0; ((float) i11) < f; i11++) {
                            sb13.append("  ");
                        }
                        StringBuilder sb14 = new StringBuilder();
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            sb4 = new StringBuilder().append(sb13.toString());
                            str9 = split[0];
                        } else {
                            sb4 = new StringBuilder().append(split[0]);
                            str9 = sb13.toString();
                        }
                        return sb14.append(sb4.append(str9).toString()).append("\n").append(split[1]).append("\n").append(split[2]).toString();
                    } else if (length2 >= length) {
                        return "";
                    } else {
                        float f10 = (length - length3) / 2.0f;
                        float f11 = (length - length2) / 2.0f;
                        StringBuilder sb15 = new StringBuilder();
                        StringBuilder sb16 = new StringBuilder();
                        if (((double) f11) == 0.5d) {
                            f11 = 0.0f;
                        }
                        if (((double) f10) != 0.5d) {
                            f = f10;
                        }
                        for (int i12 = 0; ((float) i12) < f11; i12++) {
                            sb16.append("  ");
                        }
                        for (int i13 = 0; ((float) i13) < f; i13++) {
                            sb15.append("  ");
                        }
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            str8 = sb16.toString() + split[1];
                            str7 = sb15.toString() + split[2];
                        } else {
                            str8 = split[1] + sb16.toString();
                            str7 = split[2] + sb15.toString();
                        }
                        return split[0] + "\n" + str8 + "\n" + str7;
                    }
                } else if (length != length3) {
                    return str;
                } else {
                    if (c > 0) {
                        float f12 = (length - length2) / 2.0f;
                        StringBuilder sb17 = new StringBuilder();
                        if (((double) f12) != 0.5d) {
                            f = f12;
                        }
                        for (int i14 = 0; ((float) i14) < f; i14++) {
                            sb17.append("  ");
                        }
                        StringBuilder append2 = new StringBuilder().append(split[0]).append("\n");
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            sb3 = new StringBuilder().append(sb17.toString());
                            str6 = split[1];
                        } else {
                            sb3 = new StringBuilder().append(split[1]);
                            str6 = sb17.toString();
                        }
                        return append2.append(sb3.append(str6).toString()).append("\n").append(split[2]).toString();
                    } else if (c2 <= 0) {
                        return "";
                    } else {
                        float f13 = (length2 - length) / 2.0f;
                        float f14 = (length2 - length3) / 2.0f;
                        StringBuilder sb18 = new StringBuilder();
                        StringBuilder sb19 = new StringBuilder();
                        if (((double) f13) == 0.5d) {
                            f13 = 0.0f;
                        }
                        if (((double) f14) != 0.5d) {
                            f = f14;
                        }
                        for (int i15 = 0; ((float) i15) < f13; i15++) {
                            sb18.append("  ");
                        }
                        for (int i16 = 0; ((float) i16) < f; i16++) {
                            sb19.append("  ");
                        }
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            str5 = sb18.toString() + split[0];
                            str4 = sb19.toString() + split[2];
                        } else {
                            str5 = split[0] + sb18.toString();
                            str4 = split[2] + sb19.toString();
                        }
                        return str5 + "\n" + split[1] + "\n" + str4;
                    }
                }
            } else {
                float f15 = (length - length3) / 2.0f;
                float f16 = (length - length2) / 2.0f;
                StringBuilder sb20 = new StringBuilder();
                StringBuilder sb21 = new StringBuilder();
                if (((double) f16) == 0.5d) {
                    f16 = 0.0f;
                }
                if (((double) f15) != 0.5d) {
                    f = f15;
                }
                for (int i17 = 0; ((float) i17) < f16; i17++) {
                    sb21.append("  ");
                }
                for (int i18 = 0; ((float) i18) < f; i18++) {
                    sb20.append("  ");
                }
                if (i == 0 || i == 2 || i == 5 || i == 6) {
                    str18 = sb21.toString() + split[1];
                    str17 = sb20.toString() + split[2];
                } else {
                    str18 = split[1] + sb21.toString();
                    str17 = split[2] + sb20.toString();
                }
                return split[0] + "\n" + str18 + "\n" + str17;
            }
        } else if (split.length != 2) {
            return str;
        } else {
            float length4 = (float) split[0].length();
            float length5 = (float) split[1].length();
            int i19 = (length4 > length5 ? 1 : (length4 == length5 ? 0 : -1));
            char c3 = i19 > 0 ? 1 : i19 == 0 ? (char) 0 : 65535;
            if (c3 > 0) {
                float f17 = (length4 - length5) / 2.0f;
                if (((double) f17) != 0.5d) {
                    f = f17;
                }
                StringBuilder sb22 = new StringBuilder();
                for (int i20 = 0; ((float) i20) < f; i20++) {
                    sb22.append("  ");
                }
                StringBuilder append3 = new StringBuilder().append(split[0]).append("\n");
                if (i == 0 || i == 2 || i == 5 || i == 6) {
                    sb2 = new StringBuilder().append(sb22.toString());
                    str3 = split[1];
                } else {
                    sb2 = new StringBuilder().append(split[1]);
                    str3 = sb22.toString();
                }
                return append3.append(sb2.append(str3).toString()).toString();
            } else if (length5 <= length4) {
                return c3 == 0 ? str : "";
            } else {
                float f18 = (length5 - length4) / 2.0f;
                if (((double) f18) != 0.5d) {
                    f = f18;
                }
                StringBuilder sb23 = new StringBuilder();
                for (int i21 = 0; ((float) i21) < f; i21++) {
                    sb23.append("  ");
                }
                StringBuilder sb24 = new StringBuilder();
                if (i == 0 || i == 2 || i == 5 || i == 6) {
                    sb = new StringBuilder().append(sb23.toString());
                    str2 = split[0];
                } else {
                    sb = new StringBuilder().append(split[0]);
                    str2 = sb23.toString();
                }
                return sb24.append(sb.append(str2).toString()).append("\n").append(split[1]).toString();
            }
        }
    }

    public String stringWithTextSingleSpace(int i, String str) {
        String str2;
        StringBuilder sb;
        String str3;
        StringBuilder sb2;
        String str4;
        String str5;
        String str6;
        StringBuilder sb3;
        String str7;
        String str8;
        String str9;
        StringBuilder sb4;
        String str10;
        String str11;
        String str12;
        StringBuilder sb5;
        String str13;
        String str14;
        String str15;
        String str16;
        String str17;
        String str18;
        String[] split = str.split("\n");
        char c = 65535;
        float f = 0.0f;
        if (split.length == 3) {
            float length = (float) split[0].length();
            float length2 = (float) split[1].length();
            float length3 = (float) split[2].length();
            int i2 = (length > length2 ? 1 : (length == length2 ? 0 : -1));
            char c2 = i2 > 0 ? 1 : i2 == 0 ? (char) 0 : 65535;
            if (c2 <= 0 || length <= length3) {
                int i3 = (length2 > length ? 1 : (length2 == length ? 0 : -1));
                if (i3 > 0) {
                    c = 1;
                } else if (i3 == 0) {
                    c = 0;
                }
                if (c > 0 && length2 > length3) {
                    float f2 = (length2 - length) / 2.0f;
                    float f3 = (length2 - length3) / 2.0f;
                    StringBuilder sb6 = new StringBuilder();
                    StringBuilder sb7 = new StringBuilder();
                    if (((double) f2) == 0.5d) {
                        f2 = 0.0f;
                    }
                    if (((double) f3) != 0.5d) {
                        f = f3;
                    }
                    for (int i4 = 0; ((float) i4) < f2; i4++) {
                        sb6.append(" ");
                    }
                    for (int i5 = 0; ((float) i5) < f; i5++) {
                        sb7.append(" ");
                    }
                    if (i == 0 || i == 2 || i == 5 || i == 6) {
                        str16 = sb6.toString() + split[0];
                        str15 = sb7.toString() + split[2];
                    } else {
                        str16 = split[0] + sb6.toString();
                        str15 = split[2] + sb7.toString();
                    }
                    return str16 + "\n" + split[1] + "\n" + str15;
                } else if (length3 > length2 && length3 > length) {
                    float f4 = (length3 - length) / 2.0f;
                    float f5 = (length3 - length2) / 2.0f;
                    StringBuilder sb8 = new StringBuilder();
                    StringBuilder sb9 = new StringBuilder();
                    if (((double) f4) == 0.5d) {
                        f4 = 0.0f;
                    }
                    if (((double) f5) != 0.5d) {
                        f = f5;
                    }
                    for (int i6 = 0; ((float) i6) < f4; i6++) {
                        sb8.append(" ");
                    }
                    for (int i7 = 0; ((float) i7) < f; i7++) {
                        sb9.append(" ");
                    }
                    if (i == 0 || i == 2 || i == 5 || i == 6) {
                        str14 = sb8.toString() + split[0];
                        str13 = sb9.toString() + split[1];
                    } else {
                        str14 = split[0] + sb8.toString();
                        str13 = split[1] + sb9.toString();
                    }
                    return str14 + "\n" + str13 + "\n" + split[2];
                } else if (c2 == 0) {
                    if (length > length3) {
                        float f6 = (length - length3) / 2.0f;
                        StringBuilder sb10 = new StringBuilder();
                        if (((double) f6) != 0.5d) {
                            f = f6;
                        }
                        for (int i8 = 0; ((float) i8) < f; i8++) {
                            sb10.append(" ");
                        }
                        StringBuilder append = new StringBuilder().append(split[0]).append("\n").append(split[1]).append("\n");
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            sb5 = new StringBuilder().append(sb10.toString());
                            str12 = split[2];
                        } else {
                            sb5 = new StringBuilder().append(split[2]);
                            str12 = sb10.toString();
                        }
                        return append.append(sb5.append(str12).toString()).toString();
                    } else if (length3 <= length) {
                        return null;
                    } else {
                        float f7 = (length3 - length) / 2.0f;
                        float f8 = (length3 - length2) / 2.0f;
                        StringBuilder sb11 = new StringBuilder();
                        StringBuilder sb12 = new StringBuilder();
                        if (((double) f7) == 0.5d) {
                            f7 = 0.0f;
                        }
                        if (((double) f8) != 0.5d) {
                            f = f8;
                        }
                        for (int i9 = 0; ((float) i9) < f7; i9++) {
                            sb11.append(" ");
                        }
                        for (int i10 = 0; ((float) i10) < f; i10++) {
                            sb12.append(" ");
                        }
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            str11 = sb11.toString() + split[0];
                            str10 = sb12.toString() + split[1];
                        } else {
                            str11 = split[0] + sb11.toString();
                            str10 = split[1] + sb12.toString();
                        }
                        return str11 + "\n" + str10 + "\n" + split[2];
                    }
                } else if (length2 == length3) {
                    if (c > 0) {
                        float f9 = (length2 - length) / 2.0f;
                        StringBuilder sb13 = new StringBuilder();
                        if (((double) f9) != 0.5d) {
                            f = f9;
                        }
                        for (int i11 = 0; ((float) i11) < f; i11++) {
                            sb13.append(" ");
                        }
                        StringBuilder sb14 = new StringBuilder();
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            sb4 = new StringBuilder().append(sb13.toString());
                            str9 = split[0];
                        } else {
                            sb4 = new StringBuilder().append(split[0]);
                            str9 = sb13.toString();
                        }
                        return sb14.append(sb4.append(str9).toString()).append("\n").append(split[1]).append("\n").append(split[2]).toString();
                    } else if (i3 >= 0) {
                        return null;
                    } else {
                        float f10 = (length - length3) / 2.0f;
                        float f11 = (length - length2) / 2.0f;
                        StringBuilder sb15 = new StringBuilder();
                        StringBuilder sb16 = new StringBuilder();
                        if (((double) f11) == 0.5d) {
                            f11 = 0.0f;
                        }
                        if (((double) f10) != 0.5d) {
                            f = f10;
                        }
                        for (int i12 = 0; ((float) i12) < f11; i12++) {
                            sb16.append(" ");
                        }
                        for (int i13 = 0; ((float) i13) < f; i13++) {
                            sb15.append(" ");
                        }
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            str8 = sb16.toString() + split[1];
                            str7 = sb15.toString() + split[2];
                        } else {
                            str8 = split[1] + sb16.toString();
                            str7 = split[2] + sb15.toString();
                        }
                        return split[0] + "\n" + str8 + "\n" + str7;
                    }
                } else if (length != length3) {
                    return null;
                } else {
                    if (c2 > 0) {
                        float f12 = (length - length2) / 2.0f;
                        StringBuilder sb17 = new StringBuilder();
                        if (((double) f12) != 0.5d) {
                            f = f12;
                        }
                        for (int i14 = 0; ((float) i14) < f; i14++) {
                            sb17.append(" ");
                        }
                        StringBuilder append2 = new StringBuilder().append(split[0]).append("\n");
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            sb3 = new StringBuilder().append(sb17.toString());
                            str6 = split[1];
                        } else {
                            sb3 = new StringBuilder().append(split[1]);
                            str6 = sb17.toString();
                        }
                        return append2.append(sb3.append(str6).toString()).append("\n").append(split[2]).toString();
                    } else if (c <= 0) {
                        return null;
                    } else {
                        float f13 = (length2 - length) / 2.0f;
                        float f14 = (length2 - length3) / 2.0f;
                        StringBuilder sb18 = new StringBuilder();
                        StringBuilder sb19 = new StringBuilder();
                        if (((double) f13) == 0.5d) {
                            f13 = 0.0f;
                        }
                        if (((double) f14) != 0.5d) {
                            f = f14;
                        }
                        for (int i15 = 0; ((float) i15) < f13; i15++) {
                            sb18.append(" ");
                        }
                        for (int i16 = 0; ((float) i16) < f; i16++) {
                            sb19.append(" ");
                        }
                        if (i == 0 || i == 2 || i == 5 || i == 6) {
                            str5 = sb18.toString() + split[0];
                            str4 = sb19.toString() + split[2];
                        } else {
                            str5 = split[0] + sb18.toString();
                            str4 = split[2] + sb19.toString();
                        }
                        return str5 + "\n" + split[1] + "\n" + str4;
                    }
                }
            } else {
                float f15 = (length - length3) / 2.0f;
                float f16 = (length - length2) / 2.0f;
                StringBuilder sb20 = new StringBuilder();
                StringBuilder sb21 = new StringBuilder();
                if (((double) f16) == 0.5d) {
                    f16 = 0.0f;
                }
                if (((double) f15) != 0.5d) {
                    f = f15;
                }
                for (int i17 = 0; ((float) i17) < f16; i17++) {
                    sb21.append(" ");
                }
                for (int i18 = 0; ((float) i18) < f; i18++) {
                    sb20.append(" ");
                }
                if (i == 0 || i == 2 || i == 5 || i == 6) {
                    str18 = sb21.toString() + split[1];
                    str17 = sb20.toString() + split[2];
                } else {
                    str18 = split[1] + sb21.toString();
                    str17 = split[2] + sb20.toString();
                }
                return split[0] + "\n" + str18 + "\n" + str17;
            }
        } else if (split.length != 2) {
            return str;
        } else {
            float length4 = (float) split[0].length();
            float length5 = (float) split[1].length();
            int i19 = (length4 > length5 ? 1 : (length4 == length5 ? 0 : -1));
            if (i19 > 0) {
                c = 1;
            } else if (i19 == 0) {
                c = 0;
            }
            if (c > 0) {
                float f17 = (length4 - length5) / 2.0f;
                if (((double) f17) != 0.5d) {
                    f = f17;
                }
                StringBuilder sb22 = new StringBuilder();
                for (int i20 = 0; ((float) i20) < f; i20++) {
                    sb22.append(" ");
                }
                StringBuilder append3 = new StringBuilder().append(split[0]).append("\n");
                if (i == 0 || i == 2 || i == 5 || i == 6) {
                    sb2 = new StringBuilder().append(sb22.toString());
                    str3 = split[1];
                } else {
                    sb2 = new StringBuilder().append(split[1]);
                    str3 = sb22.toString();
                }
                return append3.append(sb2.append(str3).toString()).toString();
            } else if (length5 > length4) {
                float f18 = (length5 - length4) / 2.0f;
                if (((double) f18) != 0.5d) {
                    f = f18;
                }
                StringBuilder sb23 = new StringBuilder();
                for (int i21 = 0; ((float) i21) < f; i21++) {
                    sb23.append(" ");
                }
                StringBuilder sb24 = new StringBuilder();
                if (i == 0 || i == 2 || i == 5 || i == 6) {
                    sb = new StringBuilder().append(sb23.toString());
                    str2 = split[0];
                } else {
                    sb = new StringBuilder().append(split[0]);
                    str2 = sb23.toString();
                }
                return sb24.append(sb.append(str2).toString()).append("\n").append(split[1]).toString();
            } else if (c == 0) {
                return str;
            } else {
                return null;
            }
        }
    }

    public String getDeviceName() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (str2.startsWith(str)) {
            return capitalize(str2);
        }
        return capitalize(str) + " " + str2;
    }

    private String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (char c : charArray) {
            if (!z || !Character.isLetter(c)) {
                if (Character.isWhitespace(c)) {
                    z = true;
                }
                sb.append(c);
            } else {
                sb.append(Character.toUpperCase(c));
                z = false;
            }
        }
        return sb.toString();
    }

    public ArrayList<DateTime> getDatearray(Context context) {
        return new SharePref(context).getDates(context, SharePref.arryDates);
    }

    public ArrayList<DateTime> getDatearrayHorizontal(Context context) {
        return new SharePref(context).getDates(context, SharePref.arryDates_horizontal);
    }














    public long dateDiffenceday(Date date, Date date2) {
        long time = date2.getTime() - date.getTime();
        long j = (time / 1000) % 60;
        long j2 = (time / 60000) % 60;
        long j3 = time / 3600000;
        return (long) ((int) ((date2.getTime() - date.getTime()) / 86400000));
    }

    public String getCalculatedDate(String str, int i) {
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        instance.add(6, i);
        return simpleDateFormat.format(new Date(instance.getTimeInMillis()));
    }

    public void stopLocationUpdates() {
        LocationCallback locationCallback;
        FusedLocationProviderClient fusedLocationProviderClient = this.mFusedLocationClient;
        if (fusedLocationProviderClient != null && (locationCallback = this.mLocationCallback) != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    public int getLocationMode(Context context) {
        if (context == null) {
            return 0;
        }
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "location_mode");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
