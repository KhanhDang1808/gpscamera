package com.gps.camera.timestamp.photo.geotag.map.area_caculator;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

public class CameraModel implements Parcelable {
    public static final Creator<CameraModel> CREATOR = new Creator<CameraModel>() {
        

        @Override 
        public CameraModel createFromParcel(Parcel parcel) {
            return new CameraModel(parcel);
        }

        @Override 
        public CameraModel[] newArray(int i) {
            return new CameraModel[i];
        }
    };
    private int count;
    private LatLng[] points;

    public int describeContents() {
        return 0;
    }

    public CameraModel() {
    }

    protected CameraModel(Parcel parcel) {
        this.points = (LatLng[]) parcel.createTypedArray(LatLng.CREATOR);
        this.count = parcel.readInt();
    }

    public LatLng[] getPoints() {
        return this.points;
    }

    public void setPoints(LatLng[] latLngArr) {
        this.points = latLngArr;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(this.points, i);
        parcel.writeInt(this.count);
    }
}
