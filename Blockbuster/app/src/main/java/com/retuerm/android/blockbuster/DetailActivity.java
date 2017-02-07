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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_movie_poster_detail) ImageView mPosterDisplay;
    @BindView(R.id.tv_movie_title) TextView mTitleDisplay;
    @BindView(R.id.tv_movie_release_date) TextView mReleaseDateDisplay;
    @BindView(R.id.tv_movie_plot) TextView mPlotDisplay;
    @BindView(R.id.tv_average_rating) TextView mAverageRatingDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

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
