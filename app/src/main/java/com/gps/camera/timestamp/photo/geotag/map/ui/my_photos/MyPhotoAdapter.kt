package com.gps.camera.timestamp.photo.geotag.map.ui.my_photos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.gps.camera.timestamp.photo.geotag.map.R
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager
import com.gps.camera.timestamp.photo.geotag.map.databinding.ItemMyPhotoBinding

class MyPhotoAdapter(val context: Context, val viewModel: MyPhotoViewModel, private val onClickStorageImage: (Media) -> Unit) :
    ListAdapter<Media, MyPhotoAdapter.MainViewHolder>(MyPhotoDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainViewHolder(ItemMyPhotoBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class MainViewHolder(val binding: ItemMyPhotoBinding) : ViewHolder(binding.root) {
        fun onBind(media: Media) {
            Glide.with(context)
                .load(media.uri)
                .centerCrop()
                .thumbnail(
                    Glide.with(context)
                        .load(media.uri)
                        .sizeMultiplier(0.2f)
                        .override(binding.ivPhoto.width, binding.ivPhoto.height)
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivPhoto)

            if (viewModel.isSelectionMode.value!!) {
                binding.ivSelected.visibility = View.VISIBLE
                if (viewModel.imageSelected.contains(media)) {
                    Glide.with(context).load(R.drawable.ic_selected).into(binding.ivSelected)
                } else {
                    Glide.with(context).load(R.drawable.ic_un_selected).into(binding.ivSelected)
                }
            } else {
                binding.ivSelected.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                if (AdsManager.showAdsEnable) onClickStorageImage(media)
            }
        }
    }

}