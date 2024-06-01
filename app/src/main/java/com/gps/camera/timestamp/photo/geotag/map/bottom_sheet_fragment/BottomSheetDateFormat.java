package com.gps.camera.timestamp.photo.geotag.map.bottom_sheet_fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.adapter.DateAdapterCamera;
import com.gps.camera.timestamp.photo.geotag.map.camera.utils.OnRecyclerListenerClick;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDateFormat extends BottomSheetDialogFragment {
    String[] date_entries;
    DateAdapterCamera mDateAdapter;
    RecyclerView mcoordirecycle;
    onClickDate onClickDate;

    public interface onClickDate {
        void setOnDate(int i);
    }

    public static BottomSheetDateFormat newInstance() {
        return new BottomSheetDateFormat();
    }

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @SuppressLint("RestrictedApi")
    @Override 
    public void setupDialog(final Dialog dialog, int i) {
        View inflate = View.inflate(getContext(), R.layout.car_coordi_bottom_recycle, null);
        dialog.setContentView(inflate);
        ((TextView) inflate.findViewById(R.id.toolbar_title)).setText(getResources().getString(R.string.Date_Format));
        this.date_entries = getResources().getStringArray(R.array.ddate_entries);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.coordi_recycle);
        this.mcoordirecycle = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        DateAdapterCamera cAR_DateAdapterCamera = new DateAdapterCamera(this.date_entries, getContext(), new OnRecyclerListenerClick() {
            

            @Override 
            public void setOnItemClickListener(int i, View view) {
                dialog.dismiss();
                BottomSheetDateFormat.this.onClickDate.setOnDate(i);
            }
        });
        this.mDateAdapter = cAR_DateAdapterCamera;
        this.mcoordirecycle.setAdapter(cAR_DateAdapterCamera);
        ((RelativeLayout) inflate.findViewById(R.id.toolbar_cross)).setOnClickListener(new View.OnClickListener() {
            

            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void setonDateListner(onClickDate onclickdate) {
        this.onClickDate = onclickdate;
    }
}
