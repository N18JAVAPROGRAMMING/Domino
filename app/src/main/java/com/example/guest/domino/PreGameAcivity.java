package com.example.guest.domino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PreGameAcivity extends AppCompatActivity {

    List<User> users;
    RecyclerView recyclerView;
    UserAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_game_acivity);
        recyclerView=findViewById(R.id.user_list);
           users= new ArrayList<User>();
        for (int i=0; i<5; i++){
            users.add(User.generateUser());
        }
        adapter=  new UserAdapter(users);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutFrozen(true);



    }
}
