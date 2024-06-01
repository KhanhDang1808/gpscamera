package com.gps.camera.timestamp.photo.geotag.map.ui.live_location;

import static com.gps.camera.timestamp.photo.geotag.map.utils.Utils.getLocationPermission;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.provider.Settings;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivityLiveLocationBinding;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;
import com.gps.camera.timestamp.photo.geotag.map.utils.Utils;
import com.vapp.admoblibrary.ads.AdmobUtils;
import com.vapp.admoblibrary.ads.AppOpenManager;

public class LiveLocationActivity extends BaseActivity<ActivityLiveLocationBinding> {

    FusedLocationProviderClient client;
    SupportMapFragment smf;

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_location;
    }

    @Override
    public void initView() {
        AppOpenManager.getInstance().disableAppResumeWithActivity(LiveLocationActivity.class);
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });

        binding.btnGoToSetting.setOnClickListener(view -> {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        this.client = LocationServices.getFusedLocationProviderClient((Activity) this);
        if (Utils.allPermissionGrant(this,getLocationPermission())){
            getLiveLocation();
        }
    }

    public void getLiveLocation() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                public void onSuccess(final Location location) {
                    smf.getMapAsync(googleMap -> {
                        try {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("You are here...!!"));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (AdmobUtils.isNetworkConnected(this)){
            binding.layoutNoInternet.setVisibility(View.GONE);
            binding.googleMap.setVisibility(View.VISIBLE);
        }else {
            binding.layoutNoInternet.setVisibility(View.VISIBLE);
            binding.googleMap.setVisibility(View.GONE);
        }
    }

}