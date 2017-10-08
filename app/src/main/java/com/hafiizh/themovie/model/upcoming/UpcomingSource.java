package com.hafiizh.themovie.model.upcoming;

/**
 * Created by HAFIIZH on 9/12/2017.
 */

public class UpcomingSource {
    private String vote_count;
    private String id;
    private String video;
    private String vote_average;
    private String title;
    private String popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String[] genre_ids;
    private String backdrop_path;
    private String adult;
    private String overview;
    private String release_date;

    public String getVote_count() {
        return vote_count;
    }

    public String getId() {
        return id;
    }

    public String getVideo() {
        return video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String[] getGenre_ids() {
        return genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }
}
