package com.example.guest.domino;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageWithTwoDominos extends Fragment  {

    private Domino domino1;
    private Domino domino2;

    private ImageView img11;
    private ImageView img12;
    private ImageView img21;
    private ImageView img22;

    private CardView button1;
    private CardView button2;

    private TextView textView1;
    private TextView textView2;

    public void setOnFragmentClickListener(OnFragmentClickListener listener) {
        this.listener = listener;
    }

    OnFragmentClickListener listener;

    public void setColors(){
        if(domino1.getUp() != 0)
            if(domino1.attempt == 0)
                img11.setImageBitmap(ColoredNumbers.getInstance().numberWhite(getContext(), domino1.getUp()));
            else
                img11.setImageBitmap(ColoredNumbers.getInstance().numberPink(getContext(), domino1.getUp()));
        if(domino1.getDown() != 0)
            if(domino1.attempt < 2)
                img12.setImageBitmap(ColoredNumbers.getInstance().numberWhite(getContext(), domino1.getDown()));
            else
                img12.setImageBitmap(ColoredNumbers.getInstance().numberPink(getContext(), domino1.getDown()));
        if(domino2.getUp() != 0)
            if(domino2.attempt == 0)
                img21.setImageBitmap(ColoredNumbers.getInstance().numberWhite(getContext(), domino2.getUp()));
            else
                img21.setImageBitmap(ColoredNumbers.getInstance().numberPink(getContext(), domino2.getUp()));
        if(domino2.getDown() != 0)
            if(domino2.attempt < 2)
                img22.setImageBitmap(ColoredNumbers.getInstance().numberWhite(getContext(), domino2.getDown()));
            else
                img22.setImageBitmap(ColoredNumbers.getInstance().numberPink(getContext(), domino2.getDown()));
    }

    @SuppressLint("ResourceAsColor")
    public void setButtons(){
        switch (domino1.getStatus()){
            case Domino.FREE_MODE:
                button1.setCardBackgroundColor(getResources().getColorStateList(R.color.pink_button));
                textView1.setText("Начать решать");
                break;
            case Domino.RESERVED:
                button1.setCardBackgroundColor(getResources().getColorStateList(R.color.reserved));
                textView1.setText("Решается игроком");
                break;
            case Domino.SOLVING_MODE:
                button1.setCardBackgroundColor(getResources().getColorStateList(R.color.reserved));
                textView1.setText("Решается вами");
                break;
            case Domino.WASTED_MODE:
                button1.setCardBackgroundColor(getResources().getColorStateList(R.color.solved));
                textView1.setText("Решена");
                break;
        }
    }

    public interface OnFragmentClickListener{
        void onClick(Domino domino);
    }

    public PageWithTwoDominos() {
        // Required empty public constructor
    }

    public void setDominoes(Domino domino1, Domino domino2){
        this.domino1 = domino1;
        this.domino2 = domino2;
    }

    public static PageWithTwoDominos newInstance() {
        
        Bundle args = new Bundle();
        
        PageWithTwoDominos fragment = new PageWithTwoDominos();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_with_two_dominos, container, false);
        img11 = view.findViewById(R.id.Domino11);
        img12 = view.findViewById(R.id.Domino12);
        img21 = view.findViewById(R.id.Domino21);
        img22 = view.findViewById(R.id.Domino22);

        button1 = view.findViewById(R.id.begin1);
        button2 = view.findViewById(R.id.begin2);

        textView1 = button1.findViewById(R.id.text_button_1);
        textView2 = button2.findViewById(R.id.text_button_2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(domino1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(domino2);
            }
        });

        setButtons();
        setColors();
        return view;
    }

   /* public void setListener(View v ){
        //not working now
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(new Domino());
            }
        });
    }
*/


}
