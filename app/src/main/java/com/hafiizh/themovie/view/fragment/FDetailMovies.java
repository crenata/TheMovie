package com.hafiizh.themovie.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hafiizh.themovie.R;
import com.hafiizh.themovie.adapter.*;
import com.hafiizh.themovie.adapter.ASimilar;
import com.hafiizh.themovie.model.detailmovies.DetailMoviesSource;
import com.hafiizh.themovie.model.detailmovies.Genre;
import com.hafiizh.themovie.model.similar.SimilarSource;
import com.hafiizh.themovie.networking.Injector;
import com.hafiizh.themovie.presenter.AppPresenter;
import com.hafiizh.themovie.presenter.ViewMovies;
import com.hafiizh.themovie.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FDetailMovies extends Fragment implements ViewMovies.DetailMovies {
    TextView tv_title_detail, tv_release_date_detail, tv_vote_average_detail, tv_tagline_detail,
            tv_original_title_detail, tv_status_movies_detail, tv_vote_count_detail, tv_overview_detail,
            tv_homepage_detail, tv_budget_detail, tv_revenue_detail, tv_runtime_detail;
    ImageView iv_backdrop_path_detail, iv_poster_path_detail;
    RecyclerView rv_genres_detail, rv_production_companies_detail, rv_production_countries_detail, rv_similar;
    RelativeLayout layout_similar;

    AppPresenter appPresenter;

    AGenres aGenres;
    AProductionCompanies companiesAdapter;
    AProductionCountries countriesAdapter;

    ASimilar aSimilar;

    public static int GET_MOVIES_ID;

    public FDetailMovies() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_movies, container, false);
        getActivity().setTitle(getArguments().getString("titlebar"));
        GET_MOVIES_ID = getArguments().getInt("id");
        Constants.reset();
        configViews(v);

        configRV();
        initializeAdapter();

        appPresenter = new AppPresenter(Injector.appMovies(), this);
        appPresenter.setEnumClass(Constants.CLASS.DetailMovies);
        appPresenter.getDetailMovie();
        appPresenter.getSimilar();

        setAdapterRV();

        layout_similar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FSimilar similar = new FSimilar();
                Bundle bundle = new Bundle();
                bundle.putInt("id", GET_MOVIES_ID);
                similar.setArguments(bundle);
                replaceFragment(similar);
            }
        });

        return v;
    }

    private void setAdapterRV() {
        rv_genres_detail.setAdapter(aGenres);
        rv_production_companies_detail.setAdapter(companiesAdapter);
        rv_production_countries_detail.setAdapter(countriesAdapter);
        rv_similar.setAdapter(aSimilar);
    }

    private void initializeAdapter() {
        aGenres = new AGenres(genresListener);
        companiesAdapter = new AProductionCompanies(companiesListener);
        countriesAdapter = new AProductionCountries(countriesListener);
        aSimilar = new ASimilar(similarListener);
    }

    public AGenres.GenresListener genresListener = new AGenres.GenresListener() {
        @Override
        public void onSourceClick(int id) {
            FGenres FGenres = new FGenres();
            Bundle bundle = new Bundle();
            bundle.putInt("id", id);
            FGenres.setArguments(bundle);
            replaceFragment(FGenres);
        }
    };
    public AProductionCompanies.CompaniesListener companiesListener =
            new AProductionCompanies.CompaniesListener() {
                @Override
                public void onSourceClick(int id) {
                    FCompanies FCompanies = new FCompanies();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    FCompanies.setArguments(bundle);
                    replaceFragment(FCompanies);
                }
            };
    public AProductionCountries.CountriesListener countriesListener =
            new AProductionCountries.CountriesListener() {
                @Override
                public void onSourceClick(String s) {

                }
            };
    public ASimilar.SimilarListener similarListener = new ASimilar.SimilarListener() {
        @Override
        public void onSourceClick(int id) {
            Bundle bundle = new Bundle();
            bundle.putString("titlebar", "Now Playing");
            bundle.putInt("id", id);
            FDetailMovies FDetailMovies = new FDetailMovies();
            FDetailMovies.setArguments(bundle);
            replaceFragment(FDetailMovies);
        }
    };

    private void replaceFragment(Fragment fragment) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    private void configRV() {
        rv_genres_detail.setHasFixedSize(true);
        rv_genres_detail.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        rv_genres_detail.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rv_production_companies_detail.setHasFixedSize(true);
        rv_production_companies_detail.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        rv_production_companies_detail.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rv_production_countries_detail.setHasFixedSize(true);
        rv_production_countries_detail.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        rv_production_countries_detail.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rv_similar.setHasFixedSize(true);
        rv_similar.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        rv_similar.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void configViews(View v) {
        layout_similar = (RelativeLayout) v.findViewById(R.id.layout_similar);

        tv_title_detail = (TextView) v.findViewById(R.id.tv_title_detail);
        tv_release_date_detail = (TextView) v.findViewById(R.id.tv_release_date_detail);
        tv_vote_average_detail = (TextView) v.findViewById(R.id.tv_vote_average_detail);
        tv_tagline_detail = (TextView) v.findViewById(R.id.tv_tagline_detail);
        tv_original_title_detail = (TextView) v.findViewById(R.id.tv_original_title_detail);
        tv_status_movies_detail = (TextView) v.findViewById(R.id.tv_status_movies_detail);
        tv_vote_count_detail = (TextView) v.findViewById(R.id.tv_vote_count_detail);
        tv_overview_detail = (TextView) v.findViewById(R.id.tv_overview_detail);
        tv_homepage_detail = (TextView) v.findViewById(R.id.tv_homepage_detail);
        tv_budget_detail = (TextView) v.findViewById(R.id.tv_budget_detail);
        tv_revenue_detail = (TextView) v.findViewById(R.id.tv_revenue_detail);
        tv_runtime_detail = (TextView) v.findViewById(R.id.tv_runtime_detail);

        iv_backdrop_path_detail = (ImageView) v.findViewById(R.id.iv_backdrop_path_detail);
        iv_poster_path_detail = (ImageView) v.findViewById(R.id.iv_poster_path_detail);

        rv_genres_detail = (RecyclerView) v.findViewById(R.id.rv_genres_detail);
        rv_production_companies_detail = (RecyclerView) v.findViewById(R.id.rv_production_companies_detail);
        rv_production_countries_detail = (RecyclerView) v.findViewById(R.id.rv_production_countries_detail);
        rv_similar = (RecyclerView) v.findViewById(R.id.rv_similar);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgress() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showDetailMoviesResponse(DetailMoviesSource source) {
        tv_title_detail.setText(source.getTitle());
        tv_release_date_detail.setText(source.getRelease_date());
        tv_vote_average_detail.setText(source.getVote_average());
        tv_tagline_detail.setText(source.getTagline());
        tv_original_title_detail.setText(": " + source.getOriginal_title());
        tv_status_movies_detail.setText(": " + source.getStatus());
        tv_vote_count_detail.setText(": " + source.getVote_count());
        tv_overview_detail.setText(": " + source.getOverview());
        tv_homepage_detail.setText(": " + source.getHomepage());
        tv_budget_detail.setText(": " + source.getBudget());
        tv_revenue_detail.setText(": " + source.getRevenue());
        tv_runtime_detail.setText(": " + source.getRuntime());

        Picasso.with(getActivity())
                .load(Constants.IMAGE.W500 + source.getBackdrop_path())
                .error(R.drawable.error)
                .into(iv_backdrop_path_detail);
        Picasso.with(getActivity())
                .load(Constants.IMAGE.W154 + source.getPoster_path())
                .error(R.drawable.error)
                .into(iv_poster_path_detail);

        aGenres.clear();
        for (Genre genre : source.getGenres()) {
            aGenres.addGenres(genre);
        }
        aGenres.notifyDataSetChanged();

        companiesAdapter.clear();
        for (com.hafiizh.themovie.model.detailmovies.ProductionCompanies companies : source.getProduction_companies()) {
            companiesAdapter.addCompanies(companies);
        }
        companiesAdapter.notifyDataSetChanged();

        countriesAdapter.clear();
        for (com.hafiizh.themovie.model.detailmovies.ProductionCountries countries : source.getProduction_countries()) {
            countriesAdapter.addCountries(countries);
        }
        countriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSimilarSource(List<SimilarSource> similarSources) {
        aSimilar.clear();
        for (SimilarSource source : similarSources) {
            aSimilar.addSimilar(source);
        }
        aSimilar.notifyDataSetChanged();
    }

    @Override
    public void totalPage(String total_pages) {

    }
}