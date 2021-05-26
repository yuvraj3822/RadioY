package com.example.radioy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import java.io.IOException;


public class RadioService extends JobIntentService {
    CustomPlay customPlay;

    @Override
    public void onCreate(){
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());

        String url = "https://rfcmedia.streamguys1.com/70hits.aac"; // your URL here
//         customMediaPlayer = new CustomMediaPlayer ();
//        customMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//

        Log.e("service","oncreate");
       customPlay = CustomPlay.getInstance();
        Log.e("service","near data source");

        try {
            customPlay.setDataSource(url);
            customPlay.setLooping(true);
            customPlay.prepare(); // might take long! (for buffering, etc)

        } catch (IOException e) {
            Log.e("exception",": "+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service","onstartcommand");
        customPlay.start();
        return START_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan.setLightColor(Color.BLUE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        }
        NotificationManager manager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        assert manager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(chan);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_home_black_24dp)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        customPlay.stop();
        customPlay.release();
        customPlay = null;
        CustomPlay.instance = null;
        Log.e("service","stop");


    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        
    }


}