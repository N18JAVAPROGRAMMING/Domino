package com.example.guest.domino;

public class User {

    public String name;
    public int score;

    public static User generateUser(){
        User user = new User();
        user.name = "jdjf";
        user.score = (int)(Math.random() * 1000);
        return user;
    }




}
