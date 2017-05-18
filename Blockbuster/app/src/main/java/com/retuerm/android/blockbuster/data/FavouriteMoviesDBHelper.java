package com.retuerm.android.blockbuster.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.retuerm.android.blockbuster.data.FavouriteMoviesContract.FavouriteMoviesEntry;

/**
 * Created by max on 17/05/2017.
 */

public class FavouriteMoviesDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favourites.db";

    private static final int DATABASE_VERSION = 1;

    public FavouriteMoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVOURITE_MOVIES_TABLE =
                "CREATE TABLE " + FavouriteMoviesEntry.TABLE_NAME + " (" +
                        FavouriteMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        FavouriteMoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL UNIQUE, " +
                        FavouriteMoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                        FavouriteMoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                        FavouriteMoviesEntry.COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL, " +
                        FavouriteMoviesEntry.COLUMN_AVERAGE_RATING + " REAL NOT NULL, " +
                        FavouriteMoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_FAVOURITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteMoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
