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
import com.hafiizh.themovie.adapter.fragment.AFragmentTopRated;
import com.hafiizh.themovie.model.toprated.TopRatedSource;
import com.hafiizh.themovie.networking.Injector;
import com.hafiizh.themovie.presenter.AppPresenter;
import com.hafiizh.themovie.presenter.ViewMovies;
import com.hafiizh.themovie.utilities.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FTopRated extends Fragment implements ViewMovies.TopRated {
    SwipeRefreshLayout refresh_top_rated;
    RecyclerView rv_top_rated;
    GridLayoutManager gridLayoutManager;

    AppPresenter appPresenter;
    AFragmentTopRated adapter;

    int pastVisibleItems, visibleItemCount, totalItemCount;
    boolean onScroll;
    String total_page;

    public FTopRated() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_top_rated, container, false);
        getActivity().setTitle("Top Rated");
        Constants.reset();
        configViews(v);
        adapter = new AFragmentTopRated(listener);
        runPresenter();
        rv_top_rated.setAdapter(adapter);
        assignUIEvents();
        return v;
    }

    private void assignUIEvents() {
        refresh_top_rated.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                appPresenter.getTopRated();
            }
        });
        rv_top_rated.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (Constants.PAGE_TOP_RATED <= Integer.parseInt(total_page)) {
                        Constants.PAGE_TOP_RATED += 1;
                        appPresenter.getTopRated();
                    }
                }
            }
        });
    }

    private AFragmentTopRated.AFTListener listener = new AFragmentTopRated.AFTListener() {
        @Override
        public void onSourceClick(int id) {

        }
    };

    private void runPresenter() {
        appPresenter = new AppPresenter(Injector.appMovies(), this);
        appPresenter.setEnumClass(Constants.CLASS.TopRated);
        appPresenter.getTopRated();
    }

    private void configViews(View v) {
        refresh_top_rated = (SwipeRefreshLayout) v.findViewById(R.id.refresh_top_rated);
        refresh_top_rated.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.darker_gray
        );

        rv_top_rated = (RecyclerView) v.findViewById(R.id.rv_fragment_top_rated);
        rv_top_rated.setHasFixedSize(true);
        rv_top_rated.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv_top_rated.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onProgress() {
        refresh_top_rated.setRefreshing(true);
    }

    @Override
    public void onFailed() {
        refresh_top_rated.setRefreshing(false);
    }

    @Override
    public void onComplete() {
        refresh_top_rated.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTopRatedSource(List<TopRatedSource> topRatedSources) {
        if (Constants.PAGE_TOP_RATED == 1) {
            adapter.clear();
            for (TopRatedSource source : topRatedSources) {
                adapter.addTopRated(source);
            }
            adapter.notifyDataSetChanged();
        } else {
            for (TopRatedSource source : topRatedSources) {
                adapter.addTopRated(source);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void totalPage(String total_pages) {
        total_page = total_pages;
    }
}
