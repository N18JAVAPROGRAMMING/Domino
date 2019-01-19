package com.example.guest.domino;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends Fragment
{

    ServerManager serverManager;

    onCallBackListener listener;

    //Button next;
    CardView next;
    TextView login;
    EditText password;

    public interface onCallBackListener{
        void onCallBack(String login);
    }

    public void setListener(onCallBackListener listener){
        this.listener=listener;

    }

    public AuthFragment() {
        // Required empty public constructor
    }

    public static AuthFragment newInstance() {
        Bundle args = new Bundle();
        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_auth, container, false);
        //next=v.findViewById(R.id.a_next);
        next = v.findViewById(R.id.next);
        login=v.findViewById(R.id.a_login);
        password=v.findViewById(R.id.a_password);

        serverManager= new ServerManager();







        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View view =v;

               /*Intent intent=  new Intent(getActivity().getApplicationContext(),PreGameAcivity.class);
                startActivity(intent);
*/
                serverManager.LogIn(login.getText().toString(),password.getText().toString(), new ServerManager.OnCallBackListenerAuth() {
                    @Override
                    public void onCallBack(boolean answer, String token) {
                        final String t = token;
                        if(answer){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    APIService.Token.SaveToken(getContext(),t);
                                    Intent intent =  new Intent(getContext(),MainActivity.class);
                                    User user = new User();
                                    user.name=login.getText().toString();
                                    //score
                                    MyApplication.saveActiveUser(user,getContext());
                                    startActivity(intent);
                                }
                            });

                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(view,t,Snackbar.LENGTH_LONG).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void error() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(view,"Ошибка подключения",Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                });

            }
        });
        //переопределение методов
        return v;
    }

}
