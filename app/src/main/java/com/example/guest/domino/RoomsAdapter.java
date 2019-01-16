package com.example.guest.domino;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.CustomHolder> {


    List<Room> rooms;
    RoomsFragment.OnCallBackStartGame onCallBackStartGame;


    public  RoomsAdapter(List<Room> list){
        rooms=list;
    }



    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View v =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_item,viewGroup,false);



        return new CustomHolder(v);
    }

    public void setCallBackStartGame(RoomsFragment.OnCallBackStartGame onCallBackStartGame) {
        this.onCallBackStartGame = onCallBackStartGame;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder customHolder, int i) {
        final int number=i;
        customHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBackStartGame.StartGame(rooms.get(number));
            }
        });
        Room room = rooms.get(i);
        customHolder.name.setText(room.getRoom_name());
        customHolder.ratio.setText(room.peer_count +"/"+room.capacity);
        double p = ((double)room.peer_count /room.capacity)*100;
        customHolder.progressBar.setProgress((int)p);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

   class  CustomHolder extends RecyclerView.ViewHolder{
        TextView name;
        ProgressBar progressBar;
        TextView ratio; //temporary
       View view;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            name=itemView.findViewById(R.id.name);
            progressBar=itemView.findViewById(R.id.progress);
            progressBar.setMax(100);
            ratio =itemView.findViewById(R.id.ratio);
        }


    }


}
