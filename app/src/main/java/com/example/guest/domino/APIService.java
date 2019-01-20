package com.example.guest.domino;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIService {

    public static String HOST="http://95.163.180.246/";


    @POST("room/create")
    void createRoom(@Query("token") String token, @Query("domino_amt") String amount,@Query("room_name") String name, @Query("capacity") int capacity);

    @GET("room/info")
    Call<List<Room>> getAllRooms(@Query("token") String token);

    @GET("token/check")
    Call<Token> checkToken(@Query("token") Token token);


/*    @POST("peer/signup")
    Call<Token> singUp(@Query("username")String name, @Query("password")String password);

    @POST("peer/login")
    Call<Token> logIn(@Query("username")String name, @Query("password")String password);*/

   // @Headers("Content-Type: application/json")
    @POST("peer/signup")
    Call<Token> singUp(@Body User user);

    //@Headers("Content-Type: application/json")
    @POST("peer/login")
    Call<Token> logIn(@Body User user);


    @GET("room/status")
    Call<Room> roomStatus(@Query("token") String token,@Query("id") String room_id);

    @POST()
    Call<Room.RoomStatus> peerConnect(@Query("room_id")String room_id, @Query("peer_name")String username);

    @GET("game/dependencies")
    String dependencies(@Query("room_id")String room_id,@Query("token") String token);

    @POST()
    void peerDisconnect(@Query("room_id")String room_id, @Query("peer_name")String username);

    @GET
    Call<Task> getTask(@Query("task_id")String id);


    static class User{
       public String password;
       public String username;
    }





    //@GET<L>

    public static class Token{

        public String token="";


        public static void SaveToken(Context context,String token){
           SharedPreferences sharedPreferences =context.getSharedPreferences(MyApplication.PREFERENCES_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(MyApplication.TOKEN_PREFERENCES,token);
            editor.apply();
            }

        public static String getToken(Context context){
            SharedPreferences sharedPreferences =context.getSharedPreferences(MyApplication.PREFERENCES_NAME,Context.MODE_PRIVATE);
         return sharedPreferences.getString(MyApplication.TOKEN_PREFERENCES,"");
        }

    }





}
