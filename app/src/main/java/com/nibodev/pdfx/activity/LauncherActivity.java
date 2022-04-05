package com.nibodev.pdfx.activity;

import android.os.Bundle;
import android.view.FrameMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.nibodev.pdfx.R;

import java.util.PrimitiveIterator;

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowInsetsControllerCompat insetsController = ViewCompat
                .getWindowInsetsController(getWindow().getDecorView());

        if (insetsController != null) {
            insetsController.hide(WindowInsetsCompat.Type.statusBars());
            insetsController.hide(WindowInsetsCompat.Type.tappableElement());
            insetsController.hide(WindowInsetsCompat.Type.systemBars());
        }

        setContentView(R.layout.activity_launcher);
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
