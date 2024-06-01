package com.gps.camera.timestamp.photo.geotag.map.ads

import android.app.Activity
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAdRevenue
import com.adjust.sdk.AdjustConfig
import com.codemybrainsout.ratingdialog.MaybeLaterCallback
import com.codemybrainsout.ratingdialog.RateButtonCallback
import com.codemybrainsout.ratingdialog.RatingDialog
import com.gps.camera.timestamp.photo.geotag.map.R
import com.gps.camera.timestamp.photo.geotag.map.data.database.Activity.SharePref
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.vapp.admoblibrary.AdsInterCallBack
import com.vapp.admoblibrary.ads.*
import com.vapp.admoblibrary.ads.admobnative.enumclass.CollapsibleBanner
import com.vapp.admoblibrary.ads.admobnative.enumclass.GoogleENative
import com.vapp.admoblibrary.ads.model.InterHolder
import com.vapp.admoblibrary.ads.model.NativeHolder
import java.io.File


object AdsManager {
    var INTER_SPLASH = InterHolder("")
    var NATIVE_LANGUAGE = NativeHolder("")
    var NATIVE_INTRO = NativeHolder("")
    var NATIVE_HOME = NativeHolder("")
    var NATIVE_TEMPLATE = NativeHolder("")
    var NATIVE_AREA_CALC = NativeHolder("")
    var NATIVE_MY_PHOTO = NativeHolder("")

    var BANNER_COLLAP_HOME = ""
    var BANNER_COLLAP_CAMERA = ""
    var INTER_VIEW_PHOTO = InterHolder("")

    var ONRESUME = ""

    var countShowInter = 1
    var showAdsEnable = false
    var stateTitleDraw = 0


    interface AdListener2 {
        fun onAdClose()
        fun onAdFail(error: String?)
    }

    fun loadAndShowInter(context: Context, interHolder: InterHolder, callback: AdListener2) {
        AdmobUtils.loadAndShowAdInterstitial(
            context as AppCompatActivity,
            interHolder,
            object : AdsInterCallBack {
                override fun onStartAction() {

                }

                override fun onEventClickAdClosed() {
                    callback.onAdClose()

                }

                override fun onAdShowed() {
                    Handler().postDelayed({
                        try {
                            AdmobUtils.dismissAdDialog()
                        } catch (e: Exception) {

                        }
                    }, 800)
                }

                override fun onAdLoaded() {

                }

                override fun onAdFail(error: String?) {
                    callback.onAdFail(error)
                }

                override fun onPaid(adValue: AdValue?, adUnitAds: String?) {
                    adValue?.let { postRevenueAdjust(it, adUnitAds) }
                }

            },
            true
        )
    }

    fun showAdInter(
        context: Activity,
        interHolder: InterHolder,
        callback: AdListenerNew,
        event: String
    ) {
        AppOpenManager.getInstance().isAppResumeEnabled = true
        AdmobUtils
            .showAdInterstitialWithCallbackNotLoadNew(context, interHolder, 10000, object :
                AdsInterCallBack {
                override fun onStartAction() {
                }

                override fun onEventClickAdClosed() {
                    callback.onAdClosed()
                    loadInter(context, interHolder)
                }

                override fun onAdShowed() {
                    AppOpenManager.getInstance().isAppResumeEnabled = false
                    Handler().postDelayed({
                        try {
                            AdmobUtils.dismissAdDialog()
                        } catch (e: Exception) {

                        }
                    }, 800)
                }

                override fun onAdLoaded() {

                }

                override fun onAdFail(error: String) {
                    callback.onFailed()
                    loadInter(context, interHolder)
                }

                override fun onPaid(adValue: AdValue?, adUnitAds: String?) {
                    adValue?.let { postRevenueAdjust(it, adUnitAds) }
                }
            }, true)
    }

    fun showNativeSmall1(
        activity: Activity,
        nativeHolder: NativeHolder,
        nativeAdContainer: ViewGroup
    ) {
        if (!AdmobUtils.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }
        showAdNativeWithSize(
            activity,
            nativeAdContainer,
            nativeHolder,
            GoogleENative.UNIFIED_SMALL,
            R.layout.ad_template_small
        )
    }

    fun showAdNative(activity: Activity, nativeAdContainer: ViewGroup, nativeHolder: NativeHolder) {
        if (!AdmobUtils.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }
        showAdNativeWithSize(
            activity, nativeAdContainer, nativeHolder,
            GoogleENative.UNIFIED_MEDIUM, R.layout.ad_template_medium
        )
    }

