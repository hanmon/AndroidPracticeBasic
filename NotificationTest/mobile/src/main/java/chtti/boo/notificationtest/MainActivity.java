package chtti.boo.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static String TAG="NotificationTest";
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.btnWeather:
                Log.d(TAG,"Button Clicked");
                createNotification();
                break;
        }

    }

    private void createNotification(){
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification=new Notification();
        Notification.Builder builder=new Notification.Builder(this);

        long vibration[]={100,400,100,400};
        Uri uri=Uri.parse("http://www.cht.com.tw");
        Intent webIntent=new Intent(Intent.ACTION_VIEW,uri);
        PendingIntent pWebIntent= PendingIntent.getActivity(context,1,webIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Action action=new Notification.Action.Builder(android.R.drawable.ic_menu_share,"天氣",pWebIntent).build();
        builder.setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("今日天氣")
                .setContentText("晴時多雲")
                .setContentInfo("25度C")
                .setTicker("天氣")
                .setVibrate(vibration)
                .addAction(action);
        notification=builder.build();
        notificationManager.notify(100,notification);




    }
 }
