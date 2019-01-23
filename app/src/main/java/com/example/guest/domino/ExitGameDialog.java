package com.example.guest.domino;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExitGameDialog extends DialogFragment {

    private CardView buttonExit;
    private CardView buttonCancel;
    private OnCallBackListener listener;

    public ExitGameDialog() {
        // Required empty public constructor
    }

    public static ExitGameDialog newInstance() {
        
        Bundle args = new Bundle();
        
        ExitGameDialog fragment = new ExitGameDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(OnCallBackListener listener) {
        this.listener = listener;
    }

    interface OnCallBackListener{
        void onExit();
        void onCancel();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exit_game_dialog, container, false);

        buttonCancel = view.findViewById(R.id.cancel);
        buttonExit = view.findViewById(R.id.exit);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onExit();
            }
        });

        return view;
    }

}
