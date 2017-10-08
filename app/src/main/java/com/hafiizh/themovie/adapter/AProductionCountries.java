package com.hafiizh.themovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hafiizh.themovie.R;
import com.hafiizh.themovie.model.detailmovies.ProductionCountries;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HAFIIZH on 9/5/2017.
 */

public class AProductionCountries extends RecyclerView.Adapter<AProductionCountries.Holder> {
    private List<com.hafiizh.themovie.model.detailmovies.ProductionCountries> productionCountries;
    private CountriesListener mCountriesListener;

    public AProductionCountries(CountriesListener countriesListener) {
        productionCountries = new ArrayList<>();
        mCountriesListener = countriesListener;
    }

    public void addCountries(com.hafiizh.themovie.model.detailmovies.ProductionCountries countries) {
        productionCountries.add(countries);
    }

    public void clear() {
        productionCountries.clear();
    }

    public interface CountriesListener {
        void onSourceClick(String s);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category, parent, false);
        return new Holder(view, mCountriesListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tv_category.setText(productionCountries.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return productionCountries.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_category)
        TextView tv_category;

        public Holder(View itemView, CountriesListener countriesListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mCountriesListener = countriesListener;
        }

        @Override
        public void onClick(View view) {
            ProductionCountries productionCountries = getItem(getAdapterPosition());
            mCountriesListener.onSourceClick(productionCountries.getIso_3166_1());
        }
    }

    private ProductionCountries getItem(int adapterPosition) {
        return productionCountries.get(adapterPosition);
    }
}
