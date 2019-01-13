package com.example.guest.domino;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    UserFragment userFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userFragment= UserFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,userFragment).commit();



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
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment,userFragment).commit();
                        return true;
                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction()
                                .remove(userFragment).commit();

                        return true;
                    case R.id.navigation_notifications:
                        getSupportFragmentManager().beginTransaction()
                                .remove(userFragment).commit();

                        return true;
                }
                return false;
            }
        };
    }

}
