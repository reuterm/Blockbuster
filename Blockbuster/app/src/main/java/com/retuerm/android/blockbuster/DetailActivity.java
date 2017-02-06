package com.retuerm.android.blockbuster;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.retuerm.android.blockbuster.Utility.MovieItem;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private ImageView mPosterDisplay;
    private TextView mTitleDisplay;
    private TextView mReleaseDateDisplay;
    private TextView mPlotDisplay;
    private TextView mAverageRatingDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPosterDisplay = (ImageView) findViewById(R.id.iv_movie_poster_detail);
        mTitleDisplay = (TextView) findViewById(R.id.tv_movie_title);
        mReleaseDateDisplay = (TextView) findViewById(R.id.tv_movie_release_date);
        mPlotDisplay = (TextView) findViewById(R.id.tv_movie_plot);
        mAverageRatingDisplay = (TextView) findViewById(R.id.tv_average_rating);

        Intent intent = getIntent();

        if(intent.hasExtra(MainActivity.PASS)) {
            MovieItem passedMovie = intent.getParcelableExtra(MainActivity.PASS);
            Uri posterUri = Uri.parse(passedMovie.getImageURL().toString());

            // Nicely format release date string
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String release;
            try {
                Date releaseDate = format.parse(passedMovie.getReleaseDate());
                release = df.format(releaseDate);
            } catch (ParseException e) {
                e.printStackTrace();
                release = passedMovie.getReleaseDate();
            }


            Picasso.with(this).load(posterUri).into(mPosterDisplay);
            mTitleDisplay.setText(passedMovie.getTitle());
            mReleaseDateDisplay.setText(release);
            mPlotDisplay.setText(passedMovie.getPlotSynopsis());
            mAverageRatingDisplay.setText(Double.toString(passedMovie.getAverageRating()));
        }


    }
}
