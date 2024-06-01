package com.gps.camera.timestamp.photo.geotag.map.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gps.camera.timestamp.photo.geotag.map.adapter.TempratureAdapeterCamera;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.Default;
import com.gps.camera.timestamp.photo.geotag.map.camera.HelperClass;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter.CoordinatesAdapterGps;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter.FontstyleAdapter;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter.MapAdapter;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter.Stampposition_Adapter;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Interface.OnRecyclerItemClickListener;
import com.gps.camera.timestamp.photo.geotag.map.utils.view.MarqueeTextView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MapTypeBottomSheetfragment extends BottomSheetDialogFragment {
    public static final String TAG = "MapTypeBottomSheetfragment";
    String[] Temprature;
    TextView address_tv;
    OnMapTypeSelectedListener callBack;
    Context context;
    TextView date_tv;
    String[] fontstyle;
    String[] fontstyleList;
    private CoordinatesAdapterGps gpsCoordinatesAdapter;
    TextView hastag;
    TextView latitude_name;
    TextView latitude_tv;
    TextView longitude_name;
    TextView longitude_tv;
    private MapAdapter mAdapter;
    String[] mGPS_Coordinates_array;
    TypedArray mIconArray;
    String[] mMapArray;
    private C1281SP mSP;
    String[] pressure;
    RecyclerView rv_maptype;
    TextView smap_txt_magnetic;
    Stampposition_Adapter stampposition_adapter;
    String[] stamppostion;
    String templateName;
    TempratureAdapeterCamera tempratureAdapeter;
    TextView time_local_tv;
    TextView time_tv;
    TextView tt_datetime;

    TextView tt_lot_log;
    TextView tt_numbering;

    TextView tt_timezone;
    TextView tv_address;
    TextView tv_compass;
    TextView tv_weather;
    TextView txt_accuracy;
    TextView txt_accuracy1;
    TextView txt_address;
    MarqueeTextView txt_altitude;
    TextView txt_altitude1;
    TextView txt_datetime;
    TextView txt_humidity;
    TextView txt_latlog;
    MarqueeTextView txt_numbering;
    TextView txt_numbering1;
    TextView txt_pressure;
    TextView txt_stampposition;
    TextView txt_timezone;
    TextView txt_title;
    ImageView btnClose;
    TextView txt_titleStamp;
    TextView txt_watermark;
    TextView txt_watermark_2;
    TextView txt_wind;
    int type = 0;

    private String strPos = "Top";

    public interface OnMapTypeSelectedListener {
        void OnFontselected();

        void OnStampPositionchange();

        void Onaccuracyselect();

        void Onaltitudeselect();

        void Onpressureselection();

        void Ontempraturechange();

        void Onwindselected();

        void onLatLngType();

        void onMapTypeSelected();
    }


    public MapTypeBottomSheetfragment(String str) {
        this.templateName = str;
    }

    public MapTypeBottomSheetfragment(MarqueeTextView textView, MarqueeTextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7, TextView textView8, TextView textView9, TextView textView10, TextView textView11, TextView textView12, TextView textView13, TextView textView14, TextView textView15, TextView textView16, TextView textView17, TextView textView18, TextView textView19, TextView textView20, TextView textView21, TextView textView22, TextView textView23, TextView textView24, TextView textView25, TextView textView26, TextView textView27, TextView textView28, TextView textView29, TextView textView30, TextView textView31, TextView textView32, TextView textView33) {
        this.txt_numbering = textView;
        this.txt_altitude = textView2;
        this.txt_accuracy = textView3;
        this.txt_watermark = textView4;
        this.address_tv = textView5;
        this.txt_numbering1 = textView6;
        this.longitude_name = textView7;
        this.latitude_name = textView8;
        this.hastag = textView9;
        this.longitude_tv = textView10;
        this.latitude_tv = textView11;
        this.date_tv = textView12;
        this.time_tv = textView13;
        this.time_local_tv = textView14;
        this.txt_wind = textView15;
        this.txt_humidity = textView16;
        this.txt_altitude1 = textView17;
        this.txt_pressure = textView18;
        this.txt_accuracy1 = textView19;
        this.tv_weather = textView20;
        this.tv_compass = textView21;
        this.smap_txt_magnetic = textView22;
        this.txt_watermark_2 = textView23;
        this.tv_address = textView24;
        this.txt_address = textView25;
        this.tt_lot_log = textView26;
        this.txt_latlog = textView27;
        this.tt_datetime = textView28;
        this.txt_datetime = textView29;
        this.tt_timezone = textView30;
        this.txt_timezone = textView31;
        this.tt_numbering = textView32;
        this.txt_stampposition = textView33;
    }

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSP = new C1281SP(getActivity());
        if (getArguments() != null) {
            this.type = getArguments().getInt("type");
            this.data = getArguments().getString("data");
            this.strPos = getArguments().getString("strStamp");
        }
    }


    @SuppressLint("RestrictedApi")
    @Override 
    public void setupDialog(Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.car_map_type_bottom_sheet_fragment, null);
        this.rv_maptype = inflate.findViewById(R.id.rv_maptype);
        this.txt_title = inflate.findViewById(R.id.txt_title);
        this.btnClose = inflate.findViewById(R.id.btn_close_bottom);
        this.txt_titleStamp = inflate.findViewById(R.id.txt_title1);
        this.context = inflate.getContext();
        dialog.setContentView(inflate);
        btnClose.setOnClickListener(v -> {
            MapTypeBottomSheetfragment.this.dismiss();
        });
        init();
    }

    private void init() {
        int i = this.type;
        if (i == 1) {
            this.mMapArray = getResources().getStringArray(R.array.map_title_array);
            this.mIconArray = getResources().obtainTypedArray(R.array.map_icon_array);
            setAdapter();
        } else if (i == 2) {
            this.mGPS_Coordinates_array = getResources().getStringArray(R.array.gps_coordinates_array);
            setAdapterlatlogitude();
        } else if (i == 3) {
            this.Temprature = getResources().getStringArray(R.array.temprature);
            setAdapterTempratcure();
        } else if (i == 4) {
            this.mSP.setInteger(getActivity(), HelperClass.STAMP_POS_OPEN_TIME, Integer.valueOf(this.mSP.getInteger(getActivity(), HelperClass.STAMP_POS_OPEN_TIME, 0).intValue() + 1));
            this.stamppostion = getResources().getStringArray(R.array.stamp_position);
            txt_titleStamp.setVisibility(View.VISIBLE);
            txt_title.setVisibility(View.GONE);
            setAdapterpositionTemp();
        } else if (i == 9) {
            this.mSP.setInteger(getActivity(), HelperClass.FONT_STYLE_OPEN_TIME, Integer.valueOf(this.mSP.getInteger(getActivity(), HelperClass.FONT_STYLE_OPEN_TIME, 0).intValue() + 1));
            this.fontstyleList = getResources().getStringArray(R.array.fontstyle);
            this.fontstyle = this.context.getResources().getStringArray(R.array.fontstyle_assets);
            txt_titleStamp.setVisibility(View.GONE);
            txt_title.setVisibility(View.VISIBLE);
            Appsetfontstyleadapter();
        }
    }

    public interface ListenerData{
        void listenerData(String i,String data);
        void listenerDataPosition(String i, int position);

    }

    private ListenerData listenerData;
    public void setListener(ListenerData listenerData){
        this.listenerData = listenerData;
    }


    private String data = "";
    private int pos = -1;
    private void Appsetfontstyleadapter() {
        this.rv_maptype.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        this.rv_maptype.setAdapter(new FontstyleAdapter(getActivity(),data, this.mSP.getInteger(getActivity(), "", 0).intValue(), this.fontstyleList, this.fontstyle, new OnRecyclerItemClickListener() {
            @Override 
            public void OnLongClick_(int i, View view) {
            }

            @Override 
            public void OnClick_(int i, View view) {
                pos = i;
                MapTypeBottomSheetfragment mapTypeBottomSheetfragment = MapTypeBottomSheetfragment.this;
                data = mapTypeBottomSheetfragment.fontstyle[i];

                listenerData.listenerData(String.valueOf(pos),data);
                new HelperClass(MapTypeBottomSheetfragment.this.context);
                MapTypeBottomSheetfragment.this.txt_numbering.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_altitude.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_accuracy.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_watermark.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.address_tv.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_numbering.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.longitude_name.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.latitude_name.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.hastag.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.longitude_tv.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.latitude_tv.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.date_tv.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.time_tv.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.time_local_tv.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_wind.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_humidity.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_altitude.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_pressure.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_accuracy.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.tv_weather.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.tv_compass.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.smap_txt_magnetic.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_watermark_2.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.tv_address.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_address.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.tt_lot_log.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_latlog.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.tt_datetime.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_datetime.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.tt_timezone.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.txt_timezone.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.tt_numbering.setTypeface(HelperClass.getFontStyle(MapTypeBottomSheetfragment.this.context, data));
                MapTypeBottomSheetfragment.this.Appsetfontstyleadapter();
                MapTypeBottomSheetfragment.this.dismiss();
            }
        }));
    }
    
    private void setAdapterpositionTemp() {
        this.rv_maptype.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
        Stampposition_Adapter stampposition_Adapter = new Stampposition_Adapter(strPos,getActivity(), this.templateName, this.stamppostion, new OnRecyclerItemClickListener() {
            @Override 
            public void OnLongClick_(int i, View view) {
            }

            @Override 
            public void OnClick_(int i, View view) {
                if (i == 0) {
                    strPos = "Top";
                } else {
                    strPos = "Bottom";
                }
                if (listenerData != null) {
                    listenerData.listenerDataPosition(strPos,i);
                    MapTypeBottomSheetfragment.this.dismiss();
                }else {
                    MapTypeBottomSheetfragment.this.setAdapterpositionTemp();
                    MapTypeBottomSheetfragment.this.dismiss();
                }
            }
        });
        this.stampposition_adapter = stampposition_Adapter;
        this.rv_maptype.setAdapter(stampposition_Adapter);
    }

    private void setAdapterTempratcure() {
        float floatValue = this.mSP.getFloat(getActivity(), C1281SP.TEMPRETURE_VALUE).floatValue();
        this.rv_maptype.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        TempratureAdapeterCamera cAR_TempratureAdapeterCamera = new TempratureAdapeterCamera(getContext(), this.Temprature, floatValue, new OnRecyclerItemClickListener() {
            

            @Override 
            public void OnLongClick_(int i, View view) {
            }

            @Override 
            public void OnClick_(final int i, View view) {
                new Handler().postDelayed(new Runnable() {
                    

                    public void run() {
                        if (i == 0) {
                            MapTypeBottomSheetfragment.this.mSP.setString(MapTypeBottomSheetfragment.this.getActivity(), "temprature_type_temp", "Celsius");
                        } else {
                            MapTypeBottomSheetfragment.this.mSP.setString(MapTypeBottomSheetfragment.this.getActivity(), "temprature_type_temp", "Fahrenheit");
                        }
                        if (MapTypeBottomSheetfragment.this.callBack != null) {
                            MapTypeBottomSheetfragment.this.callBack.Ontempraturechange();
                            MapTypeBottomSheetfragment.this.dismiss();
                        }
                    }
                }, 50);
            }
        });
        this.tempratureAdapeter = cAR_TempratureAdapeterCamera;
        this.rv_maptype.setAdapter(cAR_TempratureAdapeterCamera);
    }

    private void setAdapterlatlogitude() {
        this.rv_maptype.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        CoordinatesAdapterGps cAR_CoordinatesAdapterGps = new CoordinatesAdapterGps(getContext(), this.mGPS_Coordinates_array, new OnRecyclerItemClickListener() {
            

            @Override 
            public void OnLongClick_(int i, View view) {
            }

            @Override 
            public void OnClick_(final int i, View view) {
                MapTypeBottomSheetfragment.this.mSP.setInteger(MapTypeBottomSheetfragment.this.getContext(), "lat_lng_type_temp_1", Integer.valueOf(i));
                new Handler().postDelayed(new Runnable() {
                    

                    public void run() {
                        if (MapTypeBottomSheetfragment.this.gpsCoordinatesAdapter != null) {
                            MapTypeBottomSheetfragment.this.gpsCoordinatesAdapter.refAdapter(i);
                        }
                        if (MapTypeBottomSheetfragment.this.callBack != null) {
                            MapTypeBottomSheetfragment.this.callBack.onLatLngType();
                            MapTypeBottomSheetfragment.this.dismiss();
                        }
                    }
                }, 50);
            }
        });
        this.gpsCoordinatesAdapter = cAR_CoordinatesAdapterGps;
        this.rv_maptype.setAdapter(cAR_CoordinatesAdapterGps);
    }

    private void setAdapter() {
        this.rv_maptype.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        MapAdapter mapAdapter = new MapAdapter(getContext(), this.mMapArray, this.mIconArray, new OnRecyclerItemClickListener() {
            

            @Override 
            public void OnLongClick_(int i, View view) {
            }

            @Override 
            public void OnClick_(final int i, View view) {
                MapTypeBottomSheetfragment.this.mSP.setInteger(MapTypeBottomSheetfragment.this.getContext(), "map_type_temp_pos", Integer.valueOf(i));
                if (i == 0) {
                    MapTypeBottomSheetfragment.this.mSP.setString(MapTypeBottomSheetfragment.this.getContext(), "map_type_temp", Default.NORMAL_1);
                } else if (i == 1) {
                    MapTypeBottomSheetfragment.this.mSP.setString(MapTypeBottomSheetfragment.this.getContext(), "map_type_temp", "");
                } else if (i == 2) {
                    MapTypeBottomSheetfragment.this.mSP.setString(MapTypeBottomSheetfragment.this.getContext(), "map_type_temp", Default.TERRAIN_3);
                } else if (i == 3) {
                    MapTypeBottomSheetfragment.this.mSP.setString(MapTypeBottomSheetfragment.this.getContext(), "map_type_temp", Default.HYBRID_4);
                }
                new Handler().postDelayed(new Runnable() {
                    

                    public void run() {
                        MapTypeBottomSheetfragment.this.mAdapter.refAdapter(i);
                        if (MapTypeBottomSheetfragment.this.callBack != null) {
                            MapTypeBottomSheetfragment.this.callBack.onMapTypeSelected();
                            MapTypeBottomSheetfragment.this.dismiss();
                        }
                    }
                }, 50);
            }
        });
        this.mAdapter = mapAdapter;
        this.rv_maptype.setAdapter(mapAdapter);
    }
}
