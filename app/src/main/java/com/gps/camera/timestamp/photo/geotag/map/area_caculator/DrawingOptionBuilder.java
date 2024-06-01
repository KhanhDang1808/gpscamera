package com.gps.camera.timestamp.photo.geotag.map.area_caculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.gps.camera.timestamp.photo.geotag.map.ui.area_calc.map.AreaMapsActivityCar;

public class DrawingOptionBuilder {
    private DrawingOption.DrawingType drawingType = DrawingOption.DrawingType.POLYGON;
    private Boolean enableCalculateLayout = true;
    private Boolean enableSatelliteView = true;
    private int fillColor = Color.argb(0, 0, 0, 0);
    private Double locationLatitude;
    private Double locationLongitude;
    private Boolean requestGPSEnabling = false;
    private int strokeColor = Color.argb(255, 0, 0, 0);
    private int strokeWidth = 10;
    private float zoom = 14.0f;

    public DrawingOptionBuilder withLocation(double d, double d2) {
        this.locationLatitude = Double.valueOf(d);
        this.locationLongitude = Double.valueOf(d2);
        return this;
    }

    public DrawingOptionBuilder withSatelliteViewHidden() {
        this.enableSatelliteView = false;
        return this;
    }

    public DrawingOptionBuilder withDrawingType(DrawingOption.DrawingType drawingType2) {
        this.drawingType = drawingType2;
        return this;
    }

    public DrawingOptionBuilder withFillColor(int i) {
        this.fillColor = i;
        return this;
    }

    public DrawingOptionBuilder withStrokeColor(int i) {
        this.strokeColor = i;
        return this;
    }

    public DrawingOptionBuilder withStrokeWidth(int i) {
        this.strokeWidth = i;
        return this;
    }

    public DrawingOptionBuilder withRequestGPSEnabling(Boolean bool) {
        this.requestGPSEnabling = bool;
        return this;
    }

    public DrawingOptionBuilder withMapZoom(float f) {
        this.zoom = f;
        return this;
    }

    public DrawingOptionBuilder withCalculateLayoutHidden() {
        this.enableCalculateLayout = false;
        return this;
    }

    public Intent build(Context context) {
        if (this.drawingType == DrawingOption.DrawingType.POINT) {
            this.enableCalculateLayout = false;
        }
        Intent intent = new Intent(context, AreaMapsActivityCar.class);
        intent.putExtra(AreaMapsActivityCar.MAP_OPTION, new DrawingOption(this.locationLatitude, this.locationLongitude, this.zoom, this.fillColor, this.strokeColor, this.strokeWidth, this.enableSatelliteView, this.requestGPSEnabling, this.enableCalculateLayout, this.drawingType));
        return intent;
    }
}
