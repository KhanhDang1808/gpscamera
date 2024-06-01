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

public class DateAdapterCamera extends RecyclerView.Adapter<DateAdapterCamera.ViewHolder> {
    public static final String DATE_FORMAT_0 = "dd-MM-yyyy";
    public static final String DATE_FORMAT_1 = "MM-dd-yyyy";
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd";
    String[] Date_entries;
    Context mContext;
    OnRecyclerListenerClick mDate_Recycle;
    C1281SP mSP;
    int selectedd_position = 0;

    public DateAdapterCamera(String[] strArr, Context context, OnRecyclerListenerClick onrecyclerclicklistener) {
        this.Date_entries = strArr;
        this.mContext = context;
        this.mDate_Recycle = onrecyclerclicklistener;
        this.mSP = new C1281SP(context);
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_coordinates_bottom, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        if (viewHolder instanceof ViewHolder) {
            viewHolder.main_tv1.setText(this.Date_entries[i]);
        }
        viewHolder.sub_tv1.setVisibility(View.GONE);
        int intValue = this.mSP.getInteger(this.mContext, KeysConstants.DATE_POSITION, 0).intValue();
        this.selectedd_position = intValue;
        if (i == intValue) {
            viewHolder.coordi_rb.setImageResource(R.drawable.ic_radiobutton_checked);
        } else {
            viewHolder.coordi_rb.setImageResource(R.drawable.ic_radiobutton_unchecked);
        }
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DateAdapterCamera.this.selectedd_position = viewHolder.getAdapterPosition();
                DateAdapterCamera.this.mSP.setInteger(DateAdapterCamera.this.mContext, KeysConstants.DATE_POSITION, Integer.valueOf(DateAdapterCamera.this.selectedd_position));
                if (DateAdapterCamera.this.selectedd_position == 0) {
                    DateAdapterCamera.this.mSP.setString(DateAdapterCamera.this.mContext, KeysConstants.DATE_POSITION0, DateAdapterCamera.DATE_FORMAT_0);
                }
                if (DateAdapterCamera.this.selectedd_position == 1) {
                    DateAdapterCamera.this.mSP.setString(DateAdapterCamera.this.mContext, KeysConstants.DATE_POSITION0, DateAdapterCamera.DATE_FORMAT_1);
                }
                if (DateAdapterCamera.this.selectedd_position == 2) {
                    DateAdapterCamera.this.mSP.setString(DateAdapterCamera.this.mContext, KeysConstants.DATE_POSITION0, DateAdapterCamera.DATE_FORMAT_2);
                }
                DateAdapterCamera.this.mDate_Recycle.setOnItemClickListener(i, view);

                DateAdapterCamera.this.notifyDataSetChanged();
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.Date_entries.length;
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
