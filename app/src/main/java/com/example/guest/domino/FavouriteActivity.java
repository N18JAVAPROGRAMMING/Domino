package com.example.guest.domino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class FavouriteActivity extends AppCompatActivity {

    private int id;
    private Task task;

    private TextView problemText;
    private TextView solutionText;

    private CardView showSolution;
    private CardView exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        problemText = findViewById(R.id.problem_text);
        solutionText = findViewById(R.id.solution);
        showSolution = findViewById(R.id.show_card);
        exit = findViewById(R.id.exit);

        id = getIntent().getIntExtra("id", 0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                task = MyApplication.getTaskById(id);
                problemText.setText(task.getCond());
                solutionText.setText(task.getSol());
            }
        }).start();

        showSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problemText.setVisibility(View.VISIBLE);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
