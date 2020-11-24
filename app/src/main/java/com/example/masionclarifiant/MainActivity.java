package com.example.masionclarifiant;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout mainmenu1;
    LinearLayout mainmenu2;
    public int moisture = 0; //db에서 수분값 받아옴
    public int oil = 0; //db에서 유분값 받아옴
    public int blemish = 0; //db에서 잡티 받아옴
    public double clean = 0; //청결도
    public double wrinkle = 0; //주름
    public double liver_spot = 0; //기미
    public int skinDate2, skinDate3, skinDate1 = 0;
    public int Skinage1, Skinage2, Skinage3 = 0;
    public static SocketService socketService;
    String jsonResult = null;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder services) {
            SocketService.SocketBinder sb = (SocketService.SocketBinder) services;
            socketService = sb.getService();
            System.out.println("start SocketService: " + socketService);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("SocketConnect", "main");

        String userID = getIntent().getStringExtra("userID");

        /*Response.Listener<String> res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    System.out.println("json-clean: " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    clean = jsonObject.getInt("clean");
                    liver_spot = jsonObject.getDouble("liver_spot");
                    wrinkle = jsonObject.getDouble("wrinkle");
                }catch (Exception e){e.printStackTrace();}
            }
        };

        SkinResultRequest skinResultRequest = new SkinResultRequest(userID, res);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(skinResultRequest);

        //유수분, 여드름 불러오기
        Response.Listener<String> resp = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("json-moisture: " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    moisture = jsonObject.getInt("moisture");
                    oil = jsonObject.getInt("oil");
                    blemish = jsonObject.getInt("pimple");

                }catch (Exception e){e.printStackTrace();}

            }
        };
        DiagnoseRequest diagnoseRequest = new DiagnoseRequest(userID, resp);
        RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this);
        queue2.add(diagnoseRequest);

        //피부나이, 측정날짜 3개 배열 불러오기
        Response.Listener<String> res2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        int skin__age = jsonObject2.getInt("skin_age");
                        String skin__Date = jsonObject2.getString("skinDate");
                        if(i==0) {
                            Skinage3 = skin__age;
                            skinDate3 = Integer.parseInt(skin__Date.substring(5,7));
                        }
                        else if(i==1) {
                            Skinage2 = skin__age;
                            skinDate2 = Integer.parseInt(skin__Date.substring(5,7));
                        }
                        else {
                            Skinage1 = skin__age;
                            skinDate1 = Integer.parseInt(skin__Date.substring(5,7));
                        }
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        };
        Recently_three_skinageRequest recently_three_skinageRequest = new Recently_three_skinageRequest(userID, res2);
        RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
        queue1.add(recently_three_skinageRequest);*/

        Response.Listener<String> res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    jsonResult = response;

                }catch (Exception e){e.printStackTrace();}
            }
        };
        SkinResultRequest skinResultRequest = new SkinResultRequest(userID, res);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(skinResultRequest);
        Intent serviceIntent = new Intent(MainActivity.this, SocketService.class);
        bindService(serviceIntent, conn, Context.BIND_AUTO_CREATE);
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
                String userID2 = getIntent().getStringExtra("userID");
                Intent combiIntent = new Intent(MainActivity.this, CombiActivity.class);
                combiIntent.putExtra("userID", userID2);
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
                Intent intent9 = new Intent(MainActivity.this, DiagnoseFin.class);
                Response.Listener<String> res3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                };

                intent9.putExtra("userID", userID);
                intent9.putExtra("jsonResult", jsonResult);
                System.out.println("jsonResult: " + jsonResult);
                /*intent9.putExtra("moisture", moisture);
                intent9.putExtra("oil", oil);
                intent9.putExtra("blemish", blemish);
                intent9.putExtra("clean", clean);
                intent9.putExtra("wrinkle", wrinkle);
                intent9.putExtra("liver_spot", liver_spot);
                intent9.putExtra("skinDate2", skinDate2);
                intent9.putExtra("skinDate3", skinDate3);
                intent9.putExtra("skinDate1", skinDate1);
                intent9.putExtra("Skinage1", Skinage1);
                intent9.putExtra("Skinage2", Skinage2);
                intent9.putExtra("Skinage3", Skinage3);
                intent9.putExtra("Skinage3", Skinage3);*/
                intent9.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent9);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
