package com.gps.camera.timestamp.photo.geotag.map.mgrs;

public class MGRSCoord {
    private final String MGRSString;
    private final Angle latitude;
    private final Angle longitude;

    public static MGRSCoord fromLatLon(Angle angle, Angle angle2) {
        return fromLatLon(angle, angle2, 5);
    }

    public static MGRSCoord fromLatLon(Angle angle, Angle angle2, int i) {
        if (angle == null || angle2 == null) {
            throw new IllegalArgumentException("Latitude Or Longitude Is Null");
        }
        MGRSCoordConverter mGRSCoordConverter = new MGRSCoordConverter();
        if (mGRSCoordConverter.convertGeodeticToMGRS(angle.radians, angle2.radians, i) == 0) {
            return new MGRSCoord(angle, angle2, mGRSCoordConverter.getMGRSString());
        }
        throw new IllegalArgumentException("MGRS Conversion Error");
    }

    public MGRSCoord(Angle angle, Angle angle2, String str) {
        if (angle == null || angle2 == null) {
            throw new IllegalArgumentException("Latitude Or Longitude Is Null");
        } else if (str == null) {
            throw new IllegalArgumentException("String Is Null");
        } else if (str.length() != 0) {
            this.latitude = angle;
            this.longitude = angle2;
            this.MGRSString = str;
        } else {
            throw new IllegalArgumentException("String Is Empty");
        }
    }

    public Angle getLatitude() {
        return this.latitude;
    }

    public Angle getLongitude() {
        return this.longitude;
    }

    public String toString() {
        return this.MGRSString;
    }
}
