package com.github.lany192.umeng;

import android.app.Application;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.socialize.PlatformConfig;

public class UmengHelper {
    private final String TAG = "UmengHelper";
    private volatile static UmengHelper instance = null;

    private UmengHelper() {
    }

    public static UmengHelper getInstance() {
        if (instance == null) {
            synchronized (UmengHelper.class) {
                if (instance == null) {
                    instance = new UmengHelper();
                }
            }
        }
        return instance;
    }

    public void init(Application application, Config config,
                     UmengMessageHandler messageHandler, UmengNotificationClickHandler clickHandler,
                     Class clazz) {
        //微信设置
        PlatformConfig.setWeixin(config.getWxAppKey(),config.getWxSecret());
        PlatformConfig.setWXFileProvider(config.getFileProvider());
        //QQ设置
        PlatformConfig.setQQZone(config.getQqAppKey(),config.getQqSecret());
        PlatformConfig.setQQFileProvider(config.getFileProvider());
        //微博设置
        PlatformConfig.setSinaWeibo(config.getWbAppKey(),config.getWbSecret(), "http://sns.whalecloud.com");
        PlatformConfig.setSinaFileProvider(config.getFileProvider());
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        //UMConfigure.init(Context context,String appkey,String channel,int deviceType,String pushSecret);
        UMConfigure.init(application, config.getUmengAppKey(), config.getChannel(), UMConfigure.DEVICE_TYPE_PHONE, config.getUmengPushSecret());
        //集成umeng-crash-vx.x.x.aar，则需要关闭原有统计SDK异常捕获功能
        MobclickAgent.setCatchUncaughtExceptions(false);
        //统计SDK是否支持采集在子进程中打点的自定义事件，默认不支持
        UMConfigure.setProcessEvent(true);//支持多进程打点

        // 页面数据采集模式
        // setPageCollectionMode接口参数说明：
        // 1. MobclickAgent.PageMode.AUTO: 建议大多数用户使用本采集模式，SDK在此模式下自动采集Activity
        // 页面访问路径，开发者不需要针对每一个Activity在onResume/onPause函数中进行手动埋点。在此模式下，
        // 开发者如需针对Fragment、CustomView等自定义页面进行页面统计，直接调用MobclickAgent.onPageStart/
        // MobclickAgent.onPageEnd手动埋点即可。此采集模式简化埋点工作，唯一缺点是在Android 4.0以下设备中
        // 统计不到Activity页面数据和各类基础指标(提示：目前Android 4.0以下设备市场占比已经极小)。

        // 2. MobclickAgent.PageMode.MANUAL：对于要求在Android 4.0以下设备中也能正常采集数据的App,可以使用
        // 本模式，开发者需要在每一个Activity的onResume函数中手动调用MobclickAgent.onResume接口，在Activity的
        // onPause函数中手动调用MobclickAgent.onPause接口。在此模式下，开发者如需针对Fragment、CustomView等
        // 自定义页面进行页面统计，直接调用MobclickAgent.onPageStart/MobclickAgent.onPageEnd手动埋点即可。

        // 如下两种LEGACY模式不建议首次集成友盟统计SDK的新用户选用。
        // 如果您是友盟统计SDK的老用户，App需要从老版本统计SDK升级到8.0.0版本统计SDK，
        // 并且：您的App之前MobclickAgent.onResume/onPause接口埋点分散在所有Activity
        // 中，逐个删除修改工作量很大且易出错。
        // 若您的App符合以上特征，可以选用如下两种LEGACY模式，否则不建议继续使用LEGACY模式。
        // 简单来说，升级SDK的老用户，如果不需要手动统计页面路径，选用LEGACY_AUTO模式。
        // 如果需要手动统计页面路径，选用LEGACY_MANUAL模式。
        // 3. MobclickAgent.PageMode.LEGACY_AUTO: 本模式适合不需要对Fragment、CustomView
        // 等自定义页面进行页面访问统计的开发者，SDK仅对App中所有Activity进行页面统计，开发者需要在
        // 每一个Activity的onResume函数中手动调用MobclickAgent.onResume接口，在Activity的
        // onPause函数中手动调用MobclickAgent.onPause接口。此模式下MobclickAgent.onPageStart
        // ,MobclickAgent.onPageEnd这两个接口无效。

        // 4. MobclickAgent.PageMode.LEGACY_MANUAL: 本模式适合需要对Fragment、CustomView
        // 等自定义页面进行手动页面统计的开发者，开发者如需针对Fragment、CustomView等
        // 自定义页面进行页面统计，直接调用MobclickAgent.onPageStart/MobclickAgent.onPageEnd
        // 手动埋点即可。开发者还需要在每一个Activity的onResume函数中手动调用MobclickAgent.onResume接口，
        // 在Activity的onPause函数中手动调用MobclickAgent.onPause接口。
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        //初始化推送SDK----------------------------------------------------------------------------
        PushAgent pushAgent = PushAgent.getInstance(application);
        //sdk开启通知声音
        pushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // sdk关闭通知声音
        // mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
        // mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);
        // mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);

        pushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        //使用自定义的NotificationHandler
        pushAgent.setNotificationClickHandler(clickHandler);

        //注册推送服务 每次调用register都会回调该接口
        pushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                Log.i(TAG, "device token: " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i(TAG, "register failed: " + s + " " + s1);
            }
        });

        //使用完全自定义处理
        pushAgent.setPushIntentServiceClass(clazz);

        //小米通道
        //MiPushRegistar.register(this, XIAOMI_ID, XIAOMI_KEY);
        //华为通道
        //HuaWeiRegister.register(this);
        //魅族通道
        //MeizuRegister.register(this, MEIZU_APPID, MEIZU_APPKEY);
    }
}
