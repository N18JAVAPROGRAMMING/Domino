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

    private int left;
    private int right;

    public void setListener(OnFragmentClickListener listener) {
        this.listener = listener;
    }

    OnFragmentClickListener listener;

    public interface OnFragmentClickListener{
        void onClick(int number);
    }

    public PageWithTwoDominos() {
        // Required empty public constructor
    }

    public void setNumbers(int left, int right){
        this.left = left;
        this.right = right;
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
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        setOnClickListeners();
        return view;
    }

    private void setOnClickListeners(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(left);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClick(right);
            }
        });
    }

}
