package com.retuerm.android.blockbuster.utility;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import com.retuerm.android.blockbuster.data.FavouriteMoviesContract.FavouriteMoviesEntry;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

import static com.retuerm.android.blockbuster.MainActivity.TASK_EXTRA;
import static com.retuerm.android.blockbuster.MainActivity.PATH_MOST_POPULAR;
import static com.retuerm.android.blockbuster.MainActivity.PATH_TOP_RATED;
import static com.retuerm.android.blockbuster.MainActivity.FAVOURITES;


/**
 * Created by max on 24/02/2017.
 */

public class MovieListTaskLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {

    private Bundle args;
    private ArrayList<MovieItem> mMovieList;
    private static final String TAG = MovieListTaskLoader.class.getSimpleName();
    private Context mContext;

    public MovieListTaskLoader(Context context, Bundle args) {
        super(context);
        mContext = context;
        this.args = args;
        Log.d(TAG, "MovieListTaskLoader: constructed");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(mMovieList == null) {
            forceLoad();
        } else {
            deliverResult(mMovieList);
        }
    }

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        String task = args.getString(TASK_EXTRA);
        if(task == null || TextUtils.isEmpty(task)) {
            return null;
        }
        Log.d(TAG, "loadInBackground: started");
        NetworkUtils.TheMovieDBService apiService =
                NetworkUtils.retrofit.create(NetworkUtils.TheMovieDBService.class);
        switch (task) {
            case PATH_MOST_POPULAR:
            case PATH_TOP_RATED:
                try {
                    // Retrieve JSON response and have Gson parse it
                    Call<MovieList> call = apiService.listMoviesSortedBy(task, ApiContract.API_KEY);
                    Response<MovieList> response = call.execute();
                    // Only return results array
                    return response.body().getResults();

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            case FAVOURITES:
                Cursor cursor = mContext.getContentResolver().query(
                        FavouriteMoviesEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
                ArrayList<MovieItem> results = new ArrayList<MovieItem>();
                while(cursor.moveToNext()) {
                    int movieId = cursor.getInt(
                            cursor.getColumnIndex(FavouriteMoviesEntry.COLUMN_MOVIE_ID)
                    );
                    String title = cursor.getString(
                            cursor.getColumnIndex(FavouriteMoviesEntry.COLUMN_TITLE)
                    );
                    String release_date = cursor.getString(
                            cursor.getColumnIndex(FavouriteMoviesEntry.COLUMN_RELEASE_DATE)
                    );
                    String plot_synopsis = cursor.getString(
                            cursor.getColumnIndex(FavouriteMoviesEntry.COLUMN_PLOT_SYNOPSIS)
                    );
                    double vote_average = cursor.getDouble(
                            cursor.getColumnIndex(FavouriteMoviesEntry.COLUMN_AVERAGE_RATING)
                    );
                    String poster_path = cursor.getString(
                            cursor.getColumnIndex(FavouriteMoviesEntry.COLUMN_POSTER_PATH)
                    );
                    results.add(new MovieItem(movieId,
                            title, release_date, plot_synopsis, vote_average, poster_path));
                }
                return results;
            default:
                throw new UnsupportedOperationException("Unknown task: " + task);
        }
    }

    @Override
    public void deliverResult(ArrayList<MovieItem> data) {
        mMovieList = data;
        super.deliverResult(data);
    }
}
