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
public class RoomsFragment extends Fragment {


    RecyclerView recyclerView;
    List<Room> rooms;
    OnCallBackStartGame onCallBackStartGame;


    interface OnCallBackStartGame{
        void StartGame(Room room);
    }

    public RoomsFragment() {
        // Required empty public constructor
    }

    public static RoomsFragment newInstance() {

        Bundle args = new Bundle();

        RoomsFragment fragment = new RoomsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setCallBack(OnCallBackStartGame onCallBackStartGame){
        this.onCallBackStartGame=onCallBackStartGame;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rooms, container, false);
        recyclerView=v.findViewById(R.id.list);

        rooms= new ArrayList<Room>();
        for (int i=0; i<20; i++){
            rooms.add(Room.GenerateRoom());
        }

        RoomsAdapter adapter =  new RoomsAdapter(rooms);
        adapter.setCallBackStartGame(onCallBackStartGame);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);



        return v;

    }

}
