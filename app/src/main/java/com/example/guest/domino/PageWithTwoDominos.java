package com.example.guest.domino;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageWithTwoDominos extends Fragment {

    private Button button1;
    private Button button2;

    private Domino domino1;
    private Domino domino2;

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
        //button1 = view.findViewById(R.id.button1);
        //button2 = view.findViewById(R.id.button2);
        //button1.setText(domino1.getTask().getDesctiprion());
        //button2.setText(domino2.getTask().getDesctiprion());
        //setOnClickListeners();
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
