package com.example.guest.domino;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreTableFragment extends Fragment {
    private RecyclerView table;
    private ArrayList<User> list = new ArrayList<>();

    UserAdapter adapter;
    ServerManager manager;
    int room_id;
    ServerManager.BackgroundThread thread;

    public ScoreTableFragment() {
        // Required empty public constructor
    }

    public static ScoreTableFragment newInstance(int room_id) {

        Bundle args = new Bundle();
        
        ScoreTableFragment fragment = new ScoreTableFragment();
        fragment.setArguments(args);
        fragment.setRoom_id(room_id);
        return fragment;
    }

    public void setRoom_id(int room_id){
        this.room_id=room_id;
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

        manager=  new ServerManager(getActivity().getApplicationContext());
        thread=  new ServerManager.BackgroundThread(getContext(),
                ServerManager.BackgroundThread.UPDATE_SCORE,2000);


        adapter = new UserAdapter(list);
        table.setAdapter(adapter);
        table.setLayoutManager(new LinearLayoutManager(getContext()));
        startBackground();


       return  view;
    }

    public void UpdateUsers(List<User> list){
        adapter.setData(list);
    }

    public void startBackground(){
        thread.setGetterUsersScore(room_id, new ServerManager.BackgroundThread.GetterUsersScore() {
            @Override
            public void ok(APIService.ModelUserData model) {
               final List<User> users=  new ArrayList<User>();
                for (int i=0; i<users.size(); i++){
                    User user =  new User();
                    user.name=model.users_data.get(i);
                    user.localScore=model.score_data.get(i)[0];
                    user.countOk=model.score_data.get(i)[1];
                    user.countError=model.score_data.get(i)[2];
                    users.add(user);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UpdateUsers(users);
                    }
                });
            }

            @Override
            public void error() {

            }
        });
        thread.start();

    }

}