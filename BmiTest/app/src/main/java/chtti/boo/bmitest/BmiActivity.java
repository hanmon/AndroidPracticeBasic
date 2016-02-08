package chtti.boo.bmitest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by hanmon on 2016/2/7.
 */
public class BmiActivity extends Activity {
    private TextView textBmiResult;
    private float valueHeight,valueWeight,valueBmi;
    private String strBmiRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_result);
        textBmiResult=(TextView)findViewById(R.id.textBmiResult);
        Bundle extras=getIntent().getExtras();
        valueHeight=extras.getFloat("height")/100;
        valueWeight=extras.getFloat("weight");
//        valueBmi=(float)(Math.round((valueWeight/Math.pow(valueHeight,2))/0.1)*0.1);
        valueBmi=(float)(valueWeight/Math.pow(valueHeight,2));
        NumberFormat nf=new DecimalFormat("##.00");
        strBmiRecord=String.format("%s %s %s",textBmiResult.getText().toString(),nf.format(valueBmi),getBmiMsg(valueBmi));
        textBmiResult.setText(String.valueOf(strBmiRecord));
    }

    private String getBmiMsg(float bmiValue){
        String msg="";
        if(bmiValue>0 && bmiValue<20){
            msg=getResources().getString(R.string.bmi_low);
        }
        else if(bmiValue>=20 && bmiValue<26){
            msg=getResources().getString(R.string.bmi_normal);
        }
        else if(bmiValue>=26 && bmiValue<30){
            msg=getResources().getString(R.string.bmi_high);
        }
        else if(bmiValue>=30 && bmiValue<40){
            msg=getResources().getString(R.string.bmi_overhigh);
        }
        else if(bmiValue>=40 && bmiValue<100){
            msg=getResources().getString(R.string.bmi_dangerous);
        }
        else{
            msg=getResources().getString(R.string.bmi_value_error);
        }
        return msg;

    }

}
