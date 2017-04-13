package com.example.memory.dsn_kuaiyue;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by Memory on 2017/4/8.
 */

public class ShopDetail extends Activity {
    String id = new String();
    private ImageView goodsImg;
    private TextView price,textgrade,sumcomment,shopname,addr,indata,usetime;
    private LinearLayout commentlayout;
    private Button buy;
    private RecyclerView classifyView;
    private RatingBar ratinggrade;
    int count = 0;
    int flag = 0;
    private List<Integer> sumComment;
    private List<String> img,inData,useTime;
    private List<Float> p,textGrade;
    private String shopName,address;
    private List<String> classify;
    private List<Integer> idList;
    private String ig,prc,tital,id_classify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopdetail);
        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        shopName = intent.getStringExtra("title");
        address = intent.getStringExtra("addr");

        Thread thread = new Thread(runnable);
        thread.start();
        while (thread.isAlive());

        if(null != idList && !idList.isEmpty()){
            flag = idList.get(0);
            ig = img.get(0);
            prc = String.valueOf(p.get(0));
            tital = classify.get(0);
            id_classify = String.valueOf(idList.get(0));
        }

        goodsImg = (ImageView) findViewById(R.id.goodsimg);
        price = (TextView)findViewById(R.id.price);
        textgrade = (TextView)findViewById(R.id.textgrade);
        sumcomment = (TextView)findViewById(R.id.sumcomment);
        shopname = (TextView)findViewById(R.id.shopname);
        addr = (TextView)findViewById(R.id.addr);
        indata = (TextView)findViewById(R.id.indata);
        usetime = (TextView)findViewById(R.id.usetime);
        buy = (Button)findViewById(R.id.buy);
        commentlayout = (LinearLayout)findViewById(R.id.commentlayout);
        ratinggrade = (RatingBar)findViewById(R.id.ratinggrade);
        classifyView = (RecyclerView)findViewById(R.id.classifyview);

        shopname.setText(shopName);
        addr.setText(address);
        commentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag != 0) {
                    Intent i = new Intent(ShopDetail.this, CommentPage.class);
                    i.putExtra("id", String.valueOf(flag));
                    startActivity(i);
                }
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag != 0) {
                    Data data = (Data) getApplicationContext();
                    if(data.getUserName() == null){
                        new AlertDialog.Builder(ShopDetail.this).setTitle("提示").setMessage("请先登录").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(ShopDetail.this,Login.class);
                                startActivity(i);
                            }
                        }).show();
                    }else {
                        data.setFlag(2);
                        Intent i = new Intent(ShopDetail.this, Pay.class);
                        i.putExtra("goodsimg", ig);
                        i.putExtra("goodsprice", prc);
                        i.putExtra("goodstital", tital);
                        i.putExtra("goodsid", id_classify);
                        startActivity(i);
                    }
                }
            }
        });

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
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.classname.setText(classify.get(position));
            if(position == 0){
                holder.classname.setBackgroundColor(android.graphics.Color.GREEN);
                init(position);
            }
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView classname;
            public MyViewHolder(View view) {
                super(view);
                classname = (TextView)view.findViewById(R.id.classname);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        classname.setBackgroundColor(android.graphics.Color.GREEN);
                        init(getAdapterPosition());
                        ig = img.get(getAdapterPosition());
                        prc = String.valueOf(p.get(getAdapterPosition()));
                        tital = classify.get(getAdapterPosition());
                        id_classify = String.valueOf(idList.get(getAdapterPosition()));
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
                PreparedStatement statement = con.prepareStatement("select * from classify where shopid=?");
                statement.setString(1,id);
                ResultSet resultSet = statement.executeQuery();
                p = new ArrayList<Float>();
                textGrade = new ArrayList<Float>();
                sumComment = new ArrayList<Integer>();
                img = new ArrayList<String>();
                inData = new ArrayList<String>();
                useTime = new ArrayList<String>();
                classify = new ArrayList<String>();
                idList = new ArrayList<Integer>();

                while (resultSet.next()) {
                    count++;
                    p.add(resultSet.getFloat("goodsprice"));
                    textGrade.add(resultSet.getFloat("goodsgrade"));
                    sumComment.add(resultSet.getInt("goodscommentnum"));
                    img.add(resultSet.getString("goodsimg"));
                    inData.add(resultSet.getString("indata"));
                    useTime.add(resultSet.getString("usetime"));
                    classify.add(resultSet.getString("goodsname"));
                    idList.add(resultSet.getInt("id"));
                }
                resultSet.close();
                statement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
    private void init(int position){
        Picasso.with(getApplicationContext()).load(new File(img.get(position))).into(goodsImg);
        price.setText(String.valueOf(p.get(position)));
        textgrade.setText(String.valueOf(new BigDecimal(textGrade.get(position)).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue()));
        ratinggrade.setRating(textGrade.get(position));
        sumcomment.setText(String.valueOf(sumComment.get(position)));
        indata.setText(inData.get(position));
        usetime.setText(useTime.get(position));
        flag = idList.get(position);
    }
}
