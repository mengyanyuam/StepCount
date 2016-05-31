
package com.example.mengyanyuan8023.accelerometersensortest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;

import com.example.mengyanyuan8023.accelerometersensortest.R;

/**
 * Created by mengyanyuan8023 on 2016/5/29.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new CountDownTimer(3000L,3000L){

            /**
             * Callback fired on regular interval.
             *
             * @param millisUntilFinished The amount of time until finished.
             */
            @Override
            public void onTick(long millisUntilFinished) {

            }

            /**
             * Callback fired when the time is up.
             */
            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                if (getIntent().getBundleExtra(SyncStateContract.Constants.DATA) != null){
                    intent.putExtra(SyncStateContract.Constants.DATA,
                            getIntent().getBundleExtra(SyncStateContract.Constants.DATA));
                }
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                finish();
            }
        }.start();
    }
}
