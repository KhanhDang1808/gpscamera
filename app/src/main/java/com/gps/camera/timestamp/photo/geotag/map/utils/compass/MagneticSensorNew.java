package com.gps.camera.timestamp.photo.geotag.map.utils.compass;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.KeysConstants;
import com.gps.camera.timestamp.photo.geotag.map.ui.gps_camera.GpsCameraActivity;

public class MagneticSensorNew {
    private static final String TAG = "MagneticSensor";
    private Sensor mSensorMagnetic;
    private final SensorEventListener magneticListener = new SensorEventListener() {
        

        public void onSensorChanged(SensorEvent sensorEvent) {
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
            MagneticSensorNew.this.magnetic_accuracy = i;
            MagneticSensorNew.this.setMagneticAccuracyDialogText();
            MagneticSensorNew.this.isMagneticAccuracy();
        }
    };
    private boolean magneticListenerIsRegistered;
    private int magnetic_accuracy = -1;
    private AlertDialog magnetic_accuracy_dialog;
    private final GpsCameraActivity main_activity;
    private boolean shown_magnetic_accuracy_dialog = false;

    public MagneticSensorNew(GpsCameraActivity cAR_CameraActivity) {
        this.main_activity = cAR_CameraActivity;
    }

    public void initSensor(SensorManager sensorManager) {
        if (sensorManager.getDefaultSensor(2) != null) {
            this.mSensorMagnetic = sensorManager.getDefaultSensor(2);
        }
    }
    public void unregisterMagneticListener(SensorManager sensorManager) {
        if (this.magneticListenerIsRegistered) {
            sensorManager.unregisterListener(this.magneticListener);
            this.magneticListenerIsRegistered = false;
        }
    }

    public void magneticListenerRegister(SensorManager sensorManager) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.main_activity);
        if (!this.magneticListenerIsRegistered) {
            if (magneticSensorNeeds(defaultSharedPreferences)) {
                sensorManager.registerListener(this.magneticListener, this.mSensorMagnetic, 3);
                this.magneticListenerIsRegistered = true;
            }
        } else if (!magneticSensorNeeds(defaultSharedPreferences)) {
            sensorManager.unregisterListener(this.magneticListener);
            this.magneticListenerIsRegistered = false;
        }
    }


    public void isMagneticAccuracy() {
        int i = this.magnetic_accuracy;
        if ((i == 0 || i == 1) && !this.shown_magnetic_accuracy_dialog) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.main_activity);
            if (!magneticSensorNeeds(defaultSharedPreferences)) {
                return;
            }
            if (defaultSharedPreferences.contains(KeysConstants.MagneticAccuracyPreferenceKey)) {
                this.shown_magnetic_accuracy_dialog = true;
                return;
            }
            this.shown_magnetic_accuracy_dialog = true;
            setMagneticAccuracyDialogText();
        }
    }
    
    private void setMagneticAccuracyDialogText() {
        String str;
        if (this.magnetic_accuracy_dialog != null) {
            String str2 = this.main_activity.getResources().getString(R.string.magnetic_accuracy_info) + " ";
            int i = this.magnetic_accuracy;
            if (i == 0) {
                str = str2 + this.main_activity.getResources().getString(R.string.accuracy_unreliable);
            } else if (i == 1) {
                str = str2 + this.main_activity.getResources().getString(R.string.accuracy_low);
            } else if (i == 2) {
                str = str2 + this.main_activity.getResources().getString(R.string.accuracy_medium);
            } else if (i != 3) {
                str = str2 + this.main_activity.getResources().getString(R.string.accuracy_unknown);
            } else {
                str = str2 + this.main_activity.getResources().getString(R.string.accuracy_high);
            }
            this.magnetic_accuracy_dialog.setMessage(str);
        }
    }



    private boolean magneticSensorNeeds(SharedPreferences sharedPreferences) {
        return sharedPreferences.getBoolean(KeysConstants.ShowGeoDirectionLinesPreferenceKey, false) || sharedPreferences.getBoolean(KeysConstants.ShowGeoDirectionPreferenceKey, false);
    }

    public void clearDialog() {
        this.magnetic_accuracy_dialog = null;
    }
}
