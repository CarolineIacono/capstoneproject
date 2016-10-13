package com.caroline.android.udacitycapstoneproject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by carolinestewart on 9/15/16.
 */
public class DetailActivity extends FragmentActivity {

    public static final String EXTRA_MOVIE = "movie";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_screen);

//
//        MovieItem movieItem = (MovieItem) getIntent().getSerializableExtra(EXTRA_MOVIE);
//        RecyclerView fragment = RecyclerView.getInstance(movieItem);
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.fragment_container, fragment);
//        fragmentTransaction.commit();
//
//
//    }
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.recyclerview, container, false);
//        android.support.v7.widget.RecyclerView recyclerView = (android.support.v7.widget.RecyclerView) layout.findViewById(R.id.recycler_view);
//
//        adapter = new MovieListAdapter(getContext(), R.layout.activity_main_movie_list, movieItems);
//
//
//        recyclerView.setAdapter(adapter);
//
//
//        return layout;
//
//
//    }

    }
}




