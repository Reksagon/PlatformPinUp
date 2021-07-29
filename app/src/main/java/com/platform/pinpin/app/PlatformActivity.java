package com.platform.pinpin.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.platform.pinpin.app.platformconst.PlatformConst;

public class PlatformActivity extends AppCompatActivity {

    FirebaseRemoteConfig platform_Firebase_Remote_Config;
    PlatformView platformView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.platform_activity);

       /* platform_Firebase_Remote_Config = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings platform_firebaseRemoteConfigSettings = new FirebaseRemoteConfigSettings.Builder().build();
        platform_Firebase_Remote_Config.setDefaultsAsync(R.xml.platform_url);
        platform_Firebase_Remote_Config.setConfigSettingsAsync(platform_firebaseRemoteConfigSettings);

        if(!platform_Firebase_Remote_Config.getString("platform_url").equals("platform_url"))
        {

        }else{*/

        //}
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        platformView = new PlatformView(platform_Firebase_Remote_Config);
        fragmentTransaction.add(R.id.content_view, platformView);
        fragmentTransaction.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        platformView.onActivityResult(requestCode, resultCode, data);
    }
}