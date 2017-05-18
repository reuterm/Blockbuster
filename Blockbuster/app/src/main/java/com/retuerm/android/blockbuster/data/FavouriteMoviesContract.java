package com.retuerm.android.blockbuster.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by max on 17/05/2017.
 */

public class FavouriteMoviesContract {

    public static final String CONTENT_AUTHORITY = "com.reuterm.android.blockbuster";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVOURITES = "favourites";

    public static final class FavouriteMoviesEntry  implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVOURITES)
                .build();

        public static final String TABLE_NAME = "favourite_movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "movie_title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_PLOT_SYNOPSIS = "plot_synopsis";
        public static final String COLUMN_AVERAGE_RATING = "average_rating";
        public static final String COLUMN_POSTER_PATH = "poster_path";
    }
}