package com.example.imdbapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MovieRecycFragment extends Fragment {

    private SQLiteDB db;
    public RecyclerView movRecyView;
    private MoviesAdapter mAdapter;
    private List<MovObj> fromDB;
    private View view;
    FloatingActionButton button;

    public MovieRecycFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        movRecyView = (RecyclerView) view.findViewById(R.id.recyclerView);
        button = (FloatingActionButton) view.findViewById(R.id.fButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    scanBarcode(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        db = new SQLiteDB(inflater.getContext());
        fromDB = db.getAllMovies();
        MainActivity activity = (MainActivity) getActivity();
        mAdapter = new MoviesAdapter(activity,db,fromDB);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        movRecyView.setLayoutManager(layoutManager);
        movRecyView.setAdapter(mAdapter);
        movRecyView.addItemDecoration(new
                DividerItemDecoration(inflater.getContext(),
                DividerItemDecoration.VERTICAL));
        return view;
    }

    public void scanBarcode(View v){
        MainActivity activity = (MainActivity) getActivity();
        activity.changeFragment("QR");
    }

}
