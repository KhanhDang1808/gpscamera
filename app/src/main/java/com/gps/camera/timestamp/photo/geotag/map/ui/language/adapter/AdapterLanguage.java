package com.gps.camera.timestamp.photo.geotag.map.ui.language.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.data.models.LanguageModel;
import com.gps.camera.timestamp.photo.geotag.map.databinding.ItemLanguageBinding;
import com.gps.camera.timestamp.photo.geotag.map.utils.CommonUtils;


public class AdapterLanguage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    public int selectedPosition = 0;

    public AdapterLanguage(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLanguageBinding binding = ItemLanguageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new LanguageViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LanguageModel language = CommonUtils.getLanguageList().get(position);
        if (selectedPosition == position){
            ((LanguageViewHolder) holder).binding.layoutRoot.setBackgroundResource(R.drawable.bg_selected);
            ((LanguageViewHolder) holder).binding.imgSelect.setImageResource(R.drawable.ic_selected);
        }else{
            ((LanguageViewHolder) holder).binding.layoutRoot.setBackgroundResource(R.drawable.bg_un_selected);
            ((LanguageViewHolder) holder).binding.imgSelect.setImageResource(R.drawable.ic_un_selected);
        }

        ((LanguageViewHolder) holder).binding.languageIcon.setImageResource(language.getImg());
        ((LanguageViewHolder) holder).binding.languageName.setText(context.getString(language.getName()));

        holder.itemView.setOnClickListener(view -> {
            selectedPosition = position;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return CommonUtils.getLanguageList().size();
    }

    public static class LanguageViewHolder extends RecyclerView.ViewHolder {
        ItemLanguageBinding binding;

        public LanguageViewHolder(ItemLanguageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public int getSelectedPositionLanguage(){
        return selectedPosition;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedPositionLanguage(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

}
