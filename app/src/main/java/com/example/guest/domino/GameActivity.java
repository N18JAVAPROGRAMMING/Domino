package com.example.guest.domino;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private Fragment fragmentTable;
    private Fragment fragmentProblems;

    private ArrayList<Domino> dominoes = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentTable).commit();
                    //getSupportFragmentManager().beginTransaction().show(fragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    //getSupportFragmentManager().beginTransaction().hide(fragment).commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentProblems).commit();
                    return true;
                case R.id.navigation_notifications:
                    //getSupportFragmentManager().beginTransaction().hide(fragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        for(int i = 0; i < 10; i++){
            dominoes.add(Domino.generateDomino());
        }

        fragmentTable = TableFragment.newInstance(dominoes);
        fragmentProblems = ProblemsFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentTable).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
