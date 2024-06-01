package com.gps.camera.timestamp.photo.geotag.map.utils;

import static com.gps.camera.timestamp.photo.geotag.map.camera.Default.PARENT_FOLDER_PATH;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;

public class Utils {

    public static int STATE_ALIGN_RIGHT = 1;
    public static int STATE_ALIGN_CENTER = 2;
    public static int STATE_ALIGN_LEFT = 3;


    public static boolean isAndroidTiramisu() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }

    public static boolean isAndroid9() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.P;
    }

    public static boolean isAndroidR() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    public static String[] STORAGE_PERMISSION_STORAGE_SCOPE = {Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String[] STORAGE_PERMISSION_UNDER_STORAGE_SCOPE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String[] READ_MEDIA_IMAGES_PERMISSION = {Manifest.permission.READ_MEDIA_IMAGES};
//    public static String[] RECORD_PERMISSION = {Manifest.permission.RECORD_AUDIO};
    public static String[] CAMERA_PERMISSION = {Manifest.permission.CAMERA};
    public static String[] LOCATION_PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    public static String[] getStoragePermissions() {
        if (isAndroidTiramisu()) {
            return READ_MEDIA_IMAGES_PERMISSION;
        } else if (isAndroidR()) {
            return STORAGE_PERMISSION_STORAGE_SCOPE;
        } else {
            return STORAGE_PERMISSION_UNDER_STORAGE_SCOPE;
        }
    }

    public static String[] getCameraPermission() {
        return CAMERA_PERMISSION;
    }
/*    public static String[] getRecordPermission() {
        return RECORD_PERMISSION;
    }*/
    public static String[] getLocationPermission() {
        return LOCATION_PERMISSION;
    }
    public static boolean allPermissionGrant(Context context, String[] arrays) {
        boolean isGranted = true;
        for (String permission : arrays) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }
        return isGranted;
    }

    public static void setMarginsRight (View v, int t) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(p.leftMargin, p.topMargin, t, p.bottomMargin);
            v.requestLayout();
        }
    }

    public static void hideKeyboard(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    public static File screenshot(View view, String filename) {
        try {
            view.setDrawingCacheEnabled(true);
            Bitmap finalBitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File fileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            String fname = filename + System.currentTimeMillis() + ".jpg";

            File file = new File(fileDir, fname);
            if (file.exists())
                file.delete();
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int countImagesInFolder() {
        File directory = new File(PARENT_FOLDER_PATH);
        if (!directory.exists() || !directory.isDirectory()) {
            return 0; // Trả về 0 nếu thư mục không tồn tại hoặc không phải là thư mục
        }
        FilenameFilter imageFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                return lowercaseName.endsWith(".jpg") ||
                        lowercaseName.endsWith(".png") ;
            }
        };
        File[] imageFiles = directory.listFiles(imageFilter);
        return imageFiles != null ? imageFiles.length : 0;
    }

    public static int checkHaveImagesInFolder() {
        File directory = new File(PARENT_FOLDER_PATH);
        if (!directory.exists() || !directory.isDirectory()) {
            return 0; // Trả về 0 nếu thư mục không tồn tại hoặc không phải là thư mục
        }
        FilenameFilter imageFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                return lowercaseName.endsWith(".jpg") ||
                        lowercaseName.endsWith(".png") ;
            }
        };
        File[] imageFiles = directory.listFiles(imageFilter);
        return imageFiles != null ? imageFiles.length : 0;
    }

}
