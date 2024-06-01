package com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.HelperClass;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Interface.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;

public class LogoAdapterGps extends RecyclerView.Adapter<LogoAdapterGps.Holder> {
    Context mContext;
    public ArrayList<String> mList;
    OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public LogoAdapterGps(Context context, ArrayList<String> arrayList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        new ArrayList();
        this.mContext = context;
        this.mList = arrayList;
        Collections.reverse(arrayList);
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override 
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_logo_adapter, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, int i) {
        Glide.with(this.mContext).load(HelperClass.decodeBase64(this.mList.get(i))).into(holder.img);
    }

    @Override 
    public int getItemCount() {
        return this.mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        LinearLayout lin_img_click;

        public Holder(View view) {
            super(view);
            this.lin_img_click = (LinearLayout) view.findViewById(R.id.lin_img_click);
            this.img = (ImageView) view.findViewById(R.id.img);
            this.lin_img_click.setOnClickListener(new CAR_Logo_Adapter$Holdersy(this));
            this.lin_img_click.setOnLongClickListener(new CAR_Logo_Adapter$Holderff(this));
        }
        public  boolean mo16851x403fc14e(View view) {
            if (LogoAdapterGps.this.mOnRecyclerItemClickListener == null) {
                return true;
            }
            LogoAdapterGps.this.mOnRecyclerItemClickListener.OnLongClick_(getAdapterPosition(), view);
            return true;
        }
        public void mo16850xd610392f(View view) {
            if (getAdapterPosition() >= 0 && LogoAdapterGps.this.mOnRecyclerItemClickListener != null) {
                LogoAdapterGps.this.mOnRecyclerItemClickListener.OnClick_(getAdapterPosition(), view);
            }
        }


    }
}
