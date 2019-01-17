package com.example.guest.domino;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;



@Database(entities = {Task.class}, version = 1)
public abstract class TaskData extends RoomDatabase {
    public abstract DaoTask taskDao();
}
