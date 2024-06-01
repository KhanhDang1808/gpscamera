package com.gps.camera.timestamp.photo.geotag.map.mgrs;

import androidx.exifinterface.media.ExifInterface;

public class UPSCoord {
    private final double easting;
    private final String hemisphere;
    private final Angle latitude;
    private final Angle longitude;
    private final double northing;

    public static UPSCoord fromLatLon(Angle angle, Angle angle2) {
        if (angle == null || angle2 == null) {
            throw new IllegalArgumentException("Latitude Or Longitude Is Null");
        }
        UPSCoordConverter uPSCoordConverter = new UPSCoordConverter();
        if (uPSCoordConverter.convertGeodeticToUPS(angle.radians, angle2.radians) == 0) {
            return new UPSCoord(angle, angle2, uPSCoordConverter.getHemisphere(), uPSCoordConverter.getEasting(), uPSCoordConverter.getNorthing());
        }
        throw new IllegalArgumentException("UPS Conversion Error");
    }

    public UPSCoord(Angle angle, Angle angle2, String str, double d, double d2) {
        if (angle == null || angle2 == null) {
            throw new IllegalArgumentException("Latitude Or Longitude Is Null");
        }
        this.latitude = angle;
        this.longitude = angle2;
        this.hemisphere = str;
        this.easting = d;
        this.northing = d2;
    }

    public Angle getLatitude() {
        return this.latitude;
    }

    public Angle getLongitude() {
        return this.longitude;
    }

    public String getHemisphere() {
        return this.hemisphere;
    }

    public double getEasting() {
        return this.easting;
    }

    public double getNorthing() {
        return this.northing;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AVKey.NORTH.equals(this.hemisphere) ? "N" : ExifInterface.LATITUDE_SOUTH);
        sb.append(" ");
        sb.append(this.easting);
        sb.append("E ");
        sb.append(this.northing);
        sb.append("N");
        return sb.toString();
    }
}
