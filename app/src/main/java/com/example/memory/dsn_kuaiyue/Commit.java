package com.example.memory.dsn_kuaiyue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Memory on 2017/4/13.
 */

public class Commit extends Activity {
    private String price,title;
    private TextView commitPrice,commitName;
    private Button priceBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commit);
        Intent i = getIntent();
        price = i.getStringExtra("price");
        title = i.getStringExtra("title");

        commitName = (TextView)findViewById(R.id.commitname);
        commitPrice = (TextView)findViewById(R.id.commitprice);
        priceBtn = (Button)findViewById(R.id.pricebtn);
        commitName.setText(title);
        commitPrice.setText("￥"+price);
        priceBtn.setText("确认支付￥"+price);
    }
}
