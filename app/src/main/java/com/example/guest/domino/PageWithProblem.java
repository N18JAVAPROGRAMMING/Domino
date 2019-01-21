package com.example.guest.domino;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageWithProblem extends Fragment {

    public void setDomino(Domino domino) {
        this.domino = domino;
    }

    private Domino domino;
    private View likeView;

    private TextView dominoText;
    private TextView problemText;
    private EditText editText;
    private View ansNext;
    OnAnswerListener listener;

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
        dominoText = view.findViewById(R.id.domino_text);
        problemText = view.findViewById(R.id.problem_text);
        likeView= view.findViewById(R.id.like);
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
        setOnSaveListener();


        problemText.setText(domino.getTask().getCond());
        dominoText.setText(domino.getUp() + " / " + domino.getDown());

        return view;
    }

    private void setOnSaveListener(){
        likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.saveTask(domino.getTask());
                Snackbar.make(v,"Задача сохранена",Snackbar.LENGTH_SHORT);
            }
        });
    }

    public static interface OnAnswerListener{
        void send(String ans,Domino domino);
    }
}
