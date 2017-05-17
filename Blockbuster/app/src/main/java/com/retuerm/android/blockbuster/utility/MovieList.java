package com.retuerm.android.blockbuster.utility;

import java.util.ArrayList;

/**
 * Created by max on 07/02/2017.
 */

// Helper class that represents a TheMovieDB response
public class MovieList {
    private int page;
    private ArrayList<MovieItem> results;
    private int total_results;
    private int total_pages;

    public MovieList(int page, ArrayList<MovieItem> results, int totalResults, int totalPages) {
        this.page = page;
        this.results = results;
        this.total_results = totalResults;
        this.total_pages = totalPages;
    }

    public ArrayList<MovieItem> getResults() {
        return results;
    }


}
