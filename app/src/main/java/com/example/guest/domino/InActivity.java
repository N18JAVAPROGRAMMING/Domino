package com.example.guest.domino;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.logging.LogRecord;

public class InActivity extends AppCompatActivity {

    //first activity


    CardView actionButton;
    TextView textButton;
    int condition=0;
    public static final int REGISTARTION_CONDITION=0;
    public static final int AUTHORIZATION_CONDITION=1;
    // 0 -авторизация
    // 1 - регистрация


    @Override
    public void onBackPressed(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);
         condition=AUTHORIZATION_CONDITION;
         textButton=findViewById(R.id.dinamictest);


        final AuthFragment authFragment =AuthFragment.newInstance();
        getSupportFragmentManager().popBackStack();
        authFragment.setListener(new AuthFragment.onCallBackListener() {
            @Override
            public void onCallBack(String login) {

            }
        });

         FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment,authFragment).commit();

        final RegFragment regFragment =  RegFragment.newInstance(InActivity.this);

        //getSupportFragmentManager().popBackStack();


        actionButton=findViewById(R.id.add_account);
        actionButton.setOnClickListener(new View.OnClickListener() {   //изменение кнопки на back to auth
            @Override
            public void onClick(View v) {
                // нужно добавить передачу логина через CallBack
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch(condition){
                    case REGISTARTION_CONDITION:
                        fragmentTransaction.replace(R.id.fragment,authFragment).commit();
                        textButton.setText("Создать аккаунт");
                        //actionButton.setText("+ CREATE NEW ACCOUNT");
                        condition=AUTHORIZATION_CONDITION;
                        break;
                    case AUTHORIZATION_CONDITION:
                        fragmentTransaction.replace(R.id.fragment,regFragment).commit();
                        fragmentTransaction.addToBackStack(null);
                        textButton.setText("Войти");
                        //actionButton.setText("LOG_IN");
                        condition=REGISTARTION_CONDITION;
                        break;


                }
            }

        });
    }





    public void defineSocket(){
        SocketThread.getInstance().setOnStateConnectionListener(new SocketThread.OnStateConnectionListener() {
            @Override
            public void onUnableConnect() {
                Toast.makeText(getApplicationContext(),"No connection",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServerProblems() {

            }
        });
    }


}
