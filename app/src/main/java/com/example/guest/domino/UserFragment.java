package com.example.guest.domino;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private TextView score;
    private TextView name;
    private ImageView userImage;
    private User mainUser;
    private View exit;
    private FavouriteProblemsAdapter adapter;
    private RecyclerView list;


    public static UserFragment newInstance() {

        Bundle args = new Bundle();
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public UserFragment() {
        // Required empty public constructor
    }

    public void setUserImage(){
        //пример реализации
        userImage.setImageResource(R.drawable.user);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_user, container, false);
        userImage=v.findViewById(R.id.user_image);
        name=v.findViewById(R.id.name);
        score=v.findViewById(R.id.score);
        mainUser=MyApplication.getActiveUser(getContext());
        list = v.findViewById(R.id.favourite_problems);
        exit=v.findViewById(R.id.exit);


        setOnExitListener(exit);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<Task> tasks = (ArrayList<Task>) MyApplication.getTasks();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new FavouriteProblemsAdapter(tasks);
                        adapter.setCallBackListener(new FavouriteProblemsAdapter.onCallBackListener() {
                            @Override
                            public void callBack(int id) {
                                Intent intent = new Intent(getContext(), FavouriteActivity.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                        });
                        list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                        list.setAdapter(adapter);
                    }
                });
            }
        }).start();


        if (mainUser==null){
            ErrorLoadUser();
        } else {

            name.setText(mainUser.name);
            mainUser.generateImg();
            userImage.setImageResource(mainUser.getImgLink());
            score.setText(String.valueOf(mainUser.score));


           // setUserImage();
        }

        return v;
    }

    public void setOnExitListener(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIService.Token.SaveToken(getContext(),"");
                getActivity().finish();
                Intent intent =  new Intent(getContext(),InActivity.class);
                startActivity(intent);
            }
        });
    }

    public void ErrorLoadUser(){
       Snackbar.make(name,"Ошибка входа",Snackbar.LENGTH_SHORT);
       Intent intent =  new Intent(getContext(),InActivity.class);
       startActivity(intent);
    }

    private void setExitOnClickListener(){
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              MyApplication.DestroyUserData(getContext());
                Intent intent =  new Intent(getContext(),InActivity.class);
                startActivity(intent);
            }
        });

    }

}
