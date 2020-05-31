package com.swufe.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView teamname;
    TextView score;
    Button add1;
    Button add2;
    Button add3;
    Button reset;//在java中声明这些变量
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
   teamname =(TextView) findViewById(R.id.TeamName);
   score=(TextView)findViewById(R.id.score);
   add1=(Button) findViewById(R.id.add1);
   add2=(Button) findViewById(R.id.add2);
   add3=(Button) findViewById(R.id.add3);
   reset=(Button) findViewById(R.id.reset);//从view中找到这些控件并转化成相应类型
    }
    public  void ad1(View btn){
   Addscore(1);
    }
    public  void ad2(View btn2){
Addscore(2);
    }
    public  void ad3(View btn3){
Addscore(3);
    }
public void Resetscore(View btn4){
        score.setText("0");
}

private void Addscore(int n){
       String oldscore= (String) score.getText();//使分数相加
      Integer newscore=Integer.parseInt(oldscore)+n;//int型才能相加

      score.setText(""+newscore);//要加一个双引号，因为这是String类型
    }
}

