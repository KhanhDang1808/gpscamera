package com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.HelperClass;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Interface.OnRecyclerItemClickListener;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Model.LoctionModel;
import com.gps.camera.timestamp.photo.geotag.map.data.database.p006UI.SingleClickListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class LocationAdapterGps extends RecyclerView.Adapter<LocationAdapterGps.Holder> {
    Activity mContext;
    ArrayList<LoctionModel> mList;
    OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    C1281SP mSP = new C1281SP(this.mContext);

    public LocationAdapterGps(Activity activity, ArrayList<LoctionModel> arrayList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        new ArrayList();
        this.mContext = activity;
        this.mList = arrayList;
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override 
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_loction_adapter, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, int i) {
        LoctionModel loctionModel = this.mList.get(i);
        holder.initializeMapView(i, loctionModel);
        holder.tv_title.setText(loctionModel.getTitle());
        holder.tv_latlog.setText(HelperClass.LatLngLocation(loctionModel.getLatitude(), loctionModel.getLongitude()));
        holder.tv_address.setText(loctionModel.getLoc_line_1());
        if (loctionModel.getSelection() == 1) {
            holder.tv_title.setTextColor(this.mContext.getResources().getColor(R.color._ffcc00));
        } else {
            holder.tv_title.setTextColor(this.mContext.getResources().getColor(R.color._3D3D3D));
        }
    }

    @Override 
    public int getItemCount() {
        return this.mList.size();
    }

    public void refreceadpter(ArrayList<LoctionModel> arrayList) {
        this.mList = arrayList;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        boolean isMapReady = false;
        LinearLayout lin_main;
        LoctionModel loctionModel;
        GoogleMap map;
        MapView map_view;
        int position;
        TextView tv_address;
        TextView tv_latlog;
        TextView tv_title;

        public Holder(View view) {
            super(view);
            this.lin_main = (LinearLayout) view.findViewById(R.id.lin_main);
            this.tv_title = (TextView) view.findViewById(R.id.txt_title);
            this.tv_address = (TextView) view.findViewById(R.id.txt_address);
            this.tv_latlog = (TextView) view.findViewById(R.id.txt_latlog);
            this.map_view = (MapView) view.findViewById(R.id.map);
            this.lin_main.setOnClickListener(new SingleClickListener(LocationAdapterGps.this, LocationAdapterGps.this) {

                @Override 
                public void performClick(View view) {
                    if (Holder.this.getAdapterPosition() >= 0 && view != null && LocationAdapterGps.this.mOnRecyclerItemClickListener != null) {
                        LocationAdapterGps.this.mOnRecyclerItemClickListener.OnClick_(Holder.this.getAdapterPosition(), view);
                    }
                }
            });
            this.lin_main.setOnLongClickListener(new CAR_LoctionAdapter$Holderex(this));
        }

        
        
        public  boolean mo16844xaae8c8ef(View view) {
            if (!(getAdapterPosition() < 0 || view == null || LocationAdapterGps.this.mOnRecyclerItemClickListener == null)) {
                LocationAdapterGps.this.mOnRecyclerItemClickListener.OnLongClick_(getAdapterPosition(), view);
            }
            return false;
        }

        @Override 
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(LocationAdapterGps.this.mContext);
            this.map = googleMap;
            this.isMapReady = true;
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            if (this.map_view.getViewTreeObserver().isAlive()) {
                this.map_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    

                    public void onGlobalLayout() {
                        Holder.this.map_view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
            try {
                setMapData(new LatLng(Double.parseDouble(this.loctionModel.getLatitude()), Double.parseDouble(this.loctionModel.getLongitude())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setMapData(LatLng latLng) {
            GoogleMap googleMap = this.map;
            if (googleMap != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                this.map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
                this.map.addMarker(new MarkerOptions().position(latLng));
                this.map.setMapType(1);
                return;
            }
            Log.e("Map", "Map null");
        }

        public void initializeMapView(int i, LoctionModel loctionModel2) {
            try {
                this.position = i;
                this.loctionModel = loctionModel2;
                MapView mapView = this.map_view;
                if (mapView != null) {
                    mapView.onCreate(null);
                    this.map_view.onResume();
                    this.map_view.getMapAsync(this);
                    return;
                }
                Log.e("Map", "Mapview null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
