package com.hafiizh.themovie.presenter;

import com.hafiizh.themovie.model.companies.CompaniesSource;
import com.hafiizh.themovie.model.detailmovies.DetailMoviesSource;
import com.hafiizh.themovie.model.genre.GenreSource;
import com.hafiizh.themovie.model.latest.LatestReponse;
import com.hafiizh.themovie.model.nowplaying.NowPlayingSource;
import com.hafiizh.themovie.model.popular.PopularSource;
import com.hafiizh.themovie.model.similar.SimilarSource;
import com.hafiizh.themovie.model.toprated.TopRatedSource;
import com.hafiizh.themovie.model.upcoming.UpcomingSource;

import java.util.List;

/**
 * Created by HAFIIZH on 9/2/2017.
 */

public class ViewMovies {
    public interface Home {
        void showNowPlayingSource(List<NowPlayingSource> nowPlayingSources);

        void showPopularSource(List<PopularSource> popularSources);

        void showTopRatedSource(List<TopRatedSource> topRatedSources);

        void showUpcomingSource(List<UpcomingSource> upcomingSources);

        void showLatestResponse(LatestReponse latestReponse);

        void showErrorMessage(String message);

        void onProgressNowPlaying();

        void onProgressPopular();

        void onProgressTopRated();

        void onProgressUpcoming();

        void onFailedNowPlaying();

        void onFailedPopular();

        void onFailedTopRated();

        void onFailedUpcoming();

        void onCompleteNowPlaying();

        void onCompletePopular();

        void onCompleteTopRated();

        void onCompleteUpcoming();

        void onProgressLatest();

        void onCompleteLatest();

        void onFailedLatest();

        void minimumNowPlaying(String minimum);

        void maximumNowPlaying(String maximum);

        void minimumUpcoming(String minimum);

        void maximumUpcoming(String maximum);
    }

    public interface NowPlaying {
        void onProgress();

        void onFailed();

        void onComplete();

        void showNowPlayingSource(List<NowPlayingSource> nowPlayingSources);

        void totalPage(String total_pages);

        void showErrorMessage(String message);

        void minimum(String minimum);

        void maximum(String maximum);
    }

    public interface Popular {
        void onProgress();

        void onFailed();

        void onComplete();

        void showPopularSource(List<PopularSource> popularSources);

        void totalPage(String total_pages);

        void showErrorMessage(String message);
    }

    public interface Genre {
        void onProgress();

        void onFailed();

        void onComplete();

        void showGenresSource(List<GenreSource> genreSources);

        void totalPage(String total_pages);

        void showErrorMessage(String message);
    }

    public interface Company {
        void onProgress();

        void onFailed();

        void onComplete();

        void showCompaniesSource(List<CompaniesSource> companiesSources);

        void totalPage(String total_pages);

        void showErrorMessage(String message);
    }

    public interface DetailMovies {
        void showErrorMessage(String message);

        void onProgress();

        void onFailed();

        void onComplete();

        void showDetailMoviesResponse(DetailMoviesSource detailMoviesSource);

        void showSimilarSource(List<SimilarSource> similarSources);

        void totalPage(String total_pages);
    }

    public interface Similar {
        void showErrorMessage(String message);

        void onProgress();

        void onFailed();

        void onComplete();

        void showSimilarSource(List<SimilarSource> similarSources);

        void totalPage(String total_pages);
    }

    public interface TopRated {
        void onProgress();

        void onFailed();

        void onComplete();

        void showErrorMessage(String message);

        void showTopRatedSource(List<TopRatedSource> topRatedSources);

        void totalPage(String total_pages);
    }

    public interface Upcoming {
        void onProgress();

        void onFailed();

        void onComplete();

        void showErrorMessage(String message);

        void showUpcomingSource(List<UpcomingSource> upcomingSources);

        void totalPage(String total_pages);
    }
}