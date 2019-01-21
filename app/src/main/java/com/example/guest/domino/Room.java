package com.example.guest.domino;

import java.util.List;

public class Room {

    public String room_name;
   public  String id;
   int img;
    public int on_start;
    public int capacity;
   public  int Complexity;
   //public  List<User> peer_list;
   public  List<String> peer_list;


   public int peer_count;
   public boolean close;

   public Room(){

   }

   public void generateImg(){

   }

    public static class RoomStatus{
        String status;
        String prepared;

    }

    public static Room GenerateRoom(){
        Room r = new Room();
        r.setCapacity((int)(Math.random()*7));
        r.setPeer_count(r.getCapacity()-(int)(Math.random()*r.getCapacity()));
        int n=(int)(Math.random()*4);
        switch (n){
            case 0: r.setName("CoNtEsT");
                break;
            case 1: r.setName("Math contest 1");
                break;
            case 2: r.setName("New contest");
                break;
            case 3: r.setName("CONTEST");
                break;
                default: r.setName(" ");
        }

        return r;
    }

    public String getName() {
        return room_name;
    }

    public void setPrivacyMode(boolean value){
        close=value;
    }

    public void setName(String name) {
        this.room_name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getComplexity() {
        return Complexity;
    }

    public void setComplexity(int complexity) {
        Complexity = complexity;
    }

    public List<String> getActive_user() {
        return peer_list;
    }

    public void setActive_user(List<String> active_user) {
        this.peer_list = active_user;
    }

    public int getPeer_count() {
        return peer_count;
    }

    public void setPeer_count(int peer_count) {
        this.peer_count = peer_count;
    }
}
