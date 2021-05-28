package com.umeng.soexample;

import android.app.Application;

import com.github.lany192.umeng.Config;
import com.github.lany192.umeng.UmengHelper;
import com.umeng.soexample.push.UmengNotificationService;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //使用时根据自己的实际情况进行修改
        Config config = new Config();
        config.setFileProvider("com.umeng.soexample.fileprovider");
        config.setUmeng("609b3eecc9aacd3bd4d0ccd2", "umeng", "5c359d6b5fb2e8f0fd01c5a95357174e");
        config.setQq("101830139", "5d63ae8858f1caab67715ccd6c18d7a5");
        config.setWeiXin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        config.setWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");

        UmengHelper.getInstance().init(this, config,
                new PushMessageHandler(), new PushNotificationClickHandler(),
                UmengNotificationService.class);
    }
}
