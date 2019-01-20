package com.example.guest.domino;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRoom extends Fragment {

    private EditText editName;
    private EditText editSescription;
    private SeekBar seekBar;
    private TextView numberOfPlayers;
    static OnCreateRoomListener onCreateRoomListener;
    CardView createButton;

   public interface  OnCreateRoomListener{
       void OnRoomCreated(Room room);
   }

    public CreateRoom() {
        // Required empty public constructor
    }

    public void setOnCreateRoomListener(OnCreateRoomListener listener){
        onCreateRoomListener=listener;
    }


    public static CreateRoom newInstance() {

        Bundle args = new Bundle();


        CreateRoom fragment = new CreateRoom();
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment_create_room, container, false);

        editName = view.findViewById(R.id.edit_name);
        editSescription = view.findViewById(R.id.edit_description);
        seekBar = view.findViewById(R.id.players_number);
        numberOfPlayers = view.findViewById(R.id.count);
        createButton = view.findViewById(R.id.next);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberOfPlayers.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    public void setCreateRoomListener(){
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().toString().length()>0){
                    Room r = new Room();
                    r.setName(editName.getText().toString());
                    r.setCapacity(seekBar.getProgress());
                    boolean mode=false;
                    Snackbar.make(v,"Заявка на турнир приянята",Snackbar.LENGTH_LONG).show();
                    onCreateRoomListener.OnRoomCreated(r);
                } else {
                    Snackbar.make(v,"Не введено имя турнира",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
