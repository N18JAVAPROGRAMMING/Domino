package com.example.guest.domino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FavouriteActivity extends AppCompatActivity {

    private int id;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        id = getIntent().getIntExtra("id", 0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                task = MyApplication.getTaskById(id);
            }
        }).start();
    }
}