    private fun showAdNativeWithSize(
        activity: Activity,
        nativeAdContainer: ViewGroup,
        nativeHolder: NativeHolder,
        googleENative: GoogleENative,
        layout: Int
    ) {
        if (!AdmobUtils.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }
        AdmobUtils.showNativeAdsWithLayout(
            activity,
            nativeHolder,
            nativeAdContainer,
            layout,
            googleENative, object : AdmobUtils.AdsNativeCallBackAdmod {
                override fun NativeFailed(massage: String) {
                    nativeAdContainer.visibility = View.GONE
                }

                override fun NativeLoaded() {
                    nativeAdContainer.visibility = View.VISIBLE
                }

                override fun onPaidNative(adValue: AdValue, adUnitAds: String) {
                    adValue?.let { postRevenueAdjust(it, adUnitAds) }
                }

            }
        )
    }

    fun showAdBanner(activity: Activity, adsEnum: String, view: ViewGroup, line: View) {
        if (AdmobUtils.isNetworkConnected(activity)) {
            AdmobUtils.loadAdBanner(activity, adsEnum, view, object :
                AdmobUtils.BannerCallBack {
                override fun onClickAds() {

                }

                override fun onFailed(message: String) {
                    view.visibility = View.GONE
                    line.visibility = View.GONE
                }

                override fun onLoad() {
                    view.visibility = View.VISIBLE
                    line.visibility = View.VISIBLE
                }

                override fun onPaid(adValue: AdValue?, mAdView: AdView?) {
                    adValue?.let { postRevenueAdjust(it, mAdView?.adUnitId) }
                }
            })
        } else {
            view.visibility = View.GONE
            line.visibility = View.GONE
        }
    }

    fun showAdBannerCollapsible(activity: Activity, adsEnum: String, view: ViewGroup, line: View) {
        if (AdmobUtils.isNetworkConnected(activity)) {
            AdmobUtils.loadAdBannerCollapsible(activity, adsEnum, CollapsibleBanner.BOTTOM, view,
                object : AdmobUtils.BannerCollapsibleAdCallback {
                    override fun onBannerAdLoaded(adSize: AdSize) {
                        view.visibility = View.VISIBLE
                        line.visibility = View.VISIBLE
                        val params: ViewGroup.LayoutParams = view.layoutParams
                        params.height = adSize.getHeightInPixels(activity)
                        view.layoutParams = params
                    }

                    override fun onClickAds() {

                    }

                    override fun onAdFail(message: String) {
                        view.visibility = View.GONE
                        line.visibility = View.GONE
                    }

                    override fun onAdPaid(adValue: AdValue, mAdView: AdView) {
                        adValue?.let { postRevenueAdjust(it, mAdView.adUnitId) }
                    }
                })
        } else {
            view.visibility = View.GONE
            line.visibility = View.GONE
        }
    }

    fun loadInter(context: Context, interHolder: InterHolder) {
        AdmobUtils.loadAndGetAdInterstitial(context, interHolder, object :
            AdCallBackInterLoad {
            override fun onAdClosed() {

            }

            override fun onEventClickAdClosed() {

            }

            override fun onAdShowed() {

            }

            override fun onAdLoaded(interstitialAd: InterstitialAd?, isLoading: Boolean) {

            }

            override fun onAdFail(message: String?) {

            }

        })
    }

    fun loadNative(activity: Context, nativeHolder: NativeHolder) {
        AdmobUtils.loadAndGetNativeAds(activity, nativeHolder, object : NativeAdCallback {
            override fun onLoadedAndGetNativeAd(ad: NativeAd?) {

            }

            override fun onNativeAdLoaded() {

            }

            override fun onAdFail(error: String?) {

            }

            override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {
                adValue?.let { postRevenueAdjust(it, adUnitAds) }
            }

        })
    }



    interface AdListenerNew {
        fun onAdClosed()
        fun onFailed()
    }

    var stateShowed = true;
    fun loadAndShowNative(
        activity: Activity,
        nativeAdContainer: ViewGroup,
        nativeHolder: NativeHolder
    ) {
        if (!AdmobUtils.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }
        AdmobUtils.loadAndShowNativeAdsWithLayoutAds(activity,
            nativeHolder,
            nativeAdContainer,
            R.layout.ad_template_medium,
            GoogleENative.UNIFIED_MEDIUM,
            object : AdmobUtils.NativeAdCallbackNew {
                override fun onLoadedAndGetNativeAd(ad: NativeAd?) {

                }

                override fun onNativeAdLoaded() {
                    nativeAdContainer.visibility = View.VISIBLE
                    stateShowed = true
                }

                override fun onAdFail(error: String) {
                    stateShowed = false
                    nativeAdContainer.visibility = View.GONE
                    if (error != null) {
                        Log.d("===NativeFalse", error)
                    }
                }

                override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {
                    adValue?.let { postRevenueAdjust(it, adUnitAds) }
                }

                override fun onClickAds() {

                }
            })
    }

