package com.example.guest.domino;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    public static String HOST="black-flower.tk";


    @POST("interaction/")
    Call<Room> createRoom(@Query("id") int id, @Query("capacity") int capacity);

    @GET("")
    Call<List<Room>> getAllRooms();

    @GET("")
    Call<Task> getTask();

    @GET("")
    Call<Boolean> LogIn();

    @POST("")
    Call<Boolean> CreateAccount();

    //@GET<L>





}
