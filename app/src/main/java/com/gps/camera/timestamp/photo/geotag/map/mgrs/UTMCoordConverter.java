package com.gps.camera.timestamp.photo.geotag.map.mgrs;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UTMCoordConverter {
    private static final double MAX_LAT = 1.5009831567151233d;
    private static final double MIN_LAT = -1.43116998663535d;
    private double Central_Meridian;
    private double Easting;
    private String Hemisphere;
    private double Latitude;
    private double Longitude;
    private double Northing;
    private long UTM_Override = 0;
    private double UTM_a = 6378137.0d;
    private double UTM_f = 0.0033528106647474805d;
    private int Zone;

    UTMCoordConverter() {
    }

    UTMCoordConverter(double d, double d2) {
        setUTMParameters(d, d2, 0);
    }

    private long setUTMParameters(double d, double d2, long j) {
        double d3 = 1.0d / d2;
        long j2 = d <= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 128 : 0;
        if (d3 < 250.0d || d3 > 350.0d) {
            j2 |= 256;
        }
        if (j < 0 || j > 60) {
            j2 |= 64;
        }
        if (j2 == 0) {
            this.UTM_a = d;
            this.UTM_f = d2;
            this.UTM_Override = j;
        }
        return j2;
    }

    public long convertGeodeticToUTM(double d, double d2) {
        double d3;
        long j = (d < MIN_LAT || d > MAX_LAT) ? 1 : 0;
        if (d2 < -3.141592653589793d || d2 > 6.283185307179586d) {
            j |= 2;
        }
        if (j != 0) {
            return j;
        }
        double d4 = d2 < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? d2 + 6.283185307279586d : d2;
        long j2 = (long) ((d * 180.0d) / 3.141592653589793d);
        double d5 = (d4 * 180.0d) / 3.141592653589793d;
        long j3 = (long) d5;
        double d6 = d5 / 6.0d;
        long j4 = (long) (d4 < 3.141592653589793d ? d6 + 31.0d : d6 - 29.0d);
        if (j4 > 60) {
            j4 = 1;
        }
        int i = (j2 > 55 ? 1 : (j2 == 55 ? 0 : -1));
        char c = 0;
        char c2 = i > 0 ? 1 : i == 0 ? (char) 0 : 65535;
        if (c2 > 0 && j2 < 64 && j3 > -1 && j3 < 3) {
            j4 = 31;
        }
        if (c2 > 0 && j2 < 64 && j3 > 2 && j3 < 12) {
            j4 = 32;
        }
        int i2 = (j2 > 71 ? 1 : (j2 == 71 ? 0 : -1));
        if (i2 > 0) {
            c = 1;
        } else if (i2 != 0) {
            c = 65535;
        }
        if (c > 0 && j3 > -1 && j3 < 9) {
            j4 = 31;
        }
        if (c > 0 && j3 > 8 && j3 < 21) {
            j4 = 33;
        }
        if (c > 0 && j3 > 20 && j3 < 33) {
            j4 = 35;
        }
        if (c > 0 && j3 > 32 && j3 < 42) {
            j4 = 37;
        }
        long j5 = this.UTM_Override;
        if (j5 != 0) {
            if (!(j4 == 1 && j5 == 60)) {
                if (j4 == 60 && j5 == 1) {
                    j4 = j5;
                }
                if (j4 - 1 > j5 || j5 > 1 + j4) {
                    j = 64;
                }
            }
            j4 = j5;
        }
        if (j != 0) {
            return j;
        }
        if (j4 >= 31) {
            double d7 = (double) ((6 * j4) - 183);
            Double.isNaN(d7);
            this.Central_Meridian = (d7 * 3.141592653589793d) / 180.0d;
        } else {
            double d8 = (double) ((6 * j4) + 177);
            Double.isNaN(d8);
            this.Central_Meridian = (d8 * 3.141592653589793d) / 180.0d;
        }
        this.Zone = (int) j4;
        if (d < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            this.Hemisphere = AVKey.SOUTH;
            d3 = 1.0E7d;
        } else {
            this.Hemisphere = AVKey.NORTH;
            d3 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        }
        try {
            TMCoord fromLatLon = TMCoord.fromLatLon(Angle.fromRadians(d), Angle.fromRadians(d4), Double.valueOf(this.UTM_a), Double.valueOf(this.UTM_f), Angle.fromRadians(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE), Angle.fromRadians(this.Central_Meridian), 500000.0d, d3, 0.9996d);
            this.Easting = fromLatLon.getEasting();
            double northing = fromLatLon.getNorthing();
            this.Northing = northing;
            double d9 = this.Easting;
            if (d9 < 100000.0d || d9 > 900000.0d) {
                j = 4;
            }
            return (northing < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE || northing > 1.0E7d) ? j | 8 : j;
        } catch (Exception unused) {
            return 512;
        }
    }

    public double getEasting() {
        return this.Easting;
    }

    public double getNorthing() {
        return this.Northing;
    }

    public String getHemisphere() {
        return this.Hemisphere;
    }

    public int getZone() {
        return this.Zone;
    }

    public double getLatitude() {
        return this.Latitude;
    }

    public double getLongitude() {
        return this.Longitude;
    }

    public double getCentralMeridian() {
        return this.Central_Meridian;
    }
}
