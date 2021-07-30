package com.platform.pinpin.app.platformreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.WebView;

public class PlatformNetworkReciever extends BroadcastReceiver {
    String platform_oldURL;
    WebView platform_WebView;

    public PlatformNetworkReciever(WebView platform_WebView) {
        this.platform_WebView = platform_WebView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager platform_Manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo platform_NetworkInfo = platform_Manager.getActiveNetworkInfo();

        boolean isPlatformNetworkConnected = platform_NetworkInfo != null && platform_NetworkInfo.isConnectedOrConnecting();
        if (isPlatformNetworkConnected) {
            if (platform_oldURL != null) platform_WebView.loadUrl(platform_oldURL);
        }
        else {
            platform_oldURL = platform_WebView.getUrl();
            platform_WebView.loadUrl("file:///android_asset/index.html");
        }
    }
}
