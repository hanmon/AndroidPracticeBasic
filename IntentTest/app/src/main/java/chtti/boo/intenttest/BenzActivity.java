package chtti.boo.intenttest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by hanmon on 2016/2/1.
 */
public class BenzActivity extends Activity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benz);
        imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.benz);
    }
}
