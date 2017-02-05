package com.retuerm.android.blockbuster.Utility;

import okhttp3.HttpUrl;

/**
 * Created by max on 05/02/2017.
 */

public class MovieItem {
    private String title;
    private String releaseDate;
    private String plotSynopsis;
    private double averageRating;
    private HttpUrl imageURL;

    public MovieItem(String title, String releaseDate, String plotSynopsis, double averageRating,
                     HttpUrl imageURL) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.plotSynopsis = plotSynopsis;
        this.averageRating = averageRating;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public HttpUrl getImageURL() {
        return imageURL;
    }
}
