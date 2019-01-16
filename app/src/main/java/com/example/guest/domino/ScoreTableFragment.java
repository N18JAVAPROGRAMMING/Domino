package com.example.guest.domino;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreTableFragment extends Fragment {
    private RecyclerView table;
    private ArrayList<User> list = new ArrayList<>();


    public ScoreTableFragment() {
        // Required empty public constructor
    }

    public static ScoreTableFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ScoreTableFragment fragment = new ScoreTableFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score_table, container, false);
        table = view.findViewById(R.id.table);


        for (int i=0; i<20; i++) {

            list.add(User.generateUser());
        }
        ScoreTableAdapter adapter = new ScoreTableAdapter(list);
        table.setAdapter(adapter);
        table.setLayoutManager(new LinearLayoutManager(getContext()));


       return  view;


    }

}