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
import com.gps.camera.timestamp.photo.geotag.map.camera.adapter.TimeAdapter;
import com.gps.camera.timestamp.photo.geotag.map.camera.utils.OnRecyclerListenerClick;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetTimeFormat extends BottomSheetDialogFragment {
    String[] Time_entries;
    RecyclerView mcoordirecycle;
    onClickTime onClickTime;
    TimeAdapter timeAdapter;

    public interface onClickTime {
        void setonTime(int i);
    }

    public static BottomSheetTimeFormat newInstance() {
        return new BottomSheetTimeFormat();
    }

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @SuppressLint({"RestrictedApi", "WrongConstant"})
    @Override 
    public void setupDialog(final Dialog dialog, int i) {
        View inflate = View.inflate(getContext(), R.layout.car_coordi_bottom_recycle, null);
        dialog.setContentView(inflate);
        ((TextView) inflate.findViewById(R.id.toolbar_title)).setText(getResources().getString(R.string.Time_Format));
        this.Time_entries = getResources().getStringArray(R.array.time_entries);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.coordi_recycle);
        this.mcoordirecycle = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        TimeAdapter timeAdapter2 = new TimeAdapter(this.Time_entries, getContext(), new OnRecyclerListenerClick() {
            

            @Override 
            public void setOnItemClickListener(int i, View view) {
                dialog.dismiss();
                BottomSheetTimeFormat.this.onClickTime.setonTime(i);
            }
        });
        this.timeAdapter = timeAdapter2;
        this.mcoordirecycle.setAdapter(timeAdapter2);
        ((RelativeLayout) inflate.findViewById(R.id.toolbar_cross)).setOnClickListener(new View.OnClickListener() {
            

            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void setOntimeListner(onClickTime onclicktime) {
        this.onClickTime = onclicktime;
    }
}
