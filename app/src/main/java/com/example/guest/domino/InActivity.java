package com.example.guest.domino;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InActivity extends AppCompatActivity {

    AuthFragment authFragment;
    Button addAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);

        authFragment =AuthFragment.newInstance(this);
        getSupportFragmentManager().popBackStack();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment,authFragment).commit();


        addAccount=findViewById(R.id.add_account);
        addAccount.setOnClickListener(new View.OnClickListener() {   //изменение кнопки на back to auth
            @Override
            public void onClick(View v) {
                // нужно добавить передачу логина через CallBack
                RegFragment regFragment =  RegFragment.newInstance(InActivity.this);

                //getSupportFragmentManager().popBackStack();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                 fragmentTransaction.replace(R.id.fragment,regFragment).commit();
                fragmentTransaction.addToBackStack(null);

            }
        });
    }


    public void nextActivity(){
        Intent intent=  new Intent(this,MainActivity.class);
        startActivity(intent);


    }
}
