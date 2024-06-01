package com.gps.camera.timestamp.photo.geotag.map.camera;

import android.graphics.Bitmap;

public class ObjectFile {
    Bitmap bitmap;
    byte[] data;

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }
}
