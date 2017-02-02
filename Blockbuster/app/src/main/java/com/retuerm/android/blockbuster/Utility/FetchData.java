package com.retuerm.android.blockbuster.Utility;

import android.os.AsyncTask;
import android.widget.Toast;

import com.retuerm.android.blockbuster.MainActivity;

import java.io.IOException;

import okhttp3.HttpUrl;

/**
 * Created by max on 02/02/2017.
 */

public class FetchData extends AsyncTask<String, Void, String> {

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate;

    public FetchData(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... params) {
        if(params.length == 0) return null;

        String mode = params[0];
        HttpUrl url = NetworkUtils.buildMoviesURL(mode);
        try {
            return NetworkUtils.getResponseFromURL(url);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.processFinish(s);
    }
}
