package com.example.guest.domino;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    TextView score;
    TextView name;
    ImageView userImage;
    User mainUser;
    View exit;


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
      //  exit=v.findViewById(R.id.exit);
      //  setExitOnClickListener();
        mainUser=MyApplication.getActiveUser(getContext());
        if (mainUser==null){
            ErrorLoadUser();
        } else {

            name.setText(mainUser.name);
            score.setText(String.valueOf(mainUser.score));


            setUserImage();
        }

        return v;
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
