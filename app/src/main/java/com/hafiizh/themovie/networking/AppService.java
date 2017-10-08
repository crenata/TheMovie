package com.hafiizh.themovie.networking;

import com.hafiizh.themovie.model.companies.CompaniesResponse;
import com.hafiizh.themovie.model.detailmovies.DetailMoviesSource;
import com.hafiizh.themovie.model.genre.GenreResponse;
import com.hafiizh.themovie.model.latest.LatestReponse;
import com.hafiizh.themovie.model.nowplaying.NowPlayingResponse;
import com.hafiizh.themovie.model.popular.PopularResponse;
import com.hafiizh.themovie.model.similar.SimilarResponse;
import com.hafiizh.themovie.model.toprated.TopRatedResponse;
import com.hafiizh.themovie.model.upcoming.UpcomingResponse;
import com.hafiizh.themovie.utilities.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HAFIIZH on 9/9/2017.
 */

public interface AppService {
    @GET(Constants.MOVIE.NOW_PLAYING_MOVIE)
    Call<NowPlayingResponse> getNowPlayingResponse(
            @Query(Constants.QUERY.API_KEY_QUERY) String api_key,
            @Query(Constants.QUERY.LANGUAGE_QUERY) String language,
            @Query(Constants.QUERY.PAGE_QUERY) int page
    );

    @GET(Constants.MOVIE.POPULAR_MOVIE)
    Call<PopularResponse> getPopulerResponse(
            @Query(Constants.QUERY.API_KEY_QUERY) String api_key,
            @Query(Constants.QUERY.LANGUAGE_QUERY) String language,
            @Query(Constants.QUERY.PAGE_QUERY) int page
    );

    @GET(Constants.MOVIE.MOVIE_DETAIL)
    Call<DetailMoviesSource> getDetailMovies(
            @Path(Constants.QUERY.ID) int id,
            @Query(Constants.QUERY.API_KEY_QUERY) String api_key,
            @Query(Constants.QUERY.LANGUAGE_QUERY) String language
    );

    @GET(Constants.MOVIE.ID + Constants.MOVIE.SIMILAR_MOVIE)
    Call<SimilarResponse> getSimilarResponse(
            @Path(Constants.QUERY.ID) int id,
            @Query(Constants.QUERY.API_KEY_QUERY) String api_key,
            @Query(Constants.QUERY.LANGUAGE_QUERY) String language,
            @Query(Constants.QUERY.PAGE_QUERY) int page
    );

    @GET(Constants.MOVIE.ID + Constants.COMPANY.COMPANY)
    Call<CompaniesResponse> getCompaniesResponse(
            @Path(Constants.QUERY.ID) int id,
            @Query(Constants.QUERY.API_KEY_QUERY) String api_key,
            @Query(Constants.QUERY.LANGUAGE_QUERY) String language,
            @Query(Constants.QUERY.PAGE_QUERY) int page
    );

    @GET(Constants.MOVIE.ID + Constants.GENRE.MOVIES)
    Call<GenreResponse> getGenreResponse(
            @Path(Constants.QUERY.ID) int id,
            @Query(Constants.QUERY.API_KEY_QUERY) String api_key,
            @Query(Constants.QUERY.LANGUAGE_QUERY) String language,
            @Query(Constants.QUERY.INCLUCE_ADULT) boolean adult,
            @Query(Constants.QUERY.SORT_BY) String sort_by,
            @Query(Constants.QUERY.PAGE_QUERY) int page
    );

    @GET(Constants.MOVIE.LATEST_MOVIE)
    Call<LatestReponse> getLatestResponse(
            @Query(Constants.QUERY.API_KEY_QUERY) String api_key,
            @Query(Constants.QUERY.LANGUAGE_QUERY) String language
    );

    @GET(Constants.MOVIE.TOP_RATED_MOVIE)
    Call<TopRatedResponse> getTopRatedResponse(
            @Query(Constants.QUERY.API_KEY_QUERY) String api_key,
            @Query(Constants.QUERY.LANGUAGE_QUERY) String language,
            @Query(Constants.QUERY.PAGE_QUERY) int page
    );

    @GET(Constants.MOVIE.UPCOMING_MOVIE)
    Call<UpcomingResponse> getUpcomingResponse(
            @Query(Constants.QUERY.API_KEY_QUERY) String api_key,
            @Query(Constants.QUERY.LANGUAGE_QUERY) String language,
            @Query(Constants.QUERY.PAGE_QUERY) int page
    );
}
