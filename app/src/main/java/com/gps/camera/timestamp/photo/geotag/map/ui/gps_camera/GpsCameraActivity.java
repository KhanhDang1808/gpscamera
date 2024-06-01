package com.gps.camera.timestamp.photo.geotag.map.ui.gps_camera;

import static com.gps.camera.timestamp.photo.geotag.map.ui.my_photos.MyPhotoActivity.PREVIEW_EXTRA;
import static com.gps.camera.timestamp.photo.geotag.map.utils.Utils.getCameraPermission;
import static com.gps.camera.timestamp.photo.geotag.map.utils.Utils.getStoragePermissions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.renderscript.RenderScript;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.gps.camera.timestamp.photo.geotag.map.data.models.ImageGetSet;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.Util;
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.DataBase.DBManager;
import com.gps.camera.timestamp.photo.geotag.map.camera.Default;
import com.gps.camera.timestamp.photo.geotag.map.camera.HelperClass;
import com.gps.camera.timestamp.photo.geotag.map.camera.LocationSupplier;
import com.gps.camera.timestamp.photo.geotag.map.camera.ObjectFile;
import com.gps.camera.timestamp.photo.geotag.map.camera.KeysConstants;
import com.gps.camera.timestamp.photo.geotag.map.camera.adapter.DateAdapterCamera;
import com.gps.camera.timestamp.photo.geotag.map.camera.adapter.TimeAdapter;
import com.gps.camera.timestamp.photo.geotag.map.camera.utils.CommonCoordinates;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivityGpsCameraBinding;
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.my_photos.PhotoPreviewActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.setting.SettingActivity;
import com.gps.camera.timestamp.photo.geotag.map.ui.template.TemplateActivity;
import com.gps.camera.timestamp.photo.geotag.map.utils.ExtsKt;
import com.gps.camera.timestamp.photo.geotag.map.utils.Utils;
import com.gps.camera.timestamp.photo.geotag.map.utils.compass.CompassSensorEvent;
import com.gps.camera.timestamp.photo.geotag.map.utils.compass.MagneticSensorNew;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Audio;
import com.otaliastudios.cameraview.controls.Flash;
import com.otaliastudios.cameraview.controls.Grid;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;
import com.otaliastudios.cameraview.size.Size;
import com.vapp.admoblibrary.ads.AppOpenManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.HttpStatus;

