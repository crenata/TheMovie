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
import com.hafiizh.themovie.adapter.fragment.AFragmentSimilar;
import com.hafiizh.themovie.model.similar.SimilarSource;
import com.hafiizh.themovie.networking.Injector;
import com.hafiizh.themovie.presenter.AppPresenter;
import com.hafiizh.themovie.presenter.ViewMovies;
import com.hafiizh.themovie.utilities.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FSimilar extends Fragment implements ViewMovies.Similar {
    SwipeRefreshLayout refresh_similar;
    RecyclerView rv_fragment_similar;
    GridLayoutManager gridLayoutManager;

    AppPresenter appPresenter;
    AFragmentSimilar similar;

    String total_page;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    boolean onScroll;

    public static int GET_MOVIES_ID;

    public FSimilar() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_similar, container, false);
        getActivity().setTitle("Similar");
        GET_MOVIES_ID = getArguments().getInt("id");
        Constants.reset();
        configViews(v);

        similar = new AFragmentSimilar(similarListener);

        appPresenter = new AppPresenter(Injector.appMovies(), this);
        appPresenter.setEnumClass(Constants.CLASS.Similar);
        appPresenter.getSimilar();

        rv_fragment_similar.setAdapter(similar);

        assignUIEvents();

        return v;
    }

    private void assignUIEvents() {
        refresh_similar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                appPresenter.getSimilar();
            }
        });
        rv_fragment_similar.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (Constants.PAGE_SIMILAR <= Integer.parseInt(total_page)) {
                        Constants.PAGE_SIMILAR += 1;
                        appPresenter.getSimilar();
                    }
                }
            }
        });
    }

    public AFragmentSimilar.AFSimilarListener similarListener = new AFragmentSimilar.AFSimilarListener() {
        @Override
        public void onSourceClick(int id) {

        }
    };

    private void configViews(View v) {
        refresh_similar = (SwipeRefreshLayout) v.findViewById(R.id.refresh_similar);
        refresh_similar.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.darker_gray
        );

        rv_fragment_similar = (RecyclerView) v.findViewById(R.id.rv_fragment_similar);
        rv_fragment_similar.setHasFixedSize(true);
        rv_fragment_similar.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv_fragment_similar.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgress() {
        refresh_similar.setRefreshing(true);
    }

    @Override
    public void onFailed() {
        refresh_similar.setRefreshing(false);
    }

    @Override
    public void onComplete() {
        refresh_similar.setRefreshing(false);
    }

    @Override
    public void showSimilarSource(List<SimilarSource> similarSources) {
        if (Constants.PAGE_SIMILAR == 1) {
            similar.clear();
            for (SimilarSource source : similarSources) {
                similar.addSimilar(source);
            }
            similar.notifyDataSetChanged();
        } else {
            for (SimilarSource source : similarSources) {
                similar.addSimilar(source);
            }
            similar.notifyDataSetChanged();
        }
    }

    @Override
    public void totalPage(String total_pages) {
        total_page = total_pages;
    }
}
