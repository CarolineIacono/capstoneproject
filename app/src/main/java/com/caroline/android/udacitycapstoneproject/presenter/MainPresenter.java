package com.caroline.android.udacitycapstoneproject.presenter;

import com.caroline.android.udacitycapstoneproject.concurrency.UiExecutor;
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

    public MainPresenter(Executor backgroundExecutor, UiExecutor foregroundExecutor) {
        this.backgroundExecutor = backgroundExecutor;
        this.foregroundExecutor = foregroundExecutor;
    }

    public void attach(View view) {
        this.view = view;

    }


    public void present() {
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
    }

    public interface View {
        void showMovieItems(List<MovieItem> movieItems);
    }
}

