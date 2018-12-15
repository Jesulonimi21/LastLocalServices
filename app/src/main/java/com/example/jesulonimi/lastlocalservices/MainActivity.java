package com.example.jesulonimi.lastlocalservices;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Intent i;
MyService myService;
ServiceConnection serviceConnection;
Boolean isBound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i=new Intent(MainActivity.this,MyService.class);
    }
    public void bindService(View v){
        if(serviceConnection==null){
            serviceConnection=new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    MyService.MyServiceBinder serviceBinder=(MyService.MyServiceBinder) service;
                    myService=serviceBinder.getService();
                    isBound=true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
isBound=false;
                }
            };
        }
        bindService(i,serviceConnection,BIND_AUTO_CREATE);
    }

    public void startService(View v){
       startService(i);
    }

    public void stopService(View v){
        stopService(i);
    }
    public void unbindService(View v){
        unbindService(serviceConnection);
        isBound=false;
    }

    public void updateText(View v){
        TextView t1=(TextView) findViewById(R.id.myText);
        if(isBound==true){
            t1.setText(Html.fromHtml("<bstyle=\"color:blue\">the randum number is "+myService.getRandomNumber()+"</b>"));
        }else{
            t1.setText("Service not bound");
        }
    }
}
