package com.gps.camera.timestamp.photo.geotag.map.ui.my_photos

import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.gps.camera.timestamp.photo.geotag.map.R
import com.gps.camera.timestamp.photo.geotag.map.ads.AdsManager
import com.gps.camera.timestamp.photo.geotag.map.databinding.ActivityPhotoPreviewBinding
import com.gps.camera.timestamp.photo.geotag.map.databinding.DialogConfirmDeleteBinding
import com.gps.camera.timestamp.photo.geotag.map.ui.base.BaseActivity

class PhotoPreviewActivity : BaseActivity<ActivityPhotoPreviewBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_photo_preview
    }

    override fun initView() {
        AdsManager.showAdsEnable = true
        val imagePreview = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(MyPhotoActivity.PREVIEW_EXTRA, Uri::class.java)
        } else {
            intent.getParcelableExtra(MyPhotoActivity.PREVIEW_EXTRA)
        }

        Glide.with(this).load(imagePreview).into(binding.ivPreview)

        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.ivShare.setOnClickListener {
            shareImage(this@PhotoPreviewActivity, imagePreview)
        }

        binding.ivDelete.setOnClickListener {
            showDialogConfirmDelete(imagePreview)
        }

    }

    private fun shareImage(context: Context, uri: Uri?) {
        val intent = Intent()
        intent.setAction("android.intent.action.SEND")
        intent.putExtra("android.intent.extra.TITLE", "TEXT")
        intent.putExtra(
            "android.intent.extra.TEXT",
            "Try this amazing Photo Resizer.https://play.google.com/store/apps/details?id=" + context.packageName
        )
        intent.setType("image/*")
        intent.putExtra("android.intent.extra.STREAM", uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(Intent.createChooser(intent, context.getText(R.string.send_to)))
    }

    private fun showDialogConfirmDelete(uri: Uri?) {
        val dialogBinding = DialogConfirmDeleteBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnDelete.setOnClickListener {
            dialog.dismiss()
            actionDelete(uri)
        }

        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            val window = dialog.window
            val WLP = window!!.attributes
            WLP.gravity = Gravity.CENTER
            window.attributes = WLP
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
        dialog.setCancelable(false)
        dialog.show()
        dialog.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    fun actionDelete(uri: Uri?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val collectionUri = listOf(uri)
            val pendingIntent: PendingIntent = createDeleteRequest(
                collectionUri
            )
            try {
                val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent.intentSender).build()
                registerRequestIntentSender.launch(intentSenderRequest)
            } catch (e: SendIntentException) {
                throw RuntimeException(e)
            }
        } else {
            uri?.let { contentResolver.delete(it, null, null) }
            setResult(MyPhotoActivity.RESULT_DELETED)
            finish()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    fun createDeleteRequest(uris: Collection<Uri?>?): PendingIntent {
        val resolver = contentResolver
        return MediaStore.createDeleteRequest(resolver, uris!!)
    }

    private val registerRequestIntentSender = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
        if (it.resultCode == RESULT_OK) {
            setResult(MyPhotoActivity.RESULT_DELETED)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        AdsManager.showAdsEnable = true
    }
}