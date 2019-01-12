package com.example.guest.domino;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends Fragment
{
    static InActivity inActivity;

    onCallBackListener listener;

    Button next;

    public interface onCallBackListener{
        void onCallBack(String login);
    }

    public void setListener(onCallBackListener listener){
        this.listener=listener;
    }

    public AuthFragment() {
        // Required empty public constructor
    }

    public static AuthFragment newInstance(InActivity in) {
        inActivity=in;
        Bundle args = new Bundle();
        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_auth, container, false);
        next=v.findViewById(R.id.a_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //обработка входа
                 inActivity.nextActivity();
            }
        });
        //переопределение методов
        return v;
    }

}
