package com.retuerm.android.blockbuster.Utility;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

import static com.retuerm.android.blockbuster.MainActivity.SORTING_MODE_EXTRA;

/**
 * Created by max on 24/02/2017.
 */

public class MovieDataTaskLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {

    private Bundle args;
    private ArrayList<MovieItem> mMovieData;
    private static final String TAG = MovieDataTaskLoader.class.getSimpleName();

    public MovieDataTaskLoader(Context context, Bundle args) {
        super(context);
        this.args = args;
        Log.d(TAG, "MovieDataTaskLoader: constructed");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(mMovieData == null) {
            forceLoad();
        } else {
            deliverResult(mMovieData);
        }
    }

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        String sortingMode = args.getString(SORTING_MODE_EXTRA);
        if(sortingMode == null || TextUtils.isEmpty(sortingMode)) {
            return null;
        }
        try {
            Log.d(TAG, "loadInBackground: started");
            // Retrieve JSON response and have Gson parse it
            NetworkUtils.TheMovieDBService apiService = NetworkUtils.retrofit.create(NetworkUtils.TheMovieDBService.class);
            Call<MovieList> call = apiService.listMoviesSortedBy(sortingMode, ApiContract.API_KEY);
            Response<MovieList> response = call.execute();
            // Only return results array
            return response.body().getResults();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(ArrayList<MovieItem> data) {
        mMovieData = data;
        super.deliverResult(data);
    }
}
