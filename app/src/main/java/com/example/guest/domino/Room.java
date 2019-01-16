package com.example.guest.domino;

import java.util.List;

public class Room {

    String room_name;
    String id;
    int on_start;
    int capacity;
    int Complexity;
    List<User> active_user;
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
            case 0: r.setRoom_name("CoNtEsT");
                break;
            case 1: r.setRoom_name("Math contest 1");
                break;
            case 2: r.setRoom_name("New contest");
                break;
            case 3: r.setRoom_name("CONTEST");
                break;
                default: r.setRoom_name(" ");
        }

        return r;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setPrivacyMode(boolean value){
        close=value;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
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
        return active_user;
    }

    public void setActive_user(List<User> active_user) {
        this.active_user = active_user;
    }

    public int getPeer_count() {
        return peer_count;
    }

    public void setPeer_count(int peer_count) {
        this.peer_count = peer_count;
    }
}
