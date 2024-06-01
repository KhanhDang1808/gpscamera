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
import com.gps.camera.timestamp.photo.geotag.map.camera.Default;
import com.gps.camera.timestamp.photo.geotag.map.camera.HelperClass;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Interface.OnRecyclerItemClickListener;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Model.DateTime;

import java.util.ArrayList;

public class DateTimeAdapter extends RecyclerView.Adapter<DateTimeAdapter.Holder> {
    public static ArrayList<DateTime> mList = new ArrayList<>();
    Context mContext;
    OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    C1281SP mSP;
    String selected_pos;

    public DateTimeAdapter(Context context, ArrayList<DateTime> arrayList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mContext = context;
        mList = arrayList;
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
        C1281SP sp = new C1281SP(context);
        this.mSP = sp;
        this.selected_pos = sp.getString(this.mContext, "date_format_temp", Default.DEFAULT_DATE_FORMAT);
    }

    @Override 
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_cell_date_time_adapter, viewGroup, false));
    }
    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout date_main_lay;
        ImageView img_selection;
        TextView tv_dateFormat;

        public Holder(View view) {
            super(view);
            this.tv_dateFormat = (TextView) view.findViewById(R.id.tv_temp_name);
            this.date_main_lay = (LinearLayout) view.findViewById(R.id.dt_main_lay);
            this.img_selection = (ImageView) view.findViewById(R.id.img_selection);
            this.date_main_lay.setOnClickListener(new DateTimeAdapter$Holdermmk(this));
            this.date_main_lay.setOnLongClickListener(new DateTimeAdapter$Holder$ll(this));
        }



        public  void mo16856xcaa22c57(View view) {
            if (getAdapterPosition() >= 0 && DateTimeAdapter.this.mOnRecyclerItemClickListener != null) {
                DateTimeAdapter.this.mOnRecyclerItemClickListener.OnClick_(getAdapterPosition(), view);
            }
        }



        public  boolean mo16857x1861a458(View view) {
            if (getAdapterPosition() >= 0 && DateTimeAdapter.mList.get(getAdapterPosition()).getDate_custom() == 1 && DateTimeAdapter.this.mOnRecyclerItemClickListener != null) {
                DateTimeAdapter.this.mOnRecyclerItemClickListener.OnLongClick_(getAdapterPosition(), view);
            }
            return false;
        }
    }
    public void onBindViewHolder(Holder holder, int i) {
        holder.tv_dateFormat.setText(HelperClass.setDateTimeFormat(mList.get(i).getDate_format()));
        if (mList.get(i).getDate_format().equals(this.selected_pos)) {
            holder.img_selection.setVisibility(View.VISIBLE);
        } else {
            holder.img_selection.setVisibility(View.GONE);
        }
    }

    @Override 
    public int getItemCount() {
        return mList.size();
    }

    public void refAdapter(ArrayList<DateTime> arrayList, String str) {
        this.selected_pos = this.mSP.getString(this.mContext, "date_format_temp", Default.DEFAULT_DATE_FORMAT);
        mList = arrayList;
        notifyDataSetChanged();
    }


}
