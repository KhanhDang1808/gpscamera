package com.gps.camera.timestamp.photo.geotag.map.mgrs;


import com.facebook.appevents.AppEventsConstants;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

class MGRSCoordConverter {
    private static final String BESSEL_1841 = "BR";
    private static final String BESSEL_1841_NAMIBIA = "BN";
    private static final String CLARKE_1866 = "CC";
    private static final String CLARKE_1880 = "CD";
    private static final double MAX_EAST_NORTH = 4000000.0d;
    private static final double MAX_UTM_LAT = 1.4660765716752369d;
    private static final double MIN_UTM_LAT = -1.3962634015954636d;
    private static final double ONEHT = 100000.0d;
    private static final double RAD_TO_DEG = 57.29577951308232d;
    private static final double TWOMIL = 2000000.0d;
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final double[][] latitudeBandConstants;
    private static final long[][] upsConstants = {new long[]{0, 9, 25, 25, 800000, 800000}, new long[]{1, 0, 17, 25, 2000000, 800000}, new long[]{24, 9, 25, 15, 800000, 1300000}, new long[]{25, 0, 9, 15, 2000000, 1300000}};
    private String MGRSString = "";
    private String MGRS_Ellipsoid_Code = "WE";
    private double MGRS_a = 6378137.0d;
    private double MGRS_f = 0.0033528106647474805d;
    private double false_northing;
    private long lastLetter;
    private long last_error = 0;
    private double latitude;
    private double longitude;
    private long ltr2_high_value;
    private long ltr2_low_value;
    private double min_northing;
    private double north;
    private double northing_offset;
    private double south;

    static {
        double[] dArr = new double[5];
        
        dArr[0] = 18.0d;
        dArr[1] = 3500000.0d;
        dArr[2] = 40.0d;
        dArr[3] = 32.0d;
        dArr[4] = 2000000.0d;
        latitudeBandConstants = new double[][]{new double[]{2.0d, 1100000.0d, -72.0d, -80.5d, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE}, new double[]{3.0d, TWOMIL, -64.0d, -72.0d, TWOMIL}, new double[]{4.0d, 2800000.0d, -56.0d, -64.0d, TWOMIL}, new double[]{5.0d, 3700000.0d, -48.0d, -56.0d, TWOMIL}, new double[]{6.0d, 4600000.0d, -40.0d, -48.0d, MAX_EAST_NORTH}, new double[]{7.0d, 5500000.0d, -32.0d, -40.0d, MAX_EAST_NORTH}, new double[]{9.0d, 6400000.0d, -24.0d, -32.0d, 6000000.0d}, new double[]{10.0d, 7300000.0d, -16.0d, -24.0d, 6000000.0d}, new double[]{11.0d, 8200000.0d, -8.0d, -16.0d, 8000000.0d}, new double[]{12.0d, 9100000.0d, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, -8.0d, 8000000.0d}, new double[]{13.0d, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 8.0d, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE}, new double[]{15.0d, 800000.0d, 16.0d, 8.0d, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE}, new double[]{16.0d, 1700000.0d, 24.0d, 16.0d, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE}, new double[]{17.0d, 2600000.0d, 32.0d, 24.0d, TWOMIL}, dArr, new double[]{19.0d, 4400000.0d, 48.0d, 40.0d, MAX_EAST_NORTH}, new double[]{20.0d, 5300000.0d, 56.0d, 48.0d, MAX_EAST_NORTH}, new double[]{21.0d, 6200000.0d, 64.0d, 56.0d, 6000000.0d}, new double[]{22.0d, 7000000.0d, 72.0d, 64.0d, 6000000.0d}, new double[]{23.0d, 7900000.0d, 84.5d, 72.0d, 6000000.0d}};
    }

    public class MGRSComponents {
        private final double easting;
        private final int latitudeBand;
        private final double northing;
        private final int precision;
        private final int squareLetter1;
        private final int squareLetter2;
        private final int zone;

        public MGRSComponents(int i, int i2, int i3, int i4, double d, double d2, int i5) {
            this.zone = i;
            this.latitudeBand = i2;
            this.squareLetter1 = i3;
            this.squareLetter2 = i4;
            this.easting = d;
            this.northing = d2;
            this.precision = i5;
        }

        public String toString() {
            return "MGRS: " + this.zone + " " + MGRSCoordConverter.alphabet.charAt(this.latitudeBand) + " " + MGRSCoordConverter.alphabet.charAt(this.squareLetter1) + MGRSCoordConverter.alphabet.charAt(this.squareLetter2) + " " + this.easting + " " + this.northing + " (" + this.precision + ")";
        }
    }

    MGRSCoordConverter() {
    }

