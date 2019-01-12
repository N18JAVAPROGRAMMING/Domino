package com.example.guest.domino;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RegFragment extends Fragment {

   Button next;
   static InActivity inActivity;



    public RegFragment() {
        // Required empty public constructor
    }


    public static RegFragment newInstance(InActivity in) {
        inActivity=in;
        RegFragment fragment = new RegFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reg, container, false);
        next=v.findViewById(R.id.r_next);
         next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // обработка регистрации
                 inActivity.nextActivity();
             }
         });
        return v;
    }



}
