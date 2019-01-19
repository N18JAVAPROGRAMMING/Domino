package com.example.guest.domino;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends
        RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> data;

    public UserAdapter(final List<User> data) {
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.user_item,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setName(data.get(position).name);
        holder.setScore(data.get(position).score);
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

        public void setName(String name){
            this.name.setText(name);
        }

        public void setScore(int score){
            this.score.setText(String.valueOf(score));
        }

    }
}
