package com.example.guest.domino;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class FavouriteProblemsAdapter extends RecyclerView.Adapter<FavouriteProblemsAdapter.CustomHolder> {

    private List<Task> data;
    private onCallBackListener callBackListener;

    interface onCallBackListener{

        void callBack(int id);

    }

    public void setCallBackListener(onCallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

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
    public void onBindViewHolder(@NonNull final CustomHolder customHolder, int i) {
        final int id = data.get(i).getId();
        Task task = data.get(i);



        customHolder.setProblemName(data.get(i).getCond());
        customHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackListener.callBack(id);
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
        TextView answer;
        TextView solution;


        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_problem);
            problemName = itemView.findViewById(R.id.problem_name);


        }

        public void setProblemName(String s) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                problemName.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT));
            } else {
                problemName.setText(Html.fromHtml(s));
            }
            //problemName.setText(s);
        }
    }

}
