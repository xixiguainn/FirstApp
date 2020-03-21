package com.swufe.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView out;
    EditText inp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    out=(TextView) findViewById(R.id.textView2);
    inp=(EditText) findViewById(R.id.editText);

    Button btn=(Button)findViewById(R.id.button2);


    }
public void btnClick(View btn){

String str=inp.getText().toString();
    int number=Integer.parseInt( str);
    out.setText("结果为"+(number*9/5+32)+"°F");



    }


}

