package com.gps.camera.timestamp.photo.geotag.map.area_caculator;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import java.util.List;

public class AreaUtils {
    public static double getArea(List<LatLng> list) {
        return SphericalUtil.computeArea(list);
    }

    public static double getLength(List<LatLng> list) {
        return SphericalUtil.computeLength(list);
    }
}
