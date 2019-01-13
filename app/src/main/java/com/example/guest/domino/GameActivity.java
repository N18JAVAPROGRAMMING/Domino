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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private static final int NUM_PAGES = 5;
    private ViewPager viewPager;
    private PagerAdapter viewPagerAdapter;

    private ArrayList<Domino> dominoes = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        for(int i = 0; i < 10; i++){
            dominoes.add(Domino.generateDomino());
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);



    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{

        public ScreenSlidePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            PageWithTwoDominos page = PageWithTwoDominos.newInstance();
            Domino domino1 = dominoes.get(i * 2);
            Domino domino2 = dominoes.get(i * 2 + 1);
            page.setDominoes(domino1, domino2);
            page.setListener(new PageWithTwoDominos.OnFragmentClickListener() {
                @Override
                public void onClick(Domino domino) {
                    Toast.makeText(getApplicationContext(), domino.getUp() + " " + domino.getDown(), Toast.LENGTH_LONG).show();
                }
            });
            return page;
        }

        @Override
        public int getCount() {
            return dominoes.size() / 2;
        }
    }

}
