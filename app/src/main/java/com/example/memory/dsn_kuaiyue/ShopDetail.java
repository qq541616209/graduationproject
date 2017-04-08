package com.example.memory.dsn_kuaiyue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Memory on 2017/4/8.
 */

public class ShopDetail extends Activity {
    String id = new String();
    private ImageView goodsImg;
    private TextView price,textgrade,sumcomment,shopname,addr,indata,usetime;
    private Button buy;
    private RecyclerView classifyView;
    private RatingBar ratinggrade;
    int count = 0;
    private List<Integer> p,textGrade,sumComment;
    private List<String> img,inData,useTime;
    private String shopName,address;
    private List<String> classify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopdetail);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        shopName = intent.getStringExtra("title");
        address = intent.getStringExtra("addr");

        Thread thread = new Thread(runnable);
        thread.start();
        while (thread.isAlive());

        goodsImg = (ImageView) findViewById(R.id.goodsimg);
        price = (TextView)findViewById(R.id.price);
        textgrade = (TextView)findViewById(R.id.textgrade);
        sumcomment = (TextView)findViewById(R.id.sumcomment);
        shopname = (TextView)findViewById(R.id.shopname);
        addr = (TextView)findViewById(R.id.addr);
        indata = (TextView)findViewById(R.id.indata);
        usetime = (TextView)findViewById(R.id.usetime);
        buy = (Button)findViewById(R.id.buy);
        ratinggrade = (RatingBar)findViewById(R.id.ratinggrade);
        classifyView = (RecyclerView)findViewById(R.id.classifyview);

        shopname.setText(shopName);
        addr.setText(address);

        classifyView.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        classifyView.setHasFixedSize(true);
        classifyView.setAdapter(new MyAdapter());
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    ShopDetail.this).inflate(R.layout.classify, parent,
                    false));
            return holder;
        }

        @Override
        public int getItemCount() {
            return count;
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            holder.classname.setText(classify.get(position));
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView classname;
            public MyViewHolder(View view) {
                super(view);
                classname = (TextView)view.findViewById(R.id.classname);
            }
        }
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Connection con = Consql.getInstance().getCon();
                PreparedStatement statement = con.prepareStatement("select * from classify where shopid=?");
                statement.setString(1,id);
                ResultSet resultSet = statement.executeQuery();
                p = new ArrayList<Integer>();
                textGrade = new ArrayList<Integer>();
                sumComment = new ArrayList<Integer>();
                img = new ArrayList<String>();
                inData = new ArrayList<String>();
                useTime = new ArrayList<String>();
                classify = new ArrayList<String>();

                while (resultSet.next()) {
                    count++;
                    p.add(resultSet.getInt("goodsprice"));
                    textGrade.add(resultSet.getInt("goodsgrade"));
                    sumComment.add(resultSet.getInt("goodscommentnum"));
                    img.add(resultSet.getString("goodsimg"));
                    inData.add(resultSet.getString("indata"));
                    useTime.add(resultSet.getString("usetime"));
                    classify.add(resultSet.getString("goodsname"));
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
