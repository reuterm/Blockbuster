package com.retuerm.android.blockbuster.utility;

import java.util.ArrayList;

/**
 * Created by max on 19/05/2017.
 */

public class MovieReviewList {
    private int movieId;
    private int page;
    private ArrayList<MovieReview> results;
    private int total_pages;
    private int total_results;

    public MovieReviewList(int movieId, int page, ArrayList<MovieReview> results,
                           int total_pages, int total_results) {
        this.movieId = movieId;
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public int getMovieId() {
        return movieId;
    }

    public ArrayList<MovieReview> getResults() {
        return results;
    }
}
