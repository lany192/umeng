package com.umeng.soexample;

import android.content.Context;
import android.widget.Toast;

import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

/**
 * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
 * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
 * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
 */
public class PushNotificationClickHandler extends UmengNotificationClickHandler {

    @Override
    public void launchApp(Context context, UMessage msg) {
        super.launchApp(context, msg);
    }

    @Override
    public void openUrl(Context context, UMessage msg) {
        super.openUrl(context, msg);
    }

    @Override
    public void openActivity(Context context, UMessage msg) {
        super.openActivity(context, msg);
    }

    @Override
    public void dealWithCustomAction(Context context, UMessage msg) {
        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
    }
}
