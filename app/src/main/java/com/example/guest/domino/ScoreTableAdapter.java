package com.example.guest.domino;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ScoreTableAdapter extends
        RecyclerView.Adapter<ScoreTableAdapter.ViewHolder> {
    private List<User> data;

    public ScoreTableAdapter(final List<User> data) {
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_user,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(data.get(position).name);
        holder.score.setText(data.get(position).score);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setData(List<User> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView name;
        private TextView score;



        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = view.findViewById(R.id.name);
            score = view.findViewById(R.id.score);


        }
    }
}
