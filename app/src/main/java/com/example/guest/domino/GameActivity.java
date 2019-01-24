package com.example.guest.domino;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private TableFragment fragmentTable;
    private ProblemsFragment fragmentProblems;
    private ScoreTableFragment fragmentScore;

    TableFragment.GameTime gameTime;


    EndThread thread;
    ServerManager manager;
    User mainUser;
    int score=0;
    int count=0;
    Room currentroom;
    BottomNavigationView view;

    int room_id=-1;
    int localScore=0;
    ArrayList<Domino> dominoes = new ArrayList<>();


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
    public void onBackPressed() {

        final DialogFragment backDialog = ExitGameDialog.newInstance();
        ((ExitGameDialog) backDialog).setListener(new ExitGameDialog.OnCallBackListener() {
            @Override
            public void onExit() {
                manager.peerDisconnect(room_id, new ServerManager.onPeerDisonnectListener() {
                    @Override
                    public void disconnect(List<Room> list) {

                    }

                    @Override
                    public void error() {

                    }
                });

                finish();
            }

            @Override
            public void onCancel() {
                backDialog.dismiss();
            }
        });
        backDialog.show(getSupportFragmentManager(), "Игра окончена");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        manager= new ServerManager(getApplicationContext());
        view =findViewById(R.id.navigation);
        room_id=getIntent().getIntExtra(MyApplication.CURRENT_ROOM,-1);
        currentroom= new Room();
        currentroom.on_start=1;
        currentroom.id=String.valueOf(room_id);


       /* for(int i = 0; i < 10; i++){
            dominoes.add(Domino.generateDomino());
        }*/

        fragmentTable = TableFragment.newInstance(dominoes,room_id);
        fragmentProblems = ProblemsFragment.newInstance();
        fragmentScore = ScoreTableFragment.newInstance(Integer.valueOf(currentroom.id));
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentTable).commit();

        fragmentTable.setDominoOnClickListener(new TableFragment.DominoOnClickListener() {
            @Override
            public void onClick(final Domino domino) {
                   boolean con=true;
                if (count<2){
                    switch (domino.getStatus()){
                       case Domino.WASTED_MODE:
                            Snackbar.make(view,"Эта задача уже недоступна",Snackbar.LENGTH_SHORT).show();
                            con=false;
                            break;
                        case Domino.RESERVED:
                            Snackbar.make(view,"Задча решается другой командой",Snackbar.LENGTH_SHORT).show();
                            con=false;
                            break;
                        case Domino.SOLVING_MODE:
                            Snackbar.make(view,"Задча решается другой командой",Snackbar.LENGTH_SHORT).show();
                            con=false;
                            break;

                    }
                    if(!con)return;

                    if (domino.getTask()!=null){
                        fragmentTable.setStatus(domino.id, Domino.SOLVING_MODE);
                        fragmentProblems.addDomino(domino);
                        count++;
                    } else {

                    manager.getTask(room_id,domino.task_id, new ServerManager.OnCallBackListenerTask() {
                        @Override
                        public void onCallBack(Task task) {
                            domino.setTask(task);
                            fragmentTable.setStatus(domino.id, Domino.SOLVING_MODE);

                            fragmentProblems.addDomino(domino);
                            count++;
                        }

                        @Override
                        public void error(String msg) {
                          final  String m=msg;
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  Snackbar.make(view,m,Snackbar.LENGTH_SHORT).show();
                              }
                          });
                        }
                    });}

                    //внутри onCallBack();

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentProblems).commit();
                    Snackbar.make(view,"Задача добавлена",Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(view,"Одновременно можно решать только 2 задачи",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        //запрос

        fragmentProblems.setOnAnswerListener(new ProblemsFragment.onGetAnswer() {
            @Override
            public void answer(String answer,Domino domino) {
                count--;

                int add=0;
                if (domino.getTask().getAns().equals(answer)){
                    Log.d("dominotask","true");
                    if (domino.attempt==0){
                        add+=domino.getUp()+domino.getDown();
                        if(add==0)add=10;
                    } else {
                        add+=Math.max(domino.getUp(),domino.getDown());
                    }
                    Snackbar.make(view,"Правильный ответ +"+add,Snackbar.LENGTH_SHORT).show();
                    fragmentTable.setStatus(domino.id, Domino.WASTED_MODE);
                }  else {
                    Log.d("dominotask","true");
                    if (domino.getUp()+domino.getDown()==0){
                        fragmentTable.setStatus(domino.id, Domino.WASTED_MODE);
                        Snackbar.make(view,"Неверный ответ ",Snackbar.LENGTH_SHORT).show();
                    }  else {
                    if (domino.attempt==1){
                        add-=Math.min(domino.getUp(),domino.getDown());
                        fragmentTable.setStatus(domino.id, Domino.WASTED_MODE);
                        Snackbar.make(view,"Неверный ответ "+add,Snackbar.LENGTH_SHORT).show();
                    } else {
                        fragmentTable.setStatus(domino.id, Domino.FREE_MODE);
                        add=MyApplication.FIRST_ATTEMPT;
                        Snackbar.make(view,"Осталась 1 попытка"+add,Snackbar.LENGTH_SHORT).show();
                    }

                    }
                    domino.attempt++;
                }
                if (add!=MyApplication.FIRST_ATTEMPT) score+=add;

                Log.d("dominotask","score add "+add );
                Log.d("dominotask","score add "+score );
                fragmentTable.setScore(String.valueOf(score));
                if (domino.attempt==2){
                    fragmentTable.setStatus(domino.id, Domino.WASTED_MODE);
                }

                manager.setScore(room_id,add,domino.task_id);


              //начисление add
                fragmentTable.update();
                 fragmentProblems.removeDomino(domino);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentTable).commit();


            }
        });

        getDependencies();


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        currentroom.room_name=getIntent().getStringExtra(MyApplication.ROOM_NAME);
        fragmentTable.setName(currentroom.room_name);

            gameTime=new TableFragment.GameTime(fragmentTable.getTextTime(),30,this);
            gameTime.start();

    }

    @Override
    protected void onDestroy() {
        manager.peerDisconnect(room_id, new ServerManager.onPeerDisonnectListener() {
            @Override
            public void disconnect(List<Room> list) {

            }

            @Override
            public void error() {

            }
        });
        super.onDestroy();
    }

    public void getDependencies(){
        manager.setDependencies(room_id, new ServerManager.OnGetDependencies() {
            @Override
            public void response(APIService.Dependencies dependencies) {
                ArrayList<Domino> result=new ArrayList<Domino>();
                for (int i=0; i<dependencies.dominoes.size(); i++){
                    int n1 =Domino.main[dependencies.dominoes.get(i)-1][0];
                    int n2 =Domino.main[dependencies.dominoes.get(i)-1][1];
                    Log.d("dominotask",n1+"  "+n2);
                    Domino add=  new Domino(Math.min(n1,n2),Math.max(n1,n2));
                    add.id=dependencies.dominoes.get(i);
                    add.task_id=dependencies.tasks.get(i);
                    result.add(add);
                }

                fragmentTable.UpdateDominoList(result);
                fragmentTable.update();
                thread= new EndThread(result.size());
                thread.start();
            }

            @Override
            public void fail() {

            }
        });
    }


    public void setTimer(){
    }

    public void StartGame(){

    }

    public void EndGame(){
        DialogFragment endGameDialog = EndGameDialogFragment.newInstance(fragmentScore);
        ((EndGameDialogFragment) endGameDialog).setOnExitListener(new EndGameDialogFragment.OnExitListener() {
            @Override
            public void OnExit() {
                finish();
            }
        });
        endGameDialog.show(getSupportFragmentManager(), "Игра окончена");

    }


    public class EndThread extends Thread{

        View supprotView;

        long timemillis;




        @Override
        public void run() {
            super.run();
            try {
                sleep(timemillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EndGame();
                }
            });
        }

        public EndThread(int domino_count){
            if (domino_count>15){
                timemillis=100*60*45;
            } else {
                timemillis=100*60*45;
            }
        }
    }




    public void GoBack(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        Chronometer chronometer;
       // chronometer.setFormat();

    }

}

