package com.hafiizh.themovie.networking;

import com.hafiizh.themovie.BuildConfig;
import com.hafiizh.themovie.utilities.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by HAFIIZH on 9/2/2017.
 */

public class Injector {
    private static Retrofit provideRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .build();
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.d(message);
            }
        });
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return httpLoggingInterceptor;
    }

    public static AppService appMovies() {
        return provideRetrofit(Constants.HTTP.MOVIE_URL).create(AppService.class);
    }

    public static AppService appCompanies() {
        return provideRetrofit(Constants.HTTP.COMPANY_URL).create(AppService.class);
    }

    public static AppService appGenres() {
        return provideRetrofit(Constants.HTTP.GENRE_URL).create(AppService.class);
    }
}