package com.retuerm.android.blockbuster.Utility;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by max on 02/02/2017.
 */

public class NetworkUtils {

    public static String getResponseFromURL(HttpUrl url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static HttpUrl buildMoviesURL (String sorting_mode) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.themoviedb.org")
                .addPathSegments("3/movie")
                .addPathSegment(sorting_mode)
                .addQueryParameter("api_key", ApiContract.API_KEY)
                .build();
        return url;
    }
}
