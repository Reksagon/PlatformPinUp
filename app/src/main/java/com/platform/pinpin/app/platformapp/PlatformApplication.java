package com.platform.pinpin.app.platformapp;

import com.onesignal.OneSignal;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class PlatformApplication extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.setAppId("7ec585b5-de12-4a7a-abca-ea9a4538cfa6");
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(getApplicationContext());

        YandexMetricaConfig platform_yandexMetricaConfig = YandexMetricaConfig.newConfigBuilder("f02b4137-74ec-4bc8-8d3f-049185fc6773").build();
        YandexMetrica.activate(getApplicationContext(), platform_yandexMetricaConfig);
        YandexMetrica.enableActivityAutoTracking(this);

    }
}
