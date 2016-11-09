package com.caroline.android.udacitycapstoneproject.view.loaders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.caroline.android.udacitycapstoneproject.R;
import com.caroline.android.udacitycapstoneproject.model.FavoriteMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carolinestewart on 11/4/16.
 */
public class FavoriteMoviesAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<FavoriteMovie> favoriteMovies = new ArrayList<FavoriteMovie>();
    public FavoriteMoviesAdapter(Context context, List<FavoriteMovie> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        FavoriteMovie fvm = (FavoriteMovie) getItem(position);
        if (view == null) {
            view = inflater.inflate(R.layout.favorite_movie_data, null);
        }
        TextView favtitle = (TextView) view.findViewById(R.id.favtitle);
        favtitle.setText(fvm.title);
        TextView favgenre = (TextView) view.findViewById(R.id.favgenre);
        favgenre.setText(fvm.genre);
        return view;
    }
    @Override
    public Object getItem(int position) {
        return favoriteMovies.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getCount() {
        return favoriteMovies.size();
    }
    public void setFavoriteMovies(List<FavoriteMovie> data) {
        favoriteMovies.addAll(data);
        notifyDataSetChanged();
    }
}
