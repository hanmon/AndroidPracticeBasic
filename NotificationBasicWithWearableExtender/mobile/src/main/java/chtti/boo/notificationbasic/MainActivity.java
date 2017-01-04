package chtti.boo.notificationbasic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
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
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        Bitmap bmp= BitmapFactory.decodeResource(getResources(),R.drawable.apple_logo);
        //intent相關設定

        Uri uri=Uri.parse("http://www.apple.com/tw/shop/buy-iphone/iphone-7");
        Uri telUri=Uri.parse("tel:0911826012");
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        Intent intent2=new Intent(Intent.ACTION_VIEW,telUri);
        //設定Intent Flags
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                Intent.FLAG_ACTIVITY_SINGLE_TOP |
//                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int requestCode=1;
        PendingIntent pendingIntent=PendingIntent.getActivity(
                context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2=PendingIntent.getActivity(
                context, requestCode+1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        //設定action
        NotificationCompat.Action action=new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_share
                ,"Apple新機一覽",pendingIntent).build();
        NotificationCompat.Action action2=new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_call
                ,"打電話詢問客服",pendingIntent2).build();
        //加入Werable Extender
        NotificationCompat.WearableExtender extender=new NotificationCompat.WearableExtender();
        extender.addAction(action)
                .addAction(action2)
                .setBackground(bmp);
        //新增一個卡片
        Notification newPage=new NotificationCompat.Builder(context)
                .setContentTitle("iPhone7開賣了")
                .setContentText("就在9/16")
                .build();
        extender.addPage(newPage);
        long vibrate[]={100,400,100,400};
        builder.setSmallIcon(R.drawable.info)
                .setLargeIcon(bmp)
                .setContentTitle("Apple新機來了")
                .setContentText("iPhone7/iPhone7 Plus")
                .setTicker("趕快預約Apple新機!")
                .setVibrate(vibrate)
                .extend(extender);
        if(checkBox.isChecked()){
            builder.setPriority(Notification.PRIORITY_MAX);
        }
        Notification notification=builder.build();

        notificationManager.notify(nid,notification);
    }
}
