package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout mainmenu1;
    LinearLayout mainmenu2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //메인 화면 버튼설정
        mainmenu1 = (LinearLayout)findViewById(R.id.mainmenu1);
        mainmenu2 = (LinearLayout)findViewById(R.id.mainmenu2);

        mainmenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID2 = getIntent().getStringExtra("userID");
                Intent intent2 = new Intent(MainActivity.this, SelectActivity.class);
                intent2.putExtra("userID",userID2);
                startActivity(intent2);
            }
        });

        mainmenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent combiIntent = new Intent(MainActivity.this, CombiActivity.class);
                startActivity(combiIntent);
            }
        });
        //메인 화면 어답터설정
        final ViewPager viewPager = (ViewPager) findViewById(R.id.recently_purchased_products);
        final ContentMainPageAdapter myPagerAdapter = new ContentMainPageAdapter(getSupportFragmentManager(), 3);
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
        String userID =getIntent().getStringExtra("userID");
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_select:
                //String userID3 = getIntent().getStringExtra("userID");
                Intent intent2 = new Intent(MainActivity.this, SelectActivity.class);
                intent2.putExtra("userID",userID);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                break;
            case R.id.nav_ingredient:
                Intent intent3 = new Intent(MainActivity.this, FindIngredientActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
                break;
            case R.id.nav_logout:
                Intent intent4 = new Intent(MainActivity.this, LoginActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); //액티비티 초기화
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent4);
                break;
            case R.id.nav_mypage:
                //String userID5 = getIntent().getStringExtra("userID");
                Intent intent5 = new Intent(MainActivity.this, MypageActivity.class);
                intent5.putExtra("userID",userID);
                intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent5);
                break;
            case R.id.nav_mypage2:
               // String userID6 = getIntent().getStringExtra("userID");
                Intent intent6 = new Intent(MainActivity.this, MyskinActivity.class);
                intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent6.putExtra("userID",userID);
                startActivity(intent6);
                break;
            case R.id.nav_combination:
               // String userID7 = getIntent().getStringExtra("userID");
                Intent intent7 = new Intent(MainActivity.this, CombiActivity.class);
                intent7.putExtra("userID",userID);
                intent7.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent7);
                break;
            case R.id.nav_diagnose:
                Intent intent8 = new Intent(MainActivity.this, DiagnoseStart.class);
                intent8.putExtra("userID", userID);
                intent8.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent8);
                break;
            case R.id.nav_dignose_result:
                Intent intent9 = new Intent(MainActivity.this, DiagnoseResult.class);
                intent9.putExtra("userID", userID);
                intent9.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent9);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
