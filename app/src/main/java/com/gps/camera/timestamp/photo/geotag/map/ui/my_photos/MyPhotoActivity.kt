package com.gps.camera.timestamp.photo.geotag.map.ui.my_photos

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.gps.camera.timestamp.photo.geotag.map.R
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager.NATIVE_LANGUAGE
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager.NATIVE_MY_PHOTO
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager.loadAndShowNative
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager.stateShowed
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivityMyPhotoBinding
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity
import com.gps.camera.timestamp.photo.geotag.map.utils.Utils
import com.vapp.admoblibrary.ads.AdmobUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPhotoActivity : BaseActivity<ActivityMyPhotoBinding>() {

    private val myPhotoViewModel by lazy { ViewModelProvider(this)[MyPhotoViewModel::class.java] }
    private lateinit var myPhotoAdapter: MyPhotoAdapter

    companion object {
        const val PREVIEW_EXTRA = "preview_extra"
        const val RESULT_DELETED = 1221
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_my_photo
    }

    override fun initView() {
        if (Utils.checkHaveImagesInFolder() > 0){
            loadAndShowNative(this, binding.frNative, NATIVE_MY_PHOTO)
        }else{
            binding.frNative.visibility = View.GONE
        }

        getAllImageList()
        myPhotoAdapter = MyPhotoAdapter(this, myPhotoViewModel) {
            if (myPhotoViewModel.isSelectionMode.value!!) {
                if (myPhotoViewModel.imageSelected.contains(it)) {
                    myPhotoViewModel.imageSelected.remove(it)
                } else {
                    myPhotoViewModel.imageSelected.add(it)
                }
                myPhotoAdapter.notifyDataSetChanged()
                myPhotoViewModel.isSelectedAll.postValue(myPhotoViewModel.imageSelected.size == myPhotoViewModel.imageList.value!!.size)
            } else {
                if(AdsManager.countShowInter % 2 == 0){
                    AdsManager.showAdsEnable = false
                    AdsManager.countShowInter++
                    AdsManager.showAdInter(this,AdsManager.INTER_VIEW_PHOTO,object : AdsManager.AdListenerNew{
                        override fun onAdClosed() {
                            val intent = Intent(this@MyPhotoActivity, PhotoPreviewActivity::class.java)
                            intent.putExtra(PREVIEW_EXTRA, it.uri)
                            registerResultPreview.launch(intent)
                        }

                        override fun onFailed() {
                            onAdClosed()
                        }

                    },"inter_select_device")
                }else{
                    AdsManager.countShowInter++
                    val intent = Intent(this@MyPhotoActivity, PhotoPreviewActivity::class.java)
                    intent.putExtra(PREVIEW_EXTRA, it.uri)
                    registerResultPreview.launch(intent)
                }

            }
        }

        binding.rcvMyPhotos.layoutManager = GridLayoutManager(this, 3)
        binding.rcvMyPhotos.adapter = myPhotoAdapter
        myPhotoViewModel.imageList.observe(this) {
            if (it.size > 0) {
                if (AdmobUtils.isNetworkConnected(this)){
                    if (stateShowed){
                        binding.frNative.visibility = View.VISIBLE
                    }else{
                        binding.frNative.visibility = View.GONE
                    }
                }
                binding.tvEmpty.visibility = View.GONE
                myPhotoAdapter.submitList(it)
            } else {
                binding.frNative.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rcvMyPhotos.visibility = View.GONE
            }
        }

        myPhotoViewModel.isSelectionMode.observe(this) {
            if (it) {
                binding.llToolbar.visibility = View.GONE
                binding.llToolbarSelection.visibility = View.VISIBLE
            } else {
                binding.llToolbar.visibility = View.VISIBLE
                binding.llToolbarSelection.visibility = View.GONE
            }
            myPhotoAdapter.notifyDataSetChanged()
        }

        myPhotoViewModel.isSelectedAll.observe(this) {
            if (it) {
                Glide.with(this@MyPhotoActivity).load(R.drawable.ic_select_all).into(binding.ivSelectedAll)
            } else {
                Glide.with(this@MyPhotoActivity).load(R.drawable.ic_un_selected).into(binding.ivSelectedAll)
            }
        }

        binding.ivBack.setOnClickListener {
            if (AdsManager.showAdsEnable) {
                setResult(RESULT_OK)
                finish()
            }
        }

        binding.ivBackSelection.setOnClickListener {
            if (AdsManager.showAdsEnable){
                myPhotoViewModel.isSelectionMode.postValue(false)
                myPhotoViewModel.isSelectedAll.postValue(false)
                myPhotoViewModel.imageSelected.clear()
            }
        }

        binding.ivSelectAll.setOnClickListener {
            if (AdsManager.showAdsEnable) myPhotoViewModel.isSelectionMode.postValue(!myPhotoViewModel.isSelectionMode.value!!)
        }

        binding.ivSelectedAll.setOnClickListener {
            if (AdsManager.showAdsEnable){
                if (myPhotoViewModel.isSelectedAll.value!!) {
                    myPhotoViewModel.imageSelected.clear()
                } else {
                    myPhotoViewModel.imageSelected.clear()
                    myPhotoViewModel.imageSelected.addAll(myPhotoViewModel.imageList.value!!)
                }
                myPhotoViewModel.isSelectedAll.postValue(!myPhotoViewModel.isSelectedAll.value!!)
                myPhotoAdapter.notifyDataSetChanged()
            }
        }

        binding.ivShare.setOnClickListener {
            if (AdsManager.showAdsEnable) myPhotoViewModel.multipleShareImage(this)
        }

        binding.ivDelete.setOnClickListener {
            if (AdsManager.showAdsEnable) myPhotoViewModel.multipleDeleteImage(this, registerRequestIntentSender)
        }

    }

    private fun getAllImageList() {
        binding.progress.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            myPhotoViewModel.getAllMyPhotos(this@MyPhotoActivity)
            CoroutineScope(Dispatchers.Main).launch {
                binding.progress.visibility = View.GONE
            }
        }
    }

    private fun imageDeletedState() {
        getAllImageList()
        myPhotoViewModel.imageSelected.clear()
        myPhotoViewModel.isSelectionMode.postValue(false)
        myPhotoViewModel.isSelectedAll.postValue(false)
        Toast.makeText(this, getString(R.string.image_deleted), Toast.LENGTH_SHORT).show()
    }

    private val registerRequestIntentSender = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
        if (it.resultCode == RESULT_OK) {
            imageDeletedState()
        }
    }

    private val registerResultPreview = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_DELETED) {
            imageDeletedState()
        }

        if (Utils.checkHaveImagesInFolder() > 0){
            loadAndShowNative(this, binding.frNative, NATIVE_MY_PHOTO)
        }else{
            binding.frNative.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_OK)
        finish()
    }

}