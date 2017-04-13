package com.example.memory.dsn_kuaiyue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Memory on 2017/4/13.
 */

public class OrderPassword extends Activity {
    private  String orderid,ordertitle;
    private TextView orName,orPassword;
    private Button refund;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderpassword);
        Intent i = getIntent();
        orderid = i.getStringExtra("id");
        ordertitle = i.getStringExtra("title");
        orName = (TextView)findViewById(R.id.orname);
        orPassword = (TextView)findViewById(R.id.orpassword);
        refund = (Button)findViewById(R.id.refund);
        orName.setText(ordertitle);
         Date d = new Date();
        orPassword.setText(String.valueOf(d.getTime()));
    }
}
