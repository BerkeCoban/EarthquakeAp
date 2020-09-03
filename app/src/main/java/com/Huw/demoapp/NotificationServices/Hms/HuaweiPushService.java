package com.Huw.demoapp.NotificationServices.Hms;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.Huw.demoapp.AccountActivity;
import com.Huw.demoapp.R;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

import java.util.Map;

public class HuaweiPushService extends HmsMessageService {
    private static final String TAG = "HuaweiPushService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);





        if(remoteMessage == null){
            Log.e(TAG, "onMessageReceived() remoteMessage is null");
        }

        Map<String, String> notificationData = remoteMessage.getDataOfMap();
        if(notificationData.isEmpty()){
            Log.e(TAG, "onMessageReceived: notification data is empty");
            return;
        }

        int icon = R.mipmap.ic_launcher;
        String title = notificationData.get("title");
        String text = notificationData.get("text");
        String channelId = notificationData.get("channel_id");

        if(channelId == null){
            channelId = Constant.NotificationChannel2.ID;
        }
        if(!channelId.equals(Constant.NotificationChannel1.ID)){
            channelId = Constant.NotificationChannel2.ID;
        }

        Intent intent = new Intent(this, AccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Notification notification = new NotificationCompat
                .Builder(this, channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setColor(this.getResources().getColor(R.color.colorPrimary))
                .build();

        notificationManager.notify(1, notification);




    }
}