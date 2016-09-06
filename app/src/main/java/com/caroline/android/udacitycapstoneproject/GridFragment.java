package com.caroline.android.udacitycapstoneproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainActivity.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class GridFragment extends Fragment {
    private static final String COMMIT_ID = "e50bcf43d142b2397f815f5d529d232f944f23f0";
    private static final String BASE_URL = "https://raw.githubusercontent.com/MercuryIntermedia/Sample_Json_Movies/" + COMMIT_ID + "/top_movies.json";


    private OnFragmentInteractionListener mListener;

    private ArrayList<MovieItem> gridData;
    private MovieGridAdapter gridAdapter;
    private Set<String> favoriteSet;
    private int state = R.id.rated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridData = new ArrayList<>();
        if (savedInstanceState != null) {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            favoriteSet = new HashSet<>(sharedPref.getStringSet(getActivity().getResources().getString(R.string.favorite), new HashSet<String>()));
            state = savedInstanceState.getInt("stateOptions");
            optionsSwitch(state);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_grid, container, false);

        GridView gridView = (GridView) layout.findViewById(R.id.gridView);

        gridAdapter = new MovieGridAdapter(getContext(), R.layout.movie_item, gridData);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            ///take to the details view
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (mListener != null) {
                    mListener.onMovieClick((MovieItem) parent.getItemAtPosition(position));
                }
            }
        });

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            new MovieFetchTask().execute(BASE_URL);

        } else {
            Toast.makeText(getContext(), "No network connection", Toast.LENGTH_SHORT).show();


        }
        return layout;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        favoriteSet = new HashSet<>(sharedPref.getStringSet(getActivity().getResources().getString(R.string.favorite), new HashSet<String>()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void optionsSwitch(int id) {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

//        switch (id) {
//
//
//            case R.id.popular:
//                if (isConnected) {
//                    new MovieFetchTask().execute(FEED_URL2);
//                } else {
//                    Toast.makeText(getContext(), "No network connection", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.rated:
//                if (isConnected) {
//                    new MovieFetchTask().execute(BASE_URL);
//                } else {
//                    Toast.makeText(getContext(), "No network connection", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.favorite:
//                if (isConnected) {
//                    Iterator<String> iterator = favoriteSet.iterator();
//                    while (iterator.hasNext())
//                        new MovieFetchTask().execute(FEED_URL3 + iterator.next() + API_KEY);
//                } else {
//                    Toast.makeText(getContext(), "No network connection", Toast.LENGTH_SHORT).show();
//                }
//        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        gridData.clear();
        state = item.getItemId();
        optionsSwitch(state);

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

                gridAdapter.setGridData(gridData);

            } else {
                Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

        }

        private int parseMovieItems(String result) {
            int success = 0;
            try {
                JSONObject response = new JSONObject(result);
                JSONArray posts = response.optJSONArray("results");
                if (posts == null) {
                    parseSingleObject(response);
                } else {


                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.optJSONObject(i);
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
        outState.putInt("stateOptions", state);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putStringSet(getActivity().getResources().getString(R.string.favorite), favoriteSet);
        editor.commit();

        super.onPause();
    }

    public void updateFavorite(String favorite, boolean isFavorited) {
        if (isFavorited) {
            //adding to the set of favorite movies
            favoriteSet.add(favorite);
        } else {
            favoriteSet.remove(favorite);
        }
    }

    public void parseSingleObject(JSONObject post) {
        String title = post.optString("title");
        MovieItem item = new MovieItem();
        item.setTitle(title);


        String image = post.optString("poster_path");
        image = "http://image.tmdb.org/t/p/w185/" + image;
        item.setImage(image);

        String overview = post.optString("overview");
        item.setOverview(overview);

        String vote = post.optString("vote_average");
        item.setVoteAverage(vote);

        String release = post.optString("release_date");
        item.setRelease(release);

        item.setId(post.optString("id"));

        gridData.add(item);
    }
}

