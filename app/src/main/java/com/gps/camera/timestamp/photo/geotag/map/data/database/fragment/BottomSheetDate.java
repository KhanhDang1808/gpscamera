package com.gps.camera.timestamp.photo.geotag.map.data.database.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.DateTimeFragment;
import com.gps.camera.timestamp.photo.geotag.map.camera.Default;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Interface.OnRecyclerItemClickListener;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Model.DateTime;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Activity.HorizontalCustomDate;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter.DateTimeAdapter;
import com.gps.camera.timestamp.photo.geotag.map.data.database.DateTimeDB;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.ArrayList;

public class BottomSheetDate extends BottomSheetDialogFragment {
    public static final String TAG = "DateTime_BottomSheet";
    public static ArrayList<DateTime> dateTimes_horizontal = new ArrayList<>();
    public static String selected_pos = Default.DEFAULT_DATE_FORMAT;
    DateTimeFragment.OnDateSelectedListener callBack;
    Context context;
    DateTimeDB dateTimeDB;
    LinearLayout lin_adddatetime;
    private DateTimeAdapter mAdapter;
    String[] mDateArray;
    private C1281SP mSP;
    RecyclerView rv_maptype;
    TextView txt_title;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.dateTimeDB = new DateTimeDB(getActivity());
        dateTimes_horizontal.clear();
        dateTimes_horizontal = this.dateTimeDB.getHorozontalNormal();
    }

    @SuppressLint("RestrictedApi")
    @Override 
    public void setupDialog(Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.car_datetime_bottom_sheet_fragment, null);
        this.rv_maptype = (RecyclerView) inflate.findViewById(R.id.rv_maptype);
        this.txt_title = (TextView) inflate.findViewById(R.id.txt_title);
        this.lin_adddatetime = (LinearLayout) inflate.findViewById(R.id.lin_adddatetime);
        this.context = inflate.getContext();
        dialog.setContentView(inflate);
        init();
    }

    private void init() {
        this.mSP = new C1281SP(getContext());
        this.txt_title.setText(getString(R.string.date_and_time));
        this.mDateArray = getActivity().getResources().getStringArray(R.array.datetime_format_arry);
        setAdapter();
        this.lin_adddatetime.setOnClickListener(new View.OnClickListener() {
            

            public void onClick(View view) {
                BottomSheetDate.this.getActivity().startActivity(new Intent(BottomSheetDate.this.getActivity(), HorizontalCustomDate.class));
            }
        });
    }

    public void refrecedata() {
        dateTimes_horizontal.clear();
        ArrayList<DateTime> horozontalNormal = this.dateTimeDB.getHorozontalNormal();
        dateTimes_horizontal = horozontalNormal;
        this.mAdapter.refAdapter(horozontalNormal, selected_pos);
    }

    private void setAdapter() {
        this.rv_maptype.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        this.rv_maptype.setHasFixedSize(true);
        DateTimeAdapter dateTimeAdapter = new DateTimeAdapter(getContext(), dateTimes_horizontal, new OnRecyclerItemClickListener() {
            

            @Override 
            public void OnClick_(int i, View view) {
                BottomSheetDate.this.mSP.setString(BottomSheetDate.this.getContext(), "date_format_temp", BottomSheetDate.dateTimes_horizontal.get(i).getDate_format());
                new Handler().postDelayed(new Runnable() {
                    

                    public void run() {
                        if (BottomSheetDate.this.mAdapter != null && BottomSheetDate.this.callBack != null) {
                            BottomSheetDate.this.callBack.onDateSelected();
                            BottomSheetDate.this.dismiss();
                        }
                    }
                }, 50);
            }

            @Override 
            public void OnLongClick_(int i, View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BottomSheetDate.this.getActivity());
                builder.setTitle(BottomSheetDate.this.getString(R.string.delete));
                builder.setMessage(BottomSheetDate.this.getString(R.string.delete_date_msg));
                builder.setPositiveButton(BottomSheetDate.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    

                    public void onClick(DialogInterface dialogInterface, int i) {
                        BottomSheetDate.this.dateTimeDB.deleteDate(BottomSheetDate.dateTimes_horizontal.get(i).getDate_id());
                        BottomSheetDate.this.refrecedata();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton(BottomSheetDate.this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    

                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        this.mAdapter = dateTimeAdapter;
        this.rv_maptype.setAdapter(dateTimeAdapter);
    }

    @Override 
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        DateTimeFragment.OnDateSelectedListener onDateSelectedListener = this.callBack;
        if (onDateSelectedListener != null) {
            onDateSelectedListener.onDateSelected();
        }
    }
}
