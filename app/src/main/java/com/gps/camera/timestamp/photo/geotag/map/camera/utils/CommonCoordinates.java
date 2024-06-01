package com.gps.camera.timestamp.photo.geotag.map.camera.utils;

import android.content.Context;
import android.location.Location;
import com.gps.camera.timestamp.photo.geotag.map.mgrs.Angle;
import com.gps.camera.timestamp.photo.geotag.map.mgrs.MGRSCoord;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.KeysConstants;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class CommonCoordinates {
    public static String getLatitudeMethod(int i, int i2, Context context) {
        if (context == null) {
            return null;
        }
        C1281SP sp = new C1281SP(context);
        String string = sp.getString(context, KeysConstants.LATITUDE, "00.0000");
        String string2 = sp.getString(context, KeysConstants.LONGITUDE, "00.0000");
        if (string == null || string2 == null) {
            return null;
        }
        if (i == 0) {
            return string + "°";
        }
        char c = 'N';
        if (i == 1) {
            if (Double.parseDouble(string) < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                c = 'S';
            }
            return string + " " + c;
        } else if (i == 2) {
            return replaceDelimins(Location.convert(Double.parseDouble(string), Location.FORMAT_MINUTES), i2);
        } else {
            if (i == 3) {
                String replaceDelimiters = replaceDelimiters(Location.convert(Double.parseDouble(string), Location.FORMAT_SECONDS), i2);
                if (Double.parseDouble(string) < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    c = 'S';
                }
                return replaceDelimiters + " " + c;
            } else if (i == 4) {
                String replaceDelimiterss = replaceDelimiterss(Location.convert(Double.parseDouble(string), Location.FORMAT_SECONDS), i2);
                if (Double.parseDouble(string) < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    c = 'S';
                }
                return replaceDelimiterss + " " + c;
            } else if (i == 5) {
                double parseDouble = Double.parseDouble(string);
                double parseDouble2 = Double.parseDouble(string2);
                int floor = (int) Math.floor((parseDouble2 / 6.0d) + 31.0d);
                if (parseDouble < -72.0d) {
                    c = 'C';
                } else if (parseDouble < -64.0d) {
                    c = 'D';
                } else if (parseDouble < -56.0d) {
                    c = 'E';
                } else if (parseDouble < -48.0d) {
                    c = 'F';
                } else if (parseDouble < -40.0d) {
                    c = 'G';
                } else if (parseDouble < -32.0d) {
                    c = 'H';
                } else if (parseDouble < -24.0d) {
                    c = 'J';
                } else if (parseDouble < -16.0d) {
                    c = 'K';
                } else if (parseDouble < -8.0d) {
                    c = 'L';
                } else if (parseDouble < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    c = 'M';
                } else if (parseDouble >= 8.0d) {
                    c = parseDouble < 16.0d ? 'P' : parseDouble < 24.0d ? 'Q' : parseDouble < 32.0d ? 'R' : parseDouble >= 40.0d ? parseDouble < 48.0d ? 'T' : parseDouble < 56.0d ? 'U' : parseDouble < 64.0d ? 'V' : parseDouble < 72.0d ? 'W' : 'X' : 'S';
                }
                double d = (parseDouble * 3.141592653589793d) / 180.0d;
                double d2 = ((parseDouble2 * 3.141592653589793d) / 180.0d) - ((((double) ((floor * 6) - 183)) * 3.141592653589793d) / 180.0d);
                double round = ((double) Math.round(((((((Math.log(((Math.cos(d) * Math.sin(d2)) + 1.0d) / (1.0d - (Math.cos(d) * Math.sin(d2)))) * 0.5d) * 0.9996d) * 6399593.62d) / Math.pow((Math.pow(0.0820944379d, 2.0d) * Math.pow(Math.cos(d), 2.0d)) + 1.0d, 0.5d)) * (((((Math.pow(0.0820944379d, 2.0d) / 2.0d) * Math.pow(Math.log(((Math.cos(d) * Math.sin(d2)) + 1.0d) / (1.0d - (Math.cos(d) * Math.sin(d2)))) * 0.5d, 2.0d)) * Math.pow(Math.cos(d), 2.0d)) / 3.0d) + 1.0d)) + 500000.0d) * 100.0d)) * 0.01d;
                double d3 = ((parseDouble * 2.0d) * 3.141592653589793d) / 180.0d;
                double atan = (((((Math.atan(Math.tan(d) / Math.cos(d2)) - d) * 0.9996d) * 6399593.625d) / Math.sqrt((Math.pow(Math.cos(d), 2.0d) * 0.006739496742d) + 1.0d)) * ((Math.pow(Math.log(((Math.cos(d) * Math.sin(d2)) + 1.0d) / (1.0d - (Math.cos(d) * Math.sin(d2)))) * 0.5d, 2.0d) * 0.003369748371d * Math.pow(Math.cos(d), 2.0d)) + 1.0d)) + ((((d - ((d + (Math.sin(d3) / 2.0d)) * 0.005054622556d)) + (((((d + (Math.sin(d3) / 2.0d)) * 3.0d) + (Math.sin(d3) * Math.pow(Math.cos(d), 2.0d))) * 4.258201531E-5d) / 4.0d)) - ((((((((d + (Math.sin(d3) / 2.0d)) * 3.0d) + (Math.sin(d3) * Math.pow(Math.cos(d), 2.0d))) * 5.0d) / 4.0d) + ((Math.sin(d3) * Math.pow(Math.cos(d), 2.0d)) * Math.pow(Math.cos(d), 2.0d))) * 1.674057895E-7d) / 3.0d)) * 6397033.7875500005d);
                if (c < 'M') {
                    atan += 1.0E7d;
                }
                System.out.println("Northing" + (((double) Math.round(atan * 100.0d)) * 0.01d));
                if (Double.parseDouble(string) <= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    Double.parseDouble(string);
                }
                return floor + "" + c + "  " + round + " " + (Double.parseDouble(string2) >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'E' : 'W');
            } else if (i == 6) {
                return MGRSCoord.fromLatLon(Angle.fromDegrees(Double.parseDouble(string)), Angle.fromDegrees(Double.parseDouble(string2))).toString();
            } else {
                return null;
            }
        }
    }

    private static String replaceDelimins(String str, int i) {
        String replaceFirst = str.replaceFirst(":", "");
        int indexOf = replaceFirst.indexOf(".") + 1 + i;
        return indexOf < replaceFirst.length() ? replaceFirst.substring(0, indexOf) : replaceFirst;
    }

    public static String getLongitudeMethod(int i, int i2, Context context) {
        if (context == null) {
            return null;
        }
        C1281SP sp = new C1281SP(context);
        String string = sp.getString(context, KeysConstants.LATITUDE, "00.00000");
        String string2 = sp.getString(context, KeysConstants.LONGITUDE, "00.0000");
        if (string2 == null || string == null) {
            return null;
        }
        if (i == 0) {
            return string2 + "°";
        }
        char c = 'E';
        if (i == 1) {
            if (Double.parseDouble(string2) < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                c = 'W';
            }
            return string2 + " " + c;
        } else if (i == 2) {
            return replaceDelimins(Location.convert(Double.parseDouble(string2), Location.FORMAT_MINUTES), i2);
        } else {
            if (i == 3) {
                String replaceDelimiters = replaceDelimiters(Location.convert(Double.parseDouble(string2), Location.FORMAT_SECONDS), i2);
                if (Double.parseDouble(string2) < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    c = 'W';
                }
                return replaceDelimiters + " " + c;
            } else if (i == 4) {
                String convert = Location.convert(Double.parseDouble(string2), Location.FORMAT_SECONDS);
                if (Double.parseDouble(string2) < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    c = 'W';
                }
                return replaceDelimiterss(convert, i2) + " " + c;
            } else if (i != 5) {
                return null;
            } else {
                double parseDouble = Double.parseDouble(string);
                double parseDouble2 = Double.parseDouble(string2);
                int floor = (int) Math.floor((parseDouble2 / 6.0d) + 31.0d);
                if (parseDouble < -72.0d) {
                    c = 'C';
                } else if (parseDouble < -64.0d) {
                    c = 'D';
                } else if (parseDouble >= -56.0d) {
                    c = parseDouble < -48.0d ? 'F' : parseDouble < -40.0d ? 'G' : parseDouble < -32.0d ? 'H' : parseDouble < -24.0d ? 'J' : parseDouble < -16.0d ? 'K' : parseDouble < -8.0d ? 'L' : parseDouble < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'M' : parseDouble < 8.0d ? 'N' : parseDouble < 16.0d ? 'P' : parseDouble < 24.0d ? 'Q' : parseDouble < 32.0d ? 'R' : parseDouble < 40.0d ? 'S' : parseDouble < 48.0d ? 'T' : parseDouble < 56.0d ? 'U' : parseDouble < 64.0d ? 'V' : parseDouble >= 72.0d ? 'X' : 'W';
                }
                double d = (parseDouble * 3.141592653589793d) / 180.0d;
                double d2 = ((parseDouble2 * 3.141592653589793d) / 180.0d) - ((((double) ((floor * 6) - 183)) * 3.141592653589793d) / 180.0d);
                Math.round(((((((Math.log(((Math.cos(d) * Math.sin(d2)) + 1.0d) / (1.0d - (Math.cos(d) * Math.sin(d2)))) * 0.5d) * 0.9996d) * 6399593.62d) / Math.pow((Math.pow(0.0820944379d, 2.0d) * Math.pow(Math.cos(d), 2.0d)) + 1.0d, 0.5d)) * (((((Math.pow(0.0820944379d, 2.0d) / 2.0d) * Math.pow(Math.log(((Math.cos(d) * Math.sin(d2)) + 1.0d) / (1.0d - (Math.cos(d) * Math.sin(d2)))) * 0.5d, 2.0d)) * Math.pow(Math.cos(d), 2.0d)) / 3.0d) + 1.0d)) + 500000.0d) * 100.0d);
                double d3 = ((parseDouble * 2.0d) * 3.141592653589793d) / 180.0d;
                double atan = (((((Math.atan(Math.tan(d) / Math.cos(d2)) - d) * 0.9996d) * 6399593.625d) / Math.sqrt((Math.pow(Math.cos(d), 2.0d) * 0.006739496742d) + 1.0d)) * ((Math.pow(Math.log(((Math.cos(d) * Math.sin(d2)) + 1.0d) / (1.0d - (Math.cos(d) * Math.sin(d2)))) * 0.5d, 2.0d) * 0.003369748371d * Math.pow(Math.cos(d), 2.0d)) + 1.0d)) + ((((d - ((d + (Math.sin(d3) / 2.0d)) * 0.005054622556d)) + (((((d + (Math.sin(d3) / 2.0d)) * 3.0d) + (Math.sin(d3) * Math.pow(Math.cos(d), 2.0d))) * 4.258201531E-5d) / 4.0d)) - ((((((((d + (Math.sin(d3) / 2.0d)) * 3.0d) + (Math.sin(d3) * Math.pow(Math.cos(d), 2.0d))) * 5.0d) / 4.0d) + ((Math.sin(d3) * Math.pow(Math.cos(d), 2.0d)) * Math.pow(Math.cos(d), 2.0d))) * 1.674057895E-7d) / 3.0d)) * 6397033.7875500005d);
                if (c < 'M') {
                    atan += 1.0E7d;
                }
                double round = ((double) Math.round(atan * 100.0d)) * 0.01d;
                System.out.println("Northing" + round);
                char c2 = Double.parseDouble(string) >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'N' : 'S';
                if (Double.parseDouble(string2) <= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    Double.parseDouble(string2);
                }
                return String.valueOf(round) + " " + c2;
            }
        }
    }

    private static String replaceDelimiterss(String str, int i) {
        String replaceFirst = str.replaceFirst(":", "").replaceFirst(":", "");
        int indexOf = replaceFirst.indexOf(".") + 1 + i;
        return indexOf < replaceFirst.length() ? replaceFirst.substring(0, indexOf) : replaceFirst;
    }

    public static String replaceDelimiters(String str, int i) {
        String replaceFirst = str.replaceFirst(":", "° ").replaceFirst(":", "' ");
        int indexOf = replaceFirst.indexOf(".") + 1 + i;
        if (indexOf < replaceFirst.length()) {
            replaceFirst = replaceFirst.substring(0, indexOf);
        }
        return replaceFirst + "\"";
    }
}
