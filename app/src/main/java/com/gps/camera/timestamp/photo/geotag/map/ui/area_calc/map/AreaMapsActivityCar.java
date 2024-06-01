package com.gps.camera.timestamp.photo.geotag.map.ui.area_calc.map;

import static com.gps.camera.timestamp.photo.geotag.map.utils.Utils.getLocationPermission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gps.camera.timestamp.photo.geotag.map.area_caculator.AreaUtils;
import com.gps.camera.timestamp.photo.geotag.map.area_caculator.CameraModel;
import com.gps.camera.timestamp.photo.geotag.map.area_caculator.DrawingOption;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager;
import com.gps.camera.timestamp.photo.geotag.map.databinding.CarActivityMapsBinding;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.live_location.LiveLocationActivity;
import com.gps.camera.timestamp.photo.geotag.map.utils.ExtsKt;
import com.gps.camera.timestamp.photo.geotag.map.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.vapp.admoblibrary.ads.AdmobUtils;
import com.vapp.admoblibrary.ads.AppOpenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class AreaMapsActivityCar extends BaseActivity<CarActivityMapsBinding>
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MapsActivity";

    private Observable<Location> lastKnownLocationObservable;
    private Subscription lastKnownLocationSubscription;
    private ReactiveLocationProvider locationProvider;
    private Observable<Location> locationUpdatesObservable;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private List<Marker> markerList = new ArrayList();
    private List<LatLng> points = new ArrayList();
    private Polygon polygon;
    private Polyline polyline;
    RadioGroup radioGroup;
    String calc = "meter";
    private DrawingOption carDrawingOption;
    private CompositeSubscription compositeSubscription;
    private Location currentLocation;
    private Marker currentMarker;
    RadioButton genderradioButton;
    public static final String MAP_OPTION = "map_option";
    public static final String POINTS = "points";

    private boolean isGPSOn = false;
    String lable = "m";
    String lableSquare = "m^2";
    private Subscription updatableLocationSubscription;

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    public static Bitmap getBitmapFromDrawable(Context context, int i) {
        Drawable drawable = ContextCompat.getDrawable(context, i);
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }



    @Override
    public int getLayoutId() {
        return R.layout.car_activity_maps;
    }

    @Override
    public void initView() {

        binding.btnGoToSetting.setOnClickListener(view -> {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        });

        AppOpenManager.getInstance().disableAppResumeWithActivity(AreaMapsActivityCar.class);

        if (AdsManager.INSTANCE.getStateTitleDraw() == 0) {
            binding.txtDrawTitle.setText(getString(R.string.draw_polygon));
        } else if (AdsManager.INSTANCE.getStateTitleDraw() == 1) {
            binding.txtDrawTitle.setText(getString(R.string.draw_polyline));
        } else {
            binding.txtDrawTitle.setText(getString(R.string.draw_points));
        }
        (findViewById(R.id.ic_back)).setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        });

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        this.carDrawingOption = getIntent().getParcelableExtra(MAP_OPTION);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        this.mapFragment = supportMapFragment;
        supportMapFragment.getMapAsync(this);
        fabSetup();
        binding.calculateLayout.setVisibility(this.carDrawingOption.getEnableCalculateLayout().booleanValue() ? View.VISIBLE : View.GONE);

        binding.btnUnit.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AreaMapsActivityCar.this);
            final View inflate = LayoutInflater.from(AreaMapsActivityCar.this).inflate(R.layout.car_dialog_choose_unit, (ViewGroup) null, false);
            AreaMapsActivityCar.this.radioGroup = inflate.findViewById(R.id.radioGroup);
            RadioButton radioButton = inflate.findViewById(R.id.radio_meter);
            RadioButton radioButton2 = inflate.findViewById(R.id.radio_km);
            RadioButton radioButton3 = inflate.findViewById(R.id.radiofeet);
            RadioButton radioButton4 = inflate.findViewById(R.id.radio_yard);
            RadioButton radioButton5 = inflate.findViewById(R.id.radio_mile);
            LinearLayout cardView2 = inflate.findViewById(R.id.card_cancel);
            LinearLayout card_save = inflate.findViewById(R.id.card_save);
            builder.setView(inflate);
            builder.setCancelable(true);
            if (AreaMapsActivityCar.this.calc.equals("meter")) {
                radioButton.setChecked(true);
            } else if (AreaMapsActivityCar.this.calc.equals("kilo")) {
                radioButton2.setChecked(true);
            } else if (AreaMapsActivityCar.this.calc.equals("feet")) {
                radioButton3.setChecked(true);
            } else if (AreaMapsActivityCar.this.calc.equals("yard")) {
                radioButton4.setChecked(true);
            } else if (AreaMapsActivityCar.this.calc.equals("mile")) {
                radioButton5.setChecked(true);
            } else {
                radioButton.setChecked(true);
            }
            final AlertDialog create = builder.create();
            create.getWindow().getDecorView().setBackgroundResource(17170445);
            ExtsKt.setOnClickListener500MilliSeconds(card_save, () -> {
                create.dismiss();
                AreaMapsActivityCar.this.onclickbuttonMethod(inflate);
                return null;
            });

            ExtsKt.setOnClickListener500MilliSeconds(cardView2, () -> {
                create.dismiss();
                return null;
            });
            create.show();
        });

        binding.icMap.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AreaMapsActivityCar.this);
            View inflate = LayoutInflater.from(AreaMapsActivityCar.this).inflate(R.layout.car_dialog_chnage_map, (ViewGroup) null, false);
            builder.setCancelable(true);
            builder.setView(inflate);
            final AlertDialog create = builder.create();
            create.getWindow().getDecorView().setBackgroundResource(17170445);
            (inflate.findViewById(R.id.ll_map_normal)).setOnClickListener(view14 -> {
                create.dismiss();
                AreaMapsActivityCar.this.mMap.setMapType(1);
            });
            (inflate.findViewById(R.id.ll_map_satelite)).setOnClickListener(view13 -> {
                create.dismiss();
                AreaMapsActivityCar.this.mMap.setMapType(2);
            });
            (inflate.findViewById(R.id.ll_map_terrain)).setOnClickListener(view12 -> {
                create.dismiss();
                AreaMapsActivityCar.this.mMap.setMapType(3);
            });
            (inflate.findViewById(R.id.ll_map_hybrid)).setOnClickListener(view1 -> {
                create.dismiss();
                AreaMapsActivityCar.this.mMap.setMapType(4);
            });
            create.show();
        });


    }

    public void onclickbuttonMethod(View view) {
        int checkedRadioButtonId = this.radioGroup.getCheckedRadioButtonId();
        this.genderradioButton = view.findViewById(checkedRadioButtonId);
        if (checkedRadioButtonId == -1) {
            Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {
            if (this.genderradioButton.getText().toString().equals("Meter")) {
                this.calc = "meter";
                this.lable = "m";
                this.lableSquare = "m^2";
            } else if (this.genderradioButton.getText().toString().contains("Kilo")) {
                this.calc = "kilo";
                this.lable = "km";
                this.lableSquare = "km^2";
            } else if (this.genderradioButton.getText().toString().contains("Feet")) {
                this.calc = "feet";
                this.lable = "ft";
                this.lableSquare = "ft^2";
            } else if (this.genderradioButton.getText().toString().contains("Yard")) {
                this.calc = "yard";
                this.lable = "yd";
                this.lableSquare = "yd^2";
            } else if (this.genderradioButton.getText().toString().contains("Mile")) {
                this.calc = "mile";
                this.lable = "mi";
                this.lableSquare = "mi^2";
            } else {
                this.calc = "meter";
                this.lable = "m";
                this.lableSquare = "m^2";
            }
//            Toast.makeText(this, this.genderradioButton.getText(), Toast.LENGTH_SHORT).show();
        }
        setAreaLength(this.points);
        setLength(this.points);
    }

    private void fabSetup() {
        final ImageView imageView = findViewById(R.id.btnSatellite);
        imageView.setOnClickListener(view -> {
            AreaMapsActivityCar.this.mMap.setMapType(AreaMapsActivityCar.this.mMap.getMapType() == 2 ? 1 : 2);
            imageView.setImageResource(AreaMapsActivityCar.this.mMap.getMapType() == 2 ? R.drawable.ic_satellite_off : R.drawable.ic_satellite_on);
        });


        imageView.setVisibility(this.carDrawingOption.getEnableSatelliteView().booleanValue() ? View.VISIBLE : View.GONE);
        (findViewById(R.id.btnUndo)).setOnClickListener(view -> {
            if (AreaMapsActivityCar.this.points.size() > 0) {
                Marker marker = (Marker) AreaMapsActivityCar.this.markerList.get(AreaMapsActivityCar.this.markerList.size() - 1);
                marker.remove();
                AreaMapsActivityCar.this.markerList.remove(marker);
                AreaMapsActivityCar.this.points.remove(AreaMapsActivityCar.this.points.size() - 1);
                if (AreaMapsActivityCar.this.points.size() <= 0) {
                    return;
                }
                if (AreaMapsActivityCar.this.carDrawingOption.getDrawingType() == DrawingOption.DrawingType.POLYGON) {
                    AreaMapsActivityCar area_MapsActivityCar = AreaMapsActivityCar.this;
                    area_MapsActivityCar.car_drawPolygon(area_MapsActivityCar.points);
                } else if (AreaMapsActivityCar.this.carDrawingOption.getDrawingType() == DrawingOption.DrawingType.POLYLINE) {
                    AreaMapsActivityCar area_MapsActivityCar2 = AreaMapsActivityCar.this;
                    area_MapsActivityCar2.car_drawPolyline(area_MapsActivityCar2.points);
                }
            }
        });

        binding.btnDone.setOnClickListener(view -> AreaMapsActivityCar.this.returnCurrentPositionGPS());

        AreaMapsActivityCar.this.requestActivatingGPS();
    }

    private void requestActivatingGPS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else {
            LocationServices.getFusedLocationProviderClient((Activity) this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                public void onSuccess(final Location location) {
                    mapFragment.getMapAsync(googleMap -> {
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

    @SuppressLint("PotentialBehaviorOverride")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(this.carDrawingOption.getLocationLatitude().doubleValue(), this.carDrawingOption.getLocationLongitude().doubleValue()), this.carDrawingOption.getZoom()));
        this.mMap.setOnMapClickListener(latLng -> {
            Marker addMarker = AreaMapsActivityCar.this.mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(AreaMapsActivityCar.getBitmapFromDrawable(AreaMapsActivityCar.this, R.drawable.ic_add_location_light_green_500_36dp))).draggable(true));
            addMarker.setTag(latLng);
            AreaMapsActivityCar.this.markerList.add(addMarker);
            AreaMapsActivityCar.this.points.add(latLng);
            if (AreaMapsActivityCar.this.carDrawingOption.getDrawingType() == DrawingOption.DrawingType.POLYGON) {
                AreaMapsActivityCar area_MapsActivityCar = AreaMapsActivityCar.this;
                area_MapsActivityCar.car_drawPolygon(area_MapsActivityCar.points);
                AreaMapsActivityCar area_MapsActivityCar2 = AreaMapsActivityCar.this;
                area_MapsActivityCar2.setAreaLength(area_MapsActivityCar2.points);
            } else if (AreaMapsActivityCar.this.carDrawingOption.getDrawingType() == DrawingOption.DrawingType.POLYLINE) {
                AreaMapsActivityCar area_MapsActivityCar3 = AreaMapsActivityCar.this;
                area_MapsActivityCar3.car_drawPolyline(area_MapsActivityCar3.points);
                AreaMapsActivityCar area_MapsActivityCar4 = AreaMapsActivityCar.this;
                area_MapsActivityCar4.setLength(area_MapsActivityCar4.points);
            }
        });
        this.mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                AreaMapsActivityCar.this.updateMarkerLocationLive(marker, false);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                AreaMapsActivityCar.this.updateMarkerLocationLive(marker, true);
            }
        });

        if (Utils.allPermissionGrant(this, getLocationPermission())) {

        }

    }

    private void updateMarkerLocationLive(Marker marker, boolean z) {
        this.points.set(this.points.indexOf((LatLng) marker.getTag()), marker.getPosition());
        marker.setTag(marker.getPosition());
        if (this.carDrawingOption.getDrawingType() == DrawingOption.DrawingType.POLYGON) {
            car_drawPolygon(this.points);
            if (z) {
                setAreaLength(this.points);
            }
        } else if (this.carDrawingOption.getDrawingType() == DrawingOption.DrawingType.POLYLINE) {
            car_drawPolyline(this.points);
            if (z) {
                setLength(this.points);
            }
        }
    }

    private void car_drawPolyline(List<LatLng> list) {
        Polyline polyline2 = this.polyline;
        if (polyline2 != null) {
            polyline2.remove();
        }
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(this.carDrawingOption.getStrokeColor());
        polylineOptions.width((float) this.carDrawingOption.getStrokeWidth());
        polylineOptions.addAll(list);
        this.polyline = this.mMap.addPolyline(polylineOptions);
    }

    private void car_drawPolygon(List<LatLng> list) {
        Polygon polygon2 = this.polygon;
        if (polygon2 != null) {
            polygon2.remove();
        }
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.fillColor(this.carDrawingOption.getFillColor());
        polygonOptions.strokeColor(this.carDrawingOption.getStrokeColor());
        polygonOptions.strokeWidth((float) this.carDrawingOption.getStrokeWidth());
        polygonOptions.addAll(list);
        this.polygon = this.mMap.addPolygon(polygonOptions);
    }


  /*  @Override
    public void onLocationPermissionGranted() {
        getLastKnowLocationCamera();
        car_updateLocation();
    }*/

    private void car_updateLocation() {
        Observable<Location> observable = this.locationUpdatesObservable;
        if (observable != null && this.compositeSubscription != null) {
            Subscription subscribe = observable.subscribe(new Action1<Location>() {


                public void call(Location location) {
                    if (AreaMapsActivityCar.this.currentLocation == null) {
                        AreaMapsActivityCar.this.moveMapToCenter(location);
                    }
                    AreaMapsActivityCar.this.currentLocation = location;
                    AreaMapsActivityCar.this.car_moveMarkerCurrentPosition(location);
                }
            }, new ErrorHandler());
            this.updatableLocationSubscription = subscribe;
            this.compositeSubscription.add(subscribe);
        }
    }


    private void getLastKnowLocationCamera() {
        Observable<Location> observable = this.lastKnownLocationObservable;
        if (observable != null && this.compositeSubscription != null) {
            Subscription subscribe = observable.subscribe(location -> {
                AreaMapsActivityCar.this.currentLocation = location;
                AreaMapsActivityCar.this.moveMapToCenter(location);
            }, new ErrorHandler());
            this.lastKnownLocationSubscription = subscribe;
            this.compositeSubscription.add(subscribe);
        }
    }

    public void moveMapToCenter(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (this.mMap != null) {
            locationMarkerLive(latLng);
            this.mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    public void car_moveMarkerCurrentPosition(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (this.mMap != null) {
            locationMarkerLive(latLng);
        }
    }

    private void locationMarkerLive(LatLng latLng) {
        Marker marker = this.currentMarker;
        if (marker != null) {
            marker.setPosition(latLng);
            return;
        }
        this.currentMarker = this.mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromDrawable(this, R.drawable.ic_navigation_red_a400_36dp))).draggable(false));
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 0) {
            if (i2 == -1) {
                Log.d(TAG, "User enabled location");
                getLastKnowLocationCamera();
                car_updateLocation();
                this.isGPSOn = true;
            } else if (i2 == 0) {
                Log.d(TAG, "User Cancelled enabling location");
                this.isGPSOn = false;
            }
        }
    }

    @Override
    public void onStop() {
        CompositeSubscription compositeSubscription2;
        super.onStop();
        if (this.lastKnownLocationSubscription != null && this.updatableLocationSubscription != null && (compositeSubscription2 = this.compositeSubscription) != null) {
            compositeSubscription2.unsubscribe();
            this.compositeSubscription.clear();
            this.updatableLocationSubscription.unsubscribe();
            this.lastKnownLocationSubscription.unsubscribe();
        }
    }



    private void returnCurrentPositionGPS() {
        if (this.points.size() > 0) {
            Intent intent = new Intent();
            LatLng[] latLngArr = new LatLng[this.points.size()];
            this.points.toArray(latLngArr);
            CameraModel cameraModel = new CameraModel();
            cameraModel.setCount(this.points.size());
            cameraModel.setPoints(latLngArr);
            intent.putExtra(POINTS, cameraModel);
            setResult(-1, intent);
        } else {
            setResult(0);
        }
        finish();
    }

    private void setAreaLength(List<LatLng> list) {
        double d = 0;
        double d2 = 0;
        double area = AreaUtils.getArea(list);
        double length = AreaUtils.getLength(list);
        if (!this.calc.equals("meter")) {
            if (this.calc.equals("kilo")) {
                d = 1000.0d;
            } else {
                if (this.calc.equals("feet")) {
                    d2 = 3.281d;
                } else if (this.calc.equals("yard")) {
                    d2 = 1.094d;
                } else if (this.calc.equals("mile")) {
                    d = 1609.0d;
                }
                area *= d2;
                length *= d2;
            }
            area /= d;
            length /= d;
        }
        if (AdsManager.INSTANCE.getStateTitleDraw() == 0) {
            String str = getString(R.string.area_label) + String.format(Locale.ENGLISH, "%.2f", Double.valueOf(area)) + this.lableSquare;
            binding.tvArea.setText(str);
        }
        String str2 = getString(R.string.length_label) + String.format(Locale.ENGLISH, "%.2f", Double.valueOf(length)) + this.lable;
        binding.tvDistance.setText(str2);
    }

    private void setLength(List<LatLng> list) {
        double d = 0;
        double d2 = 0;
        double length = AreaUtils.getLength(list);
        if (!this.calc.equals("meter")) {
            if (this.calc.equals("kilo")) {
                d = 1000.0d;
            } else {
                if (this.calc.equals("feet")) {
                    d2 = 3.281d;
                } else if (this.calc.equals("yard")) {
                    d2 = 1.094d;
                } else if (this.calc.equals("mile")) {
                    d = 1609.0d;
                }
                length *= d2;
            }
            length /= d;
        }
        binding.tvDistance.setText(getString(R.string.length_label) + String.format(Locale.ENGLISH, "%.2f", Double.valueOf(length)) + this.lable);
    }


    public class ErrorHandler implements Action1<Throwable> {
        private ErrorHandler() {
        }

        public void call(Throwable th) {
            Toast.makeText(AreaMapsActivityCar.this, "Error occurred.", Toast.LENGTH_SHORT).show();
            Log.d(AreaMapsActivityCar.TAG, "Error occurred", th);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_FIRST_USER, intent);
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (AdmobUtils.isNetworkConnected(this)){
            binding.layoutNoInternet.setVisibility(View.GONE);
            binding.layoutMap.setVisibility(View.VISIBLE);
        }else {
            binding.layoutNoInternet.setVisibility(View.VISIBLE);
            binding.layoutMap.setVisibility(View.GONE);
        }
    }
}
