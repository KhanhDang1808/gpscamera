package com.gps.camera.timestamp.photo.geotag.map.ui.base;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {
    protected V binding;
    public BaseViewHolder(V binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
    public abstract void bind(int position);
}
