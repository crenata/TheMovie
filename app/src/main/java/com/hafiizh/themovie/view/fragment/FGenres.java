package com.hafiizh.themovie.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.hafiizh.themovie.R;
import com.hafiizh.themovie.adapter.fragment.AFragmentGenres;
import com.hafiizh.themovie.model.genre.GenreSource;
import com.hafiizh.themovie.networking.Injector;
import com.hafiizh.themovie.presenter.AppPresenter;
import com.hafiizh.themovie.presenter.ViewMovies;
import com.hafiizh.themovie.utilities.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FGenres extends Fragment implements ViewMovies.Genre {
    SwipeRefreshLayout refresh_genres;
    RecyclerView rv_fragment_genres;
    GridLayoutManager gridLayoutManager;

    AFragmentGenres genresAdapter;
    AppPresenter appPresenter;

    public static int GET_ID_GENRES;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    boolean onScroll;
    String total_page;

    public FGenres() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_genres, container, false);
        getActivity().setTitle("Genres");
        GET_ID_GENRES = getArguments().getInt("id");
        Constants.reset();
        configViews(v);

        genresAdapter = new AFragmentGenres(fragmentGenresListener);
        appPresenter = new AppPresenter(Injector.appGenres(), this);
        appPresenter.getGenres();
        rv_fragment_genres.setAdapter(genresAdapter);

        assignUIEvents();

        return v;
    }

    private void assignUIEvents() {
        refresh_genres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                appPresenter.getGenres();
            }
        });
        rv_fragment_genres.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    onScroll = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = gridLayoutManager.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();
                if (onScroll && (visibleItemCount + pastVisibleItems) == totalItemCount) {
                    onScroll = false;
                    if (Constants.PAGE_GENRES <= Integer.parseInt(total_page)) {
                        Constants.PAGE_GENRES += 1;
                        appPresenter.getGenres();
                    }
                }
            }
        });
    }

    public AFragmentGenres.FragmentGenresListener fragmentGenresListener =
            new AFragmentGenres.FragmentGenresListener() {
                @Override
                public void onClickSource(int id) {

                }
            };

    private void configViews(View v) {
        refresh_genres = (SwipeRefreshLayout) v.findViewById(R.id.refresh_genres);
        refresh_genres.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.darker_gray
        );

        rv_fragment_genres = (RecyclerView) v.findViewById(R.id.rv_fragment_genres);
        rv_fragment_genres.setHasFixedSize(true);
        rv_fragment_genres.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv_fragment_genres.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgress() {
        refresh_genres.setRefreshing(true);
    }

    @Override
    public void onFailed() {
        refresh_genres.setRefreshing(false);
    }

    @Override
    public void onComplete() {
        refresh_genres.setRefreshing(false);
    }

    @Override
    public void showGenresSource(List<GenreSource> genreSources) {
        if (Constants.PAGE_GENRES == 1) {
            genresAdapter.clear();
            for (GenreSource source : genreSources) {
                genresAdapter.addGenreMovies(source);
            }
            genresAdapter.notifyDataSetChanged();
        } else {
            for (GenreSource source : genreSources) {
                genresAdapter.addGenreMovies(source);
            }
            genresAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void totalPage(String total_pages) {
        total_page = total_pages;
    }
}