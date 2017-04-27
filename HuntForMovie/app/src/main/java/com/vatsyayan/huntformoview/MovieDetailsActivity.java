package com.vatsyayan.huntformoview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vatsyayan.huntformovie.R;

public class MovieDetailsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Context mContext;
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        mContext = this;
        mMovie = (Movie)getIntent().getSerializableExtra("movie");
        setUpToolbar();
        setUpUI();
    }

    private void setUpUI() {
        TextView movieTitle,moviewpopularity,moviereleasingDate,movieLanguage,movieOverview;
        ImageView moviePoster;
        movieTitle = (TextView)findViewById(R.id.tv_title);
        movieLanguage = (TextView)findViewById(R.id.tv_language);
        moviereleasingDate = (TextView)findViewById(R.id.tv_releasing_date);
        moviewpopularity = (TextView)findViewById(R.id.tv_popularity);
        movieOverview = (TextView)findViewById(R.id.tv_overview);
        moviePoster = (ImageView)findViewById(R.id.iv_movie_poster);
        movieTitle.setText(mMovie.getMovieTitle());
        movieLanguage.setText(mMovie.getLanguage());
        moviereleasingDate.setText(mMovie.getReleasingDate());
        moviewpopularity.setText(mMovie.getPopularity());
        movieOverview.setText(mMovie.getOverview());
        Glide.with(mContext).load(mMovie.getImageURL())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(moviePoster);
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(R.string.title_activity_movie_details);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setTitleTextColor(getResources().getColor(R.color.primary));
        }
    }
}
