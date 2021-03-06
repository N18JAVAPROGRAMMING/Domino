package com.example.guest.domino;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PreGameAcivity extends AppCompatActivity {

    List<User> users;
    RecyclerView recyclerView;
    UserAdapter adapter;
    int room_id;
    TextView title;
    ServerManager manager;
    ServerManager.BackgroundThread thread;
    Room current_room;
    View leave;
    int capacity;
    ProgressBar progressBar;
    boolean startGame=false;
    boolean press=false;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_game_acivity);

        room_id =Integer.valueOf(getIntent().getIntExtra(MyApplication.CURRENT_ROOM,-1));
        manager=  new ServerManager(getApplicationContext());
        title = findViewById(R.id.name_room);
        LoadInformation();
        thread.start();
         current_room=new Room();
         current_room.id=String.valueOf(room_id);
         current_room.capacity=Integer.valueOf(getIntent().getIntExtra(MyApplication.CURRENT_ROOM_CAPACITY,5));





        recyclerView=findViewById(R.id.user_list);
        progressBar=findViewById(R.id.progress);

        progressBar.setMax(current_room.capacity);
        progressBar.setProgress(0);


        leave = findViewById(R.id.exit);
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.peerDisconnect(room_id, new ServerManager.onPeerDisonnectListener() {
                    @Override
                    public void disconnect(List<Room> list) {
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               goBack();
                           }
                       });
                    }

                    @Override
                    public void error() {

                    }
                });
            }
        });
           users= new ArrayList<User>();
      /*  for (int i=0; i<5; i++){
            users.add(User.generateUser());
        }*/
        adapter=  new UserAdapter(users,true);
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
        ArrayList<User> users =  new ArrayList<User>();
        if (current_room!=null) {
            for (String s : current_room.peer_list) {
                User user = new User();
                user.name = s;
                users.add(user);
            }

            // progressBar.setProgress(users.size());

            adapter.setData(users);
            adapter.notifyDataSetChanged();
        }
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
                       if(current_room==null){
                          // progressBar.setMax(r.capacity);

                           Log.d("roomlog","perr_count "+current_room.room_name);
                       }
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
        if(!startGame){
           manager.peerDisconnect(room_id, new ServerManager.onPeerDisonnectListener() {
               @Override
               public void disconnect(List<Room> list) {

               }

               @Override
               public void error() {

               }
           });}
        super.onStop();
    }


    public void LoadInformation(){
        thread=  new ServerManager.BackgroundThread(getApplicationContext(),
                ServerManager.BackgroundThread.UPDATE_ROOMINFO,1000);
        thread.setLoadRoomInformation(room_id, new ServerManager.BackgroundThread.OnLoadRoomInformation() {
            @Override
            public void ok(Room room) {

                current_room=room;
                final List<String> users=current_room.peer_list;
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {

                        UpdateAdapter();
                        if (progressBar!=null) {
                            progressBar.setProgress(users.size(),true);
                        }
                        title.setText(current_room.getName());
                        if (current_room.on_start==1 && !startGame){

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
        intent.putExtra(MyApplication.ROOM_NAME,current_room.room_name);
        startActivity(intent);

    }


}


