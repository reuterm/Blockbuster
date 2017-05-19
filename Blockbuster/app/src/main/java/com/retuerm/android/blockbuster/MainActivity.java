package com.retuerm.android.blockbuster;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.retuerm.android.blockbuster.utility.MovieListTaskLoader;
import com.retuerm.android.blockbuster.utility.MovieItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<ArrayList<MovieItem>>{

    public static final String PATH_MOST_POPULAR = "popular";
    public static final String PATH_TOP_RATED = "top_rated";
    public static final String FAVOURITES = "favourites";
    private static final String GRID_STATE_KEY = "blockbuster_gridlayout_state";
    public static final String PASS = "blockbuster_movie_item";
    private static final int MOVIE_LOADER_ID = 42;
    public static final String TASK_EXTRA = "task";
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;
    @BindView(R.id.tv_error_message) TextView mErrorDisplay;
    @BindView(R.id.rv_movie_list) RecyclerView mRecyclerView;
    private MovieListAdapter mMovieAdapter;
    private Bundle queryBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // App was tested with Nexus 5X and Nexus 9 emulator
        int columns = getResources().getInteger(R.integer.column_count);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columns, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieListAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        queryBundle = new Bundle();

        queryBundle.putString(TASK_EXTRA, PATH_MOST_POPULAR);

        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, queryBundle, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    private void loadMovieList(String task) {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);

        // Handle AsyncTask to retrieve movie list
        queryBundle.putString(TASK_EXTRA, task);

        LoaderManager loaderManager = getSupportLoaderManager();

        loaderManager.restartLoader(MOVIE_LOADER_ID, queryBundle, this);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorDisplay.setVisibility(View.VISIBLE);
    }

    private void showMovieList() {
        mErrorDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_most_popular:
                loadMovieList(PATH_MOST_POPULAR);
                return true;
            case R.id.action_sort_top_rated:
                loadMovieList(PATH_TOP_RATED);
                return true;
            case R.id.action_show_favourites:
                loadMovieList(FAVOURITES);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(MovieItem movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(PASS, movie);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable gridState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(GRID_STATE_KEY, gridState);
        Log.d("Blockbuster", "onSaveInstanceSate");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.containsKey(GRID_STATE_KEY)) {
            Parcelable gridState = savedInstanceState.getParcelable(GRID_STATE_KEY);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(gridState);
            Log.d("Blockbuster", "onRestoreInstanceState");
        }
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
        return new MovieListTaskLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {
        MainActivity.this.mLoadingIndicator.setVisibility(View.INVISIBLE);
        if(data == null) {
            showErrorMessage();
        } else {
            // Convert typed ArrayList to typed array
            MovieItem[] movieList = new MovieItem[data.size()];
            movieList = data.toArray(movieList);
            mMovieAdapter.setMovieList(movieList);
            showMovieList();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {

    }
}
