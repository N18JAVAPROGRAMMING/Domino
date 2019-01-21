package com.example.guest.domino;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ImageView numbers1;
    private ImageView numbers2;

    private CardView favourite;

    public PageWithProblem() {
        // Required empty public constructor
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
        numbers1 = view.findViewById(R.id.numbers1);
        numbers2 = view.findViewById(R.id.numbers2);
        favourite = view.findViewById(R.id.like);

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyApplication.saveTask(domino.getTask());
                    }
                }).start();
            }
        });

        if(domino.getUp() != 0)
            numbers1.setImageBitmap(ColoredNumbers.getInstance()
                    .numberWhite(getContext(), domino.getUp()));

        if(domino.getDown() != 0)
            numbers2.setImageBitmap(ColoredNumbers.getInstance()
                    .numberWhite(getContext(), domino.getDown()));

        problemText.setText(domino.getTask().getCond());

        return view;
    }

}
