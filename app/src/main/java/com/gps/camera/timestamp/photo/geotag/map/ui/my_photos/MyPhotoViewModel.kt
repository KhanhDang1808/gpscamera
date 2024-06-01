package com.gps.camera.timestamp.photo.geotag.map.ui.my_photos

import android.app.Activity
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gps.camera.timestamp.photo.geotag.map.R
import com.gps.camera.timestamp.photo.geotag.map.camera.Default
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPhotoViewModel: ViewModel() {

    val isSelectionMode = MutableLiveData<Boolean>()
    var isSelectedAll = MutableLiveData<Boolean>()
    val imageList = MutableLiveData<MutableList<Media>>()
    val imageSelected = mutableListOf<Media>()

    suspend fun getAllMyPhotos(context: Context) {
        isSelectionMode.postValue(false)
        isSelectedAll.postValue(false)
        val images = mutableListOf<Media>()
        withContext(Dispatchers.IO) {
            val projection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.RELATIVE_PATH,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.SIZE,
                    MediaStore.Images.Media.MIME_TYPE,
                    MediaStore.Images.Media.WIDTH,
                    MediaStore.Images.Media.HEIGHT,
                    MediaStore.Images.Media.DATE_ADDED
                )
            } else {
                arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.SIZE,
                    MediaStore.Images.Media.MIME_TYPE,
                    MediaStore.Images.Media.WIDTH,
                    MediaStore.Images.Media.HEIGHT,
                    MediaStore.Images.Media.DATE_ADDED
                )
            }

            val selection = "_data like ? "
            val selectionArgs = arrayOf("%/${Default.FOLDER_NAME}/%")

            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        "/storage/emulated/0/" + cursor.getString(
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.RELATIVE_PATH)
                        ) + cursor.getString(
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                        )
                    } else {
                        cursor.getString(
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                        )
                    }
                    var name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                    var folderName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                    val size = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
                    val mimeType =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE))
                    val width =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH))
                    val height =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT))
                    val date =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))
                    val uri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                    // Discard invalid images that might exist on the device
                    if (size == null) {
                        continue
                    }

                    if (name == null) name = ""
                    if (folderName == null) folderName = ""

                    images += Media(
                        id,
                        uri,
                        path,
                        name,
                        folderName,
                        size,
                        mimeType,
                        width,
                        height,
                        date
                    )
                }
                cursor.close()
                imageList.postValue(images)
            }
        }
    }

    fun multipleShareImage(context: Context) {
        if (imageSelected.size > 0) {
            val intent = Intent()
            intent.setAction("android.intent.action.SEND_MULTIPLE")
            val arrayList = ArrayList<Uri>()
            for (i in imageSelected.indices) {
                arrayList.add(
                    ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageSelected[i].id
                    )
                )
            }
            intent.putExtra("android.intent.extra.TITLE", "TEXT")
            intent.putExtra(
                "android.intent.extra.TEXT",
                "Try this amazing Photo Resizer.https://play.google.com/store/apps/details?id=" + context.packageName
            )
            intent.setType("image/*")
            intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList)
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.send_to)))
        } else {
            Toast.makeText(context, context.getString(R.string.please_select_before_share), Toast.LENGTH_SHORT).show()
        }
    }

    fun multipleDeleteImage(activity: Activity, registerRequestIntentSender: ActivityResultLauncher<IntentSenderRequest>) {
        if (imageSelected.size > 0) {
            val collectionUri: MutableList<Uri> = ArrayList()
            for (i in imageSelected.indices) {
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    imageSelected[i].id
                )
                collectionUri.add(uri)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val pendingIntent: PendingIntent = createDeleteRequest(activity, collectionUri)
                try {
                    val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent.intentSender).build()
                    registerRequestIntentSender.launch(intentSenderRequest)
                } catch (e: Exception) {
                    e.message
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    for (uri in collectionUri) {
                        activity.contentResolver.delete(uri, null, null)
                    }
                    getAllMyPhotos(activity)
                    CoroutineScope(Dispatchers.Main).launch {
                        imageSelected.clear()
                        isSelectionMode.postValue(false)
                        isSelectedAll.postValue(false)
                        Toast.makeText(activity, activity.getString(R.string.image_deleted), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(activity, activity.getString(R.string.please_select_before_delete), Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun createDeleteRequest(context: Context, uris: Collection<Uri>): PendingIntent {
        val resolver: ContentResolver = context.contentResolver
        return MediaStore.createDeleteRequest(resolver, uris)
    }

}