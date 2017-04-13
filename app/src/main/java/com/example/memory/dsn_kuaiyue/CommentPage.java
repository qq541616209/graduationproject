package com.example.memory.dsn_kuaiyue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Memory on 2017/4/10.
 */

public class CommentPage extends Activity {
    private RecyclerView commentView;
    int count = 0;
    private List<String> userId,time,comment;
    private List<Float> grade;
    String id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        Intent i = getIntent();
        id = i.getStringExtra("id");

        Thread thread = new Thread(runnable);
        thread.start();
        while (thread.isAlive());

        commentView = (RecyclerView)findViewById(R.id.commentview);

        commentView.setLayoutManager(new LinearLayoutManager(this));
        commentView.setAdapter(new MyAdapter());
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.commentUserId.setText(userId.get(position));
            holder.commentTime.setText(time.get(position));
            holder.commentGrade.setRating(grade.get(position));
            holder.commentText.setText(comment.get(position));
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    CommentPage.this).inflate(R.layout.commentlist, parent,
                    false));
            return holder;
        }

        @Override
        public int getItemCount() {
            return count;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView commentUserId,commentTime,commentText;
            RatingBar commentGrade;
            public MyViewHolder(View itemView) {
                super(itemView);
                commentGrade = (RatingBar)itemView.findViewById(R.id.commentgrade);
                commentText = (TextView)itemView.findViewById(R.id.commenttext);
                commentTime = (TextView)itemView.findViewById(R.id.commenttime);
                commentUserId = (TextView)itemView.findViewById(R.id.commentuserid);
            }
        }
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            userId = new ArrayList<String>();
            time = new ArrayList<String>();
            comment = new ArrayList<String>();
            grade = new ArrayList<Float>();
            try {
                Connection con = Consql.getInstance().getCon();
                PreparedStatement statement = con.prepareStatement("select * from comment where idclassify=?");
                statement.setString(1,id);
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    count++;
                    userId.add(resultSet.getString("commentuserid"));
                    time.add(resultSet.getString("commenttime"));
                    grade.add(resultSet.getFloat("commentgrade"));
                    comment.add(resultSet.getString("commentdetail"));
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
