package com.w2njl.VillanovaCovid19;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "covidServiceChannel";
    public static final String CHANNEL_1_ID = "covidAlert";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Covid Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationChannel covidAlert = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Covid RIS Alert",
                    NotificationManager.IMPORTANCE_HIGH
            );



            covidAlert.setDescription("Covid Alert Channel");
            covidAlert.enableLights(true);
            covidAlert.setVibrationPattern(new long[]{0, 300, 250, 300, 250, 300});
            covidAlert.enableVibration(true);
            covidAlert.setLightColor(Color.BLUE);



            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
            manager.createNotificationChannel(covidAlert);
        }
    }
}
