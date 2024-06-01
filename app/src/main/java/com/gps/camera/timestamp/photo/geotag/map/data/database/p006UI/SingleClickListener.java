package com.gps.camera.timestamp.photo.geotag.map.data.database.p006UI;

import android.os.SystemClock;
import android.view.View;

import com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter.LocationAdapterGps;


public abstract class SingleClickListener implements View.OnClickListener {
    protected int defaultInterval = 1;
    private long lastTimeClicked = 0;

    public abstract void performClick(View view);

    public SingleClickListener(LocationAdapterGps cAR_LoctionAdapterGps, LocationAdapterGps carLoctionAdapter) {
    }

    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - this.lastTimeClicked >= ((long) this.defaultInterval)) {
            this.lastTimeClicked = SystemClock.elapsedRealtime();
            performClick(view);
        }
    }
}
