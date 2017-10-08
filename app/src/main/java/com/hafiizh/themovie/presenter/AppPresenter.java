package com.hafiizh.themovie.presenter;

import com.hafiizh.themovie.model.companies.CompaniesResponse;
import com.hafiizh.themovie.model.detailmovies.DetailMoviesSource;
import com.hafiizh.themovie.model.genre.GenreResponse;
import com.hafiizh.themovie.model.latest.LatestReponse;
import com.hafiizh.themovie.model.nowplaying.NowPlayingResponse;
import com.hafiizh.themovie.model.popular.PopularResponse;
import com.hafiizh.themovie.model.similar.SimilarResponse;
import com.hafiizh.themovie.model.toprated.TopRatedResponse;
import com.hafiizh.themovie.model.upcoming.UpcomingResponse;
import com.hafiizh.themovie.networking.AppService;
import com.hafiizh.themovie.utilities.Constants;
import com.hafiizh.themovie.view.fragment.FCompanies;
import com.hafiizh.themovie.view.fragment.FDetailMovies;
import com.hafiizh.themovie.view.fragment.FGenres;
import com.hafiizh.themovie.view.fragment.FSimilar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by HAFIIZH on 9/2/2017.
 */

public class AppPresenter {
    private AppService appService;
    private ViewMovies.Home home;
    private ViewMovies.Company company;
    private ViewMovies.DetailMovies detailMovies;
    private ViewMovies.Genre genre;
    private ViewMovies.NowPlaying nowPlaying;
    private ViewMovies.Popular popular;
    private ViewMovies.Similar similar;
    private ViewMovies.TopRated topRated;
    private ViewMovies.Upcoming upcoming;

    private Constants.CLASS enumClass;

    public AppPresenter(AppService appService, ViewMovies.Home home) {
        this.appService = appService;
        this.home = home;
    }

    public AppPresenter(AppService appService, ViewMovies.Company company) {
        this.appService = appService;
        this.company = company;
    }

    public AppPresenter(AppService appService, ViewMovies.DetailMovies detailMovies) {
        this.appService = appService;
        this.detailMovies = detailMovies;
    }

    public AppPresenter(AppService appService, ViewMovies.Genre genre) {
        this.appService = appService;
        this.genre = genre;
    }

    public AppPresenter(AppService appService, ViewMovies.NowPlaying nowPlaying) {
        this.appService = appService;
        this.nowPlaying = nowPlaying;
    }

    public AppPresenter(AppService appService, ViewMovies.Popular popular) {
        this.appService = appService;
        this.popular = popular;
    }

    public AppPresenter(AppService appService, ViewMovies.Similar similar) {
        this.appService = appService;
        this.similar = similar;
    }

    public AppPresenter(AppService appService, ViewMovies.TopRated topRated) {
        this.appService = appService;
        this.topRated = topRated;
    }

    public AppPresenter(AppService appService, ViewMovies.Upcoming upcoming) {
        this.appService = appService;
        this.upcoming = upcoming;
    }

    public Constants.CLASS getEnumClass() {
        return enumClass;
    }

    public void setEnumClass(Constants.CLASS enumClass) {
        this.enumClass = enumClass;
    }

