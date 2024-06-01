package com.gps.camera.timestamp.photo.geotag.map.mgrs;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class PolarCoordConverter {
    private static final double PI_Over_4 = 0.7853981633974483d;
    private static final long POLAR_A_ERROR = 64;
    private static final long POLAR_INV_F_ERROR = 128;
    private static final long POLAR_LAT_ERROR = 1;
    private static final long POLAR_LON_ERROR = 2;
    private static final long POLAR_ORIGIN_LAT_ERROR = 4;
    private static final long POLAR_ORIGIN_LON_ERROR = 8;
    private static final double TWO_PI = 6.283185307179586d;
    private double Easting;
    private double Latitude;
    private double Longitude;
    private double Northing;
    private double Polar_Delta_Easting = 1.2713601E7d;
    private double Polar_Delta_Northing = 1.2713601E7d;
    private double Polar_False_Easting = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double Polar_False_Northing = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double Polar_Origin_Lat = 1.5707963267948966d;
    private double Polar_Origin_Long = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double Polar_a = 6378137.0d;
    private double Polar_a_mc = 6378137.0d;
    private double Polar_f = 0.0033528106647474805d;
    private double Southern_Hemisphere = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;

    
    private double f150e4 = 1.0033565552493d;

    
    private double f151es = 0.08181919084262188d;
    private double es_OVER_2 = 0.040909595421311d;

    
    private double f152mc = 1.0d;

    
    private double f153tc = 1.0d;
    private double two_Polar_a = 1.2756274E7d;

    PolarCoordConverter() {
    }

    public long setPolarStereographicParameters(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = 1.0d / d2;
        long j = d <= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 64 : 0;
        if (d7 < 250.0d || d7 > 350.0d) {
            j |= 128;
        }
        if (d3 < -1.5707963267948966d || d3 > 1.5707963267948966d) {
            j |= 4;
        }
        if (d4 < -3.141592653589793d || d4 > TWO_PI) {
            j |= 8;
        }
        if (j == 0) {
            this.Polar_a = d;
            this.two_Polar_a = d * 2.0d;
            this.Polar_f = d2;
            double d8 = d4 > 3.141592653589793d ? d4 - TWO_PI : d4;
            if (d3 < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                this.Southern_Hemisphere = 1.0d;
                this.Polar_Origin_Lat = -d3;
                this.Polar_Origin_Long = -d8;
            } else {
                this.Southern_Hemisphere = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                this.Polar_Origin_Lat = d3;
                this.Polar_Origin_Long = d8;
            }
            this.Polar_False_Easting = d5;
            this.Polar_False_Northing = d6;
            double sqrt = Math.sqrt((d2 * 2.0d) - (d2 * d2));
            this.f151es = sqrt;
            this.es_OVER_2 = sqrt / 2.0d;
            if (Math.abs(Math.abs(this.Polar_Origin_Lat) - 1.5707963267948966d) > 1.0E-10d) {
                double sin = this.f151es * Math.sin(this.Polar_Origin_Lat);
                double pow = Math.pow((1.0d - sin) / (sin + 1.0d), this.es_OVER_2);
                double cos = Math.cos(this.Polar_Origin_Lat) / Math.sqrt(1.0d - (sin * sin));
                this.f152mc = cos;
                this.Polar_a_mc = this.Polar_a * cos;
                this.f153tc = Math.tan(PI_Over_4 - (this.Polar_Origin_Lat / 2.0d)) / pow;
            } else {
                double d9 = this.f151es;
                double d10 = d9 + 1.0d;
                double d11 = 1.0d - d9;
                this.f150e4 = Math.sqrt(Math.pow(d10, d10) * Math.pow(d11, d11));
            }
        }
        convertGeodeticToPolarStereographic(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, this.Polar_Origin_Long);
        double d12 = this.Northing * 2.0d;
        this.Polar_Delta_Northing = d12;
        double abs = Math.abs(d12) + 0.01d;
        this.Polar_Delta_Northing = abs;
        this.Polar_Delta_Easting = abs;
        return j;
    }

    public long convertGeodeticToPolarStereographic(double d, double d2) {
        double d3;
        double d4;
        double d5;
        double d6;
        long j = (d < -1.5707963267948966d || d > 1.5707963267948966d) ? 1 : 0;
        if (d < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE && this.Southern_Hemisphere == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            j |= 1;
        }
        if (d > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE && this.Southern_Hemisphere == 1.0d) {
            j |= 1;
        }
        if (d2 < -3.141592653589793d || d2 > TWO_PI) {
            j |= 2;
        }
        if (j == 0) {
            if (Math.abs(Math.abs(d) - 1.5707963267948966d) < 1.0E-10d) {
                this.Easting = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                this.Northing = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            } else {
                if (this.Southern_Hemisphere != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    d3 = d2 * -1.0d;
                    d4 = -1.0d * d;
                } else {
                    d4 = d;
                    d3 = d2;
                }
                double d7 = d3 - this.Polar_Origin_Long;
                if (d7 > 3.141592653589793d) {
                    d7 -= TWO_PI;
                }
                if (d7 < -3.141592653589793d) {
                    d7 += TWO_PI;
                }
                double sin = this.f151es * Math.sin(d4);
                double tan = Math.tan(PI_Over_4 - (d4 / 2.0d)) / Math.pow((1.0d - sin) / (sin + 1.0d), this.es_OVER_2);
                if (Math.abs(Math.abs(this.Polar_Origin_Lat) - 1.5707963267948966d) > 1.0E-10d) {
                    d5 = this.Polar_a_mc * tan;
                    d6 = this.f153tc;
                } else {
                    d5 = this.two_Polar_a * tan;
                    d6 = this.f150e4;
                }
                double d8 = d5 / d6;
                if (this.Southern_Hemisphere != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    this.Easting = -((Math.sin(d7) * d8) - this.Polar_False_Easting);
                    this.Northing = (Math.cos(d7) * d8) + this.Polar_False_Northing;
                } else {
                    this.Easting = (Math.sin(d7) * d8) + this.Polar_False_Easting;
                }
                this.Northing = ((-d8) * Math.cos(d7)) + this.Polar_False_Northing;
            }
        }
        return j;
    }

    public double getEasting() {
        return this.Easting;
    }

    public double getNorthing() {
        return this.Northing;
    }

    public long convertPolarStereographicToGeodetic(double d, double d2) {
        double d3;
        double d4;
        long j;
        double d5;
        long j2;
        double d6;
        double d7;
        double d8 = this.Polar_False_Northing;
        double d9 = this.Polar_Delta_Northing;
        double d10 = d8 - d9;
        double d11 = d9 + d8;
        double d12 = this.Polar_False_Easting;
        double d13 = this.Polar_Delta_Easting;
        long j3 = (d > d12 + d13 || d < d12 - d13) ? 16 : 0;
        if (d2 > d11 || d2 < d10) {
            j3 |= 32;
        }
        if (j3 == 0) {
            d5 = d2 - d8;
            d4 = d - d12;
            d3 = Math.sqrt((d4 * d4) + (d5 * d5));
            double d14 = this.Polar_Delta_Easting;
            double d15 = this.Polar_Delta_Northing;
            if (d3 > Math.sqrt((d14 * d14) + (d15 * d15))) {
                j3 = 256 | j3;
            }
            j = 0;
        } else {
            j = 0;
            d5 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            d4 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            d3 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        }
        if (j3 != j) {
            return j3;
        }
        if (d5 == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE && d4 == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            this.Latitude = 1.5707963267948966d;
            this.Longitude = this.Polar_Origin_Long;
            j2 = j3;
        } else {
            if (this.Southern_Hemisphere != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                d5 *= -1.0d;
                d4 *= -1.0d;
            }
            if (Math.abs(Math.abs(this.Polar_Origin_Lat) - 1.5707963267948966d) > 1.0E-10d) {
                d6 = d3 * this.f153tc;
                d7 = this.Polar_a_mc;
            } else {
                d6 = d3 * this.f150e4;
                d7 = this.two_Polar_a;
            }
            double d16 = d6 / d7;
            double atan = 1.5707963267948966d - (Math.atan(d16) * 2.0d);
            double d17 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            for (double d18 = 1.0E-10d; Math.abs(atan - d17) > d18; d18 = 1.0E-10d) {
                double sin = this.f151es * Math.sin(atan);
                d17 = atan;
                atan = 1.5707963267948966d - (Math.atan(Math.pow((1.0d - sin) / (sin + 1.0d), this.es_OVER_2) * d16) * 2.0d);
                j3 = j3;
            }
            j2 = j3;
            this.Latitude = atan;
            double atan2 = this.Polar_Origin_Long + Math.atan2(d4, -d5);
            this.Longitude = atan2;
            if (atan2 > 3.141592653589793d) {
                this.Longitude = atan2 - TWO_PI;
            } else if (atan2 < -3.141592653589793d) {
                this.Longitude = atan2 + TWO_PI;
            }
            double d19 = this.Latitude;
            if (d19 > 1.5707963267948966d) {
                this.Latitude = 1.5707963267948966d;
            } else if (d19 < -1.5707963267948966d) {
                this.Latitude = -1.5707963267948966d;
            }
            double d20 = this.Longitude;
            if (d20 > 3.141592653589793d) {
                this.Longitude = 3.141592653589793d;
            } else if (d20 < -3.141592653589793d) {
                this.Longitude = -3.141592653589793d;
            }
        }
        if (this.Southern_Hemisphere == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            return j2;
        }
        this.Latitude *= -1.0d;
        this.Longitude *= -1.0d;
        return j2;
    }

    public double getLatitude() {
        return this.Latitude;
    }

    public double getLongitude() {
        return this.Longitude;
    }
}
