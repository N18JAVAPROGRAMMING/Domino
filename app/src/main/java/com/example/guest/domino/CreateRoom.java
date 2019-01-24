package com.example.guest.domino;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

    public final static int CLASSIC_TYPE = 0;
    public final static int FAST_TYPE = 1;

    private EditText editName;
    private EditText editDescription;
    private SeekBar seekBar;
    private TextView numberOfPlayers;
    static OnCreateRoomListener onCreateRoomListener;
    CardView createButton;
    ServerManager manager ;
    private int currentType;

    private CardView classicButton;
    private CardView fastButton;


   public interface  OnCreateRoomListener{
       void OnRoomCreated(Room room);
   }

    public CreateRoom() {
        // Required empty public constructor
    }

    public void setOnCreateRoomListener(OnCreateRoomListener listener){
        onCreateRoomListener=listener;
    }

    public void setButtons(){
       switch(currentType){
           case CLASSIC_TYPE:
               classicButton.setCardBackgroundColor(getResources().getColorStateList(R.color.pink));
               fastButton.setCardBackgroundColor(getResources().getColorStateList(R.color.grey));
               break;
           case FAST_TYPE:
               classicButton.setCardBackgroundColor(getResources().getColorStateList(R.color.grey));
               fastButton.setCardBackgroundColor(getResources().getColorStateList(R.color.pink));
       }
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

        classicButton = view.findViewById(R.id.classic);
        fastButton = view.findViewById(R.id.fast);

        setButtons();

        classicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentType = CLASSIC_TYPE;
                setButtons();
            }
        });

        fastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentType = FAST_TYPE;
                setButtons();
            }
        });

        editName = view.findViewById(R.id.edit_name);
        editDescription = view.findViewById(R.id.edit_description);
        seekBar = view.findViewById(R.id.players_number);
        seekBar.setMax(4);

        numberOfPlayers = view.findViewById(R.id.count);
        numberOfPlayers.setText("3");
        createButton = view.findViewById(R.id.next);
        setCreateRoomListener();
        manager =  new ServerManager(getContext());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numberOfPlayers.setText(String.valueOf(progress+2));
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

                if (editName.length()>15){
                    Snackbar.make(v,"Максимальная длинна название турнира 15 символов",Snackbar.LENGTH_SHORT).show();
                   return;
                }
                if(editDescription.length()>200){
                    Snackbar.make(v,"Слишком длинное описание",Snackbar.LENGTH_SHORT).show();
               return;
                }

                if (editName.getText().toString().length()>0){
                    Room r = new Room();
                    r.setName(editName.getText().toString());
                    r.setCapacity(seekBar.getProgress()+2);
                    boolean mode=false;
                    r.setPrivacyMode(mode);
                    Snackbar.make(v,"Заявка на турнир принята",Snackbar.LENGTH_SHORT).show();
                    manager.createRoom(r.room_name, r.capacity, new ServerManager.OnCreateRoomListener() {
                        @Override
                        public void create(Room room) {
                            onCreateRoomListener.OnRoomCreated(room);
                        }
                    });



                } else {
                    Snackbar.make(v,"Не введено имя турнира",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

}
