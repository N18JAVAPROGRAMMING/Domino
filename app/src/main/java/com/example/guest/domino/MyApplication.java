package com.example.guest.domino;

import android.app.Application;
import android.content.Intent;

public class MyApplication extends Application {

    static SocketThread socketThread;


    @Override
    public void onCreate() {
        super.onCreate();
        socketThread=SocketThread.getInstance();
        socketThread.start();

    }
}
