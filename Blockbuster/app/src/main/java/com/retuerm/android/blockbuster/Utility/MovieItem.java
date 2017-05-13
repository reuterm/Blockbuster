package com.retuerm.android.blockbuster.Utility;

import android.os.Parcel;
import android.os.Parcelable;

import okhttp3.HttpUrl;

import static com.retuerm.android.blockbuster.Utility.NetworkUtils.buildMoviePosterURL;

/**
 * Created by max on 05/02/2017.
 */

// Helper class that represents a single movie
public class MovieItem  implements Parcelable {
    private String title;
    private String release_date;
    private String overview;
    private double vote_average;
    private String poster_path;

    public MovieItem(String title, String releaseDate, String plotSynopsis, double averageRating,
                     String imageURL) {
        this.title = title;
        this.release_date = releaseDate;
        this.overview = plotSynopsis;
        this.vote_average = averageRating;
        poster_path = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getPosterURL() {
        return NetworkUtils.buildMoviePosterURL(poster_path);
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in.readString(), in.readString(), in.readString(), in.readDouble(),
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
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeDouble(vote_average);
        dest.writeString(poster_path);
    }
}
