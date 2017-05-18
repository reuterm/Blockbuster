package com.retuerm.android.blockbuster.utility;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

import static com.retuerm.android.blockbuster.DetailActivity.MOVIE_ID_EXTRA;

/**
 * Created by max on 18/05/2017.
 */

public class TrailerListTaskLoader extends AsyncTaskLoader<ArrayList<MovieTrailer>> {

    private static final String TAG = TrailerListTaskLoader.class.getSimpleName();
    private Context mContext;
    private Bundle args;
    private ArrayList<MovieTrailer> mMovieTrailers;

    public TrailerListTaskLoader(Context context, Bundle args) {
        super(context);
        mContext = context;
        this.args = args;
        Log.d(TAG, "TrailerListTaskLoader: constructed");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(mMovieTrailers == null) {
            forceLoad();
        } else {
            deliverResult(mMovieTrailers);
        }
    }

    @Override
    public ArrayList<MovieTrailer> loadInBackground() {
        int movieId = args.getInt(MOVIE_ID_EXTRA);
        Log.d(TAG, "loadInBackground: started");
        NetworkUtils.TheMovieDBService apiService =
                NetworkUtils.retrofit.create(NetworkUtils.TheMovieDBService.class);

        try {
            Call<MovieTrailerList> call = apiService.getMovieTrailer(movieId, ApiContract.API_KEY);
            Response<MovieTrailerList> response = call.execute();
            ArrayList<MovieTrailer> results = response.body().getResults();
            ArrayList<MovieTrailer> filtered = new ArrayList<MovieTrailer>();
            for (MovieTrailer item : results) {
                if (item.getType().equals("Trailer")) filtered.add(item);
            }
            return filtered;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(ArrayList<MovieTrailer> data) {
        mMovieTrailers = data;
        super.deliverResult(data);
    }
}
