package com.example.guest.domino;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class RegFragment extends Fragment {

   CardView next;
   EditText editPassword;
   EditText editCPassword;
   EditText editLogin;

   ServerManager serverManager;

   static InActivity inActivity;



    public RegFragment() {
        // Required empty public constructor
    }


    public static RegFragment newInstance(InActivity in) {
        inActivity=in;
        RegFragment fragment = new RegFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        serverManager= new ServerManager(getActivity().getApplicationContext());
        View v = inflater.inflate(R.layout.fragment_reg, container, false);
        next=v.findViewById(R.id.next);
        editCPassword=v.findViewById(R.id.repeat_password);
        editPassword=v.findViewById(R.id.edit_password);
        editLogin=v.findViewById(R.id.edit_login);
        setOnClickListener();


        return v;
    }


    public void setOnClickListener(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password =editPassword.getText().toString();
                String conf_password=editCPassword.getText().toString();
                final String name=editLogin.getText().toString();

                if(name.length()==0){
                    Snackbar.make(v,"Введите логин",Snackbar.LENGTH_LONG).show();
                    return;
                }

                if(name.length()>15){
                    Snackbar.make(v,"Максимальная длинна логина 15 символов",Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (!password.equals(conf_password)){
                    Snackbar.make(v,"Пароли не совпадают",Snackbar.LENGTH_LONG).show();
                    return;
                }

                if(password.length()==0){
                    Snackbar.make(v,"Введите пароль",Snackbar.LENGTH_LONG).show();
                    return;
                }

                serverManager.CreateNewAccount(name, password, new ServerManager.OnCallBackListenerReg() {
                    @Override
                    public void onCallBack(boolean answer, String token) {
                        final String t =token;
                        if (answer){
                            APIService.Token.SaveToken(getActivity().getApplicationContext(),token);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(editCPassword,"Вы успешно зарегистрированы",Snackbar.LENGTH_SHORT).show();
                                    Intent intent =  new Intent(getActivity().getApplicationContext(),MainActivity.class);
                                    User user = new User();
                                    user.name=name;
                                    //score
                                    MyApplication.saveActiveUser(user,getContext());
                                    startActivity(intent);
                                }
                            });

                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(editCPassword,t,Snackbar.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                    @Override
                    public void error() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(editCPassword,"Ошибка подключения",Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                });





               /* Snackbar.make(v,"Вы успешно зарегистрированы (нет)",Snackbar.LENGTH_LONG).show();
                Intent intent =  new Intent(getActivity().getApplicationContext(),MainActivity.class);
                startActivity(intent);*/
                return;

            }
        });
    }



}
