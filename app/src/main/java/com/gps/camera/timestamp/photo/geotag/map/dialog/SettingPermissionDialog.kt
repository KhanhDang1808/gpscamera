package com.gps.camera.timestamp.photo.geotag.map.dialog

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.gps.camera.timestamp.photo.geotag.map.R
import com.gps.camera.timestamp.photo.geotag.map.ui.permission.PermissionActivity
import com.vapp.admoblibrary.ads.AppOpenManager

class SettingPermissionDialog(
    val activity: Activity,
    val callback: () -> Unit
) {
    private var dialog: AlertDialog? = null

    init {
        AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.grant_title_permission))
            .setMessage(activity.getString(R.string.content_setting_permission))
            .setCancelable(true)
            .setPositiveButton(activity.getString(R.string.goto_setting_permission)) { _, _ ->
                AppOpenManager.getInstance()
                    .disableAppResumeWithActivity(PermissionActivity::class.java)
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                activity.startActivity(intent)
            }
            .setNegativeButton(activity.getString(R.string.cancel_permission)) { _, _ ->
                callback.invoke()
                dialog?.dismiss()
            }.create().apply {
                setCanceledOnTouchOutside(true)
                if (!isShowing){
                    show()
                }
                dialog = this
            }
    }
}