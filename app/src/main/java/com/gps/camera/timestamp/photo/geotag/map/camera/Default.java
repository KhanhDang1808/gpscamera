package com.gps.camera.timestamp.photo.geotag.map.camera;

import android.os.Environment;

public class Default {
    public static final String AUTOMATIC = "Automatic";
    public static final Object CAMERA_FOLDER = "camera_folder";
    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yy hh:mm a";
    public static final String DEFAULT_FOLDER_NAME = "Default";
    public static final String DEFAULT_FOLDER_PATH = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera");
    public static final String DEFAULT_FONT_STYLE = "sfuitext_regular.otf";
    public static final String HYBRID_4 = "hybrid";
    public static final String LOGO_uri = "no_logo";
    public static final String MANUAL = "Manual";
    public static final String NORMAL_1 = "roadmap";
    public static final String FOLDER_NAME = "GPS MAP CAMERA";
    public static final String PARENT_FOLDER_PATH = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + FOLDER_NAME);
    public static final String SETELLITE_2 = "satellite";
    public static final String SITE_1 = "Desination 1";
    public static final String SITE_2 = "Desination 2";
    public static final String TERRAIN_3 = "terrain";
    public static final String notes = "Note : Captured by GPS Map Camera";
}
