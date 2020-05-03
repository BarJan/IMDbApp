package com.example.imdbapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class DetailsFragment extends Fragment {

    TextView title,rel,rat,gen;
    TextView rely,rati,genr;
    CircularImageView img;
    public DetailsFragment () {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        this.img = (CircularImageView) view.findViewById(R.id.img);
        this.title = (TextView) view.findViewById(R.id.title);

        this.rati = (TextView) view.findViewById(R.id.rating);
        this.rely = (TextView) view.findViewById(R.id.release);
        this.genr = (TextView) view.findViewById(R.id.genre);

        this.rel = (TextView) view.findViewById(R.id.releaseVal);
        this.rat = (TextView) view.findViewById(R.id.ratingVal);
        this.gen = (TextView) view.findViewById(R.id.genreVal);



        SQLiteDB sqLiteDB = new SQLiteDB(getActivity());
        MovObj movObj = sqLiteDB.getMovie(this.getArguments().getString("item"));

        Picasso.get().load(movObj.getImage()).into(img);
        title.setText(movObj.getTitle());
        rel.setText(String.valueOf(movObj.getReleaseYear()));
        rat.setText(String.valueOf(movObj.getRating()));
        gen.setText(movObj.stringtify_genre());

        return view;
    }
}