    fun loadAndShowNativeSmallHome(
        activity: Activity,
        nativeHolder: NativeHolder,
        nativeAdContainer: ViewGroup, view1: CardView
    ) {
        if (!AdmobUtils.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            view1.visibility = View.GONE
            return
        }

        AdmobUtils.loadAndShowNativeAdsWithLayoutAds(activity,
            nativeHolder,
            nativeAdContainer,
            R.layout.ad_template_small,
            GoogleENative.UNIFIED_SMALL,
            object : AdmobUtils.NativeAdCallbackNew {
                override fun onLoadedAndGetNativeAd(ad: NativeAd?) {

                }

                override fun onNativeAdLoaded() {
                    nativeAdContainer.visibility = View.VISIBLE
                    view1.visibility = View.VISIBLE
                }

                override fun onAdFail(error: String) {
                    nativeAdContainer.visibility = View.GONE
                    view1.visibility = View.GONE
                    if (error != null) {
                        Log.d("===NativeFalse", error)
                    }
                }

                override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {
                    adValue?.let { postRevenueAdjust(it, adUnitAds) }
                }

                override fun onClickAds() {

                }
            })
    }


    fun loadAndShowNativeSmall(
        activity: Activity,
        nativeHolder: NativeHolder,
        nativeAdContainer: ViewGroup
    ) {
        if (!AdmobUtils.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }

        AdmobUtils.loadAndShowNativeAdsWithLayoutAds(activity,
            nativeHolder,
            nativeAdContainer,
            R.layout.ad_template_small,
            GoogleENative.UNIFIED_SMALL,
            object : AdmobUtils.NativeAdCallbackNew {
                override fun onLoadedAndGetNativeAd(ad: NativeAd?) {

                }

                override fun onNativeAdLoaded() {
                    nativeAdContainer.visibility = View.VISIBLE
                }

                override fun onAdFail(error: String) {
                    nativeAdContainer.visibility = View.GONE
                    if (error != null) {
                        Log.d("===NativeFalse", error)
                    }
                }

                override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {
                    adValue?.let { postRevenueAdjust(it, adUnitAds) }
                }

                override fun onClickAds() {

                }
            })
    }

    fun postRevenueAdjust(ad: AdValue, adUnit: String?) {
        val adjustAdRevenue = AdjustAdRevenue(AdjustConfig.AD_REVENUE_ADMOB)
        val rev = ad.valueMicros.toDouble() / 1000000
        adjustAdRevenue.setRevenue(rev, ad.currencyCode)
        adjustAdRevenue.setAdRevenueUnit(adUnit)
        Adjust.trackAdRevenue(adjustAdRevenue)
    }

    fun deleteFileWithUri(uri: Uri) {
        try {
            val file = uri.path?.let { File(it) }
            file?.delete()
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun createDeleteRequest(
        context: Context,
        uris: Collection<Uri>
    ): PendingIntent {
        val resolver: ContentResolver = context.contentResolver
        return MediaStore.createDeleteRequest(resolver, uris)
    }



    var countTakePhoto = false
    var showRate = false
    fun setInitVariable() {
        countTakePhoto = false
        showRate = false
        countShowInter = 1
        showAdsEnable = false
        stateTitleDraw = 0
    }

    fun showRate(context: Context) {
        if (!countTakePhoto) {
            countTakePhoto = true
            return
        }
        if (showRate){
            return
        }
        showRate = true
        val sharePref =
            SharePref(
                context
            )
        if (sharePref.showRate4s) {
            return
        }

        val ratingDialog: RatingDialog = RatingDialog.Builder(context as Activity)
            .session(1)
            .date(1)
            .setNameApp(context.getString(R.string.app_name))
            .setIcon(R.drawable.img_logo_splash)
            .setEmail("canhsathinhsu6969@gmail.com")
            .setOnlickRate(RateButtonCallback { rate ->
                AppOpenManager.getInstance().disableAppResumeWithActivity(context.javaClass)
                if (rate >= 4) {
                    sharePref.setShowRate4s()
                }
            })
            .ignoreRated(false)
            .isShowButtonLater(true)
            .isClickLaterDismiss(true)
            .setTextButtonLater("Maybe Later")
            .setOnlickMaybeLate(MaybeLaterCallback {

            })
            .ratingButtonColor(R.color.red)
            .build()
        ratingDialog.setCanceledOnTouchOutside(false)
        ratingDialog.show()
    }
}



