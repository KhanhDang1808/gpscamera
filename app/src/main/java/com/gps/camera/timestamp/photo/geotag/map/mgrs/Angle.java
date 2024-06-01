package com.gps.camera.timestamp.photo.geotag.map.mgrs;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class Angle implements Comparable<Angle> {
    private static final double RADIANS_TO_DEGREES = 57.29577951308232d;
    public final double degrees;
    public final double radians;

    public static Angle fromDegrees(double d) {
        return new Angle(d, 0.017453292519943295d * d);
    }

    public static Angle fromRadians(double d) {
        return new Angle(RADIANS_TO_DEGREES * d, d);
    }

    public Angle(Angle angle) {
        this.degrees = angle.degrees;
        this.radians = angle.radians;
    }

    private Angle(double d, double d2) {
        this.degrees = d;
        this.radians = d2;
    }

    public final double getRadians() {
        return this.radians;
    }

    public final Angle add(Angle angle) {
        if (angle != null) {
            return fromDegrees(this.degrees + angle.degrees);
        }
        throw new IllegalArgumentException("Angle Is Null");
    }

    public final double sin() {
        return Math.sin(this.radians);
    }

    public final double sinHalfAngle() {
        return Math.sin(this.radians * 0.5d);
    }

    public final double cos() {
        return Math.cos(this.radians);
    }

    public final double cosHalfAngle() {
        return Math.cos(this.radians * 0.5d);
    }

    public final double tanHalfAngle() {
        return Math.tan(this.radians * 0.5d);
    }

    public final int compareTo(Angle angle) {
        if (angle != null) {
            double d = this.degrees;
            double d2 = angle.degrees;
            if (d < d2) {
                return -1;
            }
            return d > d2 ? 1 : 0;
        }
        throw new IllegalArgumentException("Angle Is Null");
    }

    public final String toString() {
        return Double.toString(this.degrees) + 176;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && ((Angle) obj).degrees == this.degrees;
    }

    public int hashCode() {
        double d = this.degrees;
        long doubleToLongBits = d != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? Double.doubleToLongBits(d) : 0;
        return (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
    }
}
