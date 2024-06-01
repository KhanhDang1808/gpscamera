package com.gps.camera.timestamp.photo.geotag.map.ui.my_photos

import androidx.recyclerview.widget.DiffUtil

class MyPhotoDiffUtilCallback : DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
        return false
    }
}
