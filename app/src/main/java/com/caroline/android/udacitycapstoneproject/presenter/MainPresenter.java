package com.caroline.android.udacitycapstoneproject.presenter;

import com.caroline.android.udacitycapstoneproject.concurrency.UiExecutor;
import com.caroline.android.udacitycapstoneproject.model.ConnectivityProvider;
import com.caroline.android.udacitycapstoneproject.model.MovieDao;
import com.caroline.android.udacitycapstoneproject.model.MovieItem;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

/**
 * Created by carolinestewart on 11/9/16.
 */
public class MainPresenter {
    private final Executor backgroundExecutor;
    private final Executor foregroundExecutor;
    private View view;
    private final ConnectivityProvider connectivityProvider;
    private String currentLatitude;
    private String currentLongitude;
    private final MovieDao movieDao;

    public MainPresenter(Executor backgroundExecutor, UiExecutor foregroundExecutor, ConnectivityProvider connectivityProvider, MovieDao movieDao) {
        this.backgroundExecutor = backgroundExecutor;
        this.foregroundExecutor = foregroundExecutor;
        this.connectivityProvider = connectivityProvider;
        this.movieDao = movieDao;
    }

    public void attach(View view) {
        this.view = view;
    }

    public void detach() {
        view = null;
    }

    public void present() {
        if (connectivityProvider.isConnected()) {

            backgroundExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    final List<MovieItem> movieItems = movieDao.fetchMovieItems();

                    foregroundExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (view != null) {
                                view.showMovieItems(movieItems);
                            }
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

    public void handleShowTheatersRequest() {
        String url = String.format(Locale.ENGLISH, "geo:%s, %s?q=theater", currentLatitude, currentLongitude);
        view.showTheatersNearMe(url);
    }

    public void updateLocation(String lat, String lon) {
        currentLatitude = lat;
        currentLongitude = lon;
    }

    public interface View {
        void showMovieItems(List<MovieItem> movieItems);
        void showDisconnectedWhenDisplayList();
        void showMovie(MovieItem movieItem);
        void showDisconnectWhenSelected();
        void showTheatersNearMe(String url);
    }

    private static class NullInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Class<?> returnType = method.getReturnType();
            if (returnType == Byte.TYPE || returnType == Short.TYPE || returnType == Integer.TYPE ||
                returnType == Long.TYPE || returnType == Float.TYPE || returnType == Double.TYPE ||
                returnType == Character.TYPE) {
                return 0;
            } else if (returnType == Boolean.TYPE) {
                return false;
            } else {
                return null;
            }
        }
    }

    public static <T> T create(Class<T> nullObjClazz) {
        Object proxy = Proxy.newProxyInstance(nullObjClazz.getClassLoader(), new Class<?>[]{nullObjClazz}, new NullInvocationHandler());
        return nullObjClazz.cast(proxy);
    }
}

