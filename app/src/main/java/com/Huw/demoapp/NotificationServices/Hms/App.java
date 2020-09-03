package com.Huw.demoapp.NotificationServices.Hms;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;



public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    Constant.NotificationChannel1.ID,
                    Constant.NotificationChannel1.NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription(Constant.NotificationChannel1.DESCRIPTION);

            NotificationChannel channel2 = new NotificationChannel(
                    Constant.NotificationChannel2.ID,
                    Constant.NotificationChannel2.NAME,
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription(Constant.NotificationChannel2.DESCRIPTION);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}

