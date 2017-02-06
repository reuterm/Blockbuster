package com.retuerm.android.blockbuster.Utility;

import android.os.Parcel;
import android.os.Parcelable;

import okhttp3.HttpUrl;

/**
 * Created by max on 05/02/2017.
 */

// Helper class which represents a single movie
public class MovieItem  implements Parcelable {
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

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in.readString(), in.readString(), in.readString(), in.readDouble(),
                    HttpUrl.parse(in.readString()));
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
        dest.writeString(releaseDate);
        dest.writeString(plotSynopsis);
        dest.writeDouble(averageRating);
        dest.writeString(imageURL.toString());
    }
}
