package com.example.guest.domino;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.content.SharedPreferences;

@Entity
public class User {
    @PrimaryKey
    int id=0; // default id for main user
    public String name;
    public int score;

    public static User generateUser(){
        User user = new User();
        user.name = "jdjf";
        user.score = (int)(Math.random() * 1000);
        return user;
    }

    public static void saveUser(Context context, User user){


    }




}
