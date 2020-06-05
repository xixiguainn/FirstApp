package com.swufe.myapplication3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main3Activity extends AppCompatActivity implements Runnable{//implements Runnable 是开启多线程要实现的接口
    private final String TAG="Rate";
     EditText put;
   TextView show;
   Button dollar;
   Button europe;
   Button korea;
   Handler handler;
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
        SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        //获取sp里保存的数据
        dollarRate=sharedPreferences.getFloat("dollar_rate",0.0f);
        europeRate=sharedPreferences.getFloat("europe_rate",0.0f);
        koreaRate=sharedPreferences.getFloat("korea_rate",0.0f);




        Thread t =new Thread(this);
        //开启子线程  t代表线程 可以去调用run方法
    t.start();//开始调用

        handler=new Handler(){
            @Override //改写类 用来处理子线程收到的信息
            public void handleMessage(Message msg){
               if(msg.what==5){//如果what==5说明消息来源于run中的msg对象

                   String str= (String) msg.obj;
               }

                super.handleMessage(msg);

            }
        };




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

    public void openOne(View btn){
        Intent config = new Intent(this,Main4Activity.class);//和最后一句组成一个跳转功能，从当前页面到目的界面
       config.putExtra("dollar_key",dollarRate);
        config.putExtra("europe_key",europeRate);
        config.putExtra("korea_key",koreaRate); //将值传过去

//startActivity(config);
  startActivityForResult(config,1);//请求代码
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//传过去的请求代码是几，回传的代码是几，然后返回相应值
        if(requestCode==1&&resultCode==2){
            Bundle bundle =data.getExtras();
            dollarRate =bundle.getFloat("key_dollar",0.1f);
            europeRate =bundle.getFloat("key_europe",0.1f);
            koreaRate =bundle.getFloat("key_korea",0.1f);

            SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("europe_rate",europeRate);
            editor.putFloat("korea_rate",koreaRate);
            editor.commit();//将索引对应的值传给对应的变量
        }




        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void run(){//子线程中运行的方法

    //获取message对象，用于返回线程
       Message msg =handler.obtainMessage(5);//整数和之前传递数据一样，用于比对，和取件收件要写收件人取件人一样
//用于传输数据（看数据类型选择方法）
        msg.obj="hello form run()";
        handler.sendMessage(msg);//将msg对象中储存的数据传到main函数中去处理


        //获取网络数据
        URL url= null;
        try {
            url = new URL("http://www.usd-cny.com/icbc.htm");

            HttpURLConnection http= (HttpURLConnection) url.openConnection();//相当于打开链接后按回车 1.有可能出现网络链接问题之类的要写异常处理
            InputStream in=http.getInputStream();//java中传输数据用输入输出流 这是输入流 因为获取的是网页的源代码，都是java代码



            String html=inputStream25String(in);//调用inputStream25String()方法将输入流转字符串

            Log.i(TAG,"run:htlm="+html);


        } catch (MalformedURLException e) {
            e.printStackTrace();//有可能出现网页打不开之类的需要捕获异常
            Log.i(TAG,"run:htlm="+"2222");
        }

     catch (IOException e) {                        //2.还有类型不匹配需要转换前面是http 后面是url
         e.printStackTrace();
         Log.i(TAG,""+e);

        }



    }



    //到网上找个方法将inputStream转化成String类型输出
private  String inputStream25String(InputStream inputStream) throws IOException {
    final int bufferSize = 1024;
    final char[] buffer = new char[bufferSize];
    final StringBuilder out = new StringBuilder();
    Reader in = new InputStreamReader(inputStream,"gb2312");//不一定原网页中文就是utf-8去网站源代码上找
    for(;;){
        int rsz=in.read(buffer,0,buffer.length);
        if(rsz<0)
        break;
        out.append(buffer,0,rsz);
    }

    return out.toString();


}

}
