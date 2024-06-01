package com.gps.camera.timestamp.photo.geotag.map.mgrs;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UPSCoordConverter {
    private static final double MAX_EAST_NORTH = 4000000.0d;
    private static final double MAX_ORIGIN_LAT = 1.4157155848011311d;
    private static final double MIN_NORTH_LAT = 1.2566370614359172d;
    private static final double MIN_SOUTH_LAT = -1.2566370614359172d;
    private double Easting = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private String Hemisphere = AVKey.NORTH;
    private double Latitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double Longitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double Northing = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double UPS_Easting = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double UPS_False_Easting = 2000000.0d;
    private double UPS_False_Northing = 2000000.0d;
    private double UPS_Northing = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double UPS_Origin_Latitude = MAX_ORIGIN_LAT;
    private double UPS_Origin_Longitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double UPS_a = 6378137.0d;
    private double UPS_f = 0.0033528106647474805d;
    private double false_easting = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double false_northing = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private PolarCoordConverter polarConverter = new PolarCoordConverter();

    UPSCoordConverter() {
    }

    public long convertGeodeticToUPS(double d, double d2) {
        if (d < -1.5707963267948966d || d > 1.5707963267948966d) {
            return 1;
        }
        int i = (d > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 1 : (d == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 0 : -1));
        char c = i > 0 ? 1 : i == 0 ? (char) 0 : 65535;
        if (c < 0 && d > MIN_SOUTH_LAT) {
            return 1;
        }
        if (i >= 0 && d < MIN_NORTH_LAT) {
            return 1;
        }
        if (d2 < -3.141592653589793d || d2 > 6.283185307179586d) {
            return 2;
        }
        if (c < 0) {
            this.UPS_Origin_Latitude = -1.4157155848011311d;
            this.Hemisphere = AVKey.SOUTH;
        } else {
            this.UPS_Origin_Latitude = MAX_ORIGIN_LAT;
            this.Hemisphere = AVKey.NORTH;
        }
        this.polarConverter.setPolarStereographicParameters(this.UPS_a, this.UPS_f, this.UPS_Origin_Latitude, this.UPS_Origin_Longitude, this.false_easting, this.false_northing);
        this.polarConverter.convertGeodeticToPolarStereographic(d, d2);
        this.UPS_Easting = this.UPS_False_Easting + this.polarConverter.getEasting();
        this.UPS_Northing = this.UPS_False_Northing + this.polarConverter.getNorthing();
        if (AVKey.SOUTH.equals(this.Hemisphere)) {
            this.UPS_Northing = this.UPS_False_Northing - this.polarConverter.getNorthing();
        }
        this.Easting = this.UPS_Easting;
        this.Northing = this.UPS_Northing;
        return 0;
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

    public double getLatitude() {
        return this.Latitude;
    }

    public double getLongitude() {
        return this.Longitude;
    }
}
