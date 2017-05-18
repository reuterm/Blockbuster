package com.retuerm.android.blockbuster.utility;

import java.util.ArrayList;

/**
 * Created by max on 18/05/2017.
 */

public class MovieTrailerList {
    private String id;
    private ArrayList<MovieTrailer> results;

    public MovieTrailerList(String id, ArrayList<MovieTrailer> results) {
        this.id = id;
        this.results = results;
    }

    public String getId() {
        return id;
    }

    public ArrayList<MovieTrailer> getResults() {
        return results;
    }
}
