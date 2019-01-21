package com.example.guest.domino;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FavouriteProblemsAdapter extends RecyclerView.Adapter<FavouriteProblemsAdapter.CustomHolder> {

    private List<Task> data;

    public FavouriteProblemsAdapter(List<Task> data){
        this.data = data;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.problem_item,viewGroup,false);
        return new CustomHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder customHolder, int i) {
        final int id = data.get(i).getId();
        customHolder.setProblemName(data.get(i).getCond().substring(0, 25) + "...");
        customHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO DO
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CustomHolder extends RecyclerView.ViewHolder{

        private TextView problemName;
        CardView cardView;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_problem);
            problemName = itemView.findViewById(R.id.problem_name);
        }

        public void setProblemName(String s) {
            problemName.setText(s);
        }
    }

}
