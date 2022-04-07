package com.nibodev.pdfx.activity;

import static com.nibodev.pdfx.AppUtils.isDebugBuild;

import android.os.Bundle;
import android.util.Log;
import android.view.FrameMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.nibodev.pdfx.AppUtils;
import com.nibodev.pdfx.R;
import com.nibodev.pdfx.ads.AppOpenAdManager;

import static com.nibodev.pdfx.Constants.TAG;

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        WindowInsetsControllerCompat insetsController = ViewCompat
//                .getWindowInsetsController(getWindow().getDecorView());
//
//        if (insetsController != null) {
//            insetsController.hide(WindowInsetsCompat.Type.statusBars());
//            insetsController.hide(WindowInsetsCompat.Type.tappableElement());
//            insetsController.hide(WindowInsetsCompat.Type.systemBars());
//        }

        // get the fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launcher);


        // Don't request for ad if network state is not connected
        if (!AppUtils.isNetworkConnected(this)) {
            return;
        }
        AppOpenAdManager appOpenAdManager = new AppOpenAdManager(this, getString(R.string.app_open_ad_id));
        appOpenAdManager.FetchAd(new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                super.onAdLoaded(appOpenAd);
                appOpenAdManager.showAd(appOpenAd);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                if (isDebugBuild()) {
                    Log.d(TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                    Toast.makeText(LauncherActivity.this, "Could not load app open ad: " + loadAdError.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
