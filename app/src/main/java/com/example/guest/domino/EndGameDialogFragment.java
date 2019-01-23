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
public class EndGameDialogFragment extends DialogFragment {

    private CardView exitButton;
    private Fragment fragmentScore;

    private OnExitListener listener;

    public EndGameDialogFragment() {
        // Required empty public constructor
    }

    public void setOnExitListener(OnExitListener listener) {
        this.listener = listener;
    }

    interface OnExitListener{

        void OnExit();

    }


    public static EndGameDialogFragment newInstance(Fragment fragmentScore) {
        
        Bundle args = new Bundle();
        
        EndGameDialogFragment fragment = new EndGameDialogFragment();
        fragment.setArguments(args);
        fragment.setFragmentScore(fragmentScore);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_end_game_dialog, container, false);
        getChildFragmentManager().beginTransaction().replace(R.id.score_frame, fragmentScore).commit();

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnExit();
            }
        });

        return view;
    }

    public void setFragmentScore(Fragment fragmentScore) {
        this.fragmentScore = fragmentScore;
    }
}
