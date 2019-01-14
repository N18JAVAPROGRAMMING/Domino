package com.example.guest.domino;

import java.util.List;

public class Room {

    String Name;
    int required_count;
    int Complexity;
    List<User> active_user;
    int current_count;

    public static Room GenerateRoom(){
        Room r = new Room();
        r.setRequired_count((int)(Math.random()*7));
        r.setCurrent_count(r.getRequired_count()-(int)(Math.random()*r.getRequired_count()));
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
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getRequired_count() {
        return required_count;
    }

    public void setRequired_count(int required_count) {
        this.required_count = required_count;
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

    public int getCurrent_count() {
        return current_count;
    }

    public void setCurrent_count(int current_count) {
        this.current_count = current_count;
    }
}
