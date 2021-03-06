package com.example.guest.domino;

import android.app.Application;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

public class MyApplication extends Application {


    public static final String PREFERENCES_NAME="MAIN";
    public static final String TOKEN_PREFERENCES="TOKEN";
    public static final String MAIN_USER_LOGIN="USER_LOGIN";
    public static final String MAIN_USER_SCORE="USER_SCORE";
    public static final String CURRENT_ROOM="CURRENT_ROOM";
    public static final String ROOM_NAME="ROOM_NAME";
    public static final String CURRENT_ROOM_CAPACITY="CAPCITY";
    public static final int FIRST_ATTEMPT=100;

    static SocketThread socketThread;
    static User user;
   private  static TaskData MainData;


    @Override
    public void onCreate() {
        super.onCreate();
        MainData= android.arch.persistence.room.Room.databaseBuilder(getApplicationContext(),
                TaskData.class,"data").build();

        socketThread=SocketThread.getInstance();
        socketThread.start();

    }

    public static List<Task> getTasks(){
        return MainData.taskDao().getAll();

    }

    public static void saveTask(Task task){
        MainData.taskDao().insert(task);
    }

    public static void saveTasks(List<Task> tasks){
        for (Task t: tasks){
            MainData.taskDao().insert(t);
        }

    }

    public static void saveActiveUser(User user, Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences(MyApplication.PREFERENCES_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(MyApplication.MAIN_USER_LOGIN,user.name);
        editor.putInt(MyApplication.MAIN_USER_SCORE,user.score);
        editor.apply();

    }

    public static User getActiveUser(Context context){
        User user = new User();
        SharedPreferences sharedPreferences =context.getSharedPreferences(MyApplication.PREFERENCES_NAME,Context.MODE_PRIVATE);
        user.name=sharedPreferences.getString(MAIN_USER_LOGIN,"");
        Log.d("preferences",user.name);
        user.score=sharedPreferences.getInt(MAIN_USER_SCORE,0);
        Log.d("preferences",String.valueOf(user.score));
        return  user;
    }

    public static void DestroyUserData(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences(MyApplication.PREFERENCES_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(MyApplication.MAIN_USER_LOGIN,"");
        editor.putInt(MyApplication.MAIN_USER_SCORE,0);
        editor.apply();
        APIService.Token.SaveToken(context,"");
    }

    public static Task getTaskById(int id){
        for(Task t: getTasks()){
            if(t.getId() == id)
                return t;
        }
        return null;
    }

    public static void setRoomName(Context context, String room_name){
        SharedPreferences sharedPreferences =context.getSharedPreferences(MyApplication.PREFERENCES_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(MyApplication.ROOM_NAME,room_name);
        editor.apply();
    }

    public static String getRoomName(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences(MyApplication.PREFERENCES_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(ROOM_NAME,"");
    }

}
