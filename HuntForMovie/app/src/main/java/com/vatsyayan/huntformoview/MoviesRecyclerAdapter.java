package com.vatsyayan.huntformoview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vatsyayan.huntformovie.R;

import java.util.List;


public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MoviesViewHolder> {

    private Context mContext;
    private List<Movie> mMoviesList;

    public MoviesRecyclerAdapter(Context pContext, List<Movie> pMoviesList) {
        mContext = pContext;
        mMoviesList = pMoviesList;
    }


    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_recycler_item, parent, false);
        MoviesViewHolder moviesViewHolder = new MoviesViewHolder(view);
        return moviesViewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder moviesViewHolder, int position) {
        moviesViewHolder.tvMovieTitle.setText(mMoviesList.get(position).getMovieTitle());
        moviesViewHolder.tvLanguage.setText(mMoviesList.get(position).getLanguage());
        moviesViewHolder.tvMovieReleasingDate.setText(mMoviesList.get(position).getReleasingDate());
        Glide.with(mContext).load(mMoviesList.get(position).getImageURL())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(moviesViewHolder.ivMoviePoster);
        moviesViewHolder.onBind(mMoviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        TextView tvMovieTitle, tvMovieReleasingDate, tvLanguage;
        ImageView ivMoviePoster;
        CardView cvMovie;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            tvMovieTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvMovieReleasingDate = (TextView) itemView.findViewById(R.id.tv_releasing_date);
            tvLanguage = (TextView) itemView.findViewById(R.id.tv_language);
            ivMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            cvMovie = (CardView) itemView.findViewById(R.id.cardlist_item);
        }

        public void onBind(final Movie movie) {
            cvMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,MovieDetailsActivity.class);
                    intent.putExtra("movie",movie);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
