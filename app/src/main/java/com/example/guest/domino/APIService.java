package com.example.guest.domino;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    public static String HOST="";


    @POST("")
    Call<Room> createRoom();

    @GET("")
    Call<List<Room>> getAllRooms();

    //@GET<L>





}
