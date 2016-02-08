package chtti.boo.intenttest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by hanmon on 2016/2/1.
 */
public class BmwActivity extends Activity {
    private ImageView imageView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmw);
        imageView=(ImageView)findViewById(R.id.imageView2);
        imageView.setImageResource(R.drawable.bmw);
    }
}
