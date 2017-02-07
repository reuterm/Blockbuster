package com.retuerm.android.blockbuster.Utility;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by max on 02/02/2017.
 */

public class FetchMovieList extends AsyncTask<String, Void, ArrayList<MovieItem>> {

    // Interface to allow caller to be notified when AsyncTask has finished
    public interface AsyncResponse {
        void processFinish(ArrayList<MovieItem> output);
    }

    public AsyncResponse delegate;

    public FetchMovieList(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<MovieItem> doInBackground(String... params) {
        if(params.length == 0) return null;

        String sortingMode = params[0];
        try {
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
    protected void onPostExecute(ArrayList<MovieItem> movies) {
        delegate.processFinish(movies);
    }
}
