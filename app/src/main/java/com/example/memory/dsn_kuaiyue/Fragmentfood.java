package com.example.memory.dsn_kuaiyue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Memory on 2017/3/19.
 */

public class Fragmentfood extends Fragment{
    private RecyclerView recyclerView;
    private List<String> imglist,titlelist,addrlist;
    private List<Float> gradelist;
    private List<Integer> shopid;
    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgmtorderfood,container,false);
        Thread thread = new Thread(runnable);
        thread.start();
        while (thread.isAlive());
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new HomeAdapter());
        return view;
    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.shoplist, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Picasso.with(getContext()).load(new File(imglist.get(position))).resize(80,80).centerCrop().into(holder.shopimg);
            holder.shoptital.setText(titlelist.get(position));
            holder.gradetext.setText(String.valueOf(new BigDecimal(gradelist.get(position)).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue()));
            holder.shopgrade.setRating(gradelist.get(position));
        }

        @Override
        public int getItemCount() {
            return count;
        }
        class MyViewHolder extends ViewHolder
        {
            ImageView shopimg;
            TextView shoptital,gradetext;
            RatingBar shopgrade;


            public MyViewHolder(View view)
            {
                super(view);
                shopimg = (ImageView) view.findViewById(R.id.shopimg);
                shoptital = (TextView)view.findViewById(R.id.shoptital);
                gradetext = (TextView)view.findViewById(R.id.gradetext);
                shopgrade = (RatingBar)view.findViewById(R.id.shopgrade);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(),ShopDetail.class);
                        intent.putExtra("id",shopid.get(getAdapterPosition()).toString());
                        intent.putExtra("title",titlelist.get(getAdapterPosition()));
                        intent.putExtra("addr",addrlist.get(getAdapterPosition()));
                        startActivity(intent);
                    }
                });
            }
        }
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Connection con = Consql.getInstance().getCon();
                PreparedStatement statement = con.prepareStatement("select * from shoplist");
                ResultSet resultSet = statement.executeQuery();
                imglist = new ArrayList<String>();
                titlelist = new ArrayList<String>();
                gradelist = new ArrayList<Float>();
                shopid = new ArrayList<Integer>();
                addrlist = new ArrayList<String>();
                while (resultSet.next()) {
                    imglist.add(resultSet.getString("shopimg"));
                    titlelist.add(resultSet.getString("shoptitle"));
                    gradelist.add(resultSet.getFloat("shopgrade"));
                    shopid.add(resultSet.getInt("id"));
                    addrlist.add(resultSet.getString("shopaddr"));
                    count++;
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
