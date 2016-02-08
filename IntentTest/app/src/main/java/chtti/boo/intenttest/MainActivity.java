package chtti.boo.intenttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
    }

    public void onClick(View view){
        Intent intent=new Intent();
        switch(view.getId()){
           case R.id.btnBenz:
               intent.setClass(context, chtti.boo.intenttest.BenzActivity.class);
               startActivity(intent);
               break;
           case R.id.btnBmw:
               intent.setClass(context, chtti.boo.intenttest.BmwActivity.class);
               startActivity(intent);
               break;
           case R.id.btnCar:
               intent.setAction("good.car");
               intent=Intent.createChooser(intent,"選一個App來執行");
               startActivity(intent);
               break;
       }
    }

}
