package com.example.guest.domino;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Task {
    String cond;
    //условие задачи
    @PrimaryKey
    int id;
     int level;
     String sol ="";


    private double ans;


    public static Task GenerateTask(){  //ждем сервер пока так :)

        Task t=  new Task();
        int n = (int)(Math.random()*3);

        switch (n){
            case 1:
                t.setCond("Из 40 т руды выплавляют 20 т металла, " +
                        "содержащего 6% примесей. Какой процент  примесей в руде? ");
                t.setAns(53);
               break;
            case 0:
                t.setCond("Какое число лишнее? Оно не обладает свойством, которым обладают остальные числа.\n" +
                        "\n" +
                        "9678  4572  5261  5133  3527  6895  7768  ");
                t.setAns(3527);
                break;
            case 3:
                t.setCond("Какое число лишнее? Оно не обладает свойством, которым обладают остальные числа.\n" +
                        "\n" +
                        "9678  4572  5261  5133  3527  6895  7768  ");
                t.setAns(3527);
                break;
            case 2:
                t.setCond("Положительное число увеличивается в 19 раз," +
                        " если в его десятичной записи поменять местами цифры, стоящие на первом" +
                        " и третьем местах после запятой. " +
                        "Найдите третью цифру после запятой в десятичной записи этого числа. ");
                t.setAns(0.9405);
                break;


        }
        t.setId((int)Math.random()*10000);
        t.setLevel(1+(int)(Math.random()*4));
        return t;



    }










    public double getAns() {
        return ans;
    }

    public void setAns(double ans) {
        this.ans = ans;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSol() {
        return sol;
    }

    public void setSol(String sol) {
        this.sol = sol;
    }



}
