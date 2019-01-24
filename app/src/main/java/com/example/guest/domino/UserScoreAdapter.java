package com.example.guest.domino;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserScoreAdapter extends RecyclerView.Adapter<UserScoreAdapter.ViewHolder> {
    private List<User> data;

    public UserScoreAdapter(final List<User> data) {
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.user_item_score,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setName(data.get(position).name);
        holder.setScore(data.get(position).localScore);
        holder.solvedTasks.setText(String.valueOf(data.get(position).countOk));
        holder.failedTasks.setText(String.valueOf(data.get(position).countError));
        holder.place.setText(String.valueOf(position + 1));
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
        TextView solvedTasks;
        TextView failedTasks;
        TextView place;



        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = view.findViewById(R.id.name);
            score = view.findViewById(R.id.score);
            solvedTasks = view.findViewById(R.id.solved_problems);
            failedTasks = view.findViewById(R.id.failed_problems);
            place = view.findViewById(R.id.place);
        }

        public void setName(String name){
            this.name.setText(name);
        }

        public void setScore(int score){
            this.score.setText(String.valueOf(score));
        }

    }
}
