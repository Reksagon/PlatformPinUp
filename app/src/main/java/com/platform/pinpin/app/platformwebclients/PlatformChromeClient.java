package com.platform.pinpin.app.platformwebclients;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.core.content.FileProvider;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.platform.pinpin.app.platformconst.PlatformConst;
import com.ramijemli.percentagechartview.PercentageChartView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlatformChromeClient extends WebChromeClient {
    Activity platform_activity;
    PercentageChartView platformProgressBar;

    public PlatformChromeClient(Activity platform_activity, PercentageChartView platformProgressBar) {
        this.platform_activity = platform_activity;
        this.platformProgressBar = platformProgressBar;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        platformProgressBar.setProgress(newProgress,true);
        if(newProgress < 100 && platformProgressBar.getVisibility() == PercentageChartView.GONE) {
            platformProgressBar.setVisibility(PercentageChartView.VISIBLE);
            view.setAlpha(0.2f);
        }
        if(newProgress == 100) {
            platformProgressBar.setVisibility(PercentageChartView.GONE);
            view.setAlpha(1f);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        Dexter.withContext(platform_activity)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).check();
        PlatformConst.platformCallBackUri = filePathCallback;
        Intent platform_Intent_One = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File platform_File = null;
        try {
            platform_File = platformImg();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(platform_File != null)
        {
            Uri platformForFile = FileProvider.getUriForFile(platform_activity,
                    platform_activity.getApplication().getPackageName() +
                    ".provider", platform_File);

            PlatformConst.platformUrl = platformForFile;
            platform_Intent_One.putExtra(MediaStore.EXTRA_OUTPUT, platformForFile);
            platform_Intent_One.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent platform_Intent_Two = new Intent(Intent.ACTION_GET_CONTENT);
            platform_Intent_Two.addCategory(Intent.CATEGORY_OPENABLE);
            platform_Intent_Two.setType("image/*");

            Intent[] platform_Intets = {platform_Intent_Two};

            Intent platform_Choocer_Intent = new Intent(Intent.ACTION_CHOOSER);
            platform_Choocer_Intent.putExtra(Intent.EXTRA_INTENT, platform_Intent_One);
            platform_Choocer_Intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, platform_Intets);

            platform_activity.startActivityForResult(platform_Choocer_Intent, PlatformConst.reqCode);

            return true;
        }
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }

    File platformImg() throws IOException {
        String yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String platformImg = "Platform" + yyyyMMddHHmmss + "_";
        File platformFiletoDir = platform_activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(platformImg, ".jpg", platformFiletoDir);
    }
}
