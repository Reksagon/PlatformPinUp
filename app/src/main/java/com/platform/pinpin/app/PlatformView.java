package com.platform.pinpin.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.platform.pinpin.app.platformconst.PlatformConst;
import com.platform.pinpin.app.platformreciever.PlatformNetworkReciever;
import com.platform.pinpin.app.platformwebclients.PlatformChromeClient;
import com.platform.pinpin.app.platformwebclients.PlatformWebClient;
import com.ramijemli.percentagechartview.PercentageChartView;

import static android.app.Activity.RESULT_OK;


public class PlatformView extends Fragment {

    FirebaseRemoteConfig platform_Firebase_Remote_Config;
    PercentageChartView platform_ProgressBar;
    WebView platform_WebView;

    public PlatformView(FirebaseRemoteConfig platform_Firebase_Remote_Config) {
        this.platform_Firebase_Remote_Config = platform_Firebase_Remote_Config;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_platform_view, container, false);

        platform_ProgressBar = root.findViewById(R.id.platformProgressBar);
        platform_WebView = root.findViewById(R.id.plaftormWView);


        CookieManager platform_cookieManager = CookieManager.getInstance();
        CookieManager.setAcceptFileSchemeCookies(true);
        platform_cookieManager.setAcceptThirdPartyCookies(platform_WebView, true);

        platform_WebView.getSettings().setJavaScriptEnabled(true);
        platform_WebView.getSettings().setSupportZoom(true);
        platform_WebView.getSettings().setBuiltInZoomControls(false);
        platform_WebView.getSettings().setLoadWithOverviewMode(true);
        platform_WebView.getSettings().setDomStorageEnabled(true);
        platform_WebView.getSettings().setUseWideViewPort(true);
        platform_WebView.getSettings().setLoadsImagesAutomatically(true);
        platform_WebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        platform_WebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        platform_WebView.setBackgroundColor(Color.WHITE);
        //platform_WebView.loadUrl(platform_Firebase_Remote_Config.getString("platform_url"));
        platform_WebView.loadUrl("https://smallseotools.com/reverse-image-search");

        platform_WebView.setWebChromeClient(new PlatformChromeClient(getActivity(), platform_ProgressBar));
        platform_WebView.setWebViewClient(new PlatformWebClient());

        PlatformNetworkReciever platform_Network_Reciever = new PlatformNetworkReciever(platform_WebView);
        IntentFilter platform_intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        platform_intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        getActivity().registerReceiver(platform_Network_Reciever, platform_intentFilter);

        OnBackPressedCallback callback = new OnBackPressedCallback(true ) {
            @Override
            public void handleOnBackPressed() {
                if(platform_WebView.canGoBack()) platform_WebView.goBack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == PlatformConst.reqCode)
            if (PlatformConst.platformCallBackUri == null) return;

        if (resultCode != RESULT_OK) {
            PlatformConst.platformCallBackUri.onReceiveValue(null);
            return;
        }

        Uri platform_Result = (data == null)? PlatformConst.platformUrl : data.getData();

        if(platform_Result != null && PlatformConst.platformCallBackUri != null)
            PlatformConst.platformCallBackUri.onReceiveValue(new Uri[]{platform_Result});
        else if(PlatformConst.platformCallBackUri != null)
            PlatformConst.platformCallBackUri.onReceiveValue(new Uri[] {PlatformConst.platformUrl});
        PlatformConst.platformCallBackUri = null;
        super.onActivityResult(requestCode, resultCode, data);
    }


}