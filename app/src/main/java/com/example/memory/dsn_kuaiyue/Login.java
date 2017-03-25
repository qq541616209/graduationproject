package com.example.memory.dsn_kuaiyue;

import android.app.Activity;
import android.content.DialogInterface;
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
    private Connection con = null;
    private EditText lname,lpassword;
    private PreparedStatement statement = null;
    ResultSet resultSet = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        new Thread(runnable).start();

        login = (Button)findViewById(R.id.loginbtn);
        regiser = (Button)findViewById(R.id.registerbtn);
        lname = (EditText)findViewById(R.id.lusername);
        lpassword = (EditText)findViewById(R.id.lpassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 2;
                if (statement != null) {
                    try {
                        statement.setString(1, lname.getText().toString());
                        statement.setString(2, lpassword.getText().toString());
                        resultSet = statement.executeQuery();
                        if (resultSet.next()) {
                            count = resultSet.getInt(1);
                        }
                        resultSet.close();
                        statement.close();
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (count == 1) {
                    new AlertDialog.Builder(Login.this).setTitle("提示").setMessage("登录成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            finish();
                        } } ).show();
                }
            }
        });
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            con = Consql.getInstance().getCon();
            try {
                statement = con.prepareStatement("select count(*) from user where username=? and password=?");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    };
}
