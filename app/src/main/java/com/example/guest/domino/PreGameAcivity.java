package com.example.guest.domino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PreGameAcivity extends AppCompatActivity {

    List<User> users;
    RecyclerView recyclerView;
    UserAdapter adapter;
    int room_id;
    ServerManager manager;
    ServerManager.BackgroundThread thread;
    Room current_room;
    boolean startGame=false;
    boolean press=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_game_acivity);

        room_id =Integer.valueOf(getIntent().getIntExtra(MyApplication.CURRENT_ROOM,-1));
        manager=  new ServerManager(getApplicationContext());

        LoadInformation();
        thread.start();





        recyclerView=findViewById(R.id.user_list);
           users= new ArrayList<User>();
        for (int i=0; i<5; i++){
            users.add(User.generateUser());
        }
        adapter=  new UserAdapter(users);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
       // recyclerView.setLayoutFrozen(true);

    }

    @Override
    protected void onPause() {
        thread.setRunFlag(false);
        super.onPause();
    }

    @Override
    protected void onResume() {
        thread.setRunFlag(true);
        super.onResume();
    }

    public void UpdateAdapter(){
        adapter.setData(current_room.peer_list);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        manager.peerConnect(room_id, new ServerManager.onPeerConnectListener() {
            @Override
            public void connect(Room room) {
                final Room r=room;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        current_room=r;
                        UpdateAdapter();
                    }
                });

        }

            @Override
            public void fail() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        goBack();
                    }
                });

            }
        });


    }

    @Override
    protected void onStop() {
           manager.peerDisconnect(room_id, new ServerManager.onPeerDisonnectListener() {
               @Override
               public void disconnect(List<Room> list) {

               }

               @Override
               public void error() {

               }
           });
        super.onStop();
    }


    public void LoadInformation(){
        thread=  new ServerManager.BackgroundThread(getApplicationContext(),
                ServerManager.BackgroundThread.UPDATE_ROOMINFO,2000);
        thread.setLoadRoomInformation(room_id, new ServerManager.BackgroundThread.OnLoadRoomInformation() {
            @Override
            public void ok(Room room) {
               final List<User> users=current_room.peer_list;
                current_room=room;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UpdateAdapter();
                        if (current_room.on_start==1){
                            thread.setRunFlag(false);
                            startGame=true;
                            startRoom();
                        }
                    }
                });


            }

            @Override
            public void fail() {
                Log.d("listener","fail_room_information");
               // thread.setRunFlag(false);
                //goBack();

            }
        });


    }


    public void goBack(){

       /* Intent intent =  new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);*/
       finish();
    }


    public void startRoom(){
        Intent intent= new Intent(getApplicationContext(),GameActivity.class);
        intent.putExtra(MyApplication.CURRENT_ROOM,room_id);
        startActivity(intent);
    }


}


