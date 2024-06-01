package com.gps.camera.timestamp.photo.geotag.map.mgrs;

public class TMCoord {
    private final Angle centralMeridian;
    private final double easting;
    private final double falseEasting;
    private final double falseNorthing;
    private final Angle latitude;
    private final Angle longitude;
    private final double northing;
    private final Angle originLatitude;
    private final double scale;

    public static TMCoord fromLatLon(Angle angle, Angle angle2, Double d, Double d2, Angle angle3, Angle angle4, double d3, double d4, double d5) {
        Double d6;
        Double d7;
        if (angle == null || angle2 == null) {
            throw new IllegalArgumentException("Latitude Or Longitude Is Null");
        } else if (angle3 == null || angle4 == null) {
            throw new IllegalArgumentException("Angle Is Null");
        } else {
            TMCoordConverter tMCoordConverter = new TMCoordConverter();
            if (d == null || d2 == null) {
                d7 = Double.valueOf(tMCoordConverter.getA());
                d6 = Double.valueOf(tMCoordConverter.getF());
            } else {
                d7 = d;
                d6 = d2;
            }
            long transverseMercatorParameters = tMCoordConverter.setTransverseMercatorParameters(d7.doubleValue(), d6.doubleValue(), angle3.radians, angle4.radians, d3, d4, d5);
            if (transverseMercatorParameters == 0) {
                transverseMercatorParameters = tMCoordConverter.convertGeodeticToTransverseMercator(angle.radians, angle2.radians);
            }
            if (transverseMercatorParameters == 0 || transverseMercatorParameters == 512) {
                return new TMCoord(angle, angle2, tMCoordConverter.getEasting(), tMCoordConverter.getNorthing(), angle3, angle4, d3, d4, d5);
            }
            throw new IllegalArgumentException("TM Conversion Error");
        }
    }

    public TMCoord(Angle angle, Angle angle2, double d, double d2, Angle angle3, Angle angle4, double d3, double d4, double d5) {
        if (angle == null || angle2 == null) {
            throw new IllegalArgumentException("Latitude Or Longitude Is Null");
        } else if (angle3 == null || angle4 == null) {
            throw new IllegalArgumentException("Angle Is Null");
        } else {
            this.latitude = angle;
            this.longitude = angle2;
            this.easting = d;
            this.northing = d2;
            this.originLatitude = angle3;
            this.centralMeridian = angle4;
            this.falseEasting = d3;
            this.falseNorthing = d4;
            this.scale = d5;
        }
    }

    public Angle getLatitude() {
        return this.latitude;
    }

    public Angle getLongitude() {
        return this.longitude;
    }

    public double getEasting() {
        return this.easting;
    }

    public double getNorthing() {
        return this.northing;
    }
}
