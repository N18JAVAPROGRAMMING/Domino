package com.example.guest.domino;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    private ViewPager viewPager;
    private PagerAdapter viewPagerAdapter;

    private ArrayList<Domino> dominoes = new ArrayList<>();

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm){
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

    public static TableFragment newInstance() {

        Bundle args = new Bundle();

        TableFragment fragment = new TableFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table, container, false);

        for(int i = 0; i < 10; i++){
            dominoes.add(Domino.generateDomino());
        }
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPagerAdapter = new TableFragment.ScreenSlidePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        return view;
    }

}
