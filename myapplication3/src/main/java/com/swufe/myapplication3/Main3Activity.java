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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

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

                  Bundle bd1= (Bundle) msg.obj;
                   dollarRate=bd1.getFloat("dollar-rate",0f);
                   europeRate=bd1.getFloat("europe-rate",0f);
                   koreaRate=bd1.getFloat("korea-rate",0f);     //把bundle里面的值传回保存到sp里


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

        Bundle bundle=new Bundle();//用来储存汇率



        //获取网络数据
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"run:"+doc.title());//这里的title（）方法就是可以去获取网页里的标题
Elements tables =doc.getElementsByTag("table");//通过什么标签来返回数据Tag标签的意思
//            int i=1;
//            for(Element table:tables){
//               Log.i(TAG,"run:table["+i+"]"+table);
//                i++;          //循环去找汇率是第几个table 每行都会用run显示一次，加i作为计数
//          }
            Element table6=tables.get(0);//第一个就是第0个可能是数组？
            //获取td中的数据
            Elements tds=table6.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){  //该网页每一行有6个数据，都是td,现在要把每行第一个和第五个挑出来
                Element td1=tds.get(i);       //用循环去挑
                Element td2=tds.get(i+5);

                Log.i(TAG,"run:text="+td1.text()+"run:val="+td2.text());
String str1=td1.text();
String val=td2.text();

      if("美元".equals(str1)){
    bundle.putFloat("dollar-rate",100f/Float.parseFloat(val));
}
else if ("欧元".equals(str1)){
    bundle.putFloat("europe-rate",100f/Float.parseFloat(val));

}
else if("韩元".equals(str1)){
    bundle.putFloat("korea-rate",100f/Float.parseFloat(val));
}

            }

//            for(Element td:tds){
//                Log.i(TAG,"run:td="+td);//显示所有td
//                Log.i(TAG,"run:text="+td.text());//td.text()显示td中的text内容
//                Log.i(TAG,"run:html="+td.html());//超链接，图片
//
//
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //获取message对象，用于返回线程
        Message msg =handler.obtainMessage(5);//整数和之前传递数据一样，用于比对，和取件收件要写收件人取件人一样
//用于传输数据（看数据类型选择方法）
        msg.obj=bundle;
        handler.sendMessage(msg);//将msg对象中储存的数据传到main函数中去处理


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
