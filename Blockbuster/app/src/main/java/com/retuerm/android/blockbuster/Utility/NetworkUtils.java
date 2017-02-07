package com.retuerm.android.blockbuster.Utility;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by max on 02/02/2017.
 */

public class NetworkUtils {

    public static String BASE_URL = "https://api.themoviedb.org";

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface TheMovieDBService {
        @GET("3/movie/{sortingMode}")
        Call<MovieList> listMoviesSortedBy(@Path("sortingMode") String sortingMode,
                                                      @Query("api_key") String apiKey);
    }

    public static String buildMoviePosterURL (String posterPath) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("image.tmdb.org")
                // App was testet with Nexus 5X emulator
                .addPathSegments("t/p/w500")
                // Drop leading "/" because OkHTTP will try to encode it
                .addEncodedPathSegment(posterPath.substring(1))
                .build();
        return url.toString();
    }
}
