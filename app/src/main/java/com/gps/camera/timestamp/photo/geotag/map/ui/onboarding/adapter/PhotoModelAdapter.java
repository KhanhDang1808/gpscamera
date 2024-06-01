package com.gps.camera.timestamp.photo.geotag.map.ui.onboarding.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gps.camera.timestamp.photo.geotag.map.data.models.PhotoModel;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ItemPhotoModelBinding;

import java.util.List;

public class PhotoModelAdapter extends RecyclerView.Adapter<PhotoModelAdapter.PhotoModelViewHolder> {

    private final List<PhotoModel> listPhotoModels;
    private Context context;

    public PhotoModelAdapter(List<PhotoModel> listPhotoModels, Context context) {
        this.listPhotoModels = listPhotoModels;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemPhotoModelBinding itemPhotoModelBinding = ItemPhotoModelBinding.inflate(inflater, parent, false);
        return new PhotoModelViewHolder(itemPhotoModelBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoModelViewHolder holder, int position) {

        PhotoModel objPhotoModel = listPhotoModels.get(position);
        if (objPhotoModel == null) {
            return;
        }
        holder.binding.imgIdImage.setImageResource(objPhotoModel.getIdImage());
        holder.binding.tvTitle.setText(context.getString(objPhotoModel.getTitle()));
        if (objPhotoModel.getPosition() == 1){
            holder.binding.imgDot1.setVisibility(View.VISIBLE);
            holder.binding.imgDot2.setVisibility(View.GONE);
            holder.binding.imgDot3.setVisibility(View.GONE);
        }else if (objPhotoModel.getPosition() == 2){
            holder.binding.imgDot1.setVisibility(View.GONE);
            holder.binding.imgDot2.setVisibility(View.VISIBLE);
            holder.binding.imgDot3.setVisibility(View.GONE);
        }else {
            holder.binding.imgDot1.setVisibility(View.GONE);
            holder.binding.imgDot2.setVisibility(View.GONE);
            holder.binding.imgDot3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (listPhotoModels != null) {
            return listPhotoModels.size();
        }
        return 0;
    }

    public static class PhotoModelViewHolder extends RecyclerView.ViewHolder {
        ItemPhotoModelBinding binding;

        public PhotoModelViewHolder(@NonNull ItemPhotoModelBinding itemPhotoModelBinding) {
            super(itemPhotoModelBinding.getRoot());
            binding = itemPhotoModelBinding;
        }
    }
}
