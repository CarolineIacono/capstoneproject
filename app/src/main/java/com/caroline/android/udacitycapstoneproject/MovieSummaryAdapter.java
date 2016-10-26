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
 * Created by carolinestewart on 10/26/16.
 */
public class MovieSummaryAdapter extends ArrayAdapter<MovieSummary> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<MovieSummary> summaryData = new ArrayList<>();

    public MovieSummaryAdapter(Context context, int layoutResourceId, ArrayList<MovieSummary> summaryData) {
        super(context, layoutResourceId, summaryData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.summaryData = summaryData;

    }


    public void setSummaryData(ArrayList<MovieSummary> summaryData) {
        this.summaryData = summaryData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleView = (TextView) row.findViewById(R.id.title);
            holder.yearView = (TextView) row.findViewById(R.id.year);
            holder.imdbRatingView = (TextView) row.findViewById(R.id.imdbRating);
            holder.imdbVotesView = (TextView) row.findViewById(R.id.imdbVotes);
            holder.imdbIdView = (TextView) row.findViewById(R.id.imdbId);
            holder.ratedView = (TextView) row.findViewById(R.id.rated);
            holder.releasedView = (TextView) row.findViewById(R.id.released);
            holder.runtimeView = (TextView) row.findViewById(R.id.runtime);
            holder.genreView = (TextView) row.findViewById(R.id.genre);
            holder.directorView = (TextView) row.findViewById(R.id.director);
            holder.writerView = (TextView) row.findViewById(R.id.writer);
            holder.actorsView = (TextView) row.findViewById(R.id.actors);
            holder.plotView = (TextView) row.findViewById(R.id.plot);
            holder.languageView = (TextView) row.findViewById(R.id.language);
            holder.metascoreView = (TextView) row.findViewById(R.id.metascore);
            holder.awardsView = (TextView) row.findViewById(R.id.awards);
            holder.countryView = (TextView) row.findViewById(R.id.country);
            holder.rankViewView = (TextView) row.findViewById(R.id.rank);
            holder.imdbLinkView = (TextView) row.findViewById(R.id.imdbLink);



            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        MovieSummary item = summaryData.get(position);

        Picasso.with(context).load(item.getPoster()).into(holder.posterView);
        return row;
    }

    static class ViewHolder {

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

        public void setTitleText(String titleText) {
            titleView.setText(titleText);
        }

        public void setYearView(String yearText) {
            yearView.setText(yearText);
        }

        public void setImdbRatingView(String imdbRating) {
            imdbRatingView.setText(imdbRating);
        }

        public void setImdbVotesView(String imdbVotes) {
            imdbVotesView.setText(imdbVotes);
        }

        public void setImdbIdView(String imdbId) {
            imdbIdView.setText(imdbId);
        }

        public void setRatedView(String rated) {
            ratedView.setText(rated);
        }

        public void setReleasedView(String release) {
            releasedView.setText(release);
        }

        public void setRuntimeView(String runtime) {
            runtimeView.setText(runtime);
        }

        public void setGenreView(String genre) {
            genreView.setText(genre);
        }

        public void setDirectorView(String director) {
            directorView.setText(director);
        }

        public void setWriterView(String writer) {
            writerView.setText(writer);
        }

        public void setActorsView(String actor) {
            actorsView.setText(actor);
        }

        public void setPlotView(String plot) {
            plotView.setText(plot);
        }

        public void setLanguageView(String language) {
            languageView.setText(language);
        }

        public void setMetascoreView(String metascore) {
            metascoreView.setText(metascore);
        }

        public void setAwardsView(String awards) {
            awardsView.setText(awards);
        }

        public void setCountryView(String country) {
            countryView.setText(country);
        }

        public void setRankViewView(String rank) {
            rankViewView.setText(rank);
        }

        public void setImdbLinkView(String imdbdLink) {
            imdbLinkView.setText(imdbdLink);


        }

    }


}