public class GpsCameraActivity extends BaseActivity<ActivityGpsCameraBinding> implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.activity_gps_camera;
    }

    String Address;
    String DateTime;
    String Logo;

    int REQUEST_CODE = 111;

    String Timezone;

    private final Handler handlerTimer = new Handler();
    Runnable runnableTimer;

    private final SensorEventListener accelerometerListener = new SensorEventListener() {


        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
        }
    };
    private void realTime() {
        runnableTimer = new Runnable() {
            @Override
            public void run() {
                if (smap_time_tv != null) smap_time_tv.setText(getTimeZoneNew());
                handlerTimer.postDelayed(this, 1000);
            }
        };
        handlerTimer.post(runnableTimer);
    }
    String address;
    double altitude;
    private boolean app_is_paused = false;
    private ImageView arrowView;
    float bearing = 0.0f;
    LinearLayout bottomscreenpannel;
    RelativeLayout btn_Save;

    private CameraView camera;
    String cameraType = "camera";

    private CompassSensorEvent compass;
    String compass1;
    Bitmap createBitmap;
    private float currentAzimuth;
    private int current_orientation;

    TextView date_tv;

    DBManager dbManager;
    private int display_height;
    private int display_width;

    ImageView galleryButton;
    public int gpsflag = 0;

    String humidity;

    ImageView imgLogo;

    ImageView img_compass;
    ImageView img_flash;
    ImageView img_note;

    int isPermission = 0;
    boolean isRecording;
    String lagitud;
    LinearLayout lagitude;
    double lat;
    double latitude;
    String latitude111;
    LinearLayout li_addresss;
    LinearLayout li_compass;
    LinearLayout li_magnetic_field;
    RelativeLayout lin_gallery;
    LinearLayout lin_numbering;

    LinearLayout ly_numbering;
    CardView lin_stamp;
    LinearLayout lin_stamp2;

    LinearLayout lin_timezone;

    LinearLayout linearNote;
    LocationManager locationManager;
    private LocationSupplier locationSupplier;
    double longi;
    double longitude;
    String longitude111;
    LinearLayoutCompat ly_datetime;
    LinearLayout lnDate;
    LinearLayout lnTimer;
    private Location mCurrentLocation;
    private ArrayList<Flash> mFlashvalues;
    HelperClass mHelperClass;
    ImageView mImg_flash;
    LinearLayout mLinear_Flash;
    private List<Size> mRatioList;
    RelativeLayout mRel_header;
    C1281SP mSP;
    private Sensor mSensorAccelerometer;
    private SensorManager mSensorManager;
    private final Handler mTimerHandler = new Handler();
    String magnetic;
    private MagneticSensorNew magneticSensorNew;
    RelativeLayout moMainLayout;
    RelativeLayout moRelHeader;
    TextView mtv_degree;
    private WeakReference<ObjectFile> myObjectWeakReference;
    String note;
    int numbering = 0;
    String numbering1;
    private OrientationEventListener orientationEventListener;
    int padding_12;
    int padding_4;
    int padding_6;
    String presurre;

    ConstraintLayout record_panel;
    RelativeLayout rel_compass;
    RelativeLayout rel_cross_note;
    RelativeLayout rel_mainStamp;
    RelativeLayout rel_stampp;
    ReviewManager reviewManager;
    TextView smap_Latitude_name;
    TextView smap_address_tv;
    TextView smap_hastag;
    LinearLayout ly_Note;
    TextView smap_latitude_tv;
    TextView smap_time_local_tv;
    TextView smap_time_tv;
    TextView smap_tv_compass;

    TextView smap_txt_magnetic;
    TextView smap_txt_numbering;

    String str_magnaticField;
    ImageView switchCameraButton;
    ImageView takePhotoButton;
    RelativeLayout template;

    Util util;
    private boolean view_rotate_animation;

    static void lambda$showRateApp$1() {
    }

    public void getStampBitmap() {
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    @Override
    public void initView() {
        AdsManager.INSTANCE.showAdBannerCollapsible(this,AdsManager.INSTANCE.getBANNER_COLLAP_CAMERA(),binding.frBanner,binding.line);

        this.util = new Util(this);
        this.linearNote = findViewById(R.id.linear_note);
        this.img_note = findViewById(R.id.img_note);
        ExtsKt.setOnClickListener500MilliSeconds(binding.btnBack, () -> {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
            return null;
        });

        ExtsKt.setOnClickListener500MilliSeconds(binding.linearNote, () -> {
            this.linearNote.setOnClickListener(view -> {
                View inflate = LayoutInflater.from(GpsCameraActivity.this).inflate(R.layout.add_note, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(GpsCameraActivity.this);
                builder.setView(inflate);
                final AlertDialog create = builder.create();
                if (create.getWindow() != null) create.getWindow().setBackgroundDrawableResource(R.color.transparent);
                create.show();
                final EditText editText =  create.findViewById(R.id.ed_errormsg);
                SwitchCompat switchCompat =  create.findViewById(R.id.switch_on_off_note);
                GpsCameraActivity.this.btn_Save =  create.findViewById(R.id.btn_Save);
                if (GpsCameraActivity.this.showPreferences("Layout").equals("Advance")) {
                    switchCompat.setChecked(GpsCameraActivity.this.showPreferences("hashtag_Temp0").equals("Yes"));
                } else {
                    switchCompat.setChecked(GpsCameraActivity.this.showPreferences("hashtag_Temp2").equals("Yes"));
                }
         //       String textDefault = "Note: Captured by GPS Camera";
                String textDefault ="";
                if (GpsCameraActivity.this.showPreferences("Layout").equals("Advance")) {
                    if (GpsCameraActivity.this.util.showPreferences("key").equals("")) {
                        editText.setText(textDefault);
                    } else {
                        editText.setText(GpsCameraActivity.this.util.showPreferences("key"));
                    }
                } else if (GpsCameraActivity.this.util.showPreferences("key").equals("")) {
                    editText.setText(textDefault);
                } else {
                    editText.setText(GpsCameraActivity.this.util.showPreferences("key"));
                }
                GpsCameraActivity.this.rel_cross_note = create.findViewById(R.id.rel_cross_note);
                GpsCameraActivity.this.rel_cross_note.setOnClickListener(view1 -> create.dismiss());
                GpsCameraActivity.this.btn_Save.setOnClickListener(view12 -> {
                    if (editText.getText().toString().trim().equals("")){
                        Toast.makeText(GpsCameraActivity.this, R.string.please_enter_text_into_the_note,Toast.LENGTH_SHORT).show();
                    }else {
                        if (switchCompat.isChecked()) {
                            if (GpsCameraActivity.this.showPreferences("Layout").equals("Advance")) {
                                GpsCameraActivity.this.SavePreferences("hashtag_Temp0", "Yes");
                            }else {
                                GpsCameraActivity.this.SavePreferences("hashtag_Temp2", "Yes");
                            }
                        } else {
                            if (GpsCameraActivity.this.showPreferences("Layout").equals("Advance")) {
                                GpsCameraActivity.this.SavePreferences("hashtag_Temp0", "No");
                            }else {
                                GpsCameraActivity.this.SavePreferences("hashtag_Temp2", "No");
                            }
                        }
                        GpsCameraActivity.this.util.SavePreferences("key", editText.getText().toString());
                        create.dismiss();
                    }
                    GpsCameraActivity.this.setData();
                });
            });
            return null;
        });

        activateReviewInfo();
        showRateApp();
        this.rel_stampp = findViewById(R.id.rel_stampp);
        this.img_flash = findViewById(R.id.img_flash);
        this.record_panel = findViewById(R.id.record_panel);

        ((AudioManager) getSystemService(Context.AUDIO_SERVICE)).setStreamMute(5, false);
        this.mHelperClass = new HelperClass(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.display_width = displayMetrics.widthPixels;
        this.display_height = displayMetrics.heightPixels;
        init();
        this.magneticSensorNew.clearDialog();
        if (!HelperClass.check_internet(this).booleanValue()) {
//            Snackbar.make(this.moMainLayout, getResources().getString(R.string.PleasecheckyourInternet), 0).show();
        }
        camera.setAudio(Audio.OFF);
        updateGridCamera();
        setupFlashNew();
        setUpGPS();
        createSaveFolder();
        startDateTimer();
        LocationSupplier locationSupplier2 = new LocationSupplier(this);
        this.locationSupplier = locationSupplier2;
        locationSupplier2.setOnLocationUpdateListener(location -> {
            GpsCameraActivity.this.mCurrentLocation = location;
            Log.e(GpsCameraActivity.this.getResources().getString(R.string.Latitude), String.valueOf(location.getLatitude()));
            GpsCameraActivity.this.mSP.setString(GpsCameraActivity.this, KeysConstants.LATITUDE, String.valueOf(location.getLatitude()));
            GpsCameraActivity.this.mSP.setString(GpsCameraActivity.this, KeysConstants.LONGITUDE, String.valueOf(location.getLongitude()));
            GpsCameraActivity.this.mSP.setString(GpsCameraActivity.this, KeysConstants.ALTITUDE, String.valueOf(location.getAltitude()));
            GpsCameraActivity.this.getStampBitmap();
            GpsCameraActivity.this.updateLocationUI();
            GpsCameraActivity.this.setData();
        });
        ExtsKt.setOnClickListener500MilliSeconds(this.template, () -> {
            GpsCameraActivity.this.startActivityForResult(new Intent(GpsCameraActivity.this, TemplateActivity.class),1);
            return null;
        });
    }

    View view;
    private void setData() {
        view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.car_stamp_layout_new_1, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (showPreferences("Layout").equals("Advance")) {
            if (this.mSP.getString(this, "pos_type_temp1", "Bottom").equals("Top")) {
                layoutParams.addRule(RelativeLayout.BELOW, R.id.view_top);
                layoutParams.setMargins(0, 2, 0, 0);
                this.rel_mainStamp.setLayoutParams(layoutParams);
            } else {
                layoutParams.addRule(RelativeLayout.ABOVE, R.id.view_bottom);
                layoutParams.setMargins(0, 0, 0, 2);
                this.rel_mainStamp.setLayoutParams(layoutParams);
            }
        } else {
            if (this.mSP.getString(this, "pos_type_temp2", "Bottom").equals("Top")) {
                layoutParams.addRule(RelativeLayout.BELOW, R.id.view_top);
                layoutParams.setMargins(0, 2, 0, 0);
                this.rel_mainStamp.setLayoutParams(layoutParams);
            } else {
                layoutParams.addRule(RelativeLayout.ABOVE, R.id.view_bottom);
                layoutParams.setMargins(0, 0, 0, 2);
                this.rel_mainStamp.setLayoutParams(layoutParams);
            }
        }
        this.rel_mainStamp.removeAllViews();
        this.rel_mainStamp.addView(view);

        LocationManager locationManager2 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        this.locationManager = locationManager2;
        if (locationManager2.isProviderEnabled("gps")) {
            getLocationLive();
        }

        if (showPreferences("Layout").equals("Advance")) {
            AdvanceTemplate(view);
        } else {
            ClassicTemplate(view);
        }
    }

    private void setUpGPS() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.mSensorManager = sensorManager;
        if (sensorManager.getDefaultSensor(1) != null) {
            this.mSensorAccelerometer = this.mSensorManager.getDefaultSensor(1);
        }
        this.magneticSensorNew.initSensor(this.mSensorManager);
        this.orientationEventListener = new OrientationEventListener(this) {

            public void onOrientationChanged(int i) {
                int i2;
                if (i != -1) {
                    int abs = Math.abs(i - GpsCameraActivity.this.current_orientation);
                    if (abs > 180) {
                        abs = 360 - abs;
                    }
                    if (abs > 60 && (i2 = (((i + 45) / 90) * 90) % 360) != GpsCameraActivity.this.current_orientation) {
                        GpsCameraActivity.this.current_orientation = i2;
                        GpsCameraActivity.this.view_rotate_animation = true;
                        GpsCameraActivity.this.layoutUI();
                        GpsCameraActivity.this.view_rotate_animation = false;
                    }
                }
            }
        };
    }


    private void layoutUI() {
        int i = 0;
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int ui_rotation;
        if (rotation != 0) {
            if (rotation == 1) {
                i = 90;
            } else if (rotation == 2) {
                i = 180;
            } else if (rotation == 3) {
                i = 270;
            }
            ui_rotation = (360 - ((this.current_orientation + i) % 360)) % 360;
            if (true) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.takePhotoButton.getLayoutParams();
                layoutParams.addRule(13, -1);
                this.takePhotoButton.setLayoutParams(layoutParams);
                setViewRotation(this.takePhotoButton, (float) ui_rotation);
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.switchCameraButton.getLayoutParams();
                layoutParams2.addRule(11, -1);
                layoutParams2.addRule(15, -1);
                this.switchCameraButton.setLayoutParams(layoutParams2);
                setViewRotation(this.switchCameraButton, (float) ui_rotation);
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.lin_gallery.getLayoutParams();
                layoutParams3.addRule(9, -1);
                layoutParams3.addRule(15, -1);
                this.lin_gallery.setLayoutParams(layoutParams3);
                setViewRotation(this.lin_gallery, (float) ui_rotation);

                ImageView imageView = this.mImg_flash;
                imageView.setLayoutParams(imageView.getLayoutParams());
                setViewRotation(this.mImg_flash, (float) ui_rotation);

                ImageView imageView5 = findViewById(R.id.switch_camera);
                imageView5.setLayoutParams(imageView5.getLayoutParams());
                setViewRotation(imageView5, (float) ui_rotation);
                View findViewById2 = findViewById(R.id.img_home);
                findViewById2.setLayoutParams(findViewById2.getLayoutParams());
                setViewRotation(findViewById2, (float) ui_rotation);
                return;
            }
            return;
        }
    }

    public void createSaveFolder() {
        if (!isFinishing()) {
            File file = new File(Default.PARENT_FOLDER_PATH + "/" + showPreferences("FolderName"));
            if (!file.exists()) {
                file.mkdir();
            }
            if (file.exists()) {
                File file2 = new File(Default.PARENT_FOLDER_PATH + "/" + showPreferences("FolderName"));
                if (!file2.exists()) {
                    file2.mkdir();
                }
                File file3 = new File(Default.PARENT_FOLDER_PATH + "/" + showPreferences("FolderName"));
                if (!file3.exists()) {
                    file3.mkdir();
                }
            }
        }
    }

    private void setViewRotation(View view, float f) {
        if (!this.view_rotate_animation) {
            view.setRotation(f);
        }
        float rotation = f - view.getRotation();
        if (rotation > 181.0f) {
            rotation -= 360.0f;
        } else if (rotation < -181.0f) {
            rotation += 360.0f;
        }
        view.animate().rotationBy(rotation).setDuration(100).setInterpolator(new AccelerateDecelerateInterpolator()).start();
    }

    public void updateGridCamera() {
        int intValue = this.mSP.getInteger(this, KeysConstants.GRID_POS, 0).intValue();
        if (intValue == 0) {
            this.camera.setGrid(Grid.OFF);
        } else if (intValue == 1) {
            this.camera.setGrid(Grid.DRAW_3X3);
        } else if (intValue == 2) {
            this.camera.setGrid(Grid.DRAW_4X4);
        } else if (intValue == 3) {
            this.camera.setGrid(Grid.DRAW_PHI);
        }
    }

    private void internetAlertDialog() {
        if (!isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.no_internet_location));
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {


                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }
    }

    private int getRatioH(int i, int i2) {
        return (i * this.display_width) / i2;
    }

    public int getRatioW(int i, int i2) {
        return (i2 * this.display_height) / i;
    }

    private void updateLocationUI() {
        if (this.mCurrentLocation != null) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            Log.e(getResources().getString(R.string.address), String.valueOf(this.mCurrentLocation));
            try {
                List<android.location.Address> fromLocation = geocoder.getFromLocation(this.mCurrentLocation.getLatitude(), this.mCurrentLocation.getLongitude(), 1);
                if (fromLocation != null && fromLocation.size() > 0) {
                    Log.e(getResources().getString(R.string.address), fromLocation.get(0).getAddressLine(0));
                    this.address = fromLocation.get(0).getAddressLine(0);
                    this.altitude = this.mCurrentLocation.getAltitude();
                    this.latitude = this.mCurrentLocation.getLatitude();
                    this.longitude = this.mCurrentLocation.getLongitude();
                    C1281SP sp = new C1281SP(this);
                    this.mSP = sp;
                    sp.setString(this, KeysConstants.LATITUDE, String.valueOf(this.latitude));
                    this.mSP.setString(this, KeysConstants.LONGITUDE, String.valueOf(this.longitude));
                    this.mSP.setString(this, KeysConstants.ALTITUDE, String.valueOf(this.altitude));
                    this.mSP.setString(this, "address", String.valueOf(this.address));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            createLocationRequestGPS();
        }
    }

    public void createLocationRequestGPS() {
        if (this.isPermission == 0) {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(10000);

            locationRequest.setFastestInterval(5000);
            locationRequest.setPriority(100);
            SettingsClient mSettingsClient = LocationServices.getSettingsClient((Activity) this);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(locationRequest);
            mSettingsClient.checkLocationSettings(builder.build()).addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {


                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    if (!GpsCameraActivity.this.app_is_paused) {
                        GpsCameraActivity.this.locationInit();
                    }
                }
            }).addOnFailureListener(this, new OnFailureListener() {


                @Override
                public void onFailure(Exception exc) {
                    int statusCode = ((ApiException) exc).getStatusCode();
                    if (statusCode == 6) {
                        try {
                            ((ResolvableApiException) exc).startResolutionForResult(GpsCameraActivity.this, 1);
                        } catch (IntentSender.SendIntentException unused) {
                        }
                    } else if (statusCode == 8502) {
                        GpsCameraActivity cAR_CameraActivity = GpsCameraActivity.this;
                        Toast.makeText(cAR_CameraActivity, cAR_CameraActivity.getResources().getString(R.string.loaction), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        this.isPermission = 0;
    }

    public void setupFlashNew() {
        ArrayList<Flash> arrayList;
        int intValue;
        String name;
        if (!isFinishing() && (arrayList = this.mFlashvalues) != null && arrayList.size() > 1 && (intValue = this.mSP.getInteger(this, KeysConstants.getFlashPos("100"), 0).intValue()) >= 0 && intValue < this.mFlashvalues.size() && (name = this.mFlashvalues.get(intValue).name()) != null && name.equals(getResources().getString(R.string.TORCH))) {
            this.mSP.setInteger(this, KeysConstants.getFlashPos("100"), 0);
            updateCycleFlashIcon();
        }
    }

    public void updateCycleFlashIcon() {
        int intValue = this.mSP.getInteger(this, KeysConstants.getFlashPos("100"), 0).intValue();
        if (intValue >= 0 && intValue < this.mFlashvalues.size()) {
            String name = this.mFlashvalues.get(intValue).name();
            Log.e("flash", name);
            if (name != null) {
                ImageView imageView = findViewById(R.id.img_flash);
                if (name.equals("TORCH")) {
                    imageView.setImageResource(R.drawable.ic_flash_always_on);
                    this.camera.setFlash(Flash.TORCH);
                } else if (name.equals("AUTO")) {
                    imageView.setImageResource(R.drawable.ic_flash_auto);
                    this.camera.setFlash(Flash.AUTO);
                } else if (name.equals("ON")) {
                    imageView.setImageResource(R.drawable.ic_flash_on);
                    this.camera.setFlash(Flash.ON);
                } else {
                    imageView.setImageResource(R.drawable.ic_flash_off);
                    this.camera.setFlash(Flash.OFF);
                }
            }
        }
    }

    private void init() {
        this.mSP = new C1281SP(this);
        this.dbManager = new DBManager(this);
        this.magneticSensorNew = new MagneticSensorNew(this);
        if (this.dbManager.getuserdata_Tag().size() == 0) {
            this.dbManager.insert_Tag("Captured by Note cam");
        }
        this.padding_4 = getResources().getDimensionPixelOffset(R.dimen._4dp);
        this.padding_12 = getResources().getDimensionPixelOffset(R.dimen._5dp);
        this.padding_6 = getResources().getDimensionPixelOffset(R.dimen._6dp);
        this.template = findViewById(R.id.template);

        this.img_compass = findViewById(R.id.img_compass);
        this.mtv_degree = findViewById(R.id.tv_degree);
        this.rel_compass = findViewById(R.id.rel_Compass);
        this.camera = findViewById(R.id.camera);
        this.mRel_header = findViewById(R.id.rel_header);
        this.arrowView = findViewById(R.id.img_compass);
        this.rel_mainStamp = findViewById(R.id.rel_preview);
        this.switchCameraButton = findViewById(R.id.switch_camera);
        this.galleryButton = findViewById(R.id.gallery);
        this.lin_gallery = findViewById(R.id.lin_gallery);
        this.moRelHeader = findViewById(R.id.rel_header);
        this.moMainLayout = findViewById(R.id.li_custom_lay);
        this.mLinear_Flash = findViewById(R.id.linear_flash);
        this.bottomscreenpannel = findViewById(R.id.bottomscreenpannel);
        this.mImg_flash = findViewById(R.id.img_flash);
        this.takePhotoButton = findViewById(R.id.take_photo);

        this.mSP.getBoolean(this, HelperClass.IS_PURCHESH_OR_NOT, false).booleanValue();
        setupCompass();
        this.camera.setLifecycleOwner(this);
        this.camera.addCameraListener(new GpsCameraActivity.Listener());
        this.camera.addFrameProcessor(new FrameProcessor() {

            private long lastTime = System.currentTimeMillis();

            @Override
            public void process(Frame frame) {
                this.lastTime = frame.getTime();
            }
        });
        if (showPreferences("CameraSounf").equals("ON")) {
            this.camera.setPlaySounds(true);
        } else {
            this.camera.setPlaySounds(false);
        }
        if (this.cameraType.equals("camera")) {
            modePhoto();
            this.mRel_header.setVisibility(View.VISIBLE);
        }
        onClicks();
    }

    private void onClicks() {
        this.takePhotoButton.setOnClickListener(this);
        this.mLinear_Flash.setOnClickListener(this);
        this.lin_gallery.setOnClickListener(this);
        this.switchCameraButton.setOnClickListener(this);
    }

    public class Listener extends CameraListener {
        private Listener() {
        }

        @Override
        public void onCameraOpened(CameraOptions cameraOptions) {
            GpsCameraActivity.this.drawPreviewStamp();
            GpsCameraActivity.this.mRatioList = new ArrayList(cameraOptions.getSupportedPictureSizes());

            List<Size> listNew = new ArrayList<>();
            Set<String> seenJs = new HashSet<>();
            List<Size> list = GpsCameraActivity.this.mRatioList;
            for (Size item : list) {
                if (!seenJs.contains(HelperClass.getAspectRatioMPStringNew(item.getWidth(), item.getHeight()))) {
                    seenJs.add(HelperClass.getAspectRatioMPStringNew(item.getWidth(), item.getHeight()));
                    listNew.add(item);
                }
            }
            list.clear();
            list.addAll(listNew);
            GpsCameraActivity.this.setData();

            GpsCameraActivity.this.gpsflag = 1;
            GpsCameraActivity.this.mFlashvalues = new ArrayList(cameraOptions.getSupportedFlash());
            Collections.sort(GpsCameraActivity.this.mFlashvalues);
            if (GpsCameraActivity.this.mFlashvalues == null || GpsCameraActivity.this.mFlashvalues.size() <= 1) {
                GpsCameraActivity.this.img_flash.setImageResource(R.drawable.ic_flash_dis);
                return;
            }

            GpsCameraActivity.this.img_flash.setImageResource(R.drawable.ic_flash_auto);
            GpsCameraActivity.this.updateCycleFlashIcon();
        }

        @Override
        public void onAutoFocusStart(@NonNull PointF pointF) {
            super.onAutoFocusStart(pointF);
        }

        @Override
        public void onAutoFocusEnd(boolean z, PointF pointF) {
            super.onAutoFocusEnd(z, pointF);
        }

        @Override
        public void onOrientationChanged(int i) {
            super.onOrientationChanged(i);
        }

        @Override
        public void onPictureTaken(PictureResult pictureResult) {
            super.onPictureTaken(pictureResult);
            String j;
            if (GpsCameraActivity.this.showPreferences("Layout").equals("Advance")) {
                j = GpsCameraActivity.this.showPreferences("positionX1");
            }else {
                j = GpsCameraActivity.this.showPreferences("positionX2");
            }
            if (j.equals("1")) {
                if (!GpsCameraActivity.this.camera.isTakingVideo()) {
                    CameraUtils.decodeBitmap(pictureResult.getData(), GpsCameraActivity.this.moMainLayout.getWidth(), GpsCameraActivity.this.moMainLayout.getHeight(), new BitmapCallback() {
                        @Override
                        public void onBitmapReady(Bitmap bitmap) {
                            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                            if (GpsCameraActivity.this.showPreferences("Layout").equals("Advance")) {
                                Bitmap resizedBitmap = GpsCameraActivity.Listener.this.getResizedBitmap(GpsCameraActivity.this.takeScreenshot(GpsCameraActivity.this.rel_mainStamp), bitmap.getWidth(), 100 - ((int) ((((double) bitmap.getWidth()) / ((double) GpsCameraActivity.this.rel_mainStamp.getWidth())) * 100.0d)));
                                int height = bitmap.getHeight() - resizedBitmap.getHeight();
                                Canvas canvas = new Canvas(createBitmap);
                                canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                                canvas.drawBitmap(resizedBitmap, 0.0f, (float) height, null);
                            } else {
                                Bitmap resizedBitmap2 = GpsCameraActivity.Listener.this.getResizedBitmap(GpsCameraActivity.this.takeScreenshot(GpsCameraActivity.this.rel_mainStamp), bitmap.getWidth(), 100 - ((int) ((((double) bitmap.getWidth()) / ((double) GpsCameraActivity.this.rel_mainStamp.getWidth())) * 100.0d)));
                                int height2 = bitmap.getHeight() - resizedBitmap2.getHeight();
                                Canvas canvas2 = new Canvas(createBitmap);
                                canvas2.drawBitmap(bitmap, 0.0f, 0.0f, null);
                                canvas2.drawBitmap(resizedBitmap2, 0.0f, (float) height2, null);
                            }
                            GpsCameraActivity.ImageStampAsync imageStampAsync = new GpsCameraActivity.ImageStampAsync();
                            ObjectFile objectFile = new ObjectFile();
                            objectFile.setBitmap(createBitmap);
                            imageStampAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, objectFile);
                            GpsCameraActivity.this.SavePreferences("Numbering", String.valueOf(Integer.parseInt(GpsCameraActivity.this.showPreferences("Numbering")) + 1));
                        }
                    });
                }
            } else if (!GpsCameraActivity.this.camera.isTakingVideo()) {
                CameraUtils.decodeBitmap(pictureResult.getData(), GpsCameraActivity.this.moMainLayout.getWidth(), GpsCameraActivity.this.moMainLayout.getHeight(), new BitmapCallback() {
                    @Override
                    public void onBitmapReady(Bitmap bitmap) {
                        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                        Bitmap resizedBitmap = Listener.this.getResizedBitmap(GpsCameraActivity.this.takeScreenshot(GpsCameraActivity.this.rel_mainStamp), bitmap.getWidth(), 100 - ((int) ((((double) bitmap.getWidth()) / ((double) GpsCameraActivity.this.rel_mainStamp.getWidth())) * 100.0d)));
                        bitmap.getHeight();
                        resizedBitmap.getHeight();
                        Canvas canvas = new Canvas(createBitmap);
                        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                        canvas.drawBitmap(resizedBitmap, 0.0f, 0.0f, null);
                        GpsCameraActivity.ImageStampAsync imageStampAsync = new GpsCameraActivity.ImageStampAsync();
                        ObjectFile objectFile = new ObjectFile();
                        objectFile.setBitmap(createBitmap);
                        imageStampAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, objectFile);
                        GpsCameraActivity.this.SavePreferences("Numbering", String.valueOf(Integer.parseInt(GpsCameraActivity.this.showPreferences("Numbering")) + 1));
                    }
                });
            }
        }

        public Bitmap getResizedBitmap(Bitmap bitmap, int i, int i2) {
            return Bitmap.createScaledBitmap(bitmap, i, bitmap.getHeight() - ((bitmap.getHeight() * i2) / 100), true);
        }

        @Override
        public void onVideoTaken(@NonNull VideoResult videoResult) {
            super.onVideoTaken(videoResult);
        }


        @Override
        public void onExposureCorrectionChanged(float f, float[] fArr, PointF[] pointFArr) {
            super.onExposureCorrectionChanged(f, fArr, pointFArr);
        }

        @Override
        public void onZoomChanged(float f, float[] fArr, PointF[] pointFArr) {
            super.onZoomChanged(f, fArr, pointFArr);
        }
    }


    private Bitmap takeScreenshot(View view) {
        Bitmap createBitmap2 = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap2));
        return createBitmap2;
    }


    private void drawPreviewStamp() {
        startDateTimer();
    }

    private void startDateTimer() {
        Timer mTimerDate = new Timer();
        mTimerDate.schedule(new TimerTask() {


            public void run() {
                GpsCameraActivity.this.mTimerHandler.post(new Runnable() {


                    public void run() {
                    }
                });
            }
        }, 1, 1000);
    }


    private String getStoragePath() {
        String[] split = new File(Uri.parse(showPreferences("FolderName").equals(Default.DEFAULT_FOLDER_NAME) ? Default.PARENT_FOLDER_PATH : Default.PARENT_FOLDER_PATH + "/" + showPreferences("FolderName")).getPath()).getAbsolutePath().split("/");
        String str = "";
        for (String str2 : split) {
            str = (str2.equals(getResources().getString(R.string.tree)) ? new StringBuilder().append(str).append("storage/") : new StringBuilder().append(str).append(str2.replace(":", "/")).append("/")).toString();
        }
        new File(str).exists();
        Log.e(getResources().getString(R.string.new_save_location), str);
        return str;
    }


    private void setExif(android.media.ExifInterface exifInterface, android.media.ExifInterface exifInterface2) throws IOException {
        String attribute = exifInterface.getAttribute(getResources().getString(R.string.FNumber));
        String attribute2 = exifInterface.getAttribute(getResources().getString(R.string.DateTime));
        String attribute3 = exifInterface.getAttribute(getResources().getString(R.string.ExposureTime));
        String attribute4 = exifInterface.getAttribute(getResources().getString(R.string.Flash));
        String attribute5 = exifInterface.getAttribute(getResources().getString(R.string.FocalLength));
        String attribute6 = exifInterface.getAttribute(getResources().getString(R.string.GPSAltitude));
        String attribute7 = exifInterface.getAttribute(getResources().getString(R.string.GPSAltitudeRef));
        String attribute8 = exifInterface.getAttribute(getResources().getString(R.string.GPSDateStamp));
        String attribute9 = exifInterface.getAttribute(getResources().getString(R.string.GPSLatitude));
        String attribute10 = exifInterface.getAttribute(getResources().getString(R.string.GPSLatitudeRef));
        String attribute11 = exifInterface.getAttribute(getResources().getString(R.string.GPSLongitude));
        String attribute12 = exifInterface.getAttribute(getResources().getString(R.string.GPSLongitudeRef));
        String attribute13 = exifInterface.getAttribute(getResources().getString(R.string.GPSProcessingMethod));
        String attribute14 = exifInterface.getAttribute(getResources().getString(R.string.GPSTimeStamp));
        String attribute15 = exifInterface.getAttribute(getResources().getString(R.string.ISOSpeedRatings));
        String attribute16 = exifInterface.getAttribute(getResources().getString(R.string.Make));
        String attribute17 = exifInterface.getAttribute(getResources().getString(R.string.Model));
        String attribute18 = exifInterface.getAttribute(getResources().getString(R.string.WhiteBalance));
        String attribute19 = exifInterface.getAttribute("DateTimeDigitized");
        String attribute20 = exifInterface.getAttribute(getResources().getString(R.string.SubSecTime));
        String attribute21 = exifInterface.getAttribute(getResources().getString(R.string.SubSecTimeDigitized));
        String attribute22 = exifInterface.getAttribute(getResources().getString(R.string.SubSecTimeOriginal));
        String attribute23 = exifInterface.getAttribute(getResources().getString(R.string.ApertureValue));
        String attribute24 = exifInterface.getAttribute(getResources().getString(R.string.BrightnessValue));
        String attribute25 = exifInterface.getAttribute(getResources().getString(R.string.CFAPattern));
        String attribute26 = exifInterface.getAttribute(getResources().getString(R.string.ColorSpace));
        String attribute27 = exifInterface.getAttribute(getResources().getString(R.string.ComponentsConfiguration));
        String attribute28 = exifInterface.getAttribute(getResources().getString(R.string.CompressedBitsPerPixel));
        String attribute29 = exifInterface.getAttribute(getResources().getString(R.string.Compression));
        String attribute30 = exifInterface.getAttribute(getResources().getString(R.string.Contrast));
        String attribute31 = exifInterface.getAttribute("DateTimeOriginal");
        String attribute32 = exifInterface.getAttribute(getResources().getString(R.string.DeviceSettingDescription));
        String attribute33 = exifInterface.getAttribute(getResources().getString(R.string.DigitalZoomRatio));
        String attribute34 = exifInterface.getAttribute(getResources().getString(R.string.ExposureBiasValue));
        String attribute35 = exifInterface.getAttribute(getResources().getString(R.string.ExposureIndex));
        String attribute36 = exifInterface.getAttribute(getResources().getString(R.string.ExposureMode));
        String attribute37 = exifInterface.getAttribute(getResources().getString(R.string.ExposureProgram));
        String attribute38 = exifInterface.getAttribute(getResources().getString(R.string.FlashEnergy));
        String attribute39 = exifInterface.getAttribute(getResources().getString(R.string.FocalLengthIn35mmFilm));
        String attribute40 = exifInterface.getAttribute(getResources().getString(R.string.FocalPlaneResolutionUnit));
        String attribute41 = exifInterface.getAttribute(getResources().getString(R.string.FocalPlaneXResolution));
        String attribute42 = exifInterface.getAttribute(getResources().getString(R.string.FocalPlaneYResolution));
        String attribute43 = exifInterface.getAttribute(getResources().getString(R.string.GainControl));
        String attribute44 = exifInterface.getAttribute(getResources().getString(R.string.GPSAreaInformation));
        String attribute45 = exifInterface.getAttribute(getResources().getString(R.string.GPSDifferential));
        String attribute46 = exifInterface.getAttribute(getResources().getString(R.string.GPSDOP));
        String attribute47 = exifInterface.getAttribute(getResources().getString(R.string.GPSMeasureMode));
        String attribute48 = exifInterface.getAttribute(getResources().getString(R.string.ImageDescription));
        String attribute49 = exifInterface.getAttribute(getResources().getString(R.string.LightSource));
        String attribute50 = exifInterface.getAttribute(getResources().getString(R.string.MakerNote));
        String attribute51 = exifInterface.getAttribute(getResources().getString(R.string.MaxApertureValue));
        String attribute52 = exifInterface.getAttribute(getResources().getString(R.string.MeteringMode));
        String attribute53 = exifInterface.getAttribute(getResources().getString(R.string.OECF));
        String attribute54 = exifInterface.getAttribute(getResources().getString(R.string.PhotometricInterpretation));
        String attribute55 = exifInterface.getAttribute(getResources().getString(R.string.Saturation));
        String attribute56 = exifInterface.getAttribute(getResources().getString(R.string.SceneCaptureType));
        String attribute57 = exifInterface.getAttribute(getResources().getString(R.string.SceneType));
        String attribute58 = exifInterface.getAttribute(getResources().getString(R.string.SensingMethod));
        String attribute59 = exifInterface.getAttribute(getResources().getString(R.string.Sharpness));
        String attribute60 = exifInterface.getAttribute(getResources().getString(R.string.ShutterSpeedValue));
        String attribute61 = exifInterface.getAttribute(getResources().getString(R.string.Software));
        String attribute62 = exifInterface.getAttribute(ExifInterface.TAG_USER_COMMENT);
        if (attribute != null) {
            exifInterface2.setAttribute(ExifInterface.TAG_F_NUMBER, attribute);
        }
        if (attribute2 != null) {
            exifInterface2.setAttribute(ExifInterface.TAG_DATETIME, attribute2);
        }
        if (attribute3 != null) {
            exifInterface2.setAttribute(ExifInterface.TAG_EXPOSURE_TIME, attribute3);
        }
        if (attribute4 != null) {
            exifInterface2.setAttribute(ExifInterface.TAG_FLASH, attribute4);
        }
        if (attribute5 != null) {
            exifInterface2.setAttribute(ExifInterface.TAG_FOCAL_LENGTH, attribute5);
        }
        if (attribute6 != null) {
            exifInterface2.setAttribute(ExifInterface.TAG_GPS_ALTITUDE, attribute6);
        }
        if (attribute7 != null) {
            exifInterface2.setAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF, attribute7);
        }
        if (attribute8 != null) {
            exifInterface2.setAttribute(ExifInterface.TAG_GPS_DATESTAMP, attribute8);
        }
        if (attribute9 != null) {
            Log.e(":::location:::", "exif_gps_latitude: ");
            exifInterface2.setAttribute(ExifInterface.TAG_GPS_LATITUDE, attribute9);
        } else {
            Log.e(":::location:::", "exif_gps_latitude null: ");
            Location location = this.mCurrentLocation;
            if (location != null) {
                double latitude2 = location.getLatitude();
                Log.e(":::location:::", "mCurrentLocation not null: " + latitude2);
                String dec2DMS = dec2DMS(latitude2);
                Log.e(":::location:::", "exif_longi: " + dec2DMS);
                exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, dec2DMS);
                if (attribute10 == null) {
                    exifInterface2.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, attribute10);
                } else if (latitude2 > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "N");
                } else {
                    exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, ExifInterface.LATITUDE_SOUTH);
                }
                if (attribute11 == null) {
                    exifInterface2.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, attribute11);
                } else {
                    Location location2 = this.mCurrentLocation;
                    if (location2 != null) {
                        double longitude2 = location2.getLongitude();
                        String dec2DMS2 = dec2DMS(longitude2);
                        Log.e(":::location:::", "exif_longi: " + dec2DMS2);
                        exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, dec2DMS2);
                        if (attribute12 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, attribute12);
                        } else if (longitude2 > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, ExifInterface.LONGITUDE_EAST);
                        } else {
                            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, ExifInterface.LONGITUDE_WEST);
                        }
                        if (attribute13 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD, attribute13);
                        }
                        if (attribute14 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_GPS_TIMESTAMP, attribute14);
                        }
                        if (attribute15 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS, attribute15);
                        }
                        if (attribute16 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_MAKE, attribute16);
                        }
                        if (attribute17 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_MODEL, attribute17);
                        }
                        if (attribute18 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_WHITE_BALANCE, attribute18);
                        }
                        if (attribute19 != null) {
                            exifInterface2.setAttribute("DateTimeDigitized", attribute19);
                        }
                        if (attribute20 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_SUBSEC_TIME, attribute20);
                        }
                        if (attribute21 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_SUBSEC_TIME_DIGITIZED, attribute21);
                        }
                        if (attribute22 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_SUBSEC_TIME_ORIGINAL, attribute22);
                        }
                        if (attribute23 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_APERTURE_VALUE, attribute23);
                        }
                        if (attribute24 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_BRIGHTNESS_VALUE, attribute24);
                        }
                        if (attribute25 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_CFA_PATTERN, attribute25);
                        }
                        if (attribute26 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_COLOR_SPACE, attribute26);
                        }
                        if (attribute27 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_COMPONENTS_CONFIGURATION, attribute27);
                        }
                        if (attribute28 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL, attribute28);
                        }
                        if (attribute29 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_COMPRESSION, attribute29);
                        }
                        if (attribute30 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_CONTRAST, attribute30);
                        }
                        if (attribute31 != null) {
                            exifInterface2.setAttribute("DateTimeOriginal", attribute31);
                        }
                        if (attribute32 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION, attribute32);
                        }
                        if (attribute33 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_DIGITAL_ZOOM_RATIO, attribute33);
                        }
                        if (attribute34 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_EXPOSURE_BIAS_VALUE, attribute34);
                        }
                        if (attribute35 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_EXPOSURE_INDEX, attribute35);
                        }
                        if (attribute36 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_EXPOSURE_MODE, attribute36);
                        }
                        if (attribute37 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_EXPOSURE_PROGRAM, attribute37);
                        }
                        if (attribute38 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_FLASH_ENERGY, attribute38);
                        }
                        if (attribute39 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM, attribute39);
                        }
                        if (attribute40 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT, attribute40);
                        }
                        if (attribute41 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION, attribute41);
                        }
                        if (attribute42 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION, attribute42);
                        }
                        if (attribute43 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_GAIN_CONTROL, attribute43);
                        }
                        if (attribute44 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_GPS_AREA_INFORMATION, attribute44);
                        }
                        if (attribute45 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_GPS_DIFFERENTIAL, attribute45);
                        }
                        if (attribute46 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_GPS_DOP, attribute46);
                        }
                        if (attribute47 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_GPS_MEASURE_MODE, attribute47);
                        }
                        if (attribute48 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION, attribute48);
                        }
                        if (attribute49 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_LIGHT_SOURCE, attribute49);
                        }
                        if (attribute50 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_MAKER_NOTE, attribute50);
                        }
                        if (attribute51 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_MAX_APERTURE_VALUE, attribute51);
                        }
                        if (attribute52 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_METERING_MODE, attribute52);
                        }
                        if (attribute53 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_OECF, attribute53);
                        }
                        if (attribute54 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION, attribute54);
                        }
                        if (attribute55 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_SATURATION, attribute55);
                        }
                        if (attribute56 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_SCENE_CAPTURE_TYPE, attribute56);
                        }
                        if (attribute57 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_SCENE_TYPE, attribute57);
                        }
                        if (attribute58 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_SENSING_METHOD, attribute58);
                        }
                        if (attribute59 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_SHARPNESS, attribute59);
                        }
                        if (attribute60 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_SHUTTER_SPEED_VALUE, attribute60);
                        }
                        if (attribute61 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_SOFTWARE, attribute61);
                        }
                        if (attribute62 != null) {
                            exifInterface2.setAttribute(ExifInterface.TAG_USER_COMMENT, attribute62);
                        }
                        setDateTimeExif(exifInterface2);
                        exifInterface2.saveAttributes();
                    }
                }
                setDateTimeExif(exifInterface2);
                exifInterface2.saveAttributes();
            }
        }
        setDateTimeExif(exifInterface2);
        exifInterface2.saveAttributes();
    }

    private void setDateTimeExif(android.media.ExifInterface exifInterface) {
        String attribute = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
        if (attribute != null) {
            exifInterface.setAttribute("DateTimeOriginal", attribute);
            exifInterface.setAttribute("DateTimeDigitized", attribute);
        }
    }

    public String dec2DMS(double d) {
        String str = "";
        String str2 = d < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? "-" : str;
        double abs = Math.abs(d);
        int i = (int) abs;
        boolean z = false;
        boolean z2 = i == 0;
        String valueOf = String.valueOf(i);
        double d2 = (abs - ((double) i)) * 60.0d;
        int i2 = (int) d2;
        boolean z3 = z2 && i2 == 0;
        String valueOf2 = String.valueOf(i2);
        int i3 = (int) ((d2 - ((double) i2)) * 60.0d);
        if (z3 && i3 == 0) {
            z = true;
        }
        String valueOf3 = String.valueOf(i3);
        if (!z) {
            str = str2;
        }
        return str + valueOf + "°" + valueOf2 + "'" + valueOf3 + "\"";
    }

    @Override
    public void onDestroy() {
        RenderScript.releaseAllContexts();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        AppOpenManager.getInstance().enableAppResumeWithActivity(GpsCameraActivity.class);

        SensorManager sensorManager = this.mSensorManager;
        if (sensorManager != null) {
            sensorManager.registerListener(this.accelerometerListener, this.mSensorAccelerometer, 3);
        }
        MagneticSensorNew magneticSensorNew2 = this.magneticSensorNew;
        if (magneticSensorNew2 != null) {
            magneticSensorNew2.magneticListenerRegister(this.mSensorManager);
        }
        OrientationEventListener orientationEventListener2 = this.orientationEventListener;
        if (orientationEventListener2 != null) {
            orientationEventListener2.enable();
        }
        this.app_is_paused = false;

        HelperClass.check_internet(this);
        if (Utils.allPermissionGrant(this,getStoragePermissions())) {
            if (Utils.allPermissionGrant(this,getCameraPermission())) {
                updatePhotoGallery();
                if (this.isPermission == 0) {
                    try {
                        createLocationRequestGPS();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
//        setData();

        if (showPreferences("CameraSounf").equals("ON")) {
            this.camera.setPlaySounds(true);
        } else {
            this.camera.setPlaySounds(false);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        handlerTimer.removeCallbacks(runnableTimer);
        this.mSensorManager.unregisterListener(this.accelerometerListener);
        this.magneticSensorNew.unregisterMagneticListener(this.mSensorManager);
        this.orientationEventListener.disable();
        this.app_is_paused = true;
        this.locationSupplier.freeLocationListeners();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void clickedSettings(View view) {
        setupFlashNew();
        Intent intent = new Intent(this, SettingActivity.class);
        startActivityForResult(intent,1);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lin_gallery) {
            setupFlashNew();
            if (!this.isRecording) {
                String recentPath = getPathRecent();
                if (recentPath != null) {
                    new GpsCameraActivity.SingleMediaScanner(recentPath);
                    return;
                }
                return;
            }
            this.galleryButton.setImageResource(R.drawable.ic_gallery);
            return;
        }
        int i = 0;
        if (id == R.id.linear_flash) {

            ArrayList<Flash> arrayList = this.mFlashvalues;
            if (arrayList != null && arrayList.size() > 1) {
                int intValue = this.mSP.getInteger(this, KeysConstants.getFlashPos("100"), 0).intValue() + 1;
                if (intValue != this.mFlashvalues.size()) {
                    i = intValue;
                }
                this.mSP.setInteger(this, KeysConstants.getFlashPos("100"), Integer.valueOf(i));
                updateCycleFlashIcon();
            }
        } else if (id == R.id.switch_camera) {
            setupFlashNew();
            if (!this.isRecording && !this.camera.isTakingPicture() && !this.camera.isTakingVideo()) {
                this.camera.toggleFacing();
            }
        } else if (id == R.id.take_photo) {
            CameraView cameraView = this.camera;
            if (cameraView != null) {
                cameraView.setLocation(this.locationSupplier.getLocation());
            }
            if (this.cameraType.equals("camera")) {
                int intValue2 = this.mSP.getInteger(this, KeysConstants.CAPTURE_TIMER, 0);
                if (intValue2 == 0) {
                    handlerTakePhoto();
                } else if (intValue2 == 3) {
                    final int[] iArr = {3};

                    new CountDownTimer(3000, 1000) {
                        public void onTick(long j) {
                            Log.e("count 3 :", String.valueOf(iArr[0]));
                            int[] iArr = new int[0];
                            iArr[0] = iArr[0] - 1;
                        }
                        public void onFinish() {
                            GpsCameraActivity.this.handlerTakePhoto();
                        }
                    }.start();
                } else if (intValue2 == 5) {
                    final int[] iArr2 = {5};
                    new CountDownTimer(5000, 1000) {


                        public void onTick(long j) {
                            Log.e("count 5 :", String.valueOf(iArr2[0]));
                            int[] iArr = iArr2;
                            iArr[0] = iArr[0] - 1;
                        }

                        public void onFinish() {
                            GpsCameraActivity.this.handlerTakePhoto();
                        }
                    }.start();
                }
            } else {
                handlerTakePhoto();
            }
            AdsManager.INSTANCE.showRate(this);

        }
    }


    private void handlerTakePhoto() {
        SavePreferencesInt("Numberring", this.numbering);
        if (!this.camera.isTakingPicture()) {
            this.camera.takePicture();
        } else {
            return;
        }
    }

    private void modePhoto() {
        this.takePhotoButton.setImageResource(R.drawable.ic_circle);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(14, -1);
        layoutParams.addRule(15);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.setMargins(0, 0, 0, 0);
        layoutParams2.addRule(15);
    }

    public void updatePhotoGallery() {
      Glide.with(getApplicationContext()).load(getPathRecent()).centerCrop().listener(new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target target, boolean isFirstResource) {
                galleryButton.setImageResource(R.drawable.img_demo_);
                return false;
            }

            @Override
            public boolean onResourceReady(@NonNull Object resource, @NonNull Object model, Target target, @NonNull DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(this.galleryButton);
    }

    private String getPathRecent() {
        File file;
        if (showPreferences("FolderName").equals(Default.DEFAULT_FOLDER_NAME)) {
            file = new File(Default.PARENT_FOLDER_PATH);
        } else {
            file = new File(Default.PARENT_FOLDER_PATH + "/" + showPreferences("FolderName"));
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            return pathImage();
        }
        ArrayList arrayList = new ArrayList();
        for (File file2 : listFiles) {
            Date date = new Date(file2.lastModified());
            ImageGetSet imageGetSet = new ImageGetSet();
            if (file2.isFile()) {
                imageGetSet.setDateTime(date);
                imageGetSet.setImg_path(file2.getAbsolutePath());
                arrayList.add(imageGetSet);
            }
        }
        Collections.sort(arrayList, Collections.reverseOrder());
        if (arrayList.size() <= 0) {
            return null;
        }
        String img_path = ((ImageGetSet) arrayList.get(0)).getImg_path();
        return (img_path.contains(".jpg")) ? img_path : pathImage();
    }

    private String pathImage() {
        File[] listFiles;
        File file = new File((String) Default.CAMERA_FOLDER);
        if (file.exists() && (listFiles = file.listFiles()) != null && listFiles.length > 0) {
            ArrayList arrayList = new ArrayList();
            for (File file2 : listFiles) {
                Date date = new Date(file2.lastModified());
                ImageGetSet imageGetSet = new ImageGetSet();
                if (file2.isFile()) {
                    imageGetSet.setDateTime(date);
                    imageGetSet.setImg_path(file2.getAbsolutePath());
                    arrayList.add(imageGetSet);
                }
            }
            Collections.sort(arrayList, Collections.reverseOrder());
            if (arrayList.size() > 0) {
                return ((ImageGetSet) arrayList.get(0)).getImg_path();
            }
        }
        return null;
    }

    public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
        private String mFilePath;
        private MediaScannerConnection mMs;

        public SingleMediaScanner(String str) {
            this.mFilePath = str;
            MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(GpsCameraActivity.this, this);
            this.mMs = mediaScannerConnection;
            mediaScannerConnection.connect();
        }

        public void onMediaScannerConnected() {
            this.mMs.scanFile(this.mFilePath, null);
        }

        public void onScanCompleted(String str, Uri uri) {

            Intent intent = new Intent(GpsCameraActivity.this, PhotoPreviewActivity.class);
            intent.putExtra(PREVIEW_EXTRA, uri);
            startActivityForResult(intent,1);
        }
    }
    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        AdsManager.INSTANCE.showAdBannerCollapsible(this,AdsManager.INSTANCE.getBANNER_COLLAP_CAMERA(),binding.frBanner,binding.line);

        if (i != 1) {
            if (i != 103) {
                return;
            }
        } else if (i2 == 0) {
            this.isPermission++;
        }
    }

    public Bitmap drawStamp(Bitmap bitmap, boolean z) {
        if (showPreferencesString("Layout").equals("Advance")) {
            return setTemp1(bitmap, z);
        }
        return setTemp2(bitmap, z);
    }

    @SuppressLint("SimpleDateFormat")
    private String getdate() {
        String string = this.mSP.getString(this, KeysConstants.DATE_POSITION0, DateAdapterCamera.DATE_FORMAT_1);
        String str = switch (string) {
            case DateAdapterCamera.DATE_FORMAT_0 -> "EEEE, dd-MM-yyyy";
            case DateAdapterCamera.DATE_FORMAT_1 -> "EEEE, MM-dd-yyyy";
            case DateAdapterCamera.DATE_FORMAT_2 -> "EEEE, yyyy-MM-dd";
            default -> "EEEE, " + string;
        };
        return new SimpleDateFormat(str).format(Calendar.getInstance().getTime());
    }

    private String getLocalTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.mSP.getString(this, KeysConstants.TIME_POSITION0, TimeAdapter.TIME_FORMAT_1));
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        return simpleDateFormat.format(instance.getTime());
    }


    private void locationInit() {
        if (!this.app_is_paused && !this.locationSupplier.setupLocationListener()) {
            requestLocationPermission();
        }
    }

    public boolean requestLocationPermission() {
        try {
            ArrayList arrayList = new ArrayList();
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                arrayList.add("android.permission.ACCESS_FINE_LOCATION");
            }
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
            }
            if (arrayList.isEmpty()) {
                return true;
            }
            requestPermissions((String[]) arrayList.toArray(new String[arrayList.size()]), this.REQUEST_CODE);
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    class ImageStampAsync extends AsyncTask<ObjectFile, Void, Void> {
        private File getExifTempFile(byte[] bArr) {
            return null;
        }

        private boolean needGPSTimestampHack(boolean z, boolean z2, boolean z3) {
            if (!z || !z2) {
                return false;
            }
            return z3;
        }

        ImageStampAsync() {
        }

        public Void doInBackground(ObjectFile... objectFileArr) {
            String str;
            Cursor query;
            GpsCameraActivity.this.myObjectWeakReference = new WeakReference(objectFileArr[0]);
            GpsCameraActivity cAR_CameraActivity = GpsCameraActivity.this;
            Bitmap drawStamp = cAR_CameraActivity.drawStamp(((ObjectFile) cAR_CameraActivity.myObjectWeakReference.get()).getBitmap(), false);
            System.gc();
            if (drawStamp == null) {
                return null;
            }
            if (GpsCameraActivity.this.mSP.getBoolean(GpsCameraActivity.this, KeysConstants.IS_SD_CARD, false).booleanValue()) {
                String str2 = "Images" + new Random().nextInt() + ".jpg";
                String string = GpsCameraActivity.this.mSP.getString(GpsCameraActivity.this, C1281SP.FOLDER_NAME, "");
                Uri parse = Uri.parse(string);
                String str3 = new File(parse.getPath()).getAbsolutePath() + str2;
                Log.e("file_path", str3);
                File file = new File(Environment.getExternalStorageDirectory(), str2);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                    drawStamp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                Log.e("treeUri", "" + string);
                if (string == null) {
                    return null;
                }
                ContentResolver contentResolver = GpsCameraActivity.this.getContentResolver();
                contentResolver.takePersistableUriPermission(Uri.parse(string), Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String str4 = "/";
                String[] split = str3.split(str4);
                if (split != null && split.length > 0) {
                    if (split.length - 1 > 3) {
                        str = "";
                        for (int i = 3; i < split.length - 1; i++) {
                            str = str + split[i] + str4;
                        }
                        if (str.endsWith(str4)) {
                            str = str.substring(0, str.length() - 1);
                        }
                        Uri parse2 = Uri.parse(String.valueOf(DocumentsContract.buildDocumentUriUsingTree(parse, DocumentsContract.getTreeDocumentId(parse) + str)));
                        query = contentResolver.query(parse2, new String[]{"_display_name", "mime_type"}, null, null, null);
                        if (query != null) {
                            return null;
                        }
                        while (query.moveToNext()) {
                            String[] split2 = str3.split(str4);
                            String str5 = split2[split2.length - 1];
                            DocumentsContract.buildDocumentUriUsingTree(parse, DocumentsContract.getTreeDocumentId(parse) + (str + str4 + str2));
                            Uri createDocument = null;
                            try {
                                createDocument = DocumentsContract.createDocument(contentResolver, parse2, "image/*", "" + str2);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            FileInputStream fileInputStream = null;
                            try {
                                fileInputStream = new FileInputStream(file);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            byte[] bArr = new byte[((int) file.length())];
                            try {
                                fileInputStream.read(bArr);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                OutputStream openOutputStream = GpsCameraActivity.this.getContentResolver().openOutputStream(createDocument);
                                if (openOutputStream != null) {
                                    openOutputStream.write(bArr);
                                    openOutputStream.close();
                                }
                                file.delete();
                                System.gc();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                            str4 = str4;
                        }
                        query.close();
                        return null;
                    }
                }
                str = "";
                try {
                    Uri parse22 = Uri.parse(String.valueOf(DocumentsContract.buildDocumentUriUsingTree(parse, DocumentsContract.getTreeDocumentId(parse) + str)));
                    query = contentResolver.query(parse22, new String[]{"_display_name", "mime_type"}, null, null, null);
                    if (query != null) {
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                    return null;
                }
            } else {
                File file2 = new File(GpsCameraActivity.this.getStoragePath());
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                File file3 = new File(file2, "Images" + new Random().nextInt() + ".jpg");
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file3, false);
                    drawStamp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                } catch (FileNotFoundException e5) {
                    e5.printStackTrace();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
                try {
                    setExifFromData(((ObjectFile) GpsCameraActivity.this.myObjectWeakReference.get()).getData(), file3);
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
                MediaScannerConnection.scanFile(GpsCameraActivity.this.getApplicationContext(), new String[]{file3.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {


                    public void onScanCompleted(String str, Uri uri) {
                        Log.i(GpsCameraActivity.this.getResources().getString(R.string.ExternalStorage), GpsCameraActivity.this.getResources().getString(R.string.Scanned) + str + ":");
                        Log.i(GpsCameraActivity.this.getResources().getString(R.string.ExternalStorage), "-> uri=" + uri);
                    }
                });
                drawStamp.recycle();
                return null;
            }
            return null;
        }

        public void onPostExecute(Void r1) {
            super.onPostExecute(r1);
            GpsCameraActivity.this.updatePhotoGallery();
        }

        private void setExifFromData(byte[] bArr, File file) throws IOException {
            ByteArrayInputStream byteArrayInputStream = null;
            try {
                ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(bArr);
                GpsCameraActivity.this.setExif(new android.media.ExifInterface(byteArrayInputStream2), new android.media.ExifInterface(file.getAbsolutePath()));
                byteArrayInputStream2.close();
            } catch (Throwable unused) {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        realTime();
        this.compass.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.compass.stop();
    }

    private void setupCompass() {
        CompassSensorEvent cAR_CompassSensorEvent = new CompassSensorEvent(this);
        this.compass = cAR_CompassSensorEvent;
        cAR_CompassSensorEvent.setListener(new CompassSensorEvent.CompassListener() {

            @Override
            public void onMagField(float f) {
                GpsCameraActivity.this.MagField(f);
            }

            @Override
            public void onNewAzimuth(float f) {
                GpsCameraActivity.this.adjustArrow(f);
            }
        });
        CompassSensorEvent cAR_Compass2SensorEvent = this.compass;
        if (cAR_Compass2SensorEvent != null) {
            cAR_Compass2SensorEvent.setListener(new CompassSensorEvent.CompassListener() {
                @Override
                public void onMagField(float f) {
                    GpsCameraActivity.this.MagField(f);
                }

                @Override
                public void onNewAzimuth(float f) {
                    GpsCameraActivity.this.adjustArrow(f);
                }
            });

            if (this.compass.getStatusCompass()) {
//                compassFound = true;
            }else {
                Toast.makeText(this,getString(R.string.your_device_does_not_support_compass),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void adjustArrow(float f) {
        if (this.compass.getDataSensor()) {
            float f2 = (this.bearing - f) * -1.0f;
            RotateAnimation rotateAnimation = new RotateAnimation(-this.currentAzimuth, -f2, 1, 0.5f, 1, 0.5f);
            this.currentAzimuth = f2;
            rotateAnimation.setDuration(500);
            rotateAnimation.setRepeatCount(0);
            rotateAnimation.setFillAfter(true);
            this.arrowView.startAnimation(rotateAnimation);
            showDirection(f2);
        } else if (this.rel_compass.getVisibility() == View.VISIBLE) {
            this.rel_compass.setVisibility(View.GONE);
        }
    }

    private void showDirection(float f) {
        int i = 0;
        int i2 = (f > 338.0f ? 1 : (f == 338.0f ? 0 : -1));
        String str = (i2 >= 0 || f < 23.0f) ? "N" : (i < 0 || f >= 68.0f) ? (f < 68.0f || f >= 113.0f) ? (f < 113.0f || f >= 158.0f) ? (f < 158.0f || f >= 203.0f) ? (f < 203.0f || f >= 248.0f) ? (f < 248.0f || f >= 293.0f) ? (f < 293.0f || i2 >= 0) ? "" : "NW" : ExifInterface.LONGITUDE_WEST : "SW" : ExifInterface.LATITUDE_SOUTH : "SE" : ExifInterface.LONGITUDE_EAST : "NE";
        TextView textView = this.mtv_degree;
        textView.setText(Math.round(f) + "° " + str);
        SavePreferences("Compasss_Data", textView.getText().toString());
        try {
            this.smap_tv_compass.setText(showPreferences("Compasss_Data"));
        }catch (Exception e){

        }
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = getSharedPreferences(str, 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public void SavePreferencesInt(String str, int i) {
        SharedPreferences.Editor edit = getSharedPreferences(str, 0).edit();
        edit.putInt(str, 0);
        edit.commit();
    }


    private String showPreferences(String str) {
        return getSharedPreferences(str, 0).getString(str, "");
    }

    private String showPreferencesString(String str) {
        return getSharedPreferences(str, 0).getString(str, "");
    }


    public String getAllCity() throws ParseException {
        try {
            return new Geocoder(this, Locale.getDefault()).getFromLocation(Double.parseDouble(this.latitude111), Double.parseDouble(this.longitude111), 1).get(0).getLocality();
        } catch (IOException | NullPointerException unused) {
            return "mumbai";
        }
    }

    private void getLocationLive() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            Location lastKnownLocation = this.locationManager.getLastKnownLocation("gps");
            if (lastKnownLocation != null) {
                SavePreferences("Current_Accuracy", String.valueOf(lastKnownLocation.getAccuracy()));
                System.out.println("accuurancy::" + String.valueOf(lastKnownLocation.getAccuracy()));
                this.lat = lastKnownLocation.getLatitude();
                this.longi = lastKnownLocation.getLongitude();
                this.latitude111 = String.valueOf(this.lat);
                this.longitude111 = String.valueOf(this.longi);
                SavePreferences("Current_Latitude", this.latitude111);
                SavePreferences("Current_Longitude", this.longitude111);
                return;
            }
            String latitudeMethod = CommonCoordinates.getLatitudeMethod(1, 8, this);
            String longitudeMethod = CommonCoordinates.getLongitudeMethod(1, 8, this);
            SavePreferences("Current_Latitude", latitudeMethod);
            SavePreferences("Current_Longitude", longitudeMethod);
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, HttpStatus.SC_SWITCHING_PROTOCOLS);
    }

    public void MagField(float f) {
        String str = f + " μT";
        SavePreferences("Current_MagField", str);
        this.str_magnaticField = str;
        this.mSP.setString(this, C1281SP.MAGNETIC_FIELD_VALUE, str);
        TextView textView = this.smap_txt_magnetic;
        if (textView != null) {
            textView.setText(this.str_magnaticField);
        }
    }

    public Bitmap setTemp1(Bitmap bitmap, boolean z) {
        try {
            this.createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            Canvas canvas = new Canvas(this.createBitmap);
            Paint paint = null;
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            Bitmap drawingCache = this.lin_stamp.getDrawingCache();
            if (this.mSP.getString(this, "pos_type_temp1", "Bottom").equals("Top")) {
                Paint paint2 = null;
                canvas.drawBitmap(drawingCache, 0.0f, 0.0f, (Paint) null);
            } else {
                Paint paint3 = null;
                canvas.drawBitmap(drawingCache, 0.0f, (float) (bitmap.getHeight() - drawingCache.getHeight()), (Paint) null);
            }
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.createBitmap;
    }

    public Bitmap setTemp2(Bitmap bitmap, boolean z) {
        try {
            this.createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            Canvas canvas = new Canvas(this.createBitmap);
            Paint paint = null;
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            Bitmap drawingCache = this.lin_stamp2.getDrawingCache();
            if (this.mSP.getString(this, "pos_type_temp2", "Bottom").equals("Top")) {
                Paint paint2 = null;
                canvas.drawBitmap(drawingCache, 0.0f, 0.0f, (Paint) null);
            } else {
                Paint paint3 = null;
                canvas.drawBitmap(drawingCache, 0.0f, (float) (bitmap.getHeight() - drawingCache.getHeight()), (Paint) null);
            }
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.createBitmap;
    }

    public void ClassicTemplate(View view) {
        this.lin_stamp = view.findViewById(R.id.lin_stamp);
        this.li_addresss = view.findViewById(R.id.li_addresss);
        this.ly_datetime = view.findViewById(R.id.ly_datetime);
        this.lnDate = view.findViewById(R.id.ln_date);
        this.lnTimer = view.findViewById(R.id.ln_timer);
        this.lagitude = view.findViewById(R.id.lagitude);
        this.smap_Latitude_name = view.findViewById(R.id.Latitude_name);
        this.smap_latitude_tv = view.findViewById(R.id.latitude_tv);
        this.smap_time_tv = view.findViewById(R.id.time_tv);
        this.smap_time_local_tv = view.findViewById(R.id.time_local_tv);
        this.date_tv = view.findViewById(R.id.date_tv);
        this.lin_stamp = view.findViewById(R.id.lin_stamp);
        this.ly_numbering = view.findViewById(R.id.ly_numbering);
        this.smap_tv_compass = view.findViewById(R.id.tv_compass);
        this.smap_address_tv = view.findViewById(R.id.address_tv);
        this.smap_hastag = view.findViewById(R.id.hastag);
        this.ly_Note = view.findViewById(R.id.ly_Note);
        this.smap_txt_numbering = view.findViewById(R.id.txt_numbering);
        this.imgLogo = view.findViewById(R.id.imgLogo);
        this.lin_timezone = view.findViewById(R.id.lin_timezone);
        this.lin_numbering = view.findViewById(R.id.lin_numbering);
        this.smap_txt_magnetic = view.findViewById(R.id.smap_txt_magnetic);
        this.li_magnetic_field = view.findViewById(R.id.li_magnetic_field);
        this.li_compass = view.findViewById(R.id.li_compass);
        new HelperClass(this);

        this.humidity = showPreferencesString("humidity_Temp2");
        this.presurre = showPreferencesString("pressure_Temp2");
        this.magnetic = showPreferencesString("magnetic_Temp2");
        this.compass1 = showPreferencesString("compass_Temp2");
        this.note = showPreferencesString("hashtag_Temp2");
        this.numbering1 = showPreferencesString("number_Temp2");
        this.Address = showPreferencesString("Address_Temp2");
        this.DateTime = showPreferencesString("DateTime_Temp2");

        this.lagitud = showPreferencesString("laglog_Temp2");
        this.Timezone = showPreferencesString("timezone_Temp2");
        this.Logo = showPreferencesString("logo_Temp2");

        this.smap_time_tv.setText(getTimeZoneNew());

        ((ImageView)view.findViewById(R.id.img_icon_note)).setImageResource(R.drawable.ic_note_1);
        ((ImageView)view.findViewById(R.id.img_icon_number)).setImageResource(R.drawable.ic_gallery_number_1);
        ((ImageView)view.findViewById(R.id.img_icon_date_time)).setImageResource(R.drawable.ic_date_time_1);
        ((ImageView)view.findViewById(R.id.img_icon_time)).setImageResource(R.drawable.ic_clock_circle_time_1);
        ((ImageView)view.findViewById(R.id.img_icon_lat_longi)).setImageResource(R.drawable.ic_lat_longi_1);
        ((ImageView)view.findViewById(R.id.img_icon_address)).setImageResource(R.drawable.ic_point_map_1);
        ((ImageView)view.findViewById(R.id.img_icon_compass)).setImageResource(R.drawable.ic_compass_1);
        ((ImageView)view.findViewById(R.id.img_icon_magnetic)).setImageResource(R.drawable.ic_magnetic_field_1);

        Typeface typeface = HelperClass.getFontStyle(this, showPreferences("Fonttype"));
        TextView view1 = view.findViewById(R.id.txt_watermark);
        view1.setTypeface(typeface);
        TextView view2 = view.findViewById(R.id.hastag);
        view2.setTypeface(typeface);
        TextView view3 = view.findViewById(R.id.txt_numbering);
        view3.setTypeface(typeface);
        TextView view4 = view.findViewById(R.id.date_tv);
        view4.setTypeface(typeface);
        TextView view5 = view.findViewById(R.id.time_tv);
        view5.setTypeface(typeface);
        TextView view6 = view.findViewById(R.id.Longitude_name);
        view6.setTypeface(typeface);
        TextView view7 = view.findViewById(R.id.Latitude_name);
        view7.setTypeface(typeface);
        TextView view8 = view.findViewById(R.id.longitude_tv);
        view8.setTypeface(typeface);
        TextView view9 = view.findViewById(R.id.latitude_tv);
        view9.setTypeface(typeface);
        TextView view10 = view.findViewById(R.id.address_tv);
        view10.setTypeface(typeface);
        TextView view11 = view.findViewById(R.id.tv_compass);
        view11.setTypeface(typeface);
        TextView view12 = view.findViewById(R.id.smap_txt_magnetic);
        view12.setTypeface(typeface);
        TextView view13 = view.findViewById(R.id.txt_title_addr);
        view13.setTypeface(typeface);

        if (this.DateTime.equals("Yes")) {
            this.lnDate.setVisibility(View.VISIBLE);
        } else {
            this.lnDate.setVisibility(View.INVISIBLE);
        }

        if (this.Address.equals("Yes")) {
            this.li_addresss.setVisibility(View.VISIBLE);
            this.smap_address_tv.setVisibility(View.VISIBLE);
        } else {
            this.li_addresss.setVisibility(View.INVISIBLE);
            this.smap_address_tv.setVisibility(View.INVISIBLE);
        }

        if (this.lagitud.equals("Yes")) {
            this.lagitude.setVisibility(View.VISIBLE);
            this.smap_Latitude_name.setVisibility(View.VISIBLE);
            this.smap_latitude_tv.setVisibility(View.VISIBLE);
        } else {
            this.smap_Latitude_name.setVisibility(View.INVISIBLE);
            this.lagitude.setVisibility(View.INVISIBLE);
            this.smap_latitude_tv.setVisibility(View.INVISIBLE);
        }
        if (this.Timezone.equals("Yes")) {
            this.lnTimer.setVisibility(View.VISIBLE);
        } else {
            this.lnTimer.setVisibility(View.INVISIBLE);
        }
        if (this.Logo.equals("Yes")) {
            this.imgLogo.setVisibility(View.VISIBLE);
        } else {
            this.imgLogo.setVisibility(View.INVISIBLE);
        }

        if (this.magnetic.equals("Yes")) {
            this.li_magnetic_field.setVisibility(View.VISIBLE);
        } else {
            this.li_magnetic_field.setVisibility(View.INVISIBLE);
        }
        if (this.compass1.equals("Yes")) {
            this.li_compass.setVisibility(View.VISIBLE);
        } else {
            this.li_compass.setVisibility(View.INVISIBLE);
        }

        if (this.note.equals("Yes")) {
            this.ly_Note.setVisibility(View.VISIBLE);
        } else {
            this.ly_Note.setVisibility(View.INVISIBLE);
        }
        if (this.numbering1.equals("Yes")) {
            this.ly_numbering.setVisibility(View.VISIBLE);
        } else {
            this.ly_numbering.setVisibility(View.INVISIBLE);
        }
        if (!this.util.showPreferences("key").equals("")) {
            this.smap_hastag.setText(this.util.showPreferences("key"));
        }

        this.smap_Latitude_name.setText(showPreferences("Current_Latitude"));
        this.smap_latitude_tv.setText(showPreferences("Current_Longitude"));
        String string = this.mSP.getString(this, "address", "");
        System.out.println("adddd::" + string);
        this.smap_address_tv.setText(string);
        if (!showPreferences("Compasss_Data").equals("")) this.smap_tv_compass.setText(showPreferences("Compasss_Data"));
        this.smap_time_local_tv.setText(getLocalTime());
        this.date_tv.setText(getdate());
        if (!showPreferences("Current_MagField").equals(""))  this.smap_txt_magnetic.setText(showPreferences("Current_MagField"));
        if (!showPreferences("Numbering").equals("")){
            this.smap_txt_numbering.setText(showPreferences("Numbering"));
        }else {
            this.smap_txt_numbering.setText("0");
        }
        GpsCameraActivity.this.SavePreferences("Numbering", String.valueOf(Utils.countImagesInFolder()));
        this.smap_txt_numbering.setText(String.valueOf(Utils.countImagesInFolder()));
    }

    @SuppressLint("CutPasteId")
    public void AdvanceTemplate(View view) {
        this.lin_stamp = view.findViewById(R.id.lin_stamp);
        this.li_addresss = view.findViewById(R.id.li_addresss);
        this.ly_datetime = view.findViewById(R.id.ly_datetime);
        this.lnDate = view.findViewById(R.id.ln_date);
        this.lnTimer = view.findViewById(R.id.ln_timer);
        this.lagitude = view.findViewById(R.id.lagitude);
        this.smap_Latitude_name = view.findViewById(R.id.Latitude_name);
        this.smap_latitude_tv = view.findViewById(R.id.latitude_tv);
        this.smap_time_tv = view.findViewById(R.id.time_tv);
        this.smap_time_local_tv = view.findViewById(R.id.time_local_tv);
        this.date_tv = view.findViewById(R.id.date_tv);
        this.lin_stamp = view.findViewById(R.id.lin_stamp);
        this.ly_numbering = view.findViewById(R.id.ly_numbering);
        this.smap_tv_compass = view.findViewById(R.id.tv_compass);
        this.smap_address_tv = view.findViewById(R.id.address_tv);
        this.smap_hastag = view.findViewById(R.id.hastag);
        this.ly_Note = view.findViewById(R.id.ly_Note);
        this.smap_txt_numbering = view.findViewById(R.id.txt_numbering);
        this.imgLogo = view.findViewById(R.id.imgLogo);
        this.lin_timezone = view.findViewById(R.id.lin_timezone);
        this.lin_numbering = view.findViewById(R.id.lin_numbering);
        this.smap_txt_magnetic = view.findViewById(R.id.smap_txt_magnetic);
        this.li_magnetic_field = view.findViewById(R.id.li_magnetic_field);
        this.li_compass = view.findViewById(R.id.li_compass);
        new HelperClass(this);

        this.smap_txt_magnetic.setTextColor(getResources().getColor(R.color.white));
        ((ImageView)view.findViewById(R.id.img_icon_note)).setImageResource(R.drawable.ic_note_);
        ((ImageView)view.findViewById(R.id.img_icon_number)).setImageResource(R.drawable.ic_gallery_number);
        ((ImageView)view.findViewById(R.id.img_icon_date_time)).setImageResource(R.drawable.ic_date_time);
        ((ImageView)view.findViewById(R.id.img_icon_time)).setImageResource(R.drawable.ic_clock_circle_time);
        ((ImageView)view.findViewById(R.id.img_icon_lat_longi)).setImageResource(R.drawable.ic_lat_longi);
        ((ImageView)view.findViewById(R.id.img_icon_address)).setImageResource(R.drawable.ic_point_map);
        ((ImageView)view.findViewById(R.id.img_icon_compass)).setImageResource(R.drawable.ic_compass);
        ((ImageView)view.findViewById(R.id.img_icon_magnetic)).setImageResource(R.drawable.ic_magnetic_field);

        this.humidity = showPreferencesString("humidity_Temp0");
        this.presurre = showPreferencesString("pressure_Temp0");
        this.magnetic = showPreferencesString("magnetic_Temp0");
        this.compass1 = showPreferencesString("compass_Temp0");
        this.note = showPreferencesString("hashtag_Temp0");
        this.numbering1 = showPreferencesString("number_Temp0");
        this.Address = showPreferencesString("Address_Temp0");
        this.DateTime = showPreferencesString("DateTime_Temp0");
        this.lagitud = showPreferencesString("laglog_Temp0");
        this.Timezone = showPreferencesString("timezone_Temp0");
        this.Logo = showPreferencesString("logo_Temp0");

        this.smap_time_tv.setText(getTimeZoneNew());
        Typeface typeface = HelperClass.getFontStyle(this, showPreferences("Fonttype"));
        TextView view1 = view.findViewById(R.id.txt_watermark);
        view1.setTypeface(typeface);
        TextView view2 = view.findViewById(R.id.hastag);
        view2.setTypeface(typeface);
        TextView view3 = view.findViewById(R.id.txt_numbering);
        view3.setTypeface(typeface);
        TextView view4 = view.findViewById(R.id.date_tv);
        view4.setTypeface(typeface);
        TextView view5 = view.findViewById(R.id.time_tv);
        view5.setTypeface(typeface);
        TextView view6 = view.findViewById(R.id.Longitude_name);
        view6.setTypeface(typeface);
        TextView view7 = view.findViewById(R.id.Latitude_name);
        view7.setTypeface(typeface);
        TextView view8 = view.findViewById(R.id.longitude_tv);
        view8.setTypeface(typeface);
        TextView view9 = view.findViewById(R.id.latitude_tv);
        view9.setTypeface(typeface);
        TextView view10 = view.findViewById(R.id.address_tv);
        view10.setTypeface(typeface);
        TextView view11 = view.findViewById(R.id.tv_compass);
        view11.setTypeface(typeface);
        TextView view12 = view.findViewById(R.id.smap_txt_magnetic);
        view12.setTypeface(typeface);
        TextView view13 = view.findViewById(R.id.txt_title_addr);
        view13.setTypeface(typeface);

        if (this.DateTime.equals("Yes")) {
            this.lnDate.setVisibility(View.VISIBLE);
        } else {
            this.lnDate.setVisibility(View.INVISIBLE);
        }
        if (this.Address.equals("Yes")) {
            this.li_addresss.setVisibility(View.VISIBLE);
            this.smap_address_tv.setVisibility(View.VISIBLE);
        } else {
            this.li_addresss.setVisibility(View.INVISIBLE);
            this.smap_address_tv.setVisibility(View.INVISIBLE);
        }
        if (this.lagitud.equals("Yes")) {
            this.lagitude.setVisibility(View.VISIBLE);
            this.smap_Latitude_name.setVisibility(View.VISIBLE);
            this.smap_latitude_tv.setVisibility(View.VISIBLE);
        } else {
            this.smap_Latitude_name.setVisibility(View.INVISIBLE);
            this.lagitude.setVisibility(View.INVISIBLE);
            this.smap_latitude_tv.setVisibility(View.INVISIBLE);
        }
        if (this.Timezone.equals("Yes")) {
            this.lnTimer.setVisibility(View.VISIBLE);
        } else {
            this.lnTimer.setVisibility(View.INVISIBLE);
        }
        if (this.Logo.equals("Yes")) {
            this.imgLogo.setVisibility(View.VISIBLE);
        } else {
            this.imgLogo.setVisibility(View.INVISIBLE);
        }

        if (this.magnetic.equals("Yes")) {
            this.li_magnetic_field.setVisibility(View.VISIBLE);
        } else {
            this.li_magnetic_field.setVisibility(View.INVISIBLE);
        }
        if (this.compass1.equals("Yes")) {
            this.li_compass.setVisibility(View.VISIBLE);
        } else {
            this.li_compass.setVisibility(View.INVISIBLE);
        }

        if (this.note.equals("Yes")) {
            this.ly_Note.setVisibility(View.VISIBLE);
        } else {
            this.ly_Note.setVisibility(View.INVISIBLE);
        }
        if (this.numbering1.equals("Yes")) {
            this.ly_numbering.setVisibility(View.VISIBLE);
        } else {
            this.ly_numbering.setVisibility(View.INVISIBLE);
        }
        if (!this.util.showPreferences("key").equals("")) {
            this.smap_hastag.setText(this.util.showPreferences("key"));
        }

        this.smap_Latitude_name.setText(showPreferences("Current_Latitude"));
        this.smap_latitude_tv.setText(showPreferences("Current_Longitude"));
        String string = this.mSP.getString(this, "address", "");
        System.out.println("adddd::" + string);
        this.smap_address_tv.setText(string);

        if (!showPreferences("Compasss_Data").equals("")) this.smap_tv_compass.setText(showPreferences("Compasss_Data"));
        this.smap_time_local_tv.setText(getLocalTime());
        this.date_tv.setText(getdate());
        if (!showPreferences("Current_MagField").equals("")) this.smap_txt_magnetic.setText(showPreferences("Current_MagField"));
        if (!showPreferences("Numbering").equals("")){
            this.smap_txt_numbering.setText(showPreferences("Numbering"));
        }else {
            this.smap_txt_numbering.setText("0");
        }
        GpsCameraActivity.this.SavePreferences("Numbering", String.valueOf(Utils.countImagesInFolder()));
        this.smap_txt_numbering.setText(String.valueOf(Utils.countImagesInFolder()));
    }


    public void activateReviewInfo() {
        ReviewManager create = ReviewManagerFactory.create(this);
        this.reviewManager = create;

        create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(@NonNull com.google.android.play.core.tasks.Task<ReviewInfo> task) {
                mo16124x951567bf(task);
            }

        });
    }


    public void mo16124x951567bf(com.google.android.play.core.tasks.Task task) {
        if (task.isSuccessful()) {
            ReviewInfo reviewInfo = (ReviewInfo) task.getResult();
        }
    }

    public void showRateApp() {

        this.reviewManager.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(@NonNull com.google.android.play.core.tasks.Task<ReviewInfo> task) {
                mo16125x4aa2492(task);
            }
        });
    }


    public void mo16125x4aa2492(com.google.android.play.core.tasks.Task task) {
        if (task.isSuccessful()) {

            this.reviewManager.launchReviewFlow(this, (ReviewInfo) task.getResult()).addOnCompleteListener(task1 -> GpsCameraActivity.lambda$showRateApp$1());
            return;
        }
        activateReviewInfo();
    }

    private String getTimeZoneNew() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.mSP.getString(this, KeysConstants.TIME_POSITION0, TimeAdapter.TIME_FORMAT_1));
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        return simpleDateFormat.format(instance.getTime());
    }

}
