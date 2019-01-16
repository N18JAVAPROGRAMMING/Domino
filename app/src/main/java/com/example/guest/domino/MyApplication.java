package com.example.guest.domino;

import android.app.Application;
import android.content.Intent;

public class MyApplication extends Application {

    public static final String PREFERENCES_NAME="MAIN";
    public static final String TOKEN_PREFERENCES="TOKEN";

    static SocketThread socketThread;


    @Override
    public void onCreate() {
        super.onCreate();
        socketThread=SocketThread.getInstance();
        socketThread.start();

    }
}
