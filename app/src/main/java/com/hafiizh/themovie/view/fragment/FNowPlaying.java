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
import android.widget.TextView;

import com.hafiizh.themovie.R;
import com.hafiizh.themovie.adapter.fragment.AFragmentNowPlaying;
import com.hafiizh.themovie.model.nowplaying.NowPlayingSource;
import com.hafiizh.themovie.networking.Injector;
import com.hafiizh.themovie.presenter.AppPresenter;
import com.hafiizh.themovie.presenter.ViewMovies;
import com.hafiizh.themovie.utilities.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FNowPlaying extends Fragment implements ViewMovies.NowPlaying {
    SwipeRefreshLayout refresh_now_playing;
    RecyclerView rv_fragment_now_playing;
    GridLayoutManager gridLayoutManager;
    TextView tv_minimum, tv_maximum;

    private AFragmentNowPlaying aFragmentNowPlaying;
    private AppPresenter appPresenter;

    int pastVisibleItems, visibleItemCount, totalItemCount;
    boolean onScroll;
    String total_page;

    public FNowPlaying() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_now_playing, container, false);
        getActivity().setTitle("Now Playing");
        Constants.reset();
        configViews(v);
        runPresenter();

        initializeAdapter();
        assignUIEvents();

        return v;
    }

    private void runPresenter() {
        appPresenter = new AppPresenter(Injector.appMovies(), this);
        appPresenter.setEnumClass(Constants.CLASS.NowPlaying);
        appPresenter.getNowPlaying();
    }

    private void assignUIEvents() {
        refresh_now_playing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                appPresenter.getNowPlaying();
            }
        });
        rv_fragment_now_playing.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (Constants.PAGE_NOW_PLAYING <= Integer.parseInt(total_page)) {
                        Constants.PAGE_NOW_PLAYING += 1;
                        appPresenter.getNowPlaying();
                    }
                }
            }
        });
    }

    private void initializeAdapter() {
        aFragmentNowPlaying = new AFragmentNowPlaying(listener);
        rv_fragment_now_playing.setAdapter(aFragmentNowPlaying);
    }

    private AFragmentNowPlaying.FragmentNowPlayingListener listener =
            new AFragmentNowPlaying.FragmentNowPlayingListener() {
                @Override
                public void onSourceClick(int id) {

                }
            };

    private void configViews(View v) {
        tv_maximum = (TextView) v.findViewById(R.id.tv_maximum_fragment_now_playing);
        tv_minimum = (TextView) v.findViewById(R.id.tv_minimum_fragment_now_playing);

        refresh_now_playing = (SwipeRefreshLayout) v.findViewById(R.id.refresh_now_playing);
        refresh_now_playing.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.darker_gray
        );

        rv_fragment_now_playing = (RecyclerView) v.findViewById(R.id.rv_fragment_now_playing);
        rv_fragment_now_playing.setHasFixedSize(true);
        rv_fragment_now_playing.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv_fragment_now_playing.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void showNowPlayingSource(List<NowPlayingSource> nowPlayingSources) {
        if (Constants.PAGE_NOW_PLAYING == 1) {
            aFragmentNowPlaying.clear();
            for (NowPlayingSource source : nowPlayingSources) {
                aFragmentNowPlaying.addNowPlaying(source);
            }
            aFragmentNowPlaying.notifyDataSetChanged();
        } else {
            for (NowPlayingSource source : nowPlayingSources) {
                aFragmentNowPlaying.addNowPlaying(source);
            }
            aFragmentNowPlaying.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void minimum(String minimum) {
        tv_minimum.setText(minimum);
    }

    @Override
    public void maximum(String maximum) {
        tv_maximum.setText(maximum);
    }

    @Override
    public void onProgress() {
        refresh_now_playing.setRefreshing(true);
    }

    @Override
    public void onFailed() {
        refresh_now_playing.setRefreshing(false);
    }

    @Override
    public void onComplete() {
        refresh_now_playing.setRefreshing(false);
    }

    @Override
    public void totalPage(String total_pages) {
        total_page = total_pages;
    }
}
