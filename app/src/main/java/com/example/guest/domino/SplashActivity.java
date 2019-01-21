package com.example.guest.domino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         ServerManager server =  new ServerManager(getApplicationContext());
         server.CheckToken(new ServerManager.OnCheckTokenListener() {
             @Override
             public void ok() {
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         Intent intent =  new Intent(getApplicationContext(),MainActivity.class);
                         startActivity(intent);
                     }
                 });
             }

             @Override
             public void error() {
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         Intent intent =  new Intent(getApplicationContext(),MainActivity.class);
                         startActivity(intent);
                     }
                 });

             }
         },getApplicationContext());


    }
}
