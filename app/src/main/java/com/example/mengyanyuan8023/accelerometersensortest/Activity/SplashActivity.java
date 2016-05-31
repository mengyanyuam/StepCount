package com.example.mengyanyuan8023.accelerometersensortest.Activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengyanyuan8023.accelerometersensortest.R;
import com.example.mengyanyuan8023.accelerometersensortest.Service.StepService;
import com.example.mengyanyuan8023.accelerometersensortest.Util.DateSave;
import com.example.mengyanyuan8023.accelerometersensortest.Util.StepDetector;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SplashActivity extends Activity implements View.OnClickListener {


    private static TextView count;
    private static Button calories;
    private static Button distance;
    private static Button walkTime;
    private static Button aveSpeed;
    private Button on_off;
    private SensorManager sensorManager;
    private static int shakingCount = 0;
    private static long secondTime = 0;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private static Boolean isQuit = false;

    private DateSave dateSave = new DateSave();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(100);
        initial();

    }

    private void initial(){

        count = (TextView) findViewById(R.id.count);
        calories = (Button) findViewById(R.id.calories);
        distance = (Button) findViewById(R.id.distance);
        walkTime = (Button) findViewById(R.id.walk_time);
        aveSpeed = (Button) findViewById(R.id.ave_speed);
        on_off = (Button) findViewById(R.id.on_off);
        if (StepService.flag == false){
            on_off.setText("开启计步器");
        }else{
            on_off.setText("关闭计步器");
            handler.postDelayed(runnable,1000);
        }

        int step = dateSave.getStepNumber(this);
        if (step > StepDetector.CURRENT_SETP){
            count.setText(String.valueOf(step));
            StepDetector.CURRENT_SETP = step;
        }

        on_off.setOnClickListener(this);
        calories.setOnClickListener(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener,sensor,sensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onDestroy() {
        saveSelf();
        super.onDestroy();
        if (sensorManager != null){
            sensorManager.unregisterListener(listener);
        }
        if (handler != null){
            handler.removeCallbacks(runnable);
        }
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            shakingCount = StepDetector.CURRENT_SETP;
            count.setText(String.valueOf(shakingCount));
            calories.setText("消耗卡路里\n"+String.format("%.2f",shakingCount*0.04)+"卡");
            distance.setText("今日累计运动\n"+String.format("%.2f",shakingCount*0.75)+"m");
            saveSelf();           //自动保存当前步数
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.on_off:
                if (on_off.getText().equals("开启计步器") && StepService.flag == false){
                    if (StepService.START_TIME == null) {
                        StepService.START_TIME = new Date();
                        StepService.rightNow = Calendar.getInstance();
                        handler.postDelayed(runnable,1000);
                    }
                    Intent startIntent = new Intent(this,StepService.class);
                    startService(startIntent);      //打开服务

                    on_off.setText("关闭计步器");
                    Toast.makeText(SplashActivity.this,"已开启计步器后台服务",Toast.LENGTH_SHORT).show();
                    // Toast.makeText(MainActivity.this,"起始时间："+StepService.START_TIME+"--当前时间："+new Date()+"\n时间差："+(new Date().getTime() - StepService.START_TIME.getTime()),Toast.LENGTH_SHORT).show();
                }else{
                    Intent stopIntent = new Intent(this,StepService.class);
                    stopService(stopIntent);        //关闭服务
                    on_off.setText("开启计步器");
                    Toast.makeText(SplashActivity.this,"已关闭计步器后台服务",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Date startTime = StepService.START_TIME;
            Date endTime = new Date();
            Date date = new Date(endTime.getTime() - startTime.getTime()-8*60*60*1000);
            Calendar now = Calendar.getInstance();

            walkTime.setText("今日运动\n"+sdf.format(date));
            secondTime = (endTime.getTime() - startTime.getTime())/1000;
            aveSpeed.setText("平均速度\n" + String.format("%.4f", (StepDetector.CURRENT_SETP * 0.75 / secondTime)) + "m/s");
            if (StepService.rightNow.get(Calendar.DAY_OF_YEAR) != now.get(Calendar.DAY_OF_YEAR)){
                /*计步器第二天数据清零*/
                StepDetector.CURRENT_SETP = 0;
                StepService.rightNow = Calendar.getInstance();
                dateClear();
            }
            handler.postDelayed(this, 1000);
        }
    };

    private void saveSelf(){
        DateSave.saveSelf(SplashActivity.this);
    }

    private void dateClear(){
        new DateSave().stepClear(this);
    }

    @Override
    public void onBackPressed() {               //双击退出
        saveSelf();
        if(isQuit){
            super.onBackPressed();
            return;
        }
        this.isQuit = true;
        Toast.makeText(this,"再按一次返回键退出程序",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isQuit = false;
            }
        },2000);

    }

}
