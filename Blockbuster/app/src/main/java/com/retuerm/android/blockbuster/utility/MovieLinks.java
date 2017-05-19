package com.retuerm.android.blockbuster.utility;

import java.util.ArrayList;

/**
 * Created by max on 19/05/2017.
 */

public class MovieLinks {
    private ArrayList<MovieTrailer> trailers;
    private ArrayList<MovieReview> reviews;

    public MovieLinks(ArrayList<MovieTrailer> trailers, ArrayList<MovieReview> reviews) {
        this.trailers = trailers;
        this.reviews = reviews;
    }

    public ArrayList<MovieTrailer> getTrailers() {
        return trailers;
    }

    public ArrayList<MovieReview> getReviews() {
        return reviews;
    }
}
