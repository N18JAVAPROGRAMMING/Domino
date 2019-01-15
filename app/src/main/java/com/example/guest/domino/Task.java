package com.example.guest.domino;

public class Task {
    private String desctiprion;  //условие задачи
    private int id;
    private int level;
    private String solution="";


    private double answer;


    public static Task GenerateTask(){  //ждем сервер пока так :)

        Task t=  new Task();
        int n = (int)(Math.random()*3);

        switch (n){
            case 1:
                t.setDesctiprion("При большом количестве информации, которую нужно поместить на экране приходится использовать полосы прокрутки. В Android существуют специальные компоненты ScrollView и HorizontalScrollView, которые являются контейнерными элементами и наследуются от ViewGroup. Обратите внимание, что класс TextView использует свою собственную прокрутку и не нуждается в добавлении отдельных полос прокрутки. Но использование отдельных полос даже с TextView может улучшить вид вашего приложения и повышает удобство работы для пользователя.");
                t.setAnswer(53);
               break;
            case 0:
                t.setDesctiprion("Какое число лишнее? Оно не обладает свойством, которым обладают остальные числа.\n" +
                        "\n" +
                        "9678  4572  5261  5133  3527  6895  7768  ");
                t.setAnswer(3527);
                break;
            case 3:
                t.setDesctiprion("Какое число лишнее? Оно не обладает свойством, которым обладают остальные числа.\n" +
                        "\n" +
                        "9678  4572  5261  5133  3527  6895  7768  ");
                t.setAnswer(3527);
                break;
            case 2:
                t.setDesctiprion("Положительное число увеличивается в 19 раз," +
                        " если в его десятичной записи поменять местами цифры, стоящие на первом" +
                        " и третьем местах после запятой. " +
                        "Найдите третью цифру после запятой в десятичной записи этого числа. ");
                t.setAnswer(0.9405);
                break;


        }
        t.setId((int)Math.random()*10000);
        t.setLevel(1+(int)(Math.random()*4));
        return t;



    }










    public double getAnswer() {
        return answer;
    }

    public void setAnswer(double answer) {
        this.answer = answer;
    }

    public String getDesctiprion() {
        return desctiprion;
    }

    public void setDesctiprion(String desctiprion) {
        this.desctiprion = desctiprion;
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

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }



}
