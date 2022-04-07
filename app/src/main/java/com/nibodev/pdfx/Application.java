package com.nibodev.pdfx;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initMobileAdsSdk();
    }

    private void initMobileAdsSdk() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
    }
}
