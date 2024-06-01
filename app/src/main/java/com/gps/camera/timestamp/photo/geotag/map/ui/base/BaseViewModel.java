package com.gps.camera.timestamp.photo.geotag.map.ui.base;

import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class BaseViewModel extends ViewModel {
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
