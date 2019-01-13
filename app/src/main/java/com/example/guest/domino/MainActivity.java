package com.example.guest.domino;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    UserFragment userFragment;
    Button next;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
            }
        });


        userFragment= UserFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,userFragment).commit();

        next.setVisibility(View.GONE);

        mTextMessage = (TextView) findViewById(R.id.message);
        InitBottomNavigationBar();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void InitBottomNavigationBar(){
        mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        next.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment,userFragment).commit();

                        return true;
                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction()
                                .remove(userFragment).commit();

                        next.setVisibility(View.VISIBLE); ///remove

                        return true;
                    case R.id.navigation_notifications:
                        getSupportFragmentManager().beginTransaction()
                                .remove(userFragment).commit();
                        next.setVisibility(View.VISIBLE);   //remove
                        return true;
                }
                return false;
            }
        };
    }

}
