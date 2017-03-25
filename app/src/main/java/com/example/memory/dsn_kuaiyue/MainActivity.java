package com.example.memory.dsn_kuaiyue;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button ordercar,orderfood;
    private RecyclerView recyclerView;
    private Fragmentcar fragmentcar;
    private Fragmentfood fragmentfood;
    private View headerView = null;
    private NavigationView navigationView;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            fragmentcar = new Fragmentcar();
            getSupportFragmentManager().beginTransaction().add(R.id.frgmtcontaint,fragmentcar).commit();
        }
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ordercar = (Button)findViewById(R.id.ordercar);
        orderfood = (Button)findViewById(R.id.orderfood);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        tv = (TextView)headerView.findViewById(R.id.login);

        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setHasFixedSize(true);
        ordercar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(fragmentfood != null)
                getSupportFragmentManager().beginTransaction().hide(fragmentfood).show(fragmentcar).commit();
                orderfood.setBackgroundColor(Color.WHITE);
                ordercar.setBackgroundColor(Color.GREEN);
            }
        });
        orderfood.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentfood == null){
                    fragmentfood = new Fragmentfood();
                    getSupportFragmentManager().beginTransaction().add(R.id.frgmtcontaint,fragmentfood).hide(fragmentcar).show(fragmentfood).commit();
                }
                else {
                    getSupportFragmentManager().beginTransaction().hide(fragmentcar).show(fragmentfood).commit();
                }
                orderfood.setBackgroundColor(Color.GREEN);
                ordercar.setBackgroundColor(Color.WHITE);
            }
        });
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_msg) {
            // Handle the camera action
        } else if (id == R.id.nav_bg) {

        } else if (id == R.id.nav_rcd) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
