package com.caroline.android.udacitycapstoneproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carolinestewart on 9/12/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> implements DataUtil.GetMovieCallback {

    private List<MovieItem> movieViewData;
    private Context context;


    public MovieListAdapter(Context context) {
        this.context = context;
        this.movieViewData = new ArrayList<>();
        DataUtil.getMovieData(this);
    }


    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //create view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardlayout, viewGroup, false);
        MovieListAdapter.ViewHolder viewHolder = new MovieListAdapter.ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //set data
        MovieItem item = movieViewData.get(position);
        holder.setTitleText(item.getTitle());
        holder.setYearView(item.getYear());


        Picasso.with(context)
                .load(item.getPoster())
                .into(holder.posterView);

    }


    @Override
    public int getItemCount() {
        return (null != movieViewData ? movieViewData.size() : 0);
    }

    @Override
    public void onComplete(List<MovieItem> list) {
        movieViewData = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private TextView yearView;
        public ImageView posterView;

        //etc.

        public void setTitleText(String titleText) {
            titleView.setText(titleText);
        }
        public void setYearView(String yearText) {
            yearView.setText(yearText);
        }


        public ViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.title);
            yearView = (TextView) itemView.findViewById(R.id.year);
            posterView = (ImageView) itemView.findViewById(R.id.poster);


        }


    }

}
