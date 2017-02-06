package com.retuerm.android.blockbuster.Utility;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.HttpUrl;

/**
 * Created by max on 02/02/2017.
 */

public class FetchMovieList extends AsyncTask<String, Void, MovieItem[]> {

    // Interface to allow caller to be notified when AsyncTask has finished
    public interface AsyncResponse {
        void processFinish(MovieItem[] output);
    }

    public AsyncResponse delegate;

    public FetchMovieList(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected MovieItem[] doInBackground(String... params) {
        if(params.length == 0) return null;

        String sortingMode = params[0];
        HttpUrl url = NetworkUtils.buildMoviesURL(sortingMode);
        String JSONResponse = "";
        try {
            JSONResponse = NetworkUtils.getResponseFromURL(url);
            // Parse JSON here so I cannot slow down UI task
            return NetworkUtils.parseJSONMovieList(JSONResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Blockbuster", "JSON exception");
            Log.d("Blockbuster", JSONResponse);
            return null;
        }
    }

    @Override
    protected void onPostExecute(MovieItem[] movies) {
        delegate.processFinish(movies);
    }
}
