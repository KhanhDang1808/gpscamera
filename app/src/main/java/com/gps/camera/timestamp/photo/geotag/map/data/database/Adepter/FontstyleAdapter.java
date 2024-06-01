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

public class FontstyleAdapter extends RecyclerView.Adapter<FontstyleAdapter.Holder> {
    String[] fontstyle;
    Context mContext;
    String[] mList;
    OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    C1281SP mSP;
    String selected_pos;

    private String showPreferences(String str) {
        return this.mContext.getSharedPreferences(str, 0).getString(str, "Images");
    }

    private String data;
    public FontstyleAdapter(Context context,String data, int i, String[] strArr, String[] strArr2, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mContext = context;
        this.mSP = new C1281SP(context);
        this.mList = strArr;
        this.fontstyle = strArr2;
        this.data = data;
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
        if (i == 0) {
            this.selected_pos = data;
        } else {
            this.selected_pos = data;
        }
    }

    @Override 
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_cell_date_time_adapter, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, int i) {
        holder.tv_dateFormat.setText(this.mList[i]);
        holder.tv_dateFormat.setTypeface(HelperClass.getFontStyle(this.mContext, this.fontstyle[i]));
        if (this.fontstyle[i].equals(this.selected_pos)) {
            holder.img_selection.setVisibility(View.VISIBLE);
        } else {
            holder.img_selection.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new FontstyleAdapterec(this, holder));
    }

    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout date_main_lay;
        ImageView img_selection;
        TextView tv_dateFormat;

        public Holder(View view) {
            super(view);
            this.tv_dateFormat =  view.findViewById(R.id.tv_temp_name);
            this.date_main_lay = view.findViewById(R.id.dt_main_lay);
            this.img_selection =  view.findViewById(R.id.img_selection);
        }
    }
    
    public  void mo16860xaeb26d8f(Holder holder, View view) {
        OnRecyclerItemClickListener onRecyclerItemClickListener;
        if (holder.getAdapterPosition() >= 0 && (onRecyclerItemClickListener = this.mOnRecyclerItemClickListener) != null) {
            onRecyclerItemClickListener.OnClick_(holder.getAdapterPosition(), view);
        }
    }

    @Override
    public int getItemCount() {
        return this.mList.length;
    }


}
