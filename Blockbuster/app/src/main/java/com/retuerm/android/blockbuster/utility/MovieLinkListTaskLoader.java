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

public class MovieLinkListTaskLoader extends AsyncTaskLoader<MovieLinks> {

    private static final String TAG = MovieLinkListTaskLoader.class.getSimpleName();
    private Context mContext;
    private Bundle args;
    private MovieLinks mMovieLinks;

    public MovieLinkListTaskLoader(Context context, Bundle args) {
        super(context);
        mContext = context;
        this.args = args;
        Log.d(TAG, "MovieLinkListTaskLoader: constructed");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(mMovieLinks == null) {
            forceLoad();
        } else {
            deliverResult(mMovieLinks);
        }
    }

    @Override
    public MovieLinks loadInBackground() {
        int movieId = args.getInt(MOVIE_ID_EXTRA);
        Log.d(TAG, "loadInBackground: started");
        NetworkUtils.TheMovieDBService apiService =
                NetworkUtils.retrofit.create(NetworkUtils.TheMovieDBService.class);

        try {
            Call<MovieTrailerList> trailerCall = apiService.getMovieTrailer(movieId, ApiContract.API_KEY);
            Response<MovieTrailerList> trailerResponse = trailerCall.execute();
            ArrayList<MovieTrailer> trailers = filterMovieVideos(trailerResponse.body().getResults());

            Call<MovieReviewList> reviewCall = apiService.getMovieReviews(movieId, ApiContract.API_KEY);
            Response<MovieReviewList> reviewResponse = reviewCall.execute();
            ArrayList<MovieReview> reviews = reviewResponse.body().getResults();

            return new MovieLinks(trailers, reviews);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(MovieLinks data) {
        mMovieLinks = data;
        super.deliverResult(data);
    }

    private ArrayList<MovieTrailer> filterMovieVideos(ArrayList<MovieTrailer> videos) {
        ArrayList<MovieTrailer> filtered = new ArrayList<MovieTrailer>();
        for (MovieTrailer item : videos) {
            if (item.getType().equals("Trailer")) filtered.add(item);
        }
        return filtered;
    }
}
