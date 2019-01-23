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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {





    ServerManager manager;
    ServerManager.BackgroundThread thread;
    int room_id;
    String room_name;
    TextView scoreView;
    TextView nameView;

    public void setScore(String score){
        scoreView.setText(score);
        Log.d("dominotask scoreView",score);
    }

    public void setName(String name){
       room_name=name;
    }

    public void setStatus(int id, int status){


        for(Domino domino : dominoes){
            if(domino.id == id){
                domino.setStatus(status);
            }
        }

        viewPagerAdapter.notifyDataSetChanged();

    }

    public void setStatus(HashMap<Integer, Integer> changes){

        boolean changed = false;

        for(Domino domino : dominoes){
            if(domino.getStatus() != changes.get(domino.id)) {
                domino.setStatus(changes.get(domino.id));
                changed = true;
            }
        }
        if(changed)
            viewPagerAdapter.notifyDataSetChanged();

    }


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

    public ArrayList<Domino> getList(){
        return  dominoes;
    }
/*
    public void addDomino(Domino domino){
        dominoes.add(domino);
        if (viewPagerAdapter!=null){
            viewPagerAdapter.notifyDataSetChanged();
        }
    }

    public void removeDomino(Domino domino){
        dominoes.remove(domino);
        if (viewPagerAdapter!=null){
            viewPagerAdapter.notifyDataSetChanged();
        }
    }*/

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
            page.setOnFragmentClickListener(new PageWithTwoDominos.OnFragmentClickListener() {
                @Override
                public void onClick(Domino domino) {
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

    public static TableFragment newInstance(ArrayList<Domino> dominoes, int room_id) {

        Bundle args = new Bundle();
        TableFragment fragment = new TableFragment();
        fragment.setDominoId(room_id);
        fragment.setArguments(args);
        fragment.setDominoes(dominoes);
        return fragment;
    }

    private void setDominoId(int id){
        room_id=id;
    }

    public void  UpdateDominoList(ArrayList<Domino> dominoes){
        setDominoes(dominoes);
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        manager= new ServerManager(getActivity().getApplicationContext());
        thread= new ServerManager.BackgroundThread(getActivity().getApplicationContext(),
                ServerManager.BackgroundThread.UPDATE_TASKS,1000);
        Log.d("TableFragment", "OnCreateViewTable");
        // Inflate the layout for this fragment
        startUpdate();
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        scoreView=view.findViewById(R.id.score);
        nameView=view.findViewById(R.id.name_room);
        viewPager = view.findViewById(R.id.pager);
        viewPagerAdapter = new TableScreenSlidePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        nameView.setText(room_name);
        return view;
    }

    @Override
    public void onResume() {
        thread.setRunFlag(true);
        super.onResume();
    }

    @Override
    public void onPause() {
        thread.setRunFlag(false);
        super.onPause();
    }

    private Domino getDominoById(int id){
        for (Domino d:dominoes){
            if(d.id==id){
                return  d;
            }
        }
        return  null;
    }



    public void startUpdate(){
        thread.setOnDominoListener(room_id, new ServerManager.BackgroundThread.DominoStatusCheckListener() {
            @Override
            public void onUpdate(final APIService.CaptureModel model) {
                if (getActivity()==null)return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Integer> d = model.dominoes;
                        List<Integer> t = model.task_status;
                        HashMap<Integer,Integer> map =  new HashMap<Integer, Integer>();

                        for (int k=0; k<d.size(); k++){
                            int i =d.get(k);
                            Domino item = getDominoById(i);
                            if (item!=null){
                                map.put(i,t.get(k));
                            }
                        }
                        setStatus(map);
                    }
                });

            }

            @Override
            public void fail() {

            }
        });

        thread.start();

    }
}
