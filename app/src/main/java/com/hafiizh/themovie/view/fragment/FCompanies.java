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
import com.hafiizh.themovie.adapter.fragment.AFragmentCompanies;
import com.hafiizh.themovie.model.companies.CompaniesSource;
import com.hafiizh.themovie.networking.Injector;
import com.hafiizh.themovie.presenter.AppPresenter;
import com.hafiizh.themovie.presenter.ViewMovies;
import com.hafiizh.themovie.utilities.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FCompanies extends Fragment implements ViewMovies.Company {
    SwipeRefreshLayout refresh_companies;
    RecyclerView rv_fragment_companies;
    GridLayoutManager gridLayoutManager;

    AFragmentCompanies aFragmentCompanies;
    AppPresenter appPresenter;

    public static int GET_COMPANIES_ID;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    boolean onScroll;
    String total_page;

    public FCompanies() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_companies, container, false);
        getActivity().setTitle("Companies");
        GET_COMPANIES_ID = getArguments().getInt("id");
        Constants.reset();
        configViews(v);

        aFragmentCompanies = new AFragmentCompanies(fragmentCompaniesListener);
        appPresenter = new AppPresenter(Injector.appCompanies(), this);
        appPresenter.getCompanies();
        rv_fragment_companies.setAdapter(aFragmentCompanies);

        assignUIEvents();

        return v;
    }

    private void assignUIEvents() {
        refresh_companies.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                appPresenter.getCompanies();
            }
        });
        rv_fragment_companies.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (Constants.PAGE_COMPANIES <= Integer.parseInt(total_page)) {
                        Constants.PAGE_COMPANIES += 1;
                        appPresenter.getCompanies();
                    }
                }
            }
        });
    }

    public AFragmentCompanies.FragmentCompaniesListener fragmentCompaniesListener =
            new AFragmentCompanies.FragmentCompaniesListener() {
                @Override
                public void onSourceClick(int id) {

                }
            };

    private void configViews(View v) {
        refresh_companies = (SwipeRefreshLayout) v.findViewById(R.id.refresh_companies);
        refresh_companies.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.darker_gray
        );

        rv_fragment_companies = (RecyclerView) v.findViewById(R.id.rv_fragment_companies);
        rv_fragment_companies.setHasFixedSize(true);
        rv_fragment_companies.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv_fragment_companies.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgress() {
        refresh_companies.setRefreshing(true);
    }

    @Override
    public void onFailed() {
        refresh_companies.setRefreshing(false);
    }

    @Override
    public void onComplete() {
        refresh_companies.setRefreshing(false);
    }

    @Override
    public void showCompaniesSource(List<CompaniesSource> companiesSources) {
        if (Constants.PAGE_COMPANIES == 1) {
            aFragmentCompanies.clear();
            for (CompaniesSource source : companiesSources) {
                aFragmentCompanies.addCompanies(source);
            }
            aFragmentCompanies.notifyDataSetChanged();
        } else {
            for (CompaniesSource source : companiesSources) {
                aFragmentCompanies.addCompanies(source);
            }
            aFragmentCompanies.notifyDataSetChanged();
        }
    }

    @Override
    public void totalPage(String total_pages) {
        total_page = total_pages;
    }
}