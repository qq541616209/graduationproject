package com.example.memory.dsn_kuaiyue;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Memory on 2017/4/13.
 */

public class GiveComment extends Activity {
    private EditText commentEdt;
    private RatingBar commentRating;
    private Button submitComment;
    private String userName,id,time,orderid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycomment);
        commentEdt = (EditText)findViewById(R.id.commentedt);
        commentRating = (RatingBar)findViewById(R.id.commentrating);
        submitComment = (Button)findViewById(R.id.submitcomment);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        orderid = i.getStringExtra("orderid");
        Data data = (Data)getApplicationContext();
        userName = data.getUserName();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        time = formatter.format(new java.util.Date());
        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(runnable);
                thread.start();
                while (thread.isAlive());
                new AlertDialog.Builder(GiveComment.this).setTitle("提示").setMessage("评论成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(GiveComment.this,MyOrder.class);
                        startActivity(i);
                        finish();
                    }
                }).show();
            }
        });
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Thread thread1 = new Thread(runnable1);
            thread1.start();
            Thread thread2 = new Thread(runnable2);
            thread2.start();
            Thread thread3 = new Thread(runnable3);
            thread3.start();
            try {
                Connection con = Consql.getInstance().getCon();
                PreparedStatement statement = con.prepareStatement("insert into comment(idclassify,commentuserid,commenttime,commentgrade,commentdetail) values(?,?,?,?,?)");
                Data data = (Data) getApplicationContext();
                statement.setString(1,id);
                statement.setString(2,data.getUserName());
                statement.setString(3,time);
                statement.setString(4,String.valueOf(commentRating.getRating()));
                statement.setString(5,commentEdt.getText().toString());
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
                PreparedStatement statement = con.prepareStatement("delete from orderlist where idorder=?");
                statement.setString(1,orderid);
                statement.executeUpdate();
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            try {
                Connection con = Consql.getInstance().getCon();
                PreparedStatement statement = con.prepareStatement("update classify set goodsgrade=(?+goodsgrade*goodscommentnum)/(goodscommentnum+1),goodscommentnum=goodscommentnum+1 where id=?");
                statement.setFloat(1,commentRating.getRating());
                statement.setInt(2,Integer.valueOf(id));
                statement.executeUpdate();
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            try {
                Connection con = Consql.getInstance().getCon();
                PreparedStatement preparedStatement = con.prepareStatement("select shopid from classify where id=?");
                preparedStatement.setInt(1,Integer.valueOf(id));
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                PreparedStatement statement = con.prepareStatement("update shoplist set shopgrade=(?+shopgrade*shopcommentnum)/(shopcommentnum+1),shopcommentnum=shopcommentnum+1 where id=?");
                statement.setFloat(1,commentRating.getRating());
                statement.setInt(2,resultSet.getInt("shopid"));
                preparedStatement.close();
                resultSet.close();
                statement.executeUpdate();
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
}
