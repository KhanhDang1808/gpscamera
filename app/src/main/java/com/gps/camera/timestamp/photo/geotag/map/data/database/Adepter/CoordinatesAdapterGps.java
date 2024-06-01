package com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.HelperClass;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Interface.OnRecyclerItemClickListener;

public class CoordinatesAdapterGps extends RecyclerView.Adapter<CoordinatesAdapterGps.Holder> {
    Context mContext;
    HelperClass mHelperClass = new HelperClass(this.mContext);
    String[] mList;
    OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    C1281SP mSP;
    int selected_pos;

    public CoordinatesAdapterGps(Context context, String[] strArr, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        C1281SP sp = new C1281SP(this.mContext);
        this.mSP = sp;
        this.selected_pos = sp.getInteger(this.mContext, "lat_lng_type_temp_1", 0).intValue();
        this.mContext = context;
        this.mList = strArr;
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override 
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_cell_date_time, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, int i) {
        holder.tv_dateFormat.setText(this.mList[i]);
        holder.txt_lat_log.setText(this.mHelperClass.getLatLong(this.mContext, i));
        if (this.selected_pos == i) {
            holder.img_selection.setVisibility(View.VISIBLE);
        } else {
            holder.img_selection.setVisibility(View.GONE);
        }
    }

    @Override 
    public int getItemCount() {
        return this.mList.length;
    }

    public void refAdapter(int i) {
        this.selected_pos = i;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout date_main_lay;
        ImageView img_selection;
        TextView tv_dateFormat;
        TextView txt_lat_log;

        public Holder(View view) {
            super(view);
            this.tv_dateFormat = (TextView) view.findViewById(R.id.tv_temp_name);
            this.txt_lat_log = (TextView) view.findViewById(R.id.txt_lat_log);
            this.date_main_lay = (LinearLayout) view.findViewById(R.id.dt_main_lay);
            this.img_selection = (ImageView) view.findViewById(R.id.img_selection);
            this.date_main_lay.setOnClickListener(new CAR_CoordinatesAdapterm(this));
        }

        
        
        public  void mo16839x4b8bcf56(View view) {
            if (getAdapterPosition() >= 0 && CoordinatesAdapterGps.this.mOnRecyclerItemClickListener != null) {
                CoordinatesAdapterGps.this.mOnRecyclerItemClickListener.OnClick_(getAdapterPosition(), view);
            }
        }
    }
}
