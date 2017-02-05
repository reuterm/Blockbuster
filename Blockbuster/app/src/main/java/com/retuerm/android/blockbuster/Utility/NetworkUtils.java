package com.retuerm.android.blockbuster.Utility;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static HttpUrl buildMoviesURL (String sortingMode) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.themoviedb.org")
                .addPathSegments("3/movie")
                .addPathSegment(sortingMode)
                .addQueryParameter("api_key", ApiContract.API_KEY)
                .build();
        Log.d("Bockbuster:MoviesURL", url.toString());
        return url;
    }

    public static HttpUrl buildMoviePosterURL (String posterPath) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("image.tmdb.org")
                .addPathSegments("t/p/w500")
                // Drop leading "/" because OkHTTP will try to encode it
                .addEncodedPathSegment(posterPath.substring(1))
                .build();
        return url;
    }

    public static MovieItem[] parseJSONMovieList(String JSONString) throws JSONException {
        JSONObject response = new JSONObject(JSONString);
        JSONArray movieList = response.getJSONArray("results");
        MovieItem[] movies = new MovieItem[movieList.length()];
        for (int i = 0; i < movieList.length(); i++ ) {
            JSONObject movieObject = movieList.getJSONObject(i);
            movies[i] = new MovieItem(movieObject.getString("title"), movieObject.getString("release_date"),
                    movieObject.getString("overview"), movieObject.getDouble("vote_average"),
                    buildMoviePosterURL(movieObject.getString("poster_path")));
//            Log.d("Blockbuster:parseJSON", movies[i].getTitle());
//            Log.d("Blockbuster:parseJSON", movies[i].getImageURL().toString());
        }
        return movies;
    }
}
