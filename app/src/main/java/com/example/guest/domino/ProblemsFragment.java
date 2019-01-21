package com.example.guest.domino;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProblemsFragment extends Fragment {

    private ViewPager viewPager;
    private PagerAdapter viewPagerAdapter;

    // ArrayList<Domino> dominoes = new ArrayList<>();
    private ArrayList<Domino> currentList = new ArrayList<>();

    private onGetAnswer listener;

    public ArrayList<Domino> getListDomino(){
        return currentList;
    }

    public interface OnDominoClickListener{
        void click(Domino domino);
    }

    public void setOnAnswerListener(onGetAnswer l){
        listener=l;
    }



    //private ArrayList<Fragment> fragments = new ArrayList<>();

    public void addDomino(Domino domino){
        currentList.add(domino);
        if(viewPagerAdapter != null)
            viewPagerAdapter.notifyDataSetChanged();
    }

    public void removeDomino(Domino domino){
        currentList.remove(domino);
        if(viewPagerAdapter != null)
            viewPagerAdapter.notifyDataSetChanged();
    }

    private class ProblemsScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        @Override
        public Parcelable saveState() {
            return null;
        }

        public ProblemsScreenSlidePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            PageWithProblem fragment = PageWithProblem.newInstance(currentList.get(i));
            fragment.setOnAnswerListener(new PageWithProblem.OnAnswerListener() {
                @Override
                public void send(String ans,Domino domino) {
                    listener.answer(ans,domino);
                }
            });
            return fragment;
        }

        @Override
        public int getCount() {
            return currentList.size();
        }
    }

    public ProblemsFragment() {
        // Required empty public constructor
    }

    public static ProblemsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProblemsFragment fragment = new ProblemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_problems, container, false);
        //dominoes.add(Domino.generateDomino());
        viewPager = (ViewPager) view.findViewById(R.id.problem_pager);
        viewPagerAdapter = new ProblemsFragment.ProblemsScreenSlidePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        return view;
    }

    public View getAnyChild(){
        return viewPager;
    }


     interface onGetAnswer{
        void answer(String answer,Domino domino);
     }
}
