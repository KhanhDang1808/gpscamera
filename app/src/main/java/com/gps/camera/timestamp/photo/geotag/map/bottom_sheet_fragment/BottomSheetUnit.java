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
import com.gps.camera.timestamp.photo.geotag.map.camera.adapter.UnitAdapter;
import com.gps.camera.timestamp.photo.geotag.map.camera.utils.OnRecyclerListenerClick;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetUnit extends BottomSheetDialogFragment {
    String[] Unit_entries;
    UnitAdapter mUnitAdapter;
    RecyclerView mcoordirecycle;
    onClickUnit onclickUnit;


    public interface onClickUnit {
        void setonUnit(int i);
    }

    public static BottomSheetUnit newInstance() {
        return new BottomSheetUnit();
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
        ((TextView) inflate.findViewById(R.id.toolbar_title)).setText(getResources().getString(R.string.Unit));
        this.Unit_entries = getResources().getStringArray(R.array.unit_entries);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.coordi_recycle);
        this.mcoordirecycle = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        UnitAdapter unitAdapter = new UnitAdapter(this.Unit_entries, getContext(), new OnRecyclerListenerClick() {

            @Override
            public void setOnItemClickListener(int i, View view) {
                BottomSheetUnit.this.onclickUnit.setonUnit(i);
                dialog.dismiss();
            }
        });
        this.mUnitAdapter = unitAdapter;
        this.mcoordirecycle.setAdapter(unitAdapter);
        ((RelativeLayout) inflate.findViewById(R.id.toolbar_cross)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void setonUnitlistner(onClickUnit onclickunit) {
        this.onclickUnit = onclickunit;
    }
}
