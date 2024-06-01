package com.gps.camera.timestamp.photo.geotag.map.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

fun View.setOnClickListener500MilliSeconds(click: () -> Unit){
    setOnClickListener {
        if(CommonUtils.singleClick500mls()) click.invoke()
    }
}
fun View.setOnClickListener250MilliSeconds(click: () -> Unit){
    setOnClickListener {
        if(CommonUtils.singleClick250mls()) click.invoke()
    }
}

fun View.setOnClickListener100MilliSeconds(click: () -> Unit){
    setOnClickListener {
        if(CommonUtils.singleClick100mls()) click.invoke()
    }
}

fun View.setOnClickListener1Seconds(click: () -> Unit){
    setOnClickListener {
        if(CommonUtils.singleClick1s()) click.invoke()
    }
}

fun ViewPager2.reduceDragSensitivity(f: Int = 4) {
    val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
    recyclerViewField.isAccessible = true
    val recyclerView = recyclerViewField.get(this) as RecyclerView

    val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
    touchSlopField.isAccessible = true
    val touchSlop = touchSlopField.get(recyclerView) as Int
    touchSlopField.set(recyclerView, touchSlop * f)       // "8" was obtained experimentally
}

