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
    public final OnMovieClickListener movieClickListener;


    public MovieListAdapter(Context context, OnMovieClickListener movieClickListener) {
        this.context = context;
        this.movieClickListener = movieClickListener;
        this.movieViewData = new ArrayList<>();
        DataUtil.getMovieData(this);
        //DataUtil.getMovieSummaryData(this);
    }




    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //create view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardlayout, viewGroup, false);
        final MovieListAdapter.ViewHolder viewHolder = new MovieListAdapter.ViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //set data
        MovieItem item = movieViewData.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieClickListener.onMovieClicked(movieViewData.get(position));
            }
        });

        holder.setTitleText(item.getTitle());
        holder.setYearView(item.getYear());
//        holder.setImdbRatingView(item.getImdbRating());
//        holder.setImdbVotesView(item.getImdbVotes());
//        holder.setImdbIdView(item.getImdbId());
//        holder.setRatedView(item.getRated());
//        holder.setReleasedView(item.getReleased());
//        holder.setRuntimeView(item.getRuntime());
//        holder.setGenreView(item.getGenre());
//        holder.setDirectorView(item.getDirector());
//        holder.setWriterView(item.getWriter());
//        holder.setActorsView(item.getActors());
//        holder.setPlotView(item.getPlot());
//        holder.setLanguageView(item.getLanguage());
//        holder.setMetascoreView(item.getMetascore());
//        holder.setAwardsView(item.getAwards());
//        holder.setCountryView(item.getCountry());
//        holder.setRankViewView(item.getRank());
//        holder.setImdbLinkView(item.getImdbLink());

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
        public MovieItem movieItem;

        private TextView titleView;
        private TextView yearView;
        public ImageView posterView;
        private TextView imdbRatingView;
        private TextView imdbVotesView;
        private TextView imdbIdView;
        private TextView ratedView;
        private TextView releasedView;
        private TextView runtimeView;
        private TextView genreView;
        private TextView directorView;
        private TextView writerView;
        private TextView actorsView;
        private TextView plotView;
        private TextView languageView;
        private TextView metascoreView;
        private TextView awardsView;
        private TextView countryView;
        private TextView rankViewView;
        private TextView imdbLinkView;

        //etc.

        public void setTitleText(String titleText) {
            titleView.setText(titleText);
        }
        public void setYearView(String yearText) {
            yearView.setText(yearText);
        }
//
//        public void setImdbRatingView(String imdbRating) {
//            imdbRatingView.setText(imdbRating);
//        }
//
//        public void setImdbVotesView(String imdbVotes) {
//            imdbVotesView.setText(imdbVotes);
//        }
//
//        public void setImdbIdView(String imdbId) {
//            imdbIdView.setText(imdbId);
//        }
//
//        public void setRatedView(String rated) {
//            ratedView.setText(rated);
//        }
//
//        public void setReleasedView(String release) {
//            releasedView.setText(release);
//        }
//
//        public void setRuntimeView(String runtime) {
//            runtimeView.setText(runtime);
//        }
//
//        public void setGenreView(String genre) {
//            genreView.setText(genre);
//        }
//
//        public void setDirectorView(String director) {
//            directorView.setText(director);
//        }
//
//        public void setWriterView(String writer) {
//            writerView.setText(writer);
//        }
//
//        public void setActorsView(String actor) {
//            actorsView.setText(actor);
//        }
//
//        public void setPlotView(String plot) {
//            plotView.setText(plot);
//        }
//
//        public void setLanguageView(String language) {
//            languageView.setText(language);
//        }
//
//        public void setMetascoreView(String metascore) {
//            metascoreView.setText(metascore);
//        }
//
//        public void setAwardsView(String awards) {
//            awardsView.setText(awards);
//        }
//
//        public void setCountryView(String country) {
//            countryView.setText(country);
//        }
//
//        public void setRankViewView(String rank) {
//            rankViewView.setText(rank);
//        }
//
//        public void setImdbLinkView(String imdbdLink) {
//            imdbLinkView.setText(imdbdLink);
//        }



        public ViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.title);
            yearView = (TextView) itemView.findViewById(R.id.year);
            posterView = (ImageView) itemView.findViewById(R.id.poster);
            imdbRatingView = (TextView) itemView.findViewById(R.id.imdbRating);
            imdbIdView = (TextView) itemView.findViewById(R.id.imdbId);
            releasedView = (TextView) itemView.findViewById(R.id.released);
            runtimeView = (TextView) itemView.findViewById(R.id.runtime);
            genreView = (TextView) itemView.findViewById(R.id.genre);
            directorView = (TextView) itemView.findViewById(R.id.director);
            writerView = (TextView) itemView.findViewById(R.id.writer);
            actorsView = (TextView) itemView.findViewById(R.id.actors);
            plotView = (TextView) itemView.findViewById(R.id.plot);
            languageView = (TextView) itemView.findViewById(R.id.language);
            metascoreView = (TextView) itemView.findViewById(R.id.metascore);
            awardsView = (TextView) itemView.findViewById(R.id.awards);
            countryView = (TextView) itemView.findViewById(R.id.country);
            rankViewView = (TextView) itemView.findViewById(R.id.rank);
            imdbLinkView = (TextView) itemView.findViewById(R.id.imdbLink);
            imdbVotesView = (TextView) itemView.findViewById(R.id.imdbVotes);
            ratedView = (TextView) itemView.findViewById(R.id.rated);



        }


    }

    public interface OnMovieClickListener {
        void onMovieClicked(MovieItem movieItem);
    }

}
