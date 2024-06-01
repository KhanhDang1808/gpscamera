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
import com.gps.camera.timestamp.photo.geotag.map.Util;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Interface.OnRecyclerItemClickListener;

public class Stampposition_Adapter extends RecyclerView.Adapter<Stampposition_Adapter.Holder> {
    Context mContext;
    String[] mList;
    OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    C1281SP mSP;
    int selPos = 1;
    String selected_pos;
    String templateName;
    Util util;

    public Stampposition_Adapter(String strPos,Context context, String str, String[] strArr, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mContext = context;
        this.mList = strArr;
        this.templateName = str;
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
        C1281SP sp = new C1281SP(this.mContext);
        this.mSP = sp;

//        String string = sp.getString(this.mContext, "pos_type_temp1", "Bottom");
        this.selected_pos = strPos;
        if (strPos.equals("Top")) {
            this.selPos = 0;
        } else {
            this.selPos = 1;
        }

       /* if (str.equals("Temp1")) {
            String string = sp.getString(this.mContext, "pos_type_temp1", "Bottom");
            this.selected_pos = string;
            if (string.equals("Top")) {
                this.selPos = 0;
            } else {
                this.selPos = 1;
            }
        } else {
            String string2 = sp.getString(this.mContext, "pos_type_temp2", "Bottom");
            this.selected_pos = string2;
            if (string2.equals("Top")) {
                this.selPos = 0;
            } else {
                this.selPos = 1;
            }
        }*/
    }

    @Override 
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_cell_date_time_adapter_new, viewGroup, false);
        this.util = new Util(this.mContext);
        return new Holder(inflate);
    }

    public void onBindViewHolder(Holder holder, int i) {
        holder.tv_dateFormat.setText(this.mList[i] + "");
        if (i == this.selPos) {
            holder.img_selection.setVisibility(View.VISIBLE);
        } else {
            holder.img_selection.setVisibility(View.GONE);
        }
        if (i == 0){
            holder.tv_dateFormat.setText(mContext.getText(R.string.stamp_to_top));
            holder.img_icon.setImageResource(R.drawable.ic_stam_top);
        }else {
            holder.tv_dateFormat.setText(mContext.getText(R.string.stamp_to_bottom));
            holder.img_icon.setImageResource(R.drawable.ic_stamp_bottom);
        }
        holder.itemView.setOnClickListener(new StamppositionAdapteGps(this, holder));
    }

    
    
    public  void mo16873x858311ba(Holder holder, View view) {
        if (holder.getAdapterPosition() >= 0 && this.mOnRecyclerItemClickListener != null) {
//            this.util.SavePreferences("postionX", String.valueOf(holder.getAdapterPosition()));
            this.mOnRecyclerItemClickListener.OnClick_(holder.getAdapterPosition(), view);
        }
    }

    @Override 
    public int getItemCount() {
        return this.mList.length;
    }

    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout date_main_lay;
        ImageView img_selection;
        ImageView img_icon;
        TextView tv_dateFormat;

        public Holder(View view) {
            super(view);
            this.tv_dateFormat = (TextView) view.findViewById(R.id.tv_temp_name);
            this.date_main_lay = (LinearLayout) view.findViewById(R.id.dt_main_lay);
            this.img_selection = (ImageView) view.findViewById(R.id.img_selection);
            this.img_icon = (ImageView) view.findViewById(R.id.img_icon);
        }
    }
}
