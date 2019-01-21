package com.example.guest.domino;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
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
    boolean connect=false;
    int press=0;





    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new ServerManager(getApplicationContext());
        connect=false;






       // StartSockets();


        userFragment= UserFragment.newInstance();
        roomsFragment=RoomsFragment.newInstance();



         roomsFragment.setListener(new RoomsFragment.OnStartListener() {
             @Override
             public void preGame(Room room) {
                 press++;
                 if (connect){
                     return;
                 }
                 if (press>1){
                     return;
                 }
                final Room r =room;
                Log.d("listener","room_id  "+r.id);
                 manager.peerConnect(Integer.valueOf(room.id),new ServerManager.onPeerConnectListener() {
                     @Override
                     public void connect(Room room) {
                         Log.d("listener",""+connect);
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 if (!connect){
                                     connect=true;
                                     Intent intent =  new Intent(getApplicationContext(),PreGameAcivity.class);
                                     Log.d("listener","topregame");
                                     intent.putExtra(MyApplication.CURRENT_ROOM,Integer.valueOf(r.id));
                                     startActivity(intent);
                                     press=0;}
                             }
                         });

                     }

                     @Override
                     public void fail() {
                         press=0;
                     }
                 });



             }
         });

        createRoomFragment=CreateRoom.newInstance();
       createRoomFragment.setOnCreateRoomListener(new CreateRoom.OnCreateRoomListener() {
           @Override
           public void OnRoomCreated(Room room) {
               final Room r=room;
               connect=true;
               //getSupportFragmentManager().beginTransaction().replace(R.id.fragment,roomsFragment).commit();
               if (room==null){
                   Snackbar.make(next,"Ошибка создания",Snackbar.LENGTH_SHORT);
               } else {
               Intent intent =  new Intent(getApplicationContext(),PreGameAcivity.class);
               intent.putExtra(MyApplication.CURRENT_ROOM,Integer.valueOf(r.id));
               startActivity(intent);}
           }
       });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,userFragment).commit();







        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        InitBottomNavigationBar();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onRestart() {
        connect=false;
        super.onRestart();
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
