package com.retuerm.android.blockbuster.utility;

/**
 * Created by max on 19/05/2017.
 */

public class MovieReview {
    private String id;
    private String author;
    private String url;

    public MovieReview(String id, String author, String url) {
        this.id = id;
        this.author = author;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }
}
