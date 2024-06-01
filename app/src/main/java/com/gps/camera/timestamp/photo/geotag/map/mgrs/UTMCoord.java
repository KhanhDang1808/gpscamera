package com.gps.camera.timestamp.photo.geotag.map.mgrs;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UTMCoord {
    private Angle centralMeridian;
    private final double easting;
    private final String hemisphere;
    private final Angle latitude;
    private final Angle longitude;
    private final double northing;
    private final int zone;

    public static UTMCoord fromLatLon(Angle angle, Angle angle2) {
        if (angle == null || angle2 == null) {
            throw new IllegalArgumentException("Latitude Or Longitude Is Null");
        }
        UTMCoordConverter uTMCoordConverter = new UTMCoordConverter();
        if (uTMCoordConverter.convertGeodeticToUTM(angle.radians, angle2.radians) == 0) {
            return new UTMCoord(angle, angle2, uTMCoordConverter.getZone(), uTMCoordConverter.getHemisphere(), uTMCoordConverter.getEasting(), uTMCoordConverter.getNorthing(), Angle.fromRadians(uTMCoordConverter.getCentralMeridian()));
        }
        throw new IllegalArgumentException("UTM Conversion Error");
    }

    public UTMCoord(Angle angle, Angle angle2, int i, String str, double d, double d2, Angle angle3) {
        if (angle == null || angle2 == null) {
            throw new IllegalArgumentException("Latitude Or Longitude Is Null");
        }
        this.latitude = angle;
        this.longitude = angle2;
        this.hemisphere = str;
        this.zone = i;
        this.easting = d;
        this.northing = d2;
        this.centralMeridian = angle3;
    }

    public Angle getLatitude() {
        return this.latitude;
    }

    public Angle getLongitude() {
        return this.longitude;
    }

    public int getZone() {
        return this.zone;
    }

    public double getEasting() {
        return this.easting;
    }

    public double getNorthing() {
        return this.northing;
    }

    public String toString() {
        return this.zone + getLetter() + " " + ((int) this.easting) + "E " + ((int) this.northing) + "N";
    }

    private String getLetter() {
        double d = getLatitude().degrees;
        return String.valueOf(d < -72.0d ? 'C' : d < -64.0d ? 'D' : d < -56.0d ? 'E' : d < -48.0d ? 'F' : d < -40.0d ? 'G' : d < -32.0d ? 'H' : d < -24.0d ? 'J' : d < -16.0d ? 'K' : d < -8.0d ? 'L' : d < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 'M' : d < 8.0d ? 'N' : d < 16.0d ? 'P' : d < 24.0d ? 'Q' : d < 32.0d ? 'R' : d < 40.0d ? 'S' : d < 48.0d ? 'T' : d < 56.0d ? 'U' : d < 64.0d ? 'V' : d < 72.0d ? 'W' : 'X');
    }
}
