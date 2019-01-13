package com.example.guest.domino;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InActivity extends AppCompatActivity {


    Button actionButton;
    int condition=0;
    public static final int REGISTARTION_CONDITION=0;
    public static final int AUTHORIZATION_CONDITION=1;
    // 0 -авторизация
    // 1 - регистрация



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);
         condition=AUTHORIZATION_CONDITION;


        final AuthFragment authFragment =AuthFragment.newInstance(this);
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
                        actionButton.setText("+ CREATE NEW ACCOUNT");
                        condition=AUTHORIZATION_CONDITION;
                        break;
                    case AUTHORIZATION_CONDITION:
                        fragmentTransaction.replace(R.id.fragment,regFragment).commit();
                        fragmentTransaction.addToBackStack(null);
                        actionButton.setText("LOG_IN");
                        condition=REGISTARTION_CONDITION;
                        break;


                }
            }

        });
    }


    public void nextActivity(){
        Intent intent=  new Intent(this,GameActivity.class);
        startActivity(intent);


    }
}
