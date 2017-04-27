package com.vatsyayan.huntformoview;

import java.io.Serializable;


public class Movie implements Serializable {
    private String movieTitle;
    private String releasingDate;
    private String language;
    private String overview;
    private String popularity;
    private String imageURL;

    public Movie(String movieTitle, String releasingDate, String overview, String popularity) {
        this.movieTitle = movieTitle;
        this.releasingDate = releasingDate;
        this.language = language;
        this.overview = overview;
        this.popularity = popularity;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getReleasingDate() {
        return releasingDate;
    }

    public void setReleasingDate(String releasingDate) {
        this.releasingDate = releasingDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }
}
