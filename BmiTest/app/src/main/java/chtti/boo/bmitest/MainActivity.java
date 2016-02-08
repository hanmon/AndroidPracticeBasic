package chtti.boo.bmitest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editTextHeight,editTextWeight;
    private ImageButton btnStart;
    private float valueHeight,valueWeight;
    //private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextHeight=(EditText)findViewById(R.id.editTextHeight);
        editTextWeight=(EditText)findViewById(R.id.editTextWeight);
        btnStart=(ImageButton)findViewById(R.id.imgBtnStart);
    }

    public void onClick(View view) {
        try {
            valueHeight = Float.valueOf(editTextHeight.getText().toString());
            valueWeight = Float.valueOf(editTextWeight.getText().toString());
            //            valueBmi = valueWeight / ((valueHeight / 100) * (valueHeight / 100));
            //            valueBmi = (float) ((Math.round(valueBmi / 0.1)) * 0.1);
            Bundle extras= new Bundle();
            extras.putFloat("height",valueHeight);
            extras.putFloat("weight",valueWeight);
            Intent intent = new Intent(this, BmiActivity.class);
            intent.putExtras(extras);
            startActivity(intent);

        }
        catch(Exception e){
            Toast.makeText(this,R.string.bmi_error_message,Toast.LENGTH_SHORT).show();
        }
    }


}
