package com.example.mengyanyuan8023.accelerometersensortest.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Button;

import com.example.mengyanyuan8023.accelerometersensortest.Activity.SplashActivity;
import com.example.mengyanyuan8023.accelerometersensortest.R;
import com.example.mengyanyuan8023.accelerometersensortest.Util.DateSave;
import com.example.mengyanyuan8023.accelerometersensortest.Util.StepDetector;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mengyanyuan8023 on 2016/5/26.
 */

public class StepService extends Service {

    public static Boolean flag = false;     //�����Ƿ�����
    private SensorManager sensorManager;    //������������
    private StepDetector stepDetector;      //�Ʋ���������

    private NotificationManager notificationManager;
    private NotificationCompat.Builder notifyBuilder;

    private Button count;
    private Button calories;
    private Button distance;

    public static Date START_TIME = null;

    public static Calendar rightNow;

    private int stepCount = 0;


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //���￪����һ���̣߳���Ϊ��̨����Ҳ�������߳��н��У��������԰�ȫ�㣬��ֹ���߳�����
        new Thread(new Runnable() {
            public void run() {
                startStepDetector();
            }
        }).start();
    }

    private void startStepDetector() {
        flag = true;
        stepDetector = new StepDetector(this);
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);//��ȡ��������������ʵ��
        Sensor sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//��ô����������ͣ������õ������Ǽ��ٶȴ�����
        //�˷�������ע�ᣬֻ��ע����Ż���Ч��������SensorEventListener��ʵ����Sensor��ʵ������������
        sensorManager.registerListener(stepDetector, sensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //��פ֪ͨ����ʵ��
        Intent intentServer = new Intent(this,SplashActivity.class);
        intentServer.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,100,intentServer,PendingIntent.FLAG_CANCEL_CURRENT);

        notifyBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(getText(R.string.notification_title))
                .setWhen(System.currentTimeMillis())
                .setContentText(getText(R.string.trade_count).toString()+StepDetector.CURRENT_SETP+getText(R.string.trade_tip));

        notifyBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(100, notifyBuilder.build());

        startForeground(100, notifyBuilder.build());    //��������Ȩ�ޣ�����Ϊǰ������

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        DateSave.saveSelf(this);
        super.onDestroy();
        flag = false;
        if (stepDetector != null) {
            sensorManager.unregisterListener(stepDetector);
        }
        if (notifyBuilder.build() != null){
            stopForeground(true);
        }
    }
}
