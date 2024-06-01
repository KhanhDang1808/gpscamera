package com.gps.camera.timestamp.photo.geotag.map.area_caculator;

import android.os.Parcel;
import android.os.Parcelable;

public class DrawingOption implements Parcelable {
    public static final Creator<DrawingOption> CREATOR = new Creator<DrawingOption>() {
        

        @Override 
        public DrawingOption createFromParcel(Parcel parcel) {
            return new DrawingOption(parcel);
        }

        @Override 
        public DrawingOption[] newArray(int i) {
            return new DrawingOption[i];
        }
    };
    private DrawingType drawingType;
    private Boolean enableCalculateLayout;
    private Boolean enableSatelliteView;
    private int fillColor;
    private Double locationLatitude;
    private Double locationLongitude;
    private Boolean requestGPSEnabling;
    private int strokeColor;
    private int strokeWidth;
    private float zoom;

    public enum DrawingType {
        POLYGON,
        POLYLINE,
        POINT
    }

    public int describeContents() {
        return 0;
    }

    public DrawingOption(Double d, Double d2, float f, int i, int i2, int i3, Boolean bool, Boolean bool2, Boolean bool3, DrawingType drawingType2) {
        this.locationLatitude = d;
        this.locationLongitude = d2;
        this.zoom = f;
        this.fillColor = i;
        this.strokeColor = i2;
        this.strokeWidth = i3;
        this.enableSatelliteView = bool;
        this.requestGPSEnabling = bool2;
        this.enableCalculateLayout = bool3;
        this.drawingType = drawingType2;
    }

    protected DrawingOption(Parcel parcel) {
        DrawingType drawingType2;
        this.locationLatitude = (Double) parcel.readValue(Double.class.getClassLoader());
        this.locationLongitude = (Double) parcel.readValue(Double.class.getClassLoader());
        this.zoom = parcel.readFloat();
        this.fillColor = parcel.readInt();
        this.strokeColor = parcel.readInt();
        this.strokeWidth = parcel.readInt();
        this.enableSatelliteView = (Boolean) parcel.readValue(Boolean.class.getClassLoader());
        this.requestGPSEnabling = (Boolean) parcel.readValue(Boolean.class.getClassLoader());
        this.enableCalculateLayout = (Boolean) parcel.readValue(Boolean.class.getClassLoader());
        int readInt = parcel.readInt();
        if (readInt == -1) {
            drawingType2 = null;
        } else {
            drawingType2 = DrawingType.values()[readInt];
        }
        this.drawingType = drawingType2;
    }

    public Double getLocationLatitude() {
        return this.locationLatitude;
    }

    public void setLocationLatitude(Double d) {
        this.locationLatitude = d;
    }

    public Double getLocationLongitude() {
        return this.locationLongitude;
    }

    public void setLocationLongitude(Double d) {
        this.locationLongitude = d;
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setZoom(float f) {
        this.zoom = f;
    }

    public int getFillColor() {
        return this.fillColor;
    }

    public void setFillColor(int i) {
        this.fillColor = i;
    }

    public int getStrokeColor() {
        return this.strokeColor;
    }

    public void setStrokeColor(int i) {
        this.strokeColor = i;
    }

    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    public void setStrokeWidth(int i) {
        this.strokeWidth = i;
    }

    public Boolean getEnableSatelliteView() {
        return this.enableSatelliteView;
    }

    public void setEnableSatelliteView(Boolean bool) {
        this.enableSatelliteView = bool;
    }

    public Boolean getRequestGPSEnabling() {
        return this.requestGPSEnabling;
    }

    public void setRequestGPSEnabling(Boolean bool) {
        this.requestGPSEnabling = bool;
    }

    public Boolean getEnableCalculateLayout() {
        return this.enableCalculateLayout;
    }

    public void setEnableCalculateLayout(Boolean bool) {
        this.enableCalculateLayout = bool;
    }

    public DrawingType getDrawingType() {
        return this.drawingType;
    }

    public void setDrawingType(DrawingType drawingType2) {
        this.drawingType = drawingType2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.locationLatitude);
        parcel.writeValue(this.locationLongitude);
        parcel.writeFloat(this.zoom);
        parcel.writeInt(this.fillColor);
        parcel.writeInt(this.strokeColor);
        parcel.writeInt(this.strokeWidth);
        parcel.writeValue(this.enableSatelliteView);
        parcel.writeValue(this.requestGPSEnabling);
        parcel.writeValue(this.enableCalculateLayout);
        DrawingType drawingType2 = this.drawingType;
        parcel.writeInt(drawingType2 == null ? -1 : drawingType2.ordinal());
    }
}
