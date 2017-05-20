package com.retuerm.android.blockbuster.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by max on 18/05/2017.
 */

public class FavouriteMoviesProvider extends ContentProvider {

    public static final int CODE_FAVOURITES = 100;
    public static final int CODE_FAVOURITES_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FavouriteMoviesDBHelper mOpenHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavouriteMoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, FavouriteMoviesContract.PATH_FAVOURITES, CODE_FAVOURITES);
        matcher.addURI(authority, FavouriteMoviesContract.PATH_FAVOURITES + "/#", CODE_FAVOURITES_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new FavouriteMoviesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CODE_FAVOURITES:
                cursor = db.query(
                        FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CODE_FAVOURITES_WITH_ID:
                String id = uri.getLastPathSegment();
                cursor = db.query(
                        FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME,
                        projection,
                        FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + "=?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri resultUri;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case CODE_FAVOURITES:
                long id = db.insert(
                        FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME,
                        null,
                        values
                );
                if(id > 0) {
                    resultUri = ContentUris.withAppendedId(
                            FavouriteMoviesContract.FavouriteMoviesEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int result;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case CODE_FAVOURITES_WITH_ID:
                String id = uri.getLastPathSegment();
                result = db.delete(
                        FavouriteMoviesContract.FavouriteMoviesEntry.TABLE_NAME,
                        FavouriteMoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + "=?",
                        new String[]{id}
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
