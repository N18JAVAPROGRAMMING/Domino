package com.example.guest.domino;

public class Domino {
   private Task task;

   public static final int FREE_MODE=0;
   public static final int SOLVING_MODE=1;
   public static final int WASTED_MODE=2;
   public static final int RESERVED=3;

   private int status = FREE_MODE;

   public int task_id;
   public int attempt=0;

   private int up;

   public Domino(int up, int down) {
      this.up = up;
      this.down = down;
   }

   private int down;

   private boolean free;
   int mode;


   public  Domino(){

   }
   public static int[][] main=
           {{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},
           {0,6},{1,1},{1,2},{1,3},{1,4},
           {1,5},{1,6},{2,2},{2,3},{2,4},
           {2,5},{2,6},{3,3},{3,4},{3,5},{3,6},
           {4,4},{4,5},{4,6},{5,5},{5,6},{6,6}};


   public static Domino generateDomino(){
       Domino d = new Domino();
       d.setTask(Task.GenerateTask());
       d.setUp((int)(Math.random() * 7));
       d.setDown((int)(Math.random() * 7));
       d.free = true;
       return d;
   }

   public Task getTask() {
      return task;
   }

   public void setTask(Task task) {
      this.task = task;
   }

   public int getUp() {
      return up;
   }

   public void setUp(int up) {
      this.up = up;
   }

   public int getDown() {
      return down;
   }

   public void setDown(int down) {
      this.down = down;
   }

   public boolean isFree() {
      return free;
   }

   public void setFree(boolean free) {
      this.free = free;
   }

   public void setStatus(int status) {
      this.status = status;
   }
}
