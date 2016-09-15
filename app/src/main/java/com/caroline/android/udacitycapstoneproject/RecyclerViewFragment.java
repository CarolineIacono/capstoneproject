package com.caroline.android.udacitycapstoneproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 */
public class RecyclerViewFragment extends Fragment {
    private static final String COMMIT_ID = "e50bcf43d142b2397f815f5d529d232f944f23f0";
    private static final String BASE_URL = "https://raw.githubusercontent.com/MercuryIntermedia/Sample_Json_Movies/" + COMMIT_ID + "/top_movies.json";


    private OnFragmentInteractionListener listener;

    private ArrayList<MovieItem> movieItems;
    private RecyclerView recyclerView;
    private RecyclerListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieItems = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.recyclerview, container, false);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);

        adapter = new RecyclerListAdapter(getContext(), R.layout.activity_movieitem_list, movieItems);


        recyclerView.setAdapter(adapter);

//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//
//                                            {
//
//                                                ///take to the details view
//                                                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                                                    if (listener != null) {
//                                                        listener.onMovieClick((MovieItem) parent.getItemAtPosition(position));
//                                                    }
//                                                }
//                                            }
//
//        );

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected)

        {
            new MovieFetchTask().execute(BASE_URL);

        } else

        {
            Toast.makeText(getContext(), "No network connection", Toast.LENGTH_SHORT).show();


        }
        return layout;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        movieItems.clear();


        return true;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public class MovieFetchTask extends AsyncTask<String, Void, Integer> {


        @Override
        protected Integer doInBackground(String... params) {
            String urlString = params[0];
            return parseMovieItems(DataUtil.fetch(urlString));

        }

        @Override
        protected void onPostExecute(Integer result) {

            if (result == 1) {

                adapter.setMovieItemArrayList(movieItems);

            } else {
                Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

        }

        private int parseMovieItems(String result) {
            int success = 0;
            try {
                JSONArray response = new JSONArray(result);

                {

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject post = response.getJSONObject(i);
                        parseSingleObject(post);
                    }
                }

                success = 1;


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {


        super.onPause();
    }


    public void parseSingleObject(JSONObject post) {
        MovieItem item = new MovieItem();


        String title = post.optString("title");
        item.setTitle(title);

        String rank = post.optString("rank");
        item.setRank(rank);

        String year = post.optString("year");
        item.setYear(year);

        String poster = post.optString("poster");
        item.setPoster(poster);

        String imdbLink = post.optString("imdbLink");
        item.setImdbLink(imdbLink);

        String imdbRating = post.optString("imdbRating");
        item.setImdbRating(imdbRating);

        String imdbVotes = post.optString("imdbVotes");
        item.setImdbVotes(imdbVotes);

        String genre = post.optString("genre");
        item.setGenre(genre);


        movieItems.add(item);
    }
}

