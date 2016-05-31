package com.example.mengyanyuan8023.accelerometersensortest.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.mengyanyuan8023.accelerometersensortest.R;

/**
 * Created by mengyanyuan8023 on 2016/5/29.
 */
public class AcountActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount);

        getSupportActionBar().setTitle("AcountActivity");
        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String detail = getIntent().getStringExtra("detail");

        ((TextView)findViewById(R.id.name)).setText(name);
        ((TextView)findViewById(R.id.price)).setText(price);
        ((TextView)findViewById(R.id.detail)).setText(detail);
    }
}
