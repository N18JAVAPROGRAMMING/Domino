package com.example.guest.domino;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    ImageView userImage;


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

        setUserImage();

        return v;
    }

}
