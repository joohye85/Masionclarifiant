package com.example.masionclarifiant;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page);
        startLoading();
    }
    private void startLoading(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);
    }

}