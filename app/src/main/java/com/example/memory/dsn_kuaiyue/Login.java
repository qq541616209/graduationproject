package com.example.memory.dsn_kuaiyue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Memory on 2017/3/22.
 */

public class Login extends Activity{
    private Button login,regiser;
    private EditText lname,lpassword;
    private String username = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (Button)findViewById(R.id.loginbtn);
        regiser = (Button)findViewById(R.id.registerbtn);
        lname = (EditText)findViewById(R.id.lusername);
        lpassword = (EditText)findViewById(R.id.lpassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(runnable);
                thread.start();
                while (thread.isAlive());
                if (username.equals(lname.getText().toString())&&username.length()>0) {
                    Intent i = new Intent(Login.this,MainActivity.class);
                    startActivity(i);
                    finish();
                    /*NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
                    View headerView = navigationView.getHeaderView(0);
                    TextView tv = (TextView)headerView.findViewById(R.id.login);
                    tv.setText(username);
                    finish();*/
                }else {
                    new AlertDialog.Builder(Login.this).setTitle("提示").setMessage("用户名或密码错误").setPositiveButton("确定",null).show();
                }
            }
        });
        regiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Connection con = Consql.getInstance().getCon();
                PreparedStatement statement = con.prepareStatement("select * from user where username=? and password=?");
                statement.setString(1, lname.getText().toString());
                statement.setString(2, lpassword.getText().toString());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    username = resultSet.getString("username");
                    Data data = (Data)getApplicationContext();
                    data.setUserName(username);
                    data.setMyUserName(username);
                }
                resultSet.close();
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
}
