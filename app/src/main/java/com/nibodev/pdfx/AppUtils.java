package com.nibodev.pdfx;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppUtils {
    public static boolean isNetworkConnected(Context context) {
        boolean connected = false;
        if (context instanceof Activity) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null)
            connected = netInfo.isAvailable() && netInfo.isConnectedOrConnecting();
        }
        return connected;
    }


    public static boolean isDebugBuild() {
        return BuildConfig.DEBUG;
    }

}
