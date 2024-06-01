package com.gps.camera.timestamp.photo.geotag.map.adapter;

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

public class TempratureAdapeterCamera extends RecyclerView.Adapter<TempratureAdapeterCamera.Holder> {
    Context mContext;
    String[] mList;
    OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    C1281SP mSP;
    float mfTemprature_value;
    String selected_pos;

    public TempratureAdapeterCamera(Context context, String[] strArr, float f, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mContext = context;
        this.mList = strArr;
        this.mfTemprature_value = f;
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
        C1281SP sp = new C1281SP(context);
        this.mSP = sp;
        this.selected_pos = sp.getString(context, "temprature_type_temp", "Celsius");
    }

    @Override 
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_cell_date_time_adapter, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, int i) {
        if (i == 0) {
            holder.tv_dateFormat.setText(HelperClass.getCelcius(this.mfTemprature_value) + "");
        } else {
            holder.tv_dateFormat.setText(HelperClass.getFahrenheit(this.mfTemprature_value) + "");
        }
        if (this.selected_pos.equals(this.mList[i])) {
            holder.img_selection.setVisibility(View.VISIBLE);
        } else {
            holder.img_selection.setVisibility(View.GONE);
        }
    }

    @Override 
    public int getItemCount() {
        return this.mList.length;
    }

    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout date_main_lay;
        ImageView img_selection;
        TextView tv_dateFormat;

        public Holder(View view) {
            super(view);
            this.tv_dateFormat = (TextView) view.findViewById(R.id.tv_temp_name);
            this.date_main_lay = (LinearLayout) view.findViewById(R.id.dt_main_lay);
            this.img_selection = (ImageView) view.findViewById(R.id.img_selection);
            this.date_main_lay.setOnClickListener(new TempratureAdapeteractCamera(this));
        }

        
        
        public  void mo16308xdb0dfa93(View view) {
            if (getAdapterPosition() >= 0 && TempratureAdapeterCamera.this.mOnRecyclerItemClickListener != null) {
                TempratureAdapeterCamera.this.mOnRecyclerItemClickListener.OnClick_(getAdapterPosition(), view);
            }
        }
    }
}
