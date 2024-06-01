package com.gps.camera.timestamp.photo.geotag.map.camera;

public class KeysConstants {
    public static final String ALTITUDE = "altitudee";
    public static final String CAMERA_POSITION0 = "CAMERAPOSITIONNN";
    public static final String CAPTURE_TIMER = "capture_timer";
    public static final String COORDINATES_POSITION = "coordi_pos";
    public static final String TIME_POSITION = "time_pos";
    public static final String TIME_POSITION0 = "hh:mm:ss";
    public static final String UNIT_POSITION = "unit_pos";
    public static final String UNIT_POSITION0 = "Matric_metric";
    public static final String DATE_POSITION = "date_pos";
    public static final String DATE_POSITION0 = "dd_mm_yyyy";

    public static final String GRID_POS = "GRID_POS";
    public static final String IS_SD_CARD = "is_sd_card";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String MagneticAccuracyPreferenceKey = "done_magnetic_accuracy";
    public static final String ShowGeoDirectionLinesPreferenceKey = "preference_show_geo_direction_lines";
    public static final String ShowGeoDirectionPreferenceKey = "preference_show_geo_direction";

    public static String getFlashPos(String str) {
        return "flash_Poos_" + str;
    }
}
