package com.example.guest.domino;


import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageWithProblem extends Fragment {

    public void setDomino(Domino domino) {
        this.domino = domino;
    }

    private Domino domino;

    private TextView problemText;
    private EditText editText;
    private View ansNext;
    OnAnswerListener listener;
    private ImageView numbers1;
    private ImageView numbers2;

    private CardView favourite;

    public interface OnAnswerListener{
        void send(String ans, Domino domino);
    }

    public PageWithProblem() {
        // Required empty public constructor
    }

    public void setOnAnswerListener(OnAnswerListener onAnswerlistener){
        listener=onAnswerlistener;
    }

    public static PageWithProblem newInstance(Domino domino) {

        Bundle args = new Bundle();

        PageWithProblem fragment = new PageWithProblem();
        fragment.setArguments(args);

        fragment.setDomino(domino);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_with_problem, container, false);
        problemText = view.findViewById(R.id.problem_text);

        editText=view.findViewById(R.id.answer_field);
        ansNext=view.findViewById(R.id.answer);
        ansNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().length()==0){
                    Snackbar.make(v,"Введите ответ",Snackbar.LENGTH_SHORT);
                    return;
                }

                listener.send(editText.getText().toString(),domino);
            }
        });


        numbers1 = view.findViewById(R.id.numbers1);
        numbers2 = view.findViewById(R.id.numbers2);
        favourite = view.findViewById(R.id.like);

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MyApplication.saveTask(domino.getTask());
                        }catch (Exception e){

                        }
                    }
                }).start();
            }
        });

        if(domino.getUp() != 0)
            if(domino.attempt == 0)
                numbers1.setImageBitmap(ColoredNumbers.getInstance()
                        .numberWhite(getContext(), domino.getUp()));
            else
                numbers1.setImageBitmap(ColoredNumbers.getInstance()
                        .numberPink(getContext(), domino.getUp()));

        if(domino.getDown() != 0)
            numbers2.setImageBitmap(ColoredNumbers.getInstance()
                    .numberWhite(getContext(), domino.getDown()));


       // problemText.setText(domino.getTask().getCond());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            problemText.setText(Html.fromHtml(domino.getTask().getCond(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            problemText.setText(Html.fromHtml(domino.getTask().getCond()));
        }

        return view;
    }



}
