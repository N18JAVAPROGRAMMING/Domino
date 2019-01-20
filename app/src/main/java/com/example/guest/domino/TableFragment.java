package com.example.guest.domino;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    public void setDominoOnClickListener(DominoOnClickListener listener) {
        this.listener = listener;
    }

    interface DominoOnClickListener {
        void onClick(Domino domino);
    }

    private ViewPager viewPager;
    private PagerAdapter viewPagerAdapter;
    private DominoOnClickListener listener;

    private ArrayList<Domino> dominoes = new ArrayList<>();
    //private ArrayList<Fragment> fragments = new ArrayList<>();

    public void setDominoes(ArrayList<Domino> dominoes) {
        this.dominoes = dominoes;
    }

    private class TableScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        @Override
        public Parcelable saveState() {
            return null;
        }

        public TableScreenSlidePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            PageWithTwoDominos page = PageWithTwoDominos.newInstance();
            Domino domino1 = dominoes.get(i * 2);
            Domino domino2 = dominoes.get(i * 2 + 1);
            page.setDominoes(domino1, domino2);
            page.setListener(new PageWithTwoDominos.OnFragmentClickListener() {
                @Override
                public void onClick(Domino domino) {
                    //Toast.makeText(getApplicationContext(), domino.getUp() + " " + domino.getDown(), Toast.LENGTH_LONG).show();
                    listener.onClick(domino);
                }
            });
            return page;
        }

        @Override
        public int getCount() {
            return dominoes.size() / 2;
        }
    }

    public TableFragment() {
        // Required empty public constructor
    }

    public static TableFragment newInstance(ArrayList<Domino> dominoes) {

        Bundle args = new Bundle();

        TableFragment fragment = new TableFragment();
        fragment.setArguments(args);
        fragment.setDominoes(dominoes);
        return fragment;
    }

    public void  UpdateDominoList(ArrayList<Domino> dominoes){
        setDominoes(dominoes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("TableFragment", "OnCreateViewTable");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        viewPager = view.findViewById(R.id.pager);
        viewPagerAdapter = new TableScreenSlidePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        return view;
    }
}
