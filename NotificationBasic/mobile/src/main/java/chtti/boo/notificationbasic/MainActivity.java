package chtti.boo.notificationbasic;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private NotificationManager notificationManager;
    private Context context;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        checkBox=(CheckBox)findViewById(R.id.chkboxHeadup);
        notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void onClick(View view){
            switch(view.getId()){
                case R.id.btnNotify:
                    createNotification(context,0);
                    break;
                case R.id.btnCancel:
                    notificationManager.cancelAll();
                    Toast.makeText(context,"通知被取消!",Toast.LENGTH_SHORT).show();
                    break;
            }

    }

    private void createNotification(Context context,int nid){
        Notification.Builder builder=new Notification.Builder(context);
        Bitmap bmp= BitmapFactory.decodeResource(getResources(),R.drawable.apple);
        long vibrate[]={100,400,100,400};
        builder.setSmallIcon(R.drawable.info)
                .setLargeIcon(bmp)
                .setContentTitle("Apple新機來了")
                .setContentText("iPhone7/iPhone7 Plus")
                .setTicker("趕快預約Apple新機!")
                .setVibrate(vibrate);
        if(checkBox.isChecked()){
            builder.setPriority(Notification.PRIORITY_MAX);
        }
        Notification notification=builder.build();

        notificationManager.notify(nid,notification);
    }
}
