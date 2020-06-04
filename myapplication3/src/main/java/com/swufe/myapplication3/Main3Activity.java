package com.swufe.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
   EditText put;
   TextView show;
   Button dollar;
   Button europe;
   Button korea;
    float dollarRate=6.7f;
    float europeRate=1/11f;
    float koreaRate=500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3); //如果R变红 是res里面的代码出错，不知道具体是哪个文件的时候可以一个个删除，排除法
   put=(EditText) findViewById(R.id.Put);
   show=(TextView)findViewById(R.id.ShowResult);
   dollar=(Button)findViewById(R.id.dollar);
   europe=(Button)findViewById(R.id.europe);
   korea=(Button)findViewById(R.id.hanguo);

    }
    public void onClick(View btn) {
        Float value=0f;
       String str=put.getText().toString();
    if(str.length()>0){
   value=Float.parseFloat(str);
   }
else{
        Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
    }
        if(btn.getId()==R.id.dollar){
float result=value*dollarRate;
show.setText(""+result);
}
else if (btn.getId()==R.id.europe){
            float result=value*europeRate;
            show.setText(""+result);
}
    else{
            float result=value*koreaRate;
            show.setText(""+result);

}
    }

//    public void openOne(View btn){
//        Intent config = new Intent(this,Main4Activity.class);//和最后一句组成一个跳转功能，从当前页面到目的界面
//       config.putExtra("dollar_key",dollarRate);
//        config.putExtra("europe_key",europeRate);
//        config.putExtra("korea_key",koreaRate); //将值传过去
//
////startActivity(config);
//  startActivityForResult(config,1);//请求代码
//    }


}
