package com.example.guest.domino;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    ServerManager manager;
    UserFragment userFragment;
    CreateRoom createRoomFragment;
    RoomsFragment roomsFragment;
    Button next;





    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new ServerManager(getApplicationContext());






       // StartSockets();


        userFragment= UserFragment.newInstance();
        roomsFragment=RoomsFragment.newInstance();

         roomsFragment.setListener(new RoomsFragment.OnStartListener() {
             @Override
             public void preGame(Room room) {
                final Room r =room;
                manager.peerConnect(Integer.valueOf(room.id),new ServerManager.onPeerConnectListener() {
                    @Override
                    public void connect(Room room) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent =  new Intent(getApplicationContext(),PreGameAcivity.class);
                                intent.putExtra(MyApplication.CURRENT_ROOM,r.id);
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void fail() {
                         //nothing to do
                    }
                });
             }
         });

        createRoomFragment=CreateRoom.newInstance();
       createRoomFragment.setOnCreateRoomListener(new CreateRoom.OnCreateRoomListener() {
           @Override
           public void OnRoomCreated(Room room) {
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment,roomsFragment).commit();
           }
       });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,userFragment).commit();







        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        InitBottomNavigationBar();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void InitBottomNavigationBar(){
        Log.d("navigate",R.id.navigation_home+"");
        Log.d("navigate",R.id.navigation_dashboard+"");
        Log.d("navigate",R.id.navigation_notifications+"");
        Log.d("navigate","click  "+navigation.getSelectedItemId());
        mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("navigate","click"+" id_item "+item.getItemId());
                switch (item.getItemId()) {
                    case R.id.profile:


                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment,userFragment).commit();
                        Log.d("NAVIGATE","user");

                        return true;

                    case R.id.room_list:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment,roomsFragment).commit();
                        Log.d("NAVIGATE","rooms");
                        return true;
                    case R.id.create_private:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment,createRoomFragment).commit();
                        Log.d("NAVIGATE","createRoomFragment");
                        return true;
                }
                return false;
            }
        };
    }

    public void StartSockets(){
       MyApplication.socketThread.setOnExampleListener(new SocketThread.ExampleListener() {
           @Override
           public void onExampleListener(String s) {
               final String s1 =s;
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(getApplicationContext(),s1,Toast.LENGTH_LONG).show();
                   }
               });
           }
       });



    }

}
