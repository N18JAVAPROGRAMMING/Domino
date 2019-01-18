package com.example.guest.domino;

import android.app.Application;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;

public class MyApplication extends Application {


    public static final String PREFERENCES_NAME="MAIN";
    public static final String TOKEN_PREFERENCES="TOKEN";

    static SocketThread socketThread;
    static User user;
   private  static TaskData MainData;


    @Override
    public void onCreate() {
        super.onCreate();
        MainData= android.arch.persistence.room.Room.databaseBuilder(getApplicationContext(),
                TaskData.class,"dp").build();

        socketThread=SocketThread.getInstance();
        socketThread.start();

    }

    public static TaskData getDataTask(){
        return MainData;

    }

}
