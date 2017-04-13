package com.example.memory.dsn_kuaiyue;

import android.app.Application;

/**
 * Created by Memory on 2017/4/11.
 */

public class Data extends Application {
    private String userName;
    private String myUserName = new String("登录");
    private int flag;

    public String getUserName(){
        return this.userName;
    }
    public String getMyUserName(){
        return this.myUserName;
    }
    public void setUserName(String s){
        this.userName = s;
    }
    public void setMyUserName(String s){
        this.myUserName = s;
    }
    public int getFlag(){
        return this.flag;
    }
    public void setFlag(int s){
        this.flag = s;
    }
}
