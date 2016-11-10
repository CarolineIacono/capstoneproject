package com.caroline.android.udacitycapstoneproject.presenter;

import com.caroline.android.udacitycapstoneproject.concurrency.UiExecutor;
import com.caroline.android.udacitycapstoneproject.model.ConnectivityProvider;
import com.caroline.android.udacitycapstoneproject.model.DataUtil;
import com.caroline.android.udacitycapstoneproject.model.MovieItem;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by carolinestewart on 11/9/16.
 */
public class MainPresenter {
    private final Executor backgroundExecutor;
    private final Executor foregroundExecutor;
    private View view;
    private final ConnectivityProvider connectivityProvider;


    public MainPresenter(Executor backgroundExecutor, UiExecutor foregroundExecutor, ConnectivityProvider connectivityProvider) {
        this.backgroundExecutor = backgroundExecutor;
        this.foregroundExecutor = foregroundExecutor;
        this.connectivityProvider = connectivityProvider;
    }

    public void attach(View view) {
        this.view = view;

    }


    public void present() {
        if (connectivityProvider.isConnected()) {

            backgroundExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    final List<MovieItem> movieItems = DataUtil.fetchMovieItems();

                    foregroundExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            view.showMovieItems(movieItems);
                        }
                    });
                }
            });
        } else {
            view.showDisconnectedWhenDisplayList();
        }

    }

    public void showMovie(MovieItem movieItem) {
        if (connectivityProvider.isConnected()) {
            view.showMovie(movieItem);
        } else {
            view.showDisconnectWhenSelected();
        }

    }

    public interface View {
        void showMovieItems(List<MovieItem> movieItems);

        void showDisconnectedWhenDisplayList();

        void showMovie(MovieItem movieItem);

        void showDisconnectWhenSelected();
    }
}

