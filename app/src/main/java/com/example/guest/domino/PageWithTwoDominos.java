package com.example.guest.domino;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageWithTwoDominos extends Fragment {

    private ConstraintLayout button1;
    private ConstraintLayout button2;

    private Domino domino1;
    private Domino domino2;

    private TextView text11;
    private TextView text12;
    private TextView text21;
    private TextView text22;

    public void setListener(OnFragmentClickListener listener) {
        this.listener = listener;
    }

    OnFragmentClickListener listener;

    public interface OnFragmentClickListener{
        void onClick(Domino domino);
    }

    public PageWithTwoDominos() {
        // Required empty public constructor
    }

    public void setDominoes(Domino domino1, Domino domino2){
        this.domino1 = domino1;
        this.domino2 = domino2;
    }

    public static PageWithTwoDominos newInstance() {
        
        Bundle args = new Bundle();
        
        PageWithTwoDominos fragment = new PageWithTwoDominos();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_with_two_dominos, container, false);
        button1 = view.findViewById(R.id.lay1);
        button2 = view.findViewById(R.id.lay2);
        text11 = view.findViewById(R.id.text11);
        text12 = view.findViewById(R.id.text12);
        text22 = view.findViewById(R.id.text22);
        text21 = view.findViewById(R.id.text21);
        text11.setText(domino1.getUp() + "");
        text12.setText(domino1.getDown() + "");
        text21.setText(domino2.getUp() + "");
        text22.setText(domino2.getDown() + "");
        //button1.setText(domino1.getTask().getCond());
        //button2.setText(domino2.getTask().getCond());
        setOnClickListeners();
        return view;
    }

    private void setOnClickListeners(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(domino1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(domino2);
            }
        });
    }

}
