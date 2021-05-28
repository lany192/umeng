package com.umeng.soexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

public class PushMessageHandler extends UmengMessageHandler {

    /**
     * 通知的回调方法（通知送达时会回调）
     */
    @Override
    public void dealWithNotificationMessage(Context context, UMessage msg) {
        //调用super，会展示通知，不调用super，则不展示通知。
        super.dealWithNotificationMessage(context, msg);
    }

    /**
     * 自定义消息的回调方法
     */
    @Override
    public void dealWithCustomMessage(final Context context, final UMessage msg) {
        new Handler(Looper.getMainLooper()).post(() -> {
            //自定义消息处理后，如果需要统计处理结果，需主动调用
            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                //自定义消息的点击统计
                UTrack.getInstance(context).trackMsgClick(msg);
            } else {
                //自定义消息的忽略统计
                UTrack.getInstance(context).trackMsgDismissed(msg);
            }

            Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
        });
    }

    /**
     * 自定义通知栏样式的回调方法
     */
    @Override
    public Notification getNotification(Context context, UMessage msg) {
        switch (msg.builder_id) {
            case 1:
                Notification.Builder builder;
                if (Build.VERSION.SDK_INT >= 26) {
                    if (!UmengMessageHandler.isChannelSet) {
                        UmengMessageHandler.isChannelSet = true;
                        NotificationChannel chan = new NotificationChannel(UmengMessageHandler.PRIMARY_CHANNEL,
                                PushAgent.getInstance(context).getNotificationChannelName(),
                                NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager manager = (NotificationManager) context.getSystemService(
                                Context.NOTIFICATION_SERVICE);
                        if (manager != null) {
                            manager.createNotificationChannel(chan);
                        }
                    }
                    builder = new Notification.Builder(context, UmengMessageHandler.PRIMARY_CHANNEL);
                } else {
                    builder = new Notification.Builder(context);
                }
                RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
                        R.layout.notification_view);
                myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                myNotificationView.setImageViewResource(R.id.notification_small_icon,
                        getSmallIconId(context, msg));
                builder.setContent(myNotificationView)
                        .setSmallIcon(getSmallIconId(context, msg))
                        .setTicker(msg.ticker)
                        .setAutoCancel(true);

                return builder.getNotification();
            default:
                //默认为0，若填写的builder_id并不存在，也使用默认。
                return super.getNotification(context, msg);
        }
    }
}
