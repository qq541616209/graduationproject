package com.example.memory.dsn_kuaiyue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.memory.dsn_kuaiyue.R.layout.pay;

/**
 * Created by Memory on 2017/4/12.
 */

public class Pay extends Activity {
    private String price,number,tital,id,img,userName;
    private TextView payName,money,totalMoney;
    private EditText choiceNumber;
    private ImageButton subBtn,addBtn;
    private Button payBtn;
    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pay);
        Intent i = getIntent();
        Data data = (Data)getApplicationContext();
        flag = data.getFlag();
        userName = data.getUserName();

        payName = (TextView)findViewById(R.id.payname);
        money = (TextView)findViewById(R.id.money);
        totalMoney = (TextView)findViewById(R.id.totalmoney);
        choiceNumber = (EditText)findViewById(R.id.choicenumber);
        subBtn = (ImageButton)findViewById(R.id.subbtn);
        addBtn = (ImageButton)findViewById(R.id.addbtn);
        payBtn = (Button)findViewById(R.id.paybtn);

        if(flag == 1) {
            price = i.getStringExtra("price");
            number = i.getStringExtra("number");
            tital = i.getStringExtra("tital");
            id = i.getStringExtra("id");
            payName.setText(tital);
            choiceNumber.setText(number);
            money.setText(price);
        }else if(flag == 2){
            price = i.getStringExtra("goodsprice");
            tital = i.getStringExtra("goodstital");
            id = i.getStringExtra("goodsid");
            img = i.getStringExtra("goodsimg");
            payName.setText(tital);
            money.setText(price);
        }
        totalMoney.setText(String.valueOf((Float.valueOf(price))*(Integer.valueOf(choiceNumber.getText().toString()))));

        choiceNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(choiceNumber.getText())) {
                    totalMoney.setText(String.valueOf((Float.valueOf(price)) * (Integer.valueOf(choiceNumber.getText().toString()))));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(choiceNumber.getText().toString()) > 1){
                choiceNumber.setText(String.valueOf(Integer.valueOf(choiceNumber.getText().toString()) - 1));
                }
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceNumber.setText(String.valueOf(Integer.valueOf(choiceNumber.getText().toString()) + 1));
            }
        });
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1) {
                    Thread thread = new Thread(runnable);
                    thread.start();
                    while (thread.isAlive()) ;
                }
                else if(flag == 2){
                    Thread thread1 = new Thread(runnable1);
                    thread1.start();
                    while (thread1.isAlive()) ;
                }
                Intent i = new Intent(Pay.this,Commit.class);
                i.putExtra("title",tital);
                i.putExtra("price",totalMoney.getText().toString());
                startActivity(i);
            }
        });
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Connection con = Consql.getInstance().getCon();
                PreparedStatement statement = con.prepareStatement("update orderlist set ordernumber=? where idorder=?");
                statement.setString(1, choiceNumber.getText().toString());
                statement.setString(2, id);
                statement.executeUpdate();
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            try {
                Connection con = Consql.getInstance().getCon();
                PreparedStatement statement = con.prepareStatement("insert into orderlist(classifyid,orderowner,orderstatus,orderimg,ordertital,orderprice,ordernumber) values(?,?,?,?,?,?,?)");
                statement.setString(1, id);
                statement.setString(2, userName);
                statement.setString(3, "待付款");
                statement.setString(4, img);
                statement.setString(5, tital);
                statement.setString(6, price);
                statement.setString(7, choiceNumber.getText().toString());
                statement.executeUpdate();
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
}
