package com.example.mengyanyuan8023.accelerometersensortest.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mengyanyuan8023.accelerometersensortest.Service.StepService;

/**
 * Created by mengyanyuan8023 on 2016/5/29.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent server = new Intent(context, StepService.class);
        context.startService(server);
    }
}
