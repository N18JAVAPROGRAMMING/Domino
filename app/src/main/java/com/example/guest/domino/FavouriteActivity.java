package com.example.guest.domino;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class FavouriteActivity extends AppCompatActivity {

    private int id;
    private Task task;

    private TextView problemText;
    private TextView solutionText;

    private TextView answer;
    private  TextView answerText;
    private TextView btnText;

    private CardView showSolution;
    private CardView exit;

    boolean shown=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        problemText = findViewById(R.id.problem_text);
        solutionText = findViewById(R.id.solution);
        answerText=findViewById(R.id.answer_text);
        answer=findViewById(R.id.answer);
        btnText=findViewById(R.id.btn_text);
        showSolution = findViewById(R.id.show_card);
        exit = findViewById(R.id.exit);

        id = getIntent().getIntExtra("id", 0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                task = MyApplication.getTaskById(id);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    problemText.setText(Html.fromHtml(task.getCond(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    problemText.setText(Html.fromHtml(task.getCond()));
                }
                //problemText.setText(task.getCond());
                 answerText.setText(task.ans);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    solutionText.setText(Html.fromHtml(task.getSol(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    solutionText.setText(Html.fromHtml(task.getSol()));
                }

                if(task.getSol().length()==0){
                    showSolution.setVisibility(View.GONE);
                }

                //solutionText.setText(task.getSol());
            }
        }).start();


        showSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shown=!shown;
                if (shown) {
                    solutionText.setVisibility(View.VISIBLE);
                    answer.setVisibility(View.VISIBLE);
                    answerText.setVisibility(View.VISIBLE);
                    btnText.setText("Скрыть решение");
                } else {
                    solutionText.setVisibility(View.GONE);
                    answer.setVisibility(View.GONE);
                    answerText.setVisibility(View.GONE);
                    btnText.setText("Показать решение");

                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
