package com.swufe.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Main4Activity extends AppCompatActivity {
    EditText Cgdollar;
    EditText Cgeurope;
    EditText Cgkorea;
    Button putback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Cgdollar=(EditText) findViewById(R.id.Cgdollar);
        Cgeurope=(EditText)findViewById(R.id.Cgeurope);
        Cgkorea=(EditText)findViewById(R.id.Cgkorea);
        putback=(Button)findViewById(R.id.putback);


        Intent intent = getIntent();
        float dollar2 = intent.getFloatExtra("dollar_key",0.0f);
        float euro2 = intent.getFloatExtra("europe_key",0.0f);
        float korea2 = intent.getFloatExtra("korea_key",0.0f);////得到传过来的值


        Cgdollar.setText(""+dollar2);
        Cgeurope.setText(""+euro2);
        Cgkorea.setText(""+korea2);
    }

public void save (View btn){
        float newDollar=Float.parseFloat(Cgdollar.getText().toString());
        float newEurope=Float.parseFloat(Cgeurope.getText().toString());
        float newKorea=Float.parseFloat(Cgkorea.getText().toString()); //获取数据

     Intent intent=getIntent();
     Bundle bd1 =new Bundle();
     bd1.putFloat("key_dollar",newDollar);//用Intent 和Bundle类 先创建intent 和bundle对象
     bd1.putFloat("key_europe",newEurope);//再调用extra方法将数据打包传过去
     bd1.putFloat("key_korea",newKorea);
     intent.putExtras(bd1);
   setResult(2,intent);//响应代码和回传对象
    finish();//回到调用页面
    }






}
