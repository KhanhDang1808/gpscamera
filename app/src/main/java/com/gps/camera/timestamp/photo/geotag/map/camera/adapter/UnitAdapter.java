package com.gps.camera.timestamp.photo.geotag.map.camera.adapter;

import android.annotation.SuppressLint;
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
import com.gps.camera.timestamp.photo.geotag.map.camera.KeysConstants;
import com.gps.camera.timestamp.photo.geotag.map.camera.utils.OnRecyclerListenerClick;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.ViewHolder> {
    String[] Unit_entries;
    Context mContext;
    C1281SP mSP;
    OnRecyclerListenerClick munit_Recycle;
    int selectedd_position = 0;

    public UnitAdapter(String[] strArr, Context context, OnRecyclerListenerClick onrecyclerclicklistener) {
        this.Unit_entries = strArr;
        this.mContext = context;
        this.munit_Recycle = onrecyclerclicklistener;
        this.mSP = new C1281SP(context);
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_coordinates_bottom, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        if (viewHolder instanceof ViewHolder) {
            viewHolder.main_tv1.setText(this.Unit_entries[i]);
        }
        viewHolder.sub_tv1.setVisibility(View.GONE);
        int intValue = this.mSP.getInteger(this.mContext, KeysConstants.UNIT_POSITION, 0).intValue();
        this.selectedd_position = intValue;
        if (i == intValue) {
            viewHolder.coordi_rb.setImageResource(R.drawable.ic_radiobutton_checked);
        } else {
            viewHolder.coordi_rb.setImageResource(R.drawable.ic_radiobutton_unchecked);
        }
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                UnitAdapter.this.selectedd_position = viewHolder.getAdapterPosition();
                UnitAdapter.this.mSP.setInteger(UnitAdapter.this.mContext, KeysConstants.UNIT_POSITION, Integer.valueOf(UnitAdapter.this.selectedd_position));
                if (UnitAdapter.this.selectedd_position == 0) {
                    UnitAdapter.this.mSP.setString(UnitAdapter.this.mContext, KeysConstants.UNIT_POSITION0, "Metric - meters");
                }
                if (UnitAdapter.this.selectedd_position == 1) {
                    UnitAdapter.this.mSP.setString(UnitAdapter.this.mContext, KeysConstants.UNIT_POSITION0, "Imperial - feet");
                }
                UnitAdapter.this.munit_Recycle.setOnItemClickListener(i, view);
                UnitAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.Unit_entries.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coordi_rb;
        LinearLayout linearLayout;
        TextView main_tv1;
        TextView sub_tv1;

        public ViewHolder(View view) {
            super(view);
            this.main_tv1 = (TextView) view.findViewById(R.id.coordi_Main_tv);
            this.sub_tv1 = (TextView) view.findViewById(R.id.coordi_sub_tv);
            this.coordi_rb = (ImageView) view.findViewById(R.id.coordi_radio);
            this.linearLayout = (LinearLayout) view.findViewById(R.id.coordi_radio_liner);
        }
    }
}
