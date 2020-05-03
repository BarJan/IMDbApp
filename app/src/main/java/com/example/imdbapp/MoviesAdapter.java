package com.example.imdbapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MViewHolder>
{
    private List<MovObj> movList;
    SQLiteDB db;
    MainActivity activity;

    public MoviesAdapter(MainActivity activity, SQLiteDB db, List<MovObj> data) {
        this.activity = activity;
        this.movList = data;
        this.db = db;
    }

    class MViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewTitle;
        CircleImageView imageViewImage;
        TextView textViewRelease;


        public MViewHolder(View itemView)
        {
            super(itemView);
            this.textViewTitle = (TextView) itemView.findViewById(R.id.title);
            this.imageViewImage = (CircleImageView) itemView.findViewById(R.id.img);
            this.textViewRelease = (TextView) itemView.findViewById(R.id.release);

        }
    }

    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);

        MViewHolder viewHolder = new MViewHolder(view);
        return viewHolder;
    }

    public void onBindViewHolder(MViewHolder holder, final int position)
    {
        Picasso.get().load(movList.get(position).getImage()).into(holder.imageViewImage);
        holder.textViewTitle.setText(movList.get(position).getTitle());
        holder.textViewRelease.setText(String.valueOf(movList.get(position).getReleaseYear()));
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activity.changeFragment(movList.get(position).getTitle());
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return movList.size();
    }
}