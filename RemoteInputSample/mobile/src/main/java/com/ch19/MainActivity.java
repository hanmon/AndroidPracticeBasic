package com.ch19;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private Context context;
    private TextView textView;
    private ImageView imgView;
    private static final String MY_VOICE_KEY = "MY_VOICE_KEY";
    private static final String MY_CHOICE_KEY = "MY_CHOICE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        textView = (TextView) findViewById(R.id.textView);
        imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setImageResource(R.drawable.idevices);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check answer
        Bundle bundle = RemoteInput.getResultsFromIntent(getIntent());
        if (bundle != null) {
            if (bundle.getCharSequence(MY_VOICE_KEY) != null &&
                    !bundle.getCharSequence(MY_VOICE_KEY).equals("")) {
                String ans = bundle.getCharSequence(MY_VOICE_KEY).toString();
                if (ans.equalsIgnoreCase("iphone 7")) {
                    textView.setText("您選輕巧的iPhone7");
                    imgView.setImageResource(R.drawable.iphone7);
                } else if (ans.equalsIgnoreCase("iPhone 7 Plus")) {
                    textView.setText("您選大螢幕iPhone7 Plus");
                    imgView.setImageResource(R.drawable.iphone7plus);
                } else {
                    textView.setText(ans);
                    imgView.setImageResource(R.drawable.idevices);
                }
            }
//            else if(bundle.getCharSequence(MY_CHOICE_KEY) != null &&
//                    !bundle.getCharSequence(MY_CHOICE_KEY).equals("")) {
//                String ans = bundle.getCharSequence(MY_CHOICE_KEY).toString();
//                if(ans.equals("9")) {
//                    textView.setText(ans + " 答對了");
//                } else {
//                    textView.setText(ans + " 再想想看");
//                }
//            }

        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                createNotificationVoice();
                break;
//            case R.id.button2:
//                createNotificationChoice();
//                break;

        }

    }

    private void createNotificationVoice() {

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        long[] vibratepattern = {100, 400, 500, 400};

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.iphone);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pWebIntent = PendingIntent.getActivity(
                context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // RemoteInput 語音輸入
        RemoteInput ri = new RemoteInput.Builder(MY_VOICE_KEY)
                .setLabel("請說出您喜好的款式！")
                .setAllowFreeFormInput(true)
                .build();

        // Action
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                R.drawable.microphone_red, "回覆", pWebIntent)
                .addRemoteInput(ri)
                .build();

        // WearableExtender
        NotificationCompat.WearableExtender we =
                new NotificationCompat.WearableExtender();
        we.addAction(action);
        we.setBackground(BitmapFactory.decodeResource(getResources(),
                R.drawable.iphone));

        Notification builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.iphone) // 通知服務 icon
                .setLargeIcon(largeIcon)
                .setContentTitle("iPhone新手機您喜歡哪一款") // 標題
                .setContentText("iPhone7或iPhone7 Plus") // 內文
                .setContentInfo("買哪隻?") // 信息
                .setTicker("大抉擇") // Ticker 標題
                .setLights(0xFFFFFFFF, 1000, 1000) // LED
                .setVibrate(vibratepattern) // 震動
                .extend(we)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(100, builder);
    }
}
//    private void createNotificationChoice() {
//
//        NotificationManagerCompat notificationManager =
//                NotificationManagerCompat.from(context);
//
//        long[] vibratepattern = { 100, 400, 500, 400 };
//
//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
//                R.drawable.guess);
//
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pWebIntent = PendingIntent.getActivity(
//                context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // RemoteInput 一般輸入
//        RemoteInput ri = new RemoteInput.Builder(MY_CHOICE_KEY)
//                .setLabel("請選擇答案！")
//                .setAllowFreeFormInput(false)
//                .setChoices(new String[]{"1", "9"})
//                .build();
//
//        // Action
//        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
//                R.drawable.microphone_red, "回覆", pWebIntent)
//                .addRemoteInput(ri)
//                .build();
//
//        // WearableExtender
//        NotificationCompat.WearableExtender we =
//                new NotificationCompat.WearableExtender();
//        we.addAction(action);
//        we.setBackground(BitmapFactory.decodeResource(getResources(),
//                R.drawable.math));
//
//        Notification builder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.guess) // 通知服務 icon
//                .setLargeIcon(largeIcon)
//                .setContentTitle("數學題") // 標題
//                .setContentText("6 ÷ 2*(1+2)") // 內文
//                .setContentInfo("動動腦") // 信息
//                .setTicker("數學遊戲") // Ticker 標題
//                .setLights(0xFFFFFFFF, 1000, 1000) // LED
//                .setVibrate(vibratepattern) // 震動
//                .extend(we)
//                .setAutoCancel(true)
//                .build();
//
//        notificationManager.notify(101, builder);
//    }
//}
