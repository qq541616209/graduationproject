package com.example.memory.dsn_kuaiyue;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Memory on 2017/3/21.
 */

public class Consql {
    public Connection con = null;
    private static  Consql instance = null;
    private Consql(){
    }
    public synchronized static Consql getInstance(){
        if(instance == null){
            instance = new Consql();
        }
        return instance;
    }
    public Connection getCon() {
        try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/dsnkuaiyue","root","root");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return con;
    }
}
