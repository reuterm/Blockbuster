package com.retuerm.android.blockbuster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.retuerm.android.blockbuster.Utility.FetchMovieList;
import com.retuerm.android.blockbuster.Utility.MovieItem;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String PATH_MOST_POPULAR = "popular";
    private static final String PATH_TOP_RATED = "top_rated";
    public static final String PASS = "blockbuster_movie_item";

    private ProgressBar mLoadingIndicator;
    private TextView mErrorDisplay;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorDisplay = (TextView) findViewById(R.id.tv_error_message);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieList(PATH_MOST_POPULAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    private void loadMovieList(String sortingMode) {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        new FetchMovieList(new FetchMovieList.AsyncResponse() {
            @Override
            public void processFinish(MovieItem[] output) {
                MainActivity.this.mLoadingIndicator.setVisibility(View.INVISIBLE);
                if(output == null) {
                    showErrorMessage();
                } else {
                    mMovieAdapter.setMovieList(output);
                    showMovieList();
                }
            }
        }).execute(sortingMode);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(MovieItem movie) {
//        Toast.makeText(this, movie.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(PASS, movie);
        startActivity(intent);
    }
}
