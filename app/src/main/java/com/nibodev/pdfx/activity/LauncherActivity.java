package com.nibodev.pdfx.activity;

import static com.nibodev.pdfx.AppUtils.isDebugBuild;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.nibodev.pdfx.AppUtils;
import com.nibodev.pdfx.Constants;
import com.nibodev.pdfx.R;
import com.nibodev.pdfx.ads.AppOpenAdManager;

import static com.nibodev.pdfx.Constants.TAG;

public class LauncherActivity extends AppCompatActivity {

    private Handler handler;
    private AppOpenAd appOpenAd;
    private AppOpenAdManager appOpenAdManager;

    // events
    private final int MSG_AD_LOADED = 100;
    private final int MSG_AD_LOAD_FAILED = 101;
    private final int MSG_DISMISS_AD = 102;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launcher);

        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_AD_LOADED:
                        showAd(); break;
                    case MSG_AD_LOAD_FAILED:
                    case MSG_DISMISS_AD:
                        landToHomeScreen(); break;
                }
            }
        };


        // Don't request for ad if network state is not connected
        if (AppUtils.isNetworkConnected(this)) {
            loadAppOpenAd();
        } else {
            handler.postDelayed(this::landToHomeScreen, 2000);
        }

    }

    private void showAd() {
        handler.postDelayed(() -> {
            appOpenAdManager.showAd(appOpenAd, new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    handler.sendMessage(buildMessage(MSG_DISMISS_AD));
                }
            });
        }, 2000);
    }


    private Message buildMessage(int msg) {
        Message message = Message.obtain();
        message.what = msg;
        return message;
    }

    private void loadAppOpenAd() {
        appOpenAdManager = new AppOpenAdManager(this, getString(R.string.app_open_ad_id));
        appOpenAdManager.FetchAd(new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                super.onAdLoaded(appOpenAd);
                LauncherActivity.this.appOpenAd = appOpenAd;
                handler.sendMessage(buildMessage(MSG_AD_LOADED));

                if (isDebugBuild()) {
                    Log.d(TAG, "onAdLoaded: app open ad loaded: " + appOpenAd);
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                handler.sendMessage(buildMessage(MSG_AD_LOAD_FAILED));

                if (isDebugBuild()) {
                    Log.d(TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                    Toast.makeText(LauncherActivity.this, "Could not load app open ad: " + loadAdError.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void landToHomeScreen() {
        handler.postDelayed(() -> {
            finish();
            Intent intent = new Intent(LauncherActivity.this, PdfViewerActivity.class);
            startActivity(intent);
        }, 500);
    }
}
