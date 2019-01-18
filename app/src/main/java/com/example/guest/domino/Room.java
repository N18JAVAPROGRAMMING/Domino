package com.example.guest.domino;

import java.util.List;

public class Room {

    String room_name;
    String id;
    int on_start;
    int capacity;
    int Complexity;
    List<User> users;
    List<String> peer_list;
    int peer_count;
    boolean close;

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

    public List<User> getActive_user() {
        return users;
    }

    public void setActive_user(List<User> active_user) {
        this.users = active_user;
    }

    public int getPeer_count() {
        return peer_count;
    }

    public void setPeer_count(int peer_count) {
        this.peer_count = peer_count;
    }
}
