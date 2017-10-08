package com.hafiizh.themovie.utilities;

/**
 * Created by HAFIIZH on 9/2/2017.
 */

public class Constants {
    public static final String API_KEY = "af9da61b71d8b1352c261a27d5ca212d";
    public static final String LANGUAGE = "en-US";
    public static int PAGE_NOW_PLAYING = 1;
    public static int PAGE_POPULAR = 1;
    public static int PAGE_SIMILAR = 1;
    public static int PAGE_COMPANIES = 1;
    public static int PAGE_GENRES = 1;
    public static int PAGE_TOP_RATED = 1;
    public static int PAGE_UPCOMING = 1;

    public static final class HTTP {
        public static final String BASE_URL = "https://api.themoviedb.org/3/";
        public static final String MOVIE_URL = BASE_URL + "movie/";
        public static final String GENRE_URL = BASE_URL + "genre/";
        public static final String COMPANY_URL = BASE_URL + "company/";
        public static final String IMAGE_URL = "https://image.tmdb.org/t/p/";
    }

    public static final class IMAGE {
        public static final String W45 = HTTP.IMAGE_URL + "w45";
        public static final String W92 = HTTP.IMAGE_URL + "w92";
        public static final String W154 = HTTP.IMAGE_URL + "w154";
        public static final String W185 = HTTP.IMAGE_URL + "w185";
        public static final String W300 = HTTP.IMAGE_URL + "w300";
        public static final String W500 = HTTP.IMAGE_URL + "w500";
        public static final String W780 = HTTP.IMAGE_URL + "w780";
        public static final String W1280 = HTTP.IMAGE_URL + "w1280";
        public static final String H632 = HTTP.IMAGE_URL + "h632";
    }

    public static final class MOVIE {
        public static final String MOVIE_DETAIL = "{id}?";
        public static final String ID = "{id}/";
        public static final String ALTERNATIF_TITLE = "alternative_titles?api_key=" + API_KEY + "&language=en-US";
        public static final String VIDEO = "videos?api_key=" + API_KEY + "&language=en-US";
        public static final String LATEST_MOVIE = "latest?";
        public static final String NOW_PLAYING_MOVIE = "now_playing?";
        public static final String POPULAR_MOVIE = "popular?";
        public static final String TOP_RATED_MOVIE = "top_rated?";
        public static final String UPCOMING_MOVIE = "upcoming?";
        public static final String SIMILAR_MOVIE = "similar?";
    }

    public static final class GENRE {
        public static final String MOVIE_LIST = HTTP.GENRE_URL + "movie/list?api_key=" + API_KEY + "&language=en-US";
        public static final String TV_LIST = HTTP.GENRE_URL + "tv/list?api_key=" + API_KEY + "&language=en-US";
        public static final String MOVIES = "movies?";
    }

    public static final class COMPANY {
        public static final String COMPANY = "movies?";
    }

    public static final class QUERY {
        public static final String ID = "id";
        public static final String API_KEY_QUERY = "api_key";
        public static final String LANGUAGE_QUERY = "language";
        public static final String PAGE_QUERY = "page";
        public static final String SORT_BY = "sort_by";
        public static final String INCLUCE_ADULT = "include_adult";
    }

    public enum CLASS {
        Home, NowPlaying, Popular, DetailMovies, Similar, TopRated
    }

    public static void reset() {
        PAGE_COMPANIES = 1;
        PAGE_POPULAR = 1;
        PAGE_NOW_PLAYING = 1;
        PAGE_GENRES = 1;
        PAGE_SIMILAR = 1;
        PAGE_TOP_RATED = 1;
        PAGE_UPCOMING = 1;
    }
}
