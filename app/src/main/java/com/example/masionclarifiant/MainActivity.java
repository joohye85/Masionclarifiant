package com.example.masionclarifiant;

import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //메인 화면 어답터설정
        final ViewPager viewPager = (ViewPager) findViewById(R.id.recently_purchased_products);
        final ContentMainPageAdapter myPagerAdapter = new ContentMainPageAdapter(getSupportFragmentManager(), 2);
        viewPager.setAdapter(myPagerAdapter);
        

        /*--Hooks--*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        /*--Navigation Drawer Menu--*/

        //Hide or show items
        Menu menu = navigationView.getMenu();
  //      menu.findItem(R.id.nav_logout).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,  R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /*--Make a menu clickable--*/
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }

    /*-- To avoid closing the application on Back pressed --*/
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_select:
                String userID2 = getIntent().getStringExtra("userID");
                Intent intent2 = new Intent(MainActivity.this, SelectActivity.class);
                intent2.putExtra("userID",userID2);
                startActivity(intent2);
                break;
            case R.id.nav_ingredient:
                Intent intent3 = new Intent(MainActivity.this, FindIngredientActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_logout:
                Intent intent4 = new Intent(MainActivity.this, LoginActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); //액티비티 초기화
                startActivity(intent4);
                break;
            case R.id.nav_mypage:
                String userID = getIntent().getStringExtra("userID");
                Intent intent5 = new Intent(MainActivity.this, MypageActivity.class);
                intent5.putExtra("userID",userID);
                startActivity(intent5);
                break;
            case R.id.nav_combination:
                Intent intent6 = new Intent(MainActivity.this, CombiActivity.class);
                startActivity(intent6);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

