package com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter;

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
import com.gps.camera.timestamp.photo.geotag.map.camera.NotesModal;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Interface.OnRecyclerItemClickListener;

import java.util.ArrayList;

public class NoteAdapterGps extends RecyclerView.Adapter<NoteAdapterGps.NotesViewHolder> {
    ArrayList<NotesModal> Tag_list;
    Context context;
    OnRecyclerItemClickListener mOnRecyclerItemClickListener;
    int selectedposition;

    public NoteAdapterGps(Context context2, ArrayList<NotesModal> arrayList, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        new ArrayList();
        this.context = context2;
        this.Tag_list = arrayList;
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override 
    public NotesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NotesViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_notes_list, viewGroup, false));
    }

    public void onBindViewHolder(NotesViewHolder notesViewHolder, @SuppressLint("RecyclerView") int i) {
        NotesModal notesModal = this.Tag_list.get(i);
        notesViewHolder.txt_title.setText(notesModal.getTiitle() + " " + notesModal.getNotes());
        if (notesModal.getSelected() == 1) {
            this.selectedposition = i;
            notesViewHolder.img_select.setVisibility(View.VISIBLE);
            return;
        }
        notesViewHolder.img_select.setVisibility(View.INVISIBLE);
    }

    @Override 
    public int getItemCount() {
        return this.Tag_list.size();
    }

    public void refreceadapter(ArrayList<NotesModal> arrayList) {
        this.Tag_list = arrayList;
        notifyDataSetChanged();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        ImageView img_select;
        LinearLayout main_lin;
        TextView txt_title;

        public NotesViewHolder(View view) {
            super(view);
            this.txt_title =  view.findViewById(R.id.txt_title);
            this.img_select =  view.findViewById(R.id.img_select);
            LinearLayout linearLayout = view.findViewById(R.id.main_lin);
            this.main_lin = linearLayout;
            linearLayout.setOnClickListener(new Notes_adapter$NotesViewHoldenj(this));
            this.main_lin.setOnLongClickListener(new Notes_adapter$NotesViewHolderbh(this));
        }


        public  boolean mo16870x3c9fdcc3(View view) {
            if (!(getAdapterPosition() <= 0 || getAdapterPosition() == NoteAdapterGps.this.selectedposition || NoteAdapterGps.this.mOnRecyclerItemClickListener == null)) {
                NoteAdapterGps.this.mOnRecyclerItemClickListener.OnLongClick_(getAdapterPosition(), view);
            }
            return false;
        }
        public  void mo16869xba5527e4(View view) {
            if (getAdapterPosition() >= 0 && NoteAdapterGps.this.mOnRecyclerItemClickListener != null) {
                NoteAdapterGps.this.mOnRecyclerItemClickListener.OnClick_(getAdapterPosition(), view);
            }
        }

        
        

    }
}
