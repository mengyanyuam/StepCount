package com.example.mengyanyuan8023.accelerometersensortest.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mengyanyuan8023.accelerometersensortest.Activity.SplashActivity;

/**
 * Created by mengyanyuan8023 on 2016/5/29.
 */
public class BroadcastClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentReceiver = new Intent(context,SplashActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentReceiver);

    }
}
