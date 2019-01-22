package com.example.guest.domino;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIService {

    public static String HOST="http://95.163.180.246/";

    public static class ModelCreateRoom{
        String token;
        String domino_amt;
        String room_name;
        int capacity;

        public ModelCreateRoom(String token, String domino_amt, String room_name, int capacity) {
            this.token = token;
            this.domino_amt = domino_amt;
            this.room_name = room_name;
            this.capacity = capacity;
        }
    }


  /*  @POST("room/create")
    void createRoom(@Query("token") String token, @Query("domino_amt") String amount,@Query("room_name") String name, @Query("capacity") int capacity);*/

    @POST("room/create")
    Call<Room> createRoom(@Body ModelCreateRoom model);

    @GET("room/info")
    Call<List<Room>> getAllRooms(@Query("token") String token);

    @GET("token/check")
    Call<Status> checkToken(@Query("token") String token);




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

    @GET("peer/score")
    Call<ModelScore> getGlobalScore(@Query("token") String token, @Query("username")String username);





    @GET("room/status")
    Call<Room> roomStatus(@Query("token") String token,@Query("room_id") String room_id);



  /*  @POST()
    Call<Room.RoomStatus> peerConnect(@Query("room_id")String room_id, @Query("peer_name")String username);

    @POST()
    void peerDisconnect(@Query("room_id")String room_id, @Query("peer_name")String username);*/


     //new Version
    @POST("room/connect")
    Call<Room> peerConnect(@Body ModelPeer model);

    @POST("room/disconnect")
    Call<List<Room>> peerDisconnect(@Body ModelPeer model);

    @GET("game/dependencies")
    Call<Dependencies> dependencies(@Query("room_id")String room_id,@Query("token") String token);



    @GET("game/domino")
    Call<Task> getTask(@Query("token")String token,@Query("task_id")String id);

    @GET("game/task")
    Call<CaptureModel> statusTasks(@Query("token") String token,@Query("room_id") String id);

    @POST("game/score")
    Call<Void> setScore(@Body ModelPostTask mode);

    @GET("game/over")
    Status gameOver(@Query("token")String toke, @Query("room_id")String room_id );







    public class ModelScore{
        int score;
    }


    public class Status{
        String status;
    }



    public class ModelPostTask{
        int amt;
        String token;
        int task_id;

        public ModelPostTask(int amt, String token, int task_id) {
            this.amt = amt;
            this.token = token;
            this.task_id = task_id;
        }
    }




    static class User{
       public String password;
       public String username;
    }



    class CaptureModel{
        List<Integer> dominoes;
        List<Integer> task_status;
    }





    //@GET<L>

    class Dependencies{
        List<Integer> dominoes;
        List<Integer>  tasks;
    }

    public static class ModelPeer{

        String room_id;
        String token;

        public ModelPeer(String room_id, String token) {
            this.room_id = room_id;
            this.token = token;
        }
    }

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
