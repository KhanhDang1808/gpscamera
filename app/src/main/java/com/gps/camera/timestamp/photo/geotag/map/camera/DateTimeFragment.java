package com.gps.camera.timestamp.photo.geotag.map.camera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gps.camera.timestamp.photo.geotag.map.R;

public class DateTimeFragment extends Fragment {
    OnDateSelectedListener callBack;
    private RecyclerView.Adapter mAdapter;
    String[] mDateArray;
    private RecyclerView mRecyclerview;
    private C1281SP mSP;

    public interface OnDateSelectedListener {
        void onDateSelected();
    }

    public void setOnDate_SelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.callBack = onDateSelectedListener;
    }

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.car_fragment_template, viewGroup, false);
    }

    @Override 
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        init(view);
    }

    private void init(View view) {
        this.mSP = new C1281SP(getContext());
        this.mRecyclerview = (RecyclerView) view.findViewById(R.id.fragment_recyclerview);
        this.mDateArray = getResources().getStringArray(R.array.datetime_format_arry);
        setAdapter();
    }

    private void setAdapter() {
        this.mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        this.mRecyclerview.setHasFixedSize(true);
        this.mRecyclerview.setAdapter(this.mAdapter);
    }
}