    private long getLastLetter() {
        return this.lastLetter;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public long convertGeodeticToMGRS(double d, double d2, int i) {
        long j;
        this.MGRSString = "";
        long j2 = (d < -1.5707963267948966d || d > 1.5707963267948966d) ? 1 : 0;
        if (d2 < -3.141592653589793d || d2 > 6.283185307179586d) {
            j2 = 2;
        }
        if (i < 0 || i > 5) {
            j2 = 8;
        }
        if (j2 != 0) {
            return j2;
        }
        if (d < MIN_UTM_LAT || d > MAX_UTM_LAT) {
            try {
                UPSCoord fromLatLon = UPSCoord.fromLatLon(Angle.fromRadians(d), Angle.fromRadians(d2));
                j = convertUPSToMGRS(fromLatLon.getHemisphere(), Double.valueOf(fromLatLon.getEasting()), Double.valueOf(fromLatLon.getNorthing()), (long) i);
            } catch (Exception unused) {
                return 8192;
            }
        } else {
            try {
                UTMCoord fromLatLon2 = UTMCoord.fromLatLon(Angle.fromRadians(d), Angle.fromRadians(d2));
                j = convertUTMToMGRS((long) fromLatLon2.getZone(), d, fromLatLon2.getEasting(), fromLatLon2.getNorthing(), (long) i);
            } catch (Exception unused2) {
                return 4096;
            }
        }
        return j | j2;
    }

    public String getMGRSString() {
        return this.MGRSString;
    }

    private long convertUPSToMGRS(String str, Double d, Double d2, long j) {
        double d3;
        int i;
        double d4;
        long j2;
        long[] jArr = new long[3];
        long j3 = (AVKey.NORTH.equals(str) || AVKey.SOUTH.equals(str)) ? 0 : 512;
        if (d.doubleValue() < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE || d.doubleValue() > MAX_EAST_NORTH) {
            j3 |= 64;
        }
        if (d2.doubleValue() < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE || d2.doubleValue() > MAX_EAST_NORTH) {
            j3 |= 128;
        }
        if (j < 0 || j > 5) {
            j3 |= 8;
        }
        if (j3 == 0) {
            double pow = Math.pow(10.0d, (double) (5 - j));
            Double valueOf = Double.valueOf(roundMGRS(d.doubleValue() / pow) * pow);
            Double valueOf2 = Double.valueOf(roundMGRS(d2.doubleValue() / pow) * pow);
            if (AVKey.NORTH.equals(str)) {
                if (valueOf.doubleValue() >= TWOMIL) {
                    jArr[0] = 25;
                } else {
                    jArr[0] = 24;
                }
                long[] jArr2 = upsConstants[((int) jArr[0]) - 22];
                i = (int) jArr2[1];
                d3 = (double) jArr2[4];
                d4 = (double) jArr2[5];
            } else {
                if (valueOf.doubleValue() >= TWOMIL) {
                    jArr[0] = 1;
                } else {
                    jArr[0] = 0;
                }
                long[][] jArr3 = upsConstants;
                long j4 = jArr[0];
                int i2 = (int) jArr3[(int) j4][1];
                d3 = (double) jArr3[(int) j4][4];
                d4 = (double) jArr3[(int) j4][5];
                i = i2;
            }
            long doubleValue = (long) ((int) ((valueOf2.doubleValue() - d4) / ONEHT));
            jArr[2] = doubleValue;
            if (doubleValue > 7) {
                j2 = 1;
                jArr[2] = doubleValue + 1;
            } else {
                j2 = 1;
            }
            long j5 = jArr[2];
            if (j5 > 13) {
                jArr[2] = j5 + j2;
            }
            jArr[1] = (long) (i + ((int) ((valueOf.doubleValue() - d3) / ONEHT)));
            if (valueOf.doubleValue() < TWOMIL) {
                long j6 = jArr[1];
                if (j6 > 11) {
                    jArr[1] = j6 + 3;
                }
                long j7 = jArr[1];
                if (j7 > 20) {
                    jArr[1] = j7 + 2;
                }
            } else {
                long j8 = jArr[1];
                if (j8 > 2) {
                    jArr[1] = j8 + 2;
                }
                long j9 = jArr[1];
                if (j9 > 7) {
                    jArr[1] = j9 + 1;
                }
                long j10 = jArr[1];
                if (j10 > 11) {
                    jArr[1] = j10 + 3;
                }
            }
            makeMGRSString(0, jArr, valueOf.doubleValue(), valueOf2.doubleValue(), j);
        }
        return j3;
    }

    private long convertUTMToMGRS(long j, double d, double d2, double d3, long j2) {
        long[] jArr = new long[3];
        double pow = Math.pow(10.0d, (double) (5 - j2));
        double roundMGRS = roundMGRS(d2 / pow) * pow;
        double roundMGRS2 = roundMGRS(d3 / pow) * pow;
        getGridValues(j);
        long latitudeLetter = getLatitudeLetter(d);
        jArr[0] = getLastLetter();
        if (latitudeLetter == 0) {
            double d4 = roundMGRS2 == 1.0E7d ? roundMGRS2 - 1.0d : roundMGRS2;
            while (d4 >= TWOMIL) {
                d4 -= TWOMIL;
            }
            double d5 = d4 + this.false_northing;
            if (d5 >= TWOMIL) {
                d5 -= TWOMIL;
            }
            long j3 = (long) (d5 / ONEHT);
            jArr[2] = j3;
            if (j3 > 7) {
                jArr[2] = j3 + 1;
            }
            long j4 = jArr[2];
            if (j4 > 13) {
                jArr[2] = j4 + 1;
            }
            double d6 = (jArr[0] == 21 && j == 31 && roundMGRS == 500000.0d) ? roundMGRS - 1.0d : roundMGRS;
            long j5 = this.ltr2_low_value;
            long j6 = (((long) (d6 / ONEHT)) - 1) + j5;
            jArr[1] = j6;
            if (j5 == 9 && j6 > 13) {
                jArr[1] = j6 + 1;
            }
            makeMGRSString(j, jArr, roundMGRS, roundMGRS2, j2);
        }
        return latitudeLetter;
    }

    private void getGridValues(long j) {
        long j2 = j % 6;
        if (j2 == 0) {
            j2 = 6;
        }
        long j3 = (this.MGRS_Ellipsoid_Code.compareTo(CLARKE_1866) == 0 || this.MGRS_Ellipsoid_Code.compareTo(CLARKE_1880) == 0 || this.MGRS_Ellipsoid_Code.compareTo(BESSEL_1841) == 0 || this.MGRS_Ellipsoid_Code.compareTo(BESSEL_1841_NAMIBIA) == 0) ? 0 : 1;
        if (j2 == 1 || j2 == 4) {
            this.ltr2_low_value = 0;
            this.ltr2_high_value = 7;
        } else if (j2 == 2 || j2 == 5) {
            this.ltr2_low_value = 9;
            this.ltr2_high_value = 17;
        } else if (j2 == 3 || j2 == 6) {
            this.ltr2_low_value = 18;
            this.ltr2_high_value = 25;
        }
        if (j3 == 1) {
            if (j2 % 2 == 0) {
                this.false_northing = 500000.0d;
            } else {
                this.false_northing = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            }
        } else if (j2 % 2 == 0) {
            this.false_northing = 1500000.0d;
        } else {
            this.false_northing = 1000000.0d;
        }
    }

    private long getLatitudeLetter(double d) {
        double d2 = RAD_TO_DEG * d;
        int i = (d2 > 72.0d ? 1 : (d2 == 72.0d ? 0 : -1));
        if (i >= 0 && d2 < 84.5d) {
            this.lastLetter = 23;
            return 0;
        } else if (d2 <= -80.5d || i >= 0) {
            return 1;
        } else {
            this.lastLetter = (long) latitudeBandConstants[(int) (((d + 1.3962634015954636d) / 0.13962634015954636d) + 1.0E-12d)][0];
            return 0;
        }
    }

    private double roundMGRS(double d) {
        double floor = Math.floor(d);
        double d2 = d - floor;
        long j = (long) floor;
        int i = (d2 > 0.5d ? 1 : (d2 == 0.5d ? 0 : -1));
        char c = i > 0 ? 1 : i == 0 ? (char) 0 : 65535;
        if (c > 0 || (c == 0 && j % 2 == 1)) {
            j++;
        }
        return (double) j;
    }

    private long makeMGRSString(long j, long[] jArr, double d, double d2, long j2) {
        double d3;
        String str;
        String str2;
        if (j != 0) {
            this.MGRSString = String.format("%02d", Long.valueOf(j));
        } else {
            this.MGRSString = "  ";
        }
        for (int i = 0; i < 3; i++) {
            long j3 = jArr[i];
            if (j3 < 0 || j3 > 26) {
                return 256;
            }
            this.MGRSString += alphabet.charAt((int) jArr[i]);
        }
        double pow = Math.pow(10.0d, (double) (5 - j2));
        double d4 = d % ONEHT;
        double d5 = 99999.0d;
        if (d4 >= 99999.5d) {
            d4 = 99999.0d;
        }
        String num = Integer.valueOf((int) ((long) (d4 / pow))).toString();
        if (((long) num.length()) > j2) {
            str = num.substring(0, ((int) j2) - 1);
            d3 = pow;
        } else {
            int length = num.length();
            d3 = pow;
            for (int i2 = 0; ((long) i2) < j2 - ((long) length); i2++) {
                num = AppEventsConstants.EVENT_PARAM_VALUE_NO + num;
            }
            str = num;
        }
        this.MGRSString += " " + str;
        double d6 = d2 % ONEHT;
        if (d6 < 99999.5d) {
            d5 = d6;
        }
        String num2 = Integer.valueOf((int) ((long) (d5 / d3))).toString();
        if (((long) num2.length()) > j2) {
            str2 = num2.substring(0, ((int) j2) - 1);
        } else {
            int length2 = num2.length();
            for (int i3 = 0; ((long) i3) < j2 - ((long) length2); i3++) {
                num2 = AppEventsConstants.EVENT_PARAM_VALUE_NO + num2;
            }
            str2 = num2;
        }
        this.MGRSString += " " + str2;
        return 0;
    }

    public long getError() {
        return this.last_error;
    }
}
