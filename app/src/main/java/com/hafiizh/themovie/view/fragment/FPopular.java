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
import com.hafiizh.themovie.adapter.fragment.AFragmentPopular;
import com.hafiizh.themovie.model.popular.PopularSource;
import com.hafiizh.themovie.networking.Injector;
import com.hafiizh.themovie.presenter.AppPresenter;
import com.hafiizh.themovie.presenter.ViewMovies;
import com.hafiizh.themovie.utilities.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FPopular extends Fragment implements ViewMovies.Popular {
    SwipeRefreshLayout refresh_popular;
    RecyclerView rv_popular;
    GridLayoutManager gridLayoutManager;

    AppPresenter appPresenter;
    AFragmentPopular adapter;

    String total_page;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    boolean onScroll;

    public FPopular() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_popular, container, false);
        getActivity().setTitle("Popular");
        Constants.reset();
        configViews(v);

        runPresenter();
        initializeAdapter();

        assignUIEvents();

        return v;
    }

    private void assignUIEvents() {
        refresh_popular.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                appPresenter.getPopular();
            }
        });
        rv_popular.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (Constants.PAGE_POPULAR <= Integer.parseInt(total_page)) {
                        Constants.PAGE_POPULAR += 1;
                        appPresenter.getPopular();
                    }
                }
            }
        });
    }

    private void initializeAdapter() {
        adapter = new AFragmentPopular(listener);
        rv_popular.setAdapter(adapter);
    }

    private AFragmentPopular.AFPListener listener = new AFragmentPopular.AFPListener() {
        @Override
        public void onSourceClick(int id) {

        }
    };

    private void runPresenter() {
        appPresenter = new AppPresenter(Injector.appMovies(), this);
        appPresenter.setEnumClass(Constants.CLASS.Popular);
        appPresenter.getPopular();
    }

    private void configViews(View v) {
        refresh_popular = (SwipeRefreshLayout) v.findViewById(R.id.refresh_popular);
        refresh_popular.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.darker_gray
        );

        rv_popular = (RecyclerView) v.findViewById(R.id.rv_fragment_popular);
        rv_popular.setHasFixedSize(true);
        rv_popular.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv_popular.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onProgress() {
        refresh_popular.setRefreshing(true);
    }

    @Override
    public void onFailed() {
        refresh_popular.setRefreshing(false);
    }

    @Override
    public void onComplete() {
        refresh_popular.setRefreshing(false);
    }

    @Override
    public void showPopularSource(List<PopularSource> popularSources) {
        if (Constants.PAGE_POPULAR == 1) {
            adapter.clear();
            for (PopularSource source : popularSources) {
                adapter.addPopular(source);
            }
            adapter.notifyDataSetChanged();
        } else {
            for (PopularSource source : popularSources) {
                adapter.addPopular(source);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void totalPage(String total_pages) {
        total_page = total_pages;
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}