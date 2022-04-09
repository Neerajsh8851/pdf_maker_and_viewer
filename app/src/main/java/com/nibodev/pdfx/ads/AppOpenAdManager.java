package com.nibodev.pdfx.ads;

import static com.nibodev.pdfx.AppUtils.isDebugBuild;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.nibodev.pdfx.AppUtils;

public class AppOpenAdManager {
    private final static String TAG = "AppOpenAdManager";
    private String AD_UNIT_ID;
    private Context context;

    public AppOpenAdManager(Context context, String adUnitId) {
        this.context = context;
        AD_UNIT_ID = adUnitId;
    }

    public void FetchAd(AppOpenAd.AppOpenAdLoadCallback callback) {
        AdRequest adRequest = buildAdRequest();
        AppOpenAd.load(
                context, AD_UNIT_ID,
                adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                callback
        );
    }

    private AdRequest buildAdRequest() {
        return new AdRequest.Builder().build();
    }

    public void showAd(final AppOpenAd appOpenAd, FullScreenContentCallback callback) {
        if (appOpenAd != null) {
            appOpenAd.setFullScreenContentCallback(callback);
            appOpenAd.show((Activity) context);
        }
    }
}
