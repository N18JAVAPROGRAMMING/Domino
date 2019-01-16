package com.example.guest.domino;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    public static String HOST="black-flower.tk/";


    @POST("room/create")
    void createRoom(@Query("name") String name, @Query("capacity") int capacity,@Query("initiator") String initiator);

    @GET("room/info")
    Call<List<Room>> getAllRooms();

    @POST("peer/signup")
    Call<Token> singUp(@Query("username")String name, @Query("password")String password);

    @POST("peer/signin")
    Call<Token> singIn(@Query("username")String name, @Query("password")String password);

    @POST
    Call<Room.RoomStatus> peerConnect(@Query("room_id")String room_id, @Query("peer_name")String username);

    @POST
    void peerDisconnect(@Query("room_id")String room_id, @Query("peer_name")String username);

    @POST
    Call<Task> getTask(@Query("task_id")String id);




    @GET("")
    Call<Boolean> LogIn();

    @POST("")
    Call<Boolean> CreateAccount();

    //@GET<L>

    public static class Token{

        static String VALUE="";

        public static void SaveToken(Context context,String token){
           SharedPreferences sharedPreferences =context.getSharedPreferences(MyApplication.PREFERENCES_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(MyApplication.TOKEN_PREFERENCES,token);
            editor.apply();
            }

        public static String getToken(Context context){
            SharedPreferences sharedPreferences =context.getSharedPreferences(MyApplication.PREFERENCES_NAME,Context.MODE_PRIVATE);
         return sharedPreferences.getString(MyApplication.PREFERENCES_NAME,"");
        }

    }





}
