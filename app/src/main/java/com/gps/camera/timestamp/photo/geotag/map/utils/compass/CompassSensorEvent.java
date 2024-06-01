package com.gps.camera.timestamp.photo.geotag.map.utils.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class CompassSensorEvent implements SensorEventListener {

    private float[] mGeomagnetic = new float[3];
    private float[] mGravity = new float[3];
    private float magStrength;
    private Sensor msensor;
    private boolean sensorData = false;
    private SensorManager sensorManager;
    private boolean statusFlag = false;
    private float[] f148I = new float[9];
    private float[] f149R = new float[9];
    private float azimuth;
    private Sensor gsensor;
    private CompassListener listener;


    public interface CompassListener {
        void onMagField(float f);

        void onNewAzimuth(float f);
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public CompassSensorEvent(Context context) {
        SensorManager sensorManager2 = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensorManager = sensorManager2;
        this.gsensor = sensorManager2.getDefaultSensor(1);
        this.msensor = this.sensorManager.getDefaultSensor(2);
    }

    public boolean getStatusCompass() {
        this.statusFlag = false;
        if (this.msensor != null) {
            this.statusFlag = true;
        }
        return this.statusFlag;
    }

    public boolean getDataSensor() {
        return this.sensorData;
    }

    public void start() {
        this.sensorManager.registerListener(this, this.gsensor, 1);
        this.sensorManager.registerListener(this, this.msensor, 1);
    }

    public void stop() {
        this.sensorManager.unregisterListener(this);
    }

    public void setListener(CompassListener compassListener) {
        this.listener = compassListener;
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this) {
            if (sensorEvent.sensor.getType() == 1) {
                float[] fArr = this.mGravity;
                fArr[0] = (fArr[0] * 0.97f) + (sensorEvent.values[0] * 0.029999971f);
                float[] fArr2 = this.mGravity;
                fArr2[1] = (fArr2[1] * 0.97f) + (sensorEvent.values[1] * 0.029999971f);
                float[] fArr3 = this.mGravity;
                fArr3[2] = (fArr3[2] * 0.97f) + (sensorEvent.values[2] * 0.029999971f);
            }
            if (sensorEvent.sensor.getType() == 2) {
                float[] fArr4 = this.mGeomagnetic;
                fArr4[0] = (fArr4[0] * 0.97f) + (sensorEvent.values[0] * 0.029999971f);
                float[] fArr5 = this.mGeomagnetic;
                fArr5[1] = (fArr5[1] * 0.97f) + (sensorEvent.values[1] * 0.029999971f);
                float[] fArr6 = this.mGeomagnetic;
                fArr6[2] = (fArr6[2] * 0.97f) + (sensorEvent.values[2] * 0.029999971f);
                if (Math.abs(this.mGeomagnetic[2]) > Math.abs(this.mGeomagnetic[1])) {
                    this.magStrength = (float) Math.round(Math.abs(this.mGeomagnetic[2]));
                } else {
                    this.magStrength = (float) Math.round(Math.abs(this.mGeomagnetic[1]));
                }
                CompassListener compassListener = this.listener;
                if (compassListener != null) {
                    compassListener.onMagField(this.magStrength);
                }
            }
            if (SensorManager.getRotationMatrix(this.f149R, this.f148I, this.mGravity, this.mGeomagnetic)) {
                this.sensorData = true;
                float[] fArr7 = new float[3];
                SensorManager.getOrientation(this.f149R, fArr7);
                float degrees = (((float) Math.toDegrees((double) fArr7[0])) + 360.0f) % 360.0f;
                this.azimuth = degrees;
                CompassListener compassListener2 = this.listener;
                if (compassListener2 != null) {
                    compassListener2.onNewAzimuth(degrees);
                }
            }
        }
    }
}
