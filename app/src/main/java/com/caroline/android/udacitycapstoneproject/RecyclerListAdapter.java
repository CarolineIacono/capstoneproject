package com.caroline.android.udacitycapstoneproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by carolinestewart on 9/12/16.
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.CustomViewHolder> {

    private ArrayList<MovieItem> movieItemArrayList;
    private Context context;

    public RecyclerListAdapter(Context context, int activity_feed_list, ArrayList<MovieItem> movieItemArrayList) {
        this.movieItemArrayList = movieItemArrayList;
        this.context = context;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_movieitem_list, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        MovieItem movieItem = movieItemArrayList.get(i);

        //Download image using picasso library
        Picasso.with(context).load(movieItem.getPoster())
//                .error(R.drawable.placeholder)
//                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.imageView);

        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(movieItem.getTitle()));
    }

    @Override
    public int getItemCount() {
        return (null != movieItemArrayList ? movieItemArrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
        }
    }

    public void setMovieItemArrayList(ArrayList<MovieItem> movieItems) {
        this.movieItemArrayList = movieItems;
        notifyDataSetChanged();
    }
}
