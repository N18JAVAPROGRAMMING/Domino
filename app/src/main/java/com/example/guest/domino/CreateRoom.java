package com.example.guest.domino;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRoom extends Fragment {

    EditText  editName;
    Switch turnSwitch;
    SeekBar seekBar;
    Button  createButton;
    static OnCreateRoomListener onCreateRoomListener;

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
        View v  =inflater.inflate(R.layout.fragment_create_room, container, false);
        editName=v.findViewById(R.id.edit_name);
        seekBar = v.findViewById(R.id.seek_bar);
        turnSwitch = v.findViewById(R.id.turnOfOn);
        seekBar.setMax(5);

        createButton=v.findViewById(R.id.create);
        setCreateRoomListener();

        return v;
    }

    public void setCreateRoomListener(){
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().toString().length()>0){
                    Room r = new Room();
                    r.setRoom_name(editName.getText().toString());
                    r.setCapacity(seekBar.getProgress());
                    boolean mode=false;
                    if(turnSwitch.isActivated()){
                        mode=true;
                    }
                    r.setPrivacyMode(mode);
                    Snackbar.make(v,"Заявка на турнир приянята",Snackbar.LENGTH_LONG).show();
                    onCreateRoomListener.OnRoomCreated(r);
                } else {
                    Snackbar.make(v,"Не введено имя турнира",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
