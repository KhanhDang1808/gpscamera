package com.gps.camera.timestamp.photo.geotag.map.mgrs;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class TMCoordConverter {
    private static final double MAX_LAT = 1.570621793869697d;
    private static final double MAX_SCALE_FACTOR = 3.0d;
    private static final double MIN_SCALE_FACTOR = 0.3d;
    private double Easting;
    private double Latitude;
    private double Longitude;
    private double Northing;
    private double TranMerc_Delta_Easting = 4.0E7d;
    private double TranMerc_Delta_Northing = 4.0E7d;
    private double TranMerc_False_Easting = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double TranMerc_False_Northing = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double TranMerc_Origin_Lat = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double TranMerc_Origin_Long = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double TranMerc_Scale_Factor = 1.0d;
    private double TranMerc_a = 6378137.0d;
    private double TranMerc_ap = 6367449.1458008d;
    private double TranMerc_bp = 16038.508696861d;
    private double TranMerc_cp = 16.832613334334d;
    private double TranMerc_dp = 0.021984404273757d;
    private double TranMerc_ebs = 0.0067394967565869d;
    private double TranMerc_ep = 3.1148371319283E-5d;
    private double TranMerc_es = 0.00669437999014138d;
    private double TranMerc_f = 0.0033528106647474805d;

    TMCoordConverter() {
    }

    public double getA() {
        return this.TranMerc_a;
    }

    public double getF() {
        return this.TranMerc_f;
    }

    public long setTransverseMercatorParameters(double d, double d2, double d3, double d4, double d5, double d6, double d7) {
        double d8 = 1.0d / d2;
        long j = d <= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 64 : 0;
        if (d8 < 250.0d || d8 > 350.0d) {
            j |= 128;
        }
        if (d3 < -1.570621793869697d || d3 > MAX_LAT) {
            j |= 16;
        }
        if (d4 < -3.141592653589793d || d4 > 6.283185307179586d) {
            j |= 32;
        }
        if (d7 < MIN_SCALE_FACTOR || d7 > MAX_SCALE_FACTOR) {
            j |= 256;
        }
        if (j == 0) {
            this.TranMerc_a = d;
            this.TranMerc_f = d2;
            this.TranMerc_Origin_Lat = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            this.TranMerc_Origin_Long = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            this.TranMerc_False_Northing = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            this.TranMerc_False_Easting = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            this.TranMerc_Scale_Factor = 1.0d;
            double d9 = (d2 * 2.0d) - (d2 * d2);
            this.TranMerc_es = d9;
            this.TranMerc_ebs = (1.0d / (1.0d - d9)) - 1.0d;
            double d10 = (1.0d - d2) * d;
            double d11 = (d - d10) / (d10 + d);
            double d12 = d11 * d11;
            double d13 = d12 * d11;
            double d14 = d13 * d11;
            double d15 = d14 * d11;
            double d16 = d12 - d13;
            double d17 = d14 - d15;
            this.TranMerc_ap = ((1.0d - d11) + ((5.0d * d16) / 4.0d) + ((81.0d * d17) / 64.0d)) * d;
            double d18 = d13 - d14;
            this.TranMerc_bp = ((d * MAX_SCALE_FACTOR) * (((d11 - d12) + ((7.0d * d18) / 8.0d)) + ((55.0d * d15) / 64.0d))) / 2.0d;
            this.TranMerc_cp = ((15.0d * d) * (d16 + ((MAX_SCALE_FACTOR * d17) / 4.0d))) / 16.0d;
            this.TranMerc_dp = ((35.0d * d) * (d18 + ((d15 * 11.0d) / 16.0d))) / 48.0d;
            this.TranMerc_ep = ((d * 315.0d) * d17) / 512.0d;
            convertGeodeticToTransverseMercator(MAX_LAT, 1.5707963267948966d);
            this.TranMerc_Delta_Easting = getEasting();
            this.TranMerc_Delta_Northing = getNorthing();
            convertGeodeticToTransverseMercator(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 1.5707963267948966d);
            this.TranMerc_Delta_Easting = getEasting();
            this.TranMerc_Origin_Lat = d3;
            this.TranMerc_Origin_Long = d4 > 3.141592653589793d ? d4 - 6.283185307179586d : d4;
            this.TranMerc_False_Northing = d6;
            this.TranMerc_False_Easting = d5;
            this.TranMerc_Scale_Factor = d7;
        }
        return j;
    }

    public long convertGeodeticToTransverseMercator(double d, double d2) {
        long j = (d < -1.570621793869697d || d > MAX_LAT) ? 1 : 0;
        double d3 = d2 > 3.141592653589793d ? d2 - 6.283185307179586d : d2;
        double d4 = this.TranMerc_Origin_Long;
        if (d3 < d4 - 1.5707963267948966d || d3 > d4 + 1.5707963267948966d) {
            double d5 = d3 < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? d3 + 6.283185307179586d : d3;
            double d6 = d4 < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? d4 + 6.283185307179586d : d4;
            if (d5 < d6 - 1.5707963267948966d || d5 > d6 + 1.5707963267948966d) {
                j |= 2;
            }
        }
        if (j != 0) {
            return j;
        }
        double d7 = d3 - d4;
        if (Math.abs(d7) > 0.15707963267948966d) {
            j |= 512;
        }
        if (d7 > 3.141592653589793d) {
            d7 -= 6.283185307179586d;
        }
        if (d7 < -3.141592653589793d) {
            d7 += 6.283185307179586d;
        }
        if (Math.abs(d7) < 2.0E-10d) {
            d7 = 0.0d;
        }
        double sin = Math.sin(d);
        double cos = Math.cos(d);
        double d8 = cos * cos;
        double d9 = d8 * cos;
        double d10 = d9 * d8;
        double d11 = d10 * d8;
        double tan = Math.tan(d);
        double d12 = tan * tan;
        double d13 = d12 * tan * tan;
        double d14 = d13 * tan * tan;
        double d15 = this.TranMerc_ebs * d8;
        double d16 = d15 * d15;
        double d17 = d16 * d15;
        double d18 = d17 * d15;
        double sqrt = this.TranMerc_a / Math.sqrt(1.0d - (this.TranMerc_es * Math.pow(Math.sin(d), 2.0d)));
        double d19 = sin * sqrt;
        double d20 = 58.0d * d12;
        double d21 = this.TranMerc_False_Northing;
        double sin2 = ((((this.TranMerc_ap * d) - (this.TranMerc_bp * Math.sin(d * 2.0d))) + (this.TranMerc_cp * Math.sin(d * 4.0d))) - (this.TranMerc_dp * Math.sin(d * 6.0d))) + (this.TranMerc_ep * Math.sin(d * 8.0d));
        double d22 = this.TranMerc_ap;
        double d23 = this.TranMerc_Origin_Lat;
        double sin3 = d21 + ((sin2 - (((((d22 * d23) - (this.TranMerc_bp * Math.sin(d23 * 2.0d))) + (this.TranMerc_cp * Math.sin(this.TranMerc_Origin_Lat * 4.0d))) - (this.TranMerc_dp * Math.sin(this.TranMerc_Origin_Lat * 6.0d))) + (this.TranMerc_ep * Math.sin(this.TranMerc_Origin_Lat * 8.0d)))) * this.TranMerc_Scale_Factor) + (Math.pow(d7, 2.0d) * (((d19 * cos) * this.TranMerc_Scale_Factor) / 2.0d)) + (Math.pow(d7, 4.0d) * ((((d19 * d9) * this.TranMerc_Scale_Factor) * (((5.0d - d12) + (9.0d * d15)) + (d16 * 4.0d))) / 24.0d)) + (Math.pow(d7, 6.0d) * ((((d19 * d10) * this.TranMerc_Scale_Factor) * ((((((((((61.0d - d20) + d13) + (270.0d * d15)) - ((330.0d * d12) * d15)) + (445.0d * d16)) + (324.0d * d17)) - ((680.0d * d12) * d16)) + (88.0d * d18)) - ((600.0d * d12) * d17)) - ((192.0d * d12) * d18))) / 720.0d));
        double pow = Math.pow(d7, 8.0d);
        double d24 = d19 * d11;
        double d25 = this.TranMerc_Scale_Factor;
        this.Northing = sin3 + (pow * (((d24 * d25) * (((1385.0d - (3111.0d * d12)) + (543.0d * d13)) - d14)) / 40320.0d));
        this.Easting = this.TranMerc_False_Easting + (cos * sqrt * d25 * d7) + (Math.pow(d7, MAX_SCALE_FACTOR) * ((((sqrt * d9) * this.TranMerc_Scale_Factor) * ((1.0d - d12) + d15)) / 6.0d)) + (Math.pow(d7, 5.0d) * ((((sqrt * d10) * this.TranMerc_Scale_Factor) * ((((((((5.0d - (18.0d * d12)) + d13) + (14.0d * d15)) - (d20 * d15)) + (d16 * 13.0d)) + (d17 * 4.0d)) - ((64.0d * d12) * d16)) - ((24.0d * d12) * d17))) / 120.0d)) + (Math.pow(d7, 7.0d) * ((((sqrt * d11) * this.TranMerc_Scale_Factor) * (((61.0d - (d12 * 479.0d)) + (d13 * 179.0d)) - d14)) / 5040.0d));
        return j;
    }

    public double getEasting() {
        return this.Easting;
    }

    public double getNorthing() {
        return this.Northing;
    }

    public double getLatitude() {
        return this.Latitude;
    }

    public double getLongitude() {
        return this.Longitude;
    }
}
