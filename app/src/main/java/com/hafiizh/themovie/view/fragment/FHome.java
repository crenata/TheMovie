package com.hafiizh.themovie.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hafiizh.themovie.R;
import com.hafiizh.themovie.adapter.ANowPlaying;
import com.hafiizh.themovie.adapter.APopular;
import com.hafiizh.themovie.adapter.ATopRated;
import com.hafiizh.themovie.model.latest.LatestReponse;
import com.hafiizh.themovie.model.nowplaying.NowPlayingSource;
import com.hafiizh.themovie.model.popular.PopularSource;
import com.hafiizh.themovie.model.toprated.TopRatedSource;
import com.hafiizh.themovie.model.upcoming.UpcomingSource;
import com.hafiizh.themovie.networking.Injector;
import com.hafiizh.themovie.networking.Utils;
import com.hafiizh.themovie.presenter.ViewMovies;
import com.hafiizh.themovie.presenter.AppPresenter;
import com.hafiizh.themovie.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FHome extends Fragment implements ViewMovies.Home {
    ScrollView scrollLayout;
    RelativeLayout layout_now_playing, layout_latest, layout_popular, layout_top_rated, layout_upcoming;
    TextView tv_latest, tv_mimimum_now_playing, tv_maximum_now_playing, tv_minimum_upcoming, tv_maximum_upcoming;
    ImageView iv_latest;
    ProgressBar pb_now, pb_popular, pb_top, pb_upcoming;
    RecyclerView rv_now_playing, rv_populer, rv_top_rated, rv_upcoming;
    LinearLayoutManager linearLayoutManager;

    private ANowPlaying aNowPlaying;
    private APopular aPopular;
    private ATopRated aTopRated;
    private AppPresenter appPresenter;

    LatestReponse latestReponses;

    public FHome() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");
        Constants.reset();
        configRV(v);

        initializeAdapter();
        appPresenter = new AppPresenter(Injector.appMovies(), this);

        getData();
        assignUIEvents();

        return v;
    }

    private void assignUIEvents() {
        layout_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FDetailMovies FDetailMovies = new FDetailMovies();
                Bundle bundle = new Bundle();
                bundle.putString("titlebar", "Latest");
                bundle.putInt("id", Integer.parseInt(latestReponses.getId()));
                FDetailMovies.setArguments(bundle);
                replaceFragment(FDetailMovies);
            }
        });
        layout_now_playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FNowPlaying fNowPlaying = new FNowPlaying();
                replaceFragment(fNowPlaying);
            }
        });
        layout_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FPopular fPopular = new FPopular();
                replaceFragment(fPopular);
            }
        });
        layout_top_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FTopRated fTopRated = new FTopRated();
                replaceFragment(fTopRated);
            }
        });
    }

    private void getData() {
        if (getNetworkStatus()) {
            getDataFromApi();
            setAdapterToRV();
        } else {

        }
    }

    private void getDataFromApi() {
        appPresenter.setEnumClass(Constants.CLASS.Home);
        appPresenter.getNowPlaying();
        appPresenter.getPopular();
        appPresenter.getLatest();
        appPresenter.getTopRated();
    }

    private void setAdapterToRV() {
        rv_now_playing.setAdapter(aNowPlaying);
        rv_populer.setAdapter(aPopular);
        rv_top_rated.setAdapter(aTopRated);
    }

    private void initializeAdapter() {
        aNowPlaying = new ANowPlaying(nowPlayingListener);
        aPopular = new APopular(popularListener);
        aTopRated = new ATopRated(topListener);
    }

    private ANowPlaying.NowPlayingListener nowPlayingListener = new ANowPlaying.NowPlayingListener() {
        @Override
        public void onSourceClick(int id) {
            FDetailMovies FDetailMovies = new FDetailMovies();
            Bundle bundle = new Bundle();
            bundle.putString("titlebar", "Now Playing");
            bundle.putInt("id", id);
            FDetailMovies.setArguments(bundle);
            replaceFragment(FDetailMovies);
        }
    };
    private APopular.PopularListener popularListener = new APopular.PopularListener() {
        @Override
        public void onSourceClick(int id) {
            FDetailMovies FDetailMovies = new FDetailMovies();
            Bundle bundle = new Bundle();
            bundle.putString("titlebar", "Popular");
            bundle.putInt("id", id);
            FDetailMovies.setArguments(bundle);
            replaceFragment(FDetailMovies);
        }
    };
    private ATopRated.ATListener topListener = new ATopRated.ATListener() {
        @Override
        public void onSourceClick(int id) {
            FDetailMovies FDetailMovies = new FDetailMovies();
            Bundle bundle = new Bundle();
            bundle.putString("titlebar", "Top Rated");
            bundle.putInt("id", id);
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

    private void configRV(View v) {
        pb_now = (ProgressBar) v.findViewById(R.id.pb_now_playing);
        pb_popular = (ProgressBar) v.findViewById(R.id.pb_popular);
        pb_top = (ProgressBar) v.findViewById(R.id.pb_top_rated);
        pb_upcoming = (ProgressBar) v.findViewById(R.id.pb_upcoming);
        layout_upcoming = (RelativeLayout) v.findViewById(R.id.layout_upcoming);
        layout_top_rated = (RelativeLayout) v.findViewById(R.id.layout_top_rated);
        layout_popular = (RelativeLayout) v.findViewById(R.id.layout_popular);
        layout_latest = (RelativeLayout) v.findViewById(R.id.layout_latest);
        layout_now_playing = (RelativeLayout) v.findViewById(R.id.layout_now_playing);
        scrollLayout = (ScrollView) v.findViewById(R.id.scrollLayout);
        tv_latest = (TextView) v.findViewById(R.id.tv_latest);
        tv_mimimum_now_playing = (TextView) v.findViewById(R.id.tv_minimum_now_playing);
        tv_maximum_now_playing = (TextView) v.findViewById(R.id.tv_maximum_now_playing);
        iv_latest = (ImageView) v.findViewById(R.id.iv_latest);

        rv_now_playing = (RecyclerView) v.findViewById(R.id.rv_now_playing);
        rv_now_playing.setHasFixedSize(true);
        rv_now_playing.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_now_playing.setLayoutManager(linearLayoutManager);

        rv_populer = (RecyclerView) v.findViewById(R.id.rv_populer);
        rv_populer.setHasFixedSize(true);
        rv_populer.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_populer.setLayoutManager(linearLayoutManager);

        rv_top_rated = (RecyclerView) v.findViewById(R.id.rv_top_rated);
        rv_top_rated.setHasFixedSize(true);
        rv_top_rated.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_top_rated.setLayoutManager(linearLayoutManager);

        rv_upcoming = (RecyclerView) v.findViewById(R.id.rv_upcoming);
        rv_upcoming.setHasFixedSize(true);
        rv_upcoming.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_upcoming.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void showNowPlayingSource(List<NowPlayingSource> nowPlayingSources) {
        aNowPlaying.clear();
        for (NowPlayingSource source : nowPlayingSources) {
            aNowPlaying.addNowPlaying(source);
        }
        aNowPlaying.notifyDataSetChanged();
    }

    @Override
    public void showPopularSource(List<PopularSource> popularSources) {
        aPopular.clear();
        for (PopularSource source : popularSources) {
            aPopular.addPopular(source);
        }
        aPopular.notifyDataSetChanged();
    }

    @Override
    public void showTopRatedSource(List<TopRatedSource> topRatedSources) {
        aTopRated.clear();
        for (TopRatedSource source : topRatedSources) {
            aTopRated.addTopRated(source);
        }
        aTopRated.notifyDataSetChanged();
    }

    @Override
    public void showUpcomingSource(List<UpcomingSource> upcomingSources) {

    }

    @Override
    public void showLatestResponse(LatestReponse latestReponse) {
        latestReponses = latestReponse;
        tv_latest.setText(latestReponses.getTitle());
        Picasso.with(getActivity())
                .load(Constants.IMAGE.W500 + latestReponses.getBackdrop_path())
                .error(R.drawable.error)
                .into(iv_latest);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), "Error retrieving movie " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressNowPlaying() {
        pb_now.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressPopular() {
        pb_popular.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressTopRated() {
        pb_top.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressUpcoming() {
        pb_upcoming.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailedNowPlaying() {

    }

    @Override
    public void onFailedPopular() {

    }

    @Override
    public void onFailedTopRated() {

    }

    @Override
    public void onFailedUpcoming() {

    }

    @Override
    public void onCompleteNowPlaying() {
        pb_now.setVisibility(View.GONE);
    }

    @Override
    public void onCompletePopular() {
        pb_popular.setVisibility(View.GONE);
    }

    @Override
    public void onCompleteTopRated() {
        pb_top.setVisibility(View.GONE);
    }

    @Override
    public void onCompleteUpcoming() {
        pb_upcoming.setVisibility(View.GONE);
    }

    @Override
    public void onProgressLatest() {

    }

    @Override
    public void onCompleteLatest() {

    }

    @Override
    public void onFailedLatest() {

    }

    @Override
    public void minimumNowPlaying(String minimum) {
        tv_mimimum_now_playing.setText(minimum);
    }

    @Override
    public void maximumNowPlaying(String maximum) {
        tv_maximum_now_playing.setText(maximum);
    }

    @Override
    public void minimumUpcoming(String minimum) {
        tv_minimum_upcoming.setText(minimum);
    }

    @Override
    public void maximumUpcoming(String maximum) {
        tv_maximum_upcoming.setText(maximum);
    }

    public boolean getNetworkStatus() {
        return Utils.isConnected(getActivity());
    }
}