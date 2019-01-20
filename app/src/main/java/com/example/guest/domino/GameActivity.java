package com.example.guest.domino;

import android.content.Intent;
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
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private TableFragment fragmentTable;
    private ProblemsFragment fragmentProblems;
    private ScoreTableFragment fragmentScore;

    ServerManager manager;

    Room currentroom;
    int room_id=-1;
    ArrayList<Domino> dominoes = new ArrayList<>();
    List<Domino> current_list;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentTable).commit();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentProblems).commit();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentScore).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        manager= new ServerManager(getApplicationContext());

        room_id=getIntent().getIntExtra(MyApplication.CURRENT_ROOM,-1);


        for(int i = 0; i < 10; i++){
            dominoes.add(Domino.generateDomino());
        }

        fragmentTable = TableFragment.newInstance(dominoes);
        fragmentProblems = ProblemsFragment.newInstance();
        fragmentScore = ScoreTableFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentTable).commit();

        fragmentTable.setDominoOnClickListener(new TableFragment.DominoOnClickListener() {
            @Override
            public void onClick(Domino domino) {
                fragmentProblems.addDomino(domino);
            }
        });

      //   getDependencies();

        for (int i=0; i<14; i++){
            fragmentProblems.addDomino(Domino.generateDomino());
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void getDependencies(){
        manager.setDependencies(room_id, new ServerManager.OnGetDependencies() {
            @Override
            public void response(APIService.Dependencies dependencies) {
                ArrayList<Domino> result=new ArrayList<Domino>();
                for (int i=0; i<dependencies.dominoes.size(); i++){
                    Domino add=  new Domino(Domino.main[dependencies.dominoes.get(i)][0],Domino.main[dependencies.dominoes.get(i)][0]);
                    add.task_id=dependencies.tasks.get(i);
                    result.add(add);
                }
               fragmentTable.UpdateDominoList(result);
            }

            @Override
            public void fail() {

            }
        });
    }


    public void GoBack(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

}

