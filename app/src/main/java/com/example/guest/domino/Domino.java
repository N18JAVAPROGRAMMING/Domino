package com.example.guest.domino;

public class Domino {
   private Task task;

   private int up;
   private int down;

   private boolean free;

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
}
