package com.example.memory.dsn_kuaiyue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Memory on 2017/4/10.
 */

public class MyOrder extends Activity {
    private RecyclerView orderContaint;
    int count = 0;
    List<String> imgList,statusList,titalList;
    List<Integer> numberList;
    List<Float> priceList;
    List<Integer> orderid,classifyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorder);

        Thread thread = new Thread(runnable);
        thread.start();
        while (thread.isAlive());

        orderContaint = (RecyclerView)findViewById(R.id.ordercontaint);

        orderContaint.setLayoutManager(new LinearLayoutManager(this));
        orderContaint.setAdapter(new MyAdapter());
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            init(holder,position);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MyOrder.this).inflate(R.layout.orderlist, parent,
                    false));
            return holder;
        }

        @Override
        public int getItemCount() {
            return count;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            ImageView orderImg;
            TextView ordertital,orderStatus,orderNumber,orderPrice;
            Button singleOrderStatus;
            public MyViewHolder(View itemView) {
                super(itemView);
                orderImg = (ImageView)itemView.findViewById(R.id.orderimg);
                orderNumber = (TextView)itemView.findViewById(R.id.ordernumber);
                orderPrice = (TextView)itemView.findViewById(R.id.orderprice);
                orderStatus = (TextView)itemView.findViewById(R.id.orderstatus);
                ordertital = (TextView)itemView.findViewById(R.id.ordertital);
                singleOrderStatus = (Button)itemView.findViewById(R.id.singleorderstatus);
            }
        }
        public void init(MyViewHolder holder, final int position){
            Picasso.with(getApplicationContext()).load(new File(imgList.get(position)));
            holder.orderNumber.setText(String.valueOf(numberList.get(position)));
            holder.orderPrice.setText(String.valueOf(priceList.get(position)));
            holder.orderStatus.setText(statusList.get(position));
            holder.ordertital.setText(titalList.get(position));
            if(statusList.get(position).equals("待付款")) {
                holder.singleOrderStatus.setText("付款");
                holder.singleOrderStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MyOrder.this,Pay.class);
                        i.putExtra("tital",titalList.get(position));
                        i.putExtra("number",String.valueOf(numberList.get(position)));
                        i.putExtra("price",String.valueOf(priceList.get(position)));
                        i.putExtra("id",String.valueOf(orderid.get(position)));
                        Data data = (Data)getApplicationContext();
                        data.setFlag(1);
                        startActivity(i);
                    }
                });
            }else if(statusList.get(position).equals("已完成")){
                holder.singleOrderStatus.setText("评价");
                holder.singleOrderStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MyOrder.this,GiveComment.class);
                        i.putExtra("id",String.valueOf(classifyId.get(position)));
                        i.putExtra("orderid",String.valueOf(orderid.get(position)));
                        startActivity(i);
                    }
                });
            }else if(statusList.get(position).equals("待使用")){
                holder.singleOrderStatus.setText("查看券玛");
                holder.singleOrderStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MyOrder.this,OrderPassword.class);
                        i.putExtra("title",titalList.get(position));
                        i.putExtra("id",String.valueOf(orderid.get(position)));
                        startActivity(i);
                    }
                });
            }
        }
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            imgList = new ArrayList<String>();
            statusList = new ArrayList<String>();
            titalList = new ArrayList<String>();
            priceList = new ArrayList<Float>();
            numberList = new ArrayList<Integer>();
            orderid = new ArrayList<Integer>();
            classifyId = new ArrayList<Integer>();
            try {
                Connection con = Consql.getInstance().getCon();
                PreparedStatement statement = con.prepareStatement("select * from orderlist where orderowner=?");
                Data data = (Data) getApplicationContext();
                statement.setString(1,data.getUserName());
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    count++;
                    statusList.add(resultSet.getString("orderstatus"));
                    titalList.add(resultSet.getString("ordertital"));
                    imgList.add(resultSet.getString("orderimg"));
                    priceList.add(resultSet.getFloat("orderprice"));
                    numberList.add(resultSet.getInt("ordernumber"));
                    orderid.add(resultSet.getInt("idorder"));
                    classifyId.add(resultSet.getInt("classifyid"));
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
