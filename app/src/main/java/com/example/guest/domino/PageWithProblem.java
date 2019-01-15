package com.example.guest.domino;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageWithProblem extends Fragment {

    public void setDomino(Domino domino) {
        this.domino = domino;
    }

    private Domino domino;

    private TextView dominoText;
    private TextView problemText;

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
        dominoText = view.findViewById(R.id.domino_text);
        problemText = view.findViewById(R.id.problem_text);

        problemText.setText(domino.getTask().getDesctiprion());
        dominoText.setText(domino.getUp() + " / " + domino.getDown());

        return view;
    }

}
