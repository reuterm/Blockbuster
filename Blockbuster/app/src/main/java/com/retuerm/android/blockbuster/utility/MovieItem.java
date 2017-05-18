package com.retuerm.android.blockbuster.utility;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by max on 05/02/2017.
 */

// Helper class that represents a single movie
public class MovieItem implements Parcelable {
    private int id;
    private String title;
    private String release_date;
    private String overview;
    private double vote_average;
    private String poster_path;
    private boolean favourite;

    public MovieItem(int id, String title, String releaseDate, String plotSynopsis, double averageRating,
                     String imageURL) {
        this.id = id;
        this.title = title;
        this.release_date = releaseDate;
        this.overview = plotSynopsis;
        this.vote_average = averageRating;
        poster_path = imageURL;
        this.favourite = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public String getPlotSynopsis() {
        return overview;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public String getPosterURL() {
        return NetworkUtils.buildMoviePosterURL(poster_path);
    }

    public String getPosterPath() {
        return poster_path;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean status) {
        favourite = status;
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in.readInt(), in.readString(), in.readString(), in.readString(), in.readDouble(),
                    in.readString());
        }

        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeDouble(vote_average);
        dest.writeString(poster_path);
    }
}
