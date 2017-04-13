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
 * Created by Memory on 2017/3/25.
 */

public class Register extends Activity {
    private EditText rusername,rpassword,rrpassword;
    private Button commitrbtn;
    private String username = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        rusername = (EditText)findViewById(R.id.rusername);
        rpassword = (EditText)findViewById(R.id.rpassword);
        rrpassword = (EditText)findViewById(R.id.rrpassword);
        commitrbtn= (Button)findViewById(R.id.commitrbtn);

        commitrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rpassword.getText().toString().equals(rrpassword.getText().toString())){
                    Thread thread = new Thread(runnable);
                    thread.start();
                    while (thread.isAlive());
                    if (username.equals(rusername.getText().toString())&&username.length()>0) {
                        new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("注册成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                finish();
                            } } ).show();
                    }
                    else {
                        new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("注册失败").setPositiveButton("确定",null ).show();
                    }
                }
                else {
                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("两次输入的密码不一致").setPositiveButton("确定",null ).show();
                }

            }
        });
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Connection con = Consql.getInstance().getCon();
                PreparedStatement statement = con.prepareStatement("select * from user where username=?");
                statement.setString(1, rusername.getText().toString());
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    PreparedStatement preparedStatement = con.prepareStatement("insert into user values(?,?)");
                    preparedStatement.setString(1,rusername.getText().toString());
                    preparedStatement.setString(2,rpassword.getText().toString());
                    preparedStatement.executeUpdate();
                    username = rusername.getText().toString();
                    preparedStatement.close();
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
