package com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Interface.OnRecyclerItemClickListener;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.Holder> {
    Context mContext;
    TypedArray mIconArray;
    String[] mList;
    OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    C1281SP mSP;
    int selected_pos;
    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgMapType;
        ImageView img_selection;
        LinearLayout map_main_lay;
        TextView tv_mapName;

        public  void mo16865xce914942(View view) {
            if (getAdapterPosition() >= 0 && MapAdapter.this.mOnRecyclerItemClickListener != null) {
                MapAdapter.this.mOnRecyclerItemClickListener.OnClick_(getAdapterPosition(), view);
            }
        }

        public Holder(View view) {
            super(view);
            this.tv_mapName = view.findViewById(R.id.tv_mapname);
            this.map_main_lay = view.findViewById(R.id.map_main_lay);
            this.img_selection = view.findViewById(R.id.img_selection);
            this.imgMapType = view.findViewById(R.id.imgMapType);
            this.map_main_lay.setOnClickListener(new MapAdapter$Holderex(this));
        }

    }
    public MapAdapter(Context context, String[] strArr, TypedArray typedArray, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mContext = context;
        this.mList = strArr;
        this.mIconArray = typedArray;
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
        C1281SP sp = new C1281SP(this.mContext);
        this.mSP = sp;
        this.selected_pos = sp.getInteger(this.mContext, "map_type_temp_pos", 1).intValue();
    }

    @Override 
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_cell_map_type, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, int i) {
        holder.tv_mapName.setText(this.mList[i]);
        holder.imgMapType.setImageResource(this.mIconArray.getResourceId(i, 0));
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


}
