package com.gps.camera.timestamp.photo.geotag.map.camera;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import androidx.core.content.ContextCompat;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class LocationSupplier {
    private static final String TAG = "LocationSupplier";
    private Location cached_location;
    private long cached_location_ms;
    private final Context context;
    private MyLocationListener[] locationListeners;
    private final LocationManager locationManager;
    private Location mLocation;
    onLocationUpdateListener mOnLocationUpdateListener;
    private volatile boolean test_force_no_location;

    public void setLocation(Location location) {
        this.mLocation = location;
    }

    public LocationSupplier(Context context2) {
        this.context = context2;
        this.locationManager = (LocationManager) context2.getSystemService(Context.LOCATION_SERVICE);
    }

    public void setOnLocationUpdateListener(onLocationUpdateListener onlocationupdatelistener) {
        this.mOnLocationUpdateListener = onlocationupdatelistener;
    }

    private Location getCachedLocation() {
        if (this.cached_location != null) {
            if (System.currentTimeMillis() <= this.cached_location_ms + 20000) {
                return this.cached_location;
            }
            this.cached_location = null;
        }
        return null;
    }



    private void cacheLocation() {
        Log.d(TAG, "cacheLocation");
        Location location = getLocation();
        if (location == null) {
            Log.d(TAG, "### asked to cache location when location not available");
            return;
        }
        this.cached_location = new Location(location);
        this.cached_location_ms = System.currentTimeMillis();
    }

    public static class LocationInfo {
        private boolean location_was_cached;

        public boolean LocationWasCached() {
            return this.location_was_cached;
        }
    }

    public Location getLocation() {
        return getLocation(null);
    }

    public Location getLocation(LocationInfo locationInfo) {
        if (locationInfo != null) {
            locationInfo.location_was_cached = false;
        }
        if (this.locationListeners == null || this.test_force_no_location) {
            return null;
        }
        for (MyLocationListener myLocationListener : this.locationListeners) {
            Location location = myLocationListener.getLocation();
            if (location != null) {
                return location;
            }
        }
        Location cachedLocation = getCachedLocation();
        if (!(cachedLocation == null || locationInfo == null)) {
            locationInfo.location_was_cached = true;
        }
        return cachedLocation;
    }

    public class MyLocationListener implements LocationListener {
        private Location location;
        volatile boolean test_has_received_location;

        public void onProviderEnabled(String str) {
        }

        private MyLocationListener() {
        }

        public Location getLocation() {
            return this.location;
        }

        public void onLocationChanged(Location location2) {
            this.test_has_received_location = true;
            if (location2 != null) {
                if (location2.getLatitude() != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE || location2.getLongitude() != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    if (LocationSupplier.this.mOnLocationUpdateListener != null) {
                        LocationSupplier.this.mOnLocationUpdateListener.setOnLocationUpdate(location2);
                    }
                    this.location = location2;
                    LocationSupplier.this.cacheLocation();
                }
            }
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            if (i == 0 || i == 1) {
                this.location = null;
                this.test_has_received_location = false;
                LocationSupplier.this.cached_location = null;
            }
        }

        public void onProviderDisabled(String str) {
            Log.d(LocationSupplier.TAG, "onProviderDisabled");
            this.location = null;
            this.test_has_received_location = false;
            LocationSupplier.this.cached_location = null;
        }
    }

    public boolean setupLocationListener() {
        PreferenceManager.getDefaultSharedPreferences(this.context);
        if (this.locationListeners == null) {
            Log.e("::::location:::", "setupLocationListener: ");
            boolean z = ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_COARSE_LOCATION") == 0;
            boolean z2 = ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") == 0;
            if (!z || !z2) {
                return false;
            }
            MyLocationListener[] myLocationListenerArr = new MyLocationListener[2];
            this.locationListeners = myLocationListenerArr;
            myLocationListenerArr[0] = new MyLocationListener();
            this.locationListeners[1] = new MyLocationListener();
            if (this.locationManager.getAllProviders().contains("network")) {
                this.locationManager.requestLocationUpdates("network", 900, 0.0f, this.locationListeners[1]);
                Log.d(TAG, "created coarse (network) location listener");
            } else {
                Log.e(TAG, "don't have a NETWORK_PROVIDER");
            }
            if (this.locationManager.getAllProviders().contains("gps")) {
                this.locationManager.requestLocationUpdates("gps", 900, 0.0f, this.locationListeners[0]);
                Log.d(TAG, "created fine (gps) location listener");
            } else {
                Log.e(TAG, "don't have a GPS_PROVIDER");
            }
        }
        return true;
    }

    public void freeLocationListeners() {
        Log.d(TAG, "freeLocationListeners");
        if (this.locationListeners != null) {
            Log.d(TAG, "check for location permissions");
            boolean z = true;
            int i = 0;
            boolean z2 = ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_COARSE_LOCATION") == 0;
            if (ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                z = false;
            }
            Log.d(TAG, "has_coarse_location_permission? " + z2);
            Log.d(TAG, "has_fine_location_permission? " + z);
            if (z2 || z) {
                while (true) {
                    MyLocationListener[] myLocationListenerArr = this.locationListeners;
                    if (i < myLocationListenerArr.length) {
                        this.locationManager.removeUpdates(myLocationListenerArr[i]);
                        this.locationListeners[i] = null;
                        i++;
                    } else {
                        this.locationListeners = null;
                        Log.d(TAG, "location listeners now freed");
                        return;
                    }
                }
            } else {
                Log.d(TAG, "location permission not available");
            }
        }
    }

}
