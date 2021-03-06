package com.example.guest.domino;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoomsFragment extends Fragment {



    RoomsAdapter adapter;
    RecyclerView recyclerView;
    List<Room> rooms;

    ServerManager.BackgroundThread thread;
    OnStartListener listener;


   static interface OnSelectedRoom{
        void StartGame(Room room);
    }

    interface  OnStartListener{
       void preGame(Room room);
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

    public void setListener(OnStartListener start){
       listener=start;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rooms, container, false);
        recyclerView=v.findViewById(R.id.list);

        thread =  new ServerManager.BackgroundThread(getContext(), ServerManager.BackgroundThread.UPDATE_ROOMLIST,1000);

        setOnUpdate();
        thread.start();

       rooms= new ArrayList<Room>();
       /* for (int i=0; i<20; i++){
            rooms.add(Room.GenerateRoom());
        }*/
        //defineSocketListener();
        adapter=  new RoomsAdapter(rooms);
         setAdapterListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);




        return v;

    }

    @Override
    public void onResume() {
       thread.setRunFlag(true);
        super.onResume();
    }

    @Override
    public void onPause() {
       thread.setRunFlag(false);
        super.onPause();
    }

    public void setAdapterListener(){
        adapter.setCallBackStartGame(new OnSelectedRoom() {
            @Override
            public void StartGame(Room room) {
                Log.d("listener","pregame");
                listener.preGame(room);
            }
        });
    }

    public void setOnUpdate(){
        thread.setUpdateRoomListListener(new ServerManager.UpdateRoomListListener() {
            @Override
            public void onUpdate(List<Room> main) {
                final List<Room> m =main;
                if (getActivity()==null)return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("listener","onUpdate");
                        adapter.setRoomList(m);
                        adapter.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void error(String msg) {
                //Snackbar.make(getView(),msg,Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void defineSocketListener(){
         SocketThread.getInstance().setOnUpdateRoomListListener(new SocketThread.OnUpdateRoomListListener() {
             @Override
             public void onAddNewRoom(Room room) {
                 final Room result=room;
                 getActivity().runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         rooms.add(result);
                         adapter.notifyDataSetChanged();
                     }
                 });
             }

             @Override
             public void onDeleteRoom(Room room) {
                 final Room result=room;
                 getActivity().runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         rooms.remove(result);
                         adapter.notifyDataSetChanged();
                     }
                 });
             }

             @Override
             public void onStartRoom(Room room) {

             }
         });
    }




}
