package com.example.guest.domino;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.CustomHolder> {


    private List<Room> rooms;
    private RoomsFragment.OnSelectedRoom listener;


    public  RoomsAdapter(List<Room> list){
        rooms=list;
    }



    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View v =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_item,viewGroup,false);
        return new CustomHolder(v);
    }

    public void setRoomList(List<Room> rooms){
        this.rooms=rooms;
    }

    public void setCallBackStartGame(RoomsFragment.OnSelectedRoom onCallBackStartGame) {
        listener= onCallBackStartGame;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder customHolder, int i) {
        final int number=i;
        customHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.StartGame(rooms.get(number));
            }
        });
        Room room = rooms.get(i);
        customHolder.name.setText(room.getName());
        customHolder.ratio.setText(room.peer_count +"/"+room.capacity);
        rooms.get(i).generateImg();
        customHolder.roomImage.setImageResource(room.getImageLink());
        double p = ((double)room.peer_count /room.capacity)*100;
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

   class  CustomHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView ratio;
        ImageView roomImage;//temporary
        View view;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            roomImage=itemView.findViewById(R.id.imageView);
            name=itemView.findViewById(R.id.name_room);
            ratio =itemView.findViewById(R.id.count);
        }


    }


}
