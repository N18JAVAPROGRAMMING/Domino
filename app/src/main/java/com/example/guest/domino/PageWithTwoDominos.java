package com.example.guest.domino;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageWithTwoDominos extends Fragment {

    private Domino domino1;
    private Domino domino2;

    private ImageView img11;
    private ImageView img12;
    private ImageView img21;
    private ImageView img22;

    public void setListener(OnFragmentClickListener listener) {
        this.listener = listener;
    }

    OnFragmentClickListener listener;

    public void setColors(){
        if(domino1.getUp() != 0)
            img11.setImageBitmap(ColoredNumbers.getInstance().numberWhite(getContext(), domino1.getUp()));
    }

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
        img11 = view.findViewById(R.id.Domino11);
        img12 = view.findViewById(R.id.Domino12);
        img21 = view.findViewById(R.id.Domino21);
        img22 = view.findViewById(R.id.Domino22);
        return view;
    }
/*
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
*/
}
