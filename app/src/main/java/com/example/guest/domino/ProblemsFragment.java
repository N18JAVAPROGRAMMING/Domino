package com.example.guest.domino;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProblemsFragment extends Fragment {


    public ProblemsFragment() {
        // Required empty public constructor
    }

    public static ProblemsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProblemsFragment fragment = new ProblemsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_problems, container, false);
        return view;
    }

}
