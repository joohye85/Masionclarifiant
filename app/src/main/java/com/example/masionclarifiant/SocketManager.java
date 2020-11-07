package com.example.masionclarifiant;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class SocketManager extends Application {
    private static final SocketManager instance = new SocketManager();
    private static Context context = null;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("SocketManager", "onServiceConnected()");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private IconnectionService binder = null;
}
