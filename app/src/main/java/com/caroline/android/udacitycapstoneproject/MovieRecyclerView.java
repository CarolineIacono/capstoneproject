package com.caroline.android.udacitycapstoneproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by carolinestewart on 5/2/16.
 */

public class MovieRecyclerView extends RecyclerView.Adapter<MovieRecyclerView.MyViewHolder> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<MovieItem> movieArray = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public MovieRecyclerView(ArrayList<MovieItem> movieItems) {
        this.movieArray = movieItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieItem movieItem = movieArray.get(position);
        holder.title.setText(movieItem.getTitle());
        holder.genre.setText(movieItem.getGenre());
        holder.year.setText(movieItem.getYear());
    }

    @Override
    public int getItemCount() {
        return movieArray.size();
    }
}