    public void getNowPlaying() {
        switch (getEnumClass()) {
            case Home:
                home.onCompleteNowPlaying();
                appService.getNowPlayingResponse(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE_NOW_PLAYING)
                        .enqueue(new Callback<NowPlayingResponse>() {
                            @Override
                            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                                if (response.isSuccessful()) {
                                    home.showNowPlayingSource(response.body().getResults());
                                    home.minimumNowPlaying(response.body().getDates().getMinimum());
                                    home.maximumNowPlaying(response.body().getDates().getMaximum());
                                    Timber.i("Got sources from API.");
                                    home.onCompleteNowPlaying();
                                }
                            }

                            @Override
                            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                                home.onFailedNowPlaying();
                                home.showErrorMessage(t.getMessage());
                                Timber.e("Error could not get data from API.");
                            }
                        });
                break;
            case NowPlaying:
                nowPlaying.onProgress();
                appService.getNowPlayingResponse(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE_NOW_PLAYING)
                        .enqueue(new Callback<NowPlayingResponse>() {
                            @Override
                            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                                if (response.isSuccessful()) {
                                    nowPlaying.showNowPlayingSource(response.body().getResults());
                                    nowPlaying.totalPage(response.body().getTotal_pages());
                                    nowPlaying.minimum(response.body().getDates().getMinimum());
                                    nowPlaying.maximum(response.body().getDates().getMaximum());
                                    Timber.i("Got sources from API.");
                                    nowPlaying.onComplete();
                                }
                            }

                            @Override
                            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                                nowPlaying.onFailed();
                                nowPlaying.showErrorMessage(t.getMessage());
                                Timber.e("Error could not get data from API.");
                            }
                        });
                break;
        }
    }

    public void getPopular() {
        switch (getEnumClass()) {
            case Home:
                home.onProgressPopular();
                appService.getPopulerResponse(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE_POPULAR)
                        .enqueue(new Callback<PopularResponse>() {
                            @Override
                            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                                if (response.isSuccessful()) {
                                    home.showPopularSource(response.body().getResults());
                                    home.onCompletePopular();
                                }
                            }

                            @Override
                            public void onFailure(Call<PopularResponse> call, Throwable t) {
                                home.onFailedPopular();
                                home.showErrorMessage(t.getMessage());
                            }
                        });
                break;
            case Popular:
                popular.onProgress();
                appService.getPopulerResponse(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE_POPULAR)
                        .enqueue(new Callback<PopularResponse>() {
                            @Override
                            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                                if (response.isSuccessful()) {
                                    popular.showPopularSource(response.body().getResults());
                                    popular.totalPage(response.body().getTotal_pages());
                                    popular.onComplete();
                                }
                            }

                            @Override
                            public void onFailure(Call<PopularResponse> call, Throwable t) {
                                popular.onFailed();
                                popular.showErrorMessage(t.getMessage());
                            }
                        });
                break;
        }
    }

    public void getDetailMovie() {
        detailMovies.onProgress();
        appService.getDetailMovies(FDetailMovies.GET_MOVIES_ID, Constants.API_KEY, Constants.LANGUAGE)
                .enqueue(new Callback<DetailMoviesSource>() {
                    @Override
                    public void onResponse(Call<DetailMoviesSource> call, Response<DetailMoviesSource> response) {
                        if (response.isSuccessful()) {
                            detailMovies.showDetailMoviesResponse(response.body());
                            detailMovies.onComplete();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailMoviesSource> call, Throwable t) {
                        detailMovies.onFailed();
                        detailMovies.showErrorMessage(t.getMessage());
                    }
                });
    }

    public void getSimilar() {
        switch (getEnumClass()) {
            case DetailMovies:
                detailMovies.onProgress();
                appService.getSimilarResponse(FDetailMovies.GET_MOVIES_ID, Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE_SIMILAR)
                        .enqueue(new Callback<SimilarResponse>() {
                            @Override
                            public void onResponse(Call<SimilarResponse> call, Response<SimilarResponse> response) {
                                if (response.isSuccessful()) {
                                    detailMovies.showSimilarSource(response.body().getResults());
                                    detailMovies.totalPage(response.body().getTotal_pages());
                                    detailMovies.onComplete();
                                }
                            }

                            @Override
                            public void onFailure(Call<SimilarResponse> call, Throwable t) {
                                detailMovies.showErrorMessage(t.getMessage());
                                detailMovies.onFailed();
                            }
                        });
                break;
            case Similar:
                similar.onProgress();
                appService.getSimilarResponse(FSimilar.GET_MOVIES_ID, Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE_SIMILAR)
                        .enqueue(new Callback<SimilarResponse>() {
                            @Override
                            public void onResponse(Call<SimilarResponse> call, Response<SimilarResponse> response) {
                                if (response.isSuccessful()) {
                                    similar.showSimilarSource(response.body().getResults());
                                    similar.totalPage(response.body().getTotal_pages());
                                    similar.onComplete();
                                }
                            }

                            @Override
                            public void onFailure(Call<SimilarResponse> call, Throwable t) {
                                similar.showErrorMessage(t.getMessage());
                                similar.onFailed();
                            }
                        });
        }
    }

    public void getCompanies() {
        company.onProgress();
        appService.getCompaniesResponse(FCompanies.GET_COMPANIES_ID, Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE_COMPANIES)
                .enqueue(new Callback<CompaniesResponse>() {
                    @Override
                    public void onResponse(Call<CompaniesResponse> call, Response<CompaniesResponse> response) {
                        if (response.isSuccessful()) {
                            company.showCompaniesSource(response.body().getResults());
                            company.totalPage(response.body().getTotal_pages());
                            company.onComplete();
                        }
                    }

                    @Override
                    public void onFailure(Call<CompaniesResponse> call, Throwable t) {
                        company.showErrorMessage(t.getMessage());
                        company.onFailed();
                    }
                });
    }

    public void getGenres() {
        genre.onProgress();
        appService.getGenreResponse(FGenres.GET_ID_GENRES, Constants.API_KEY, Constants.LANGUAGE, false, "created_at.asc", Constants.PAGE_GENRES)
                .enqueue(new Callback<GenreResponse>() {
                    @Override
                    public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                        if (response.isSuccessful()) {
                            genre.showGenresSource(response.body().getResults());
                            genre.totalPage(response.body().getTotal_pages());
                            genre.onComplete();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenreResponse> call, Throwable t) {
                        genre.onFailed();
                        genre.showErrorMessage(t.getMessage());
                    }
                });
    }

    public void getLatest() {
        home.onProgressLatest();
        appService.getLatestResponse(Constants.API_KEY, Constants.LANGUAGE).enqueue(new Callback<LatestReponse>() {
            @Override
            public void onResponse(Call<LatestReponse> call, Response<LatestReponse> response) {
                if (response.isSuccessful()) {
                    home.showLatestResponse(response.body());
                    home.onCompleteLatest();
                }
            }

            @Override
            public void onFailure(Call<LatestReponse> call, Throwable t) {
                home.onFailedLatest();
                home.showErrorMessage(t.getMessage());
            }
        });
    }

    public void getTopRated() {
        switch (getEnumClass()) {
            case Home:
                home.onProgressTopRated();
                appService.getTopRatedResponse(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE_TOP_RATED).enqueue(new Callback<TopRatedResponse>() {
                    @Override
                    public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
                        if (response.isSuccessful()) {
                            home.showTopRatedSource(response.body().getResults());
                            home.onCompleteTopRated();
                        }
                    }

                    @Override
                    public void onFailure(Call<TopRatedResponse> call, Throwable t) {
                        home.onFailedTopRated();
                        home.showErrorMessage(t.getMessage());
                    }
                });
                break;
            case TopRated:
                topRated.onProgress();
                appService.getTopRatedResponse(Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE_TOP_RATED).enqueue(new Callback<TopRatedResponse>() {
                    @Override
                    public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
                        if (response.isSuccessful()) {
                            topRated.showTopRatedSource(response.body().getResults());
                            topRated.totalPage(response.body().getTotal_pages());
                            topRated.onComplete();
                        }
                    }

                    @Override
                    public void onFailure(Call<TopRatedResponse> call, Throwable t) {
                        topRated.onFailed();
                        topRated.showErrorMessage(t.getMessage());
                    }
                });
        }
    }
    public void getUpcoming(){
        switch (getEnumClass()){
            case Home:
                home.onProgressUpcoming();
                appService.getUpcomingResponse(Constants.API_KEY,Constants.LANGUAGE,Constants.PAGE_UPCOMING).enqueue(new Callback<UpcomingResponse>() {
                    @Override
                    public void onResponse(Call<UpcomingResponse> call, Response<UpcomingResponse> response) {
                        if (response.isSuccessful()){
                            home.showUpcomingSource(response.body().getResults());
                            home.onCompleteUpcoming();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpcomingResponse> call, Throwable t) {
                        home.onFailedUpcoming();
                    }
                });
        }
    }
}