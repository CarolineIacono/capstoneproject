package com.caroline.android.udacitycapstoneproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by carolinestewart on 5/2/16.
 */
public class MovieGridAdapter extends ArrayAdapter<MovieItem> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<MovieItem> mGridData = new ArrayList<>();

    public MovieGridAdapter (Context mContext, int layoutResourceId, ArrayList<MovieItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;

    }



    public void setGridData(ArrayList<MovieItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) row.findViewById(R.id.title);
            holder.imageView = (ImageView) row.findViewById(R.id.movieImage);
            holder.overView = (TextView) row.findViewById(R.id.overview);
            holder.voteAverage = (TextView) row.findViewById(R.id.vote_average);
            holder.releaseDate = (TextView) row.findViewById(R.id.release_date);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        MovieItem item = mGridData.get(position);

        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
        TextView overView;
        TextView voteAverage;
        TextView releaseDate;

    }


}