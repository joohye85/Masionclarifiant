package com.example.masionclarifiant;

import android.app.Application;

//user의 id를 저장해놓고 계속 불러올 수 있음 -> main에서 Intent로 보냈던 것을 이런식으로 할 것
public class MyApplication extends Application {
    public static String user_id;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
