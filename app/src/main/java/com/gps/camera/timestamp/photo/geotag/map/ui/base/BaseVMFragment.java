package com.gps.camera.timestamp.photo.geotag.map.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

public abstract  class BaseVMFragment<T extends BaseViewModel,V extends ViewDataBinding> extends AbsBaseFragment<V> {
    protected T mViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    public abstract void initViewModel();

}