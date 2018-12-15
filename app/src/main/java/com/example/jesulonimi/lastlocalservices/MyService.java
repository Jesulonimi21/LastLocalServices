package com.example.jesulonimi.lastlocalservices;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

public class MyService extends Service {
    Boolean canGenerate;
    int randomNum;

    public class MyServiceBinder extends Binder {
        public MyService getService(){
            return MyService.this;
        }
    }
    IBinder Mybinder=new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return Mybinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("random","service started");
        canGenerate=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                generateRandomNum();
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        canGenerate=false;

    }

    public void generateRandomNum(){
        while(canGenerate){
            try {
                Thread.sleep(1000);
                randomNum=new Random().nextInt(100)+1;
                Log.i("random","the present random number is"+randomNum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getRandomNumber(){
       return randomNum;
    }
}
