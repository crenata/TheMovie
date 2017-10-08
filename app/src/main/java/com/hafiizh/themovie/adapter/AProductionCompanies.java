package com.hafiizh.themovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hafiizh.themovie.R;
import com.hafiizh.themovie.model.detailmovies.ProductionCompanies;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HAFIIZH on 9/5/2017.
 */

public class AProductionCompanies extends RecyclerView.Adapter<AProductionCompanies.Holder> {
    private List<com.hafiizh.themovie.model.detailmovies.ProductionCompanies> productionCompanies;
    private CompaniesListener mItemListener;

    public AProductionCompanies(CompaniesListener companiesListener) {
        productionCompanies = new ArrayList<>();
        mItemListener = companiesListener;
    }

    public void addCompanies(com.hafiizh.themovie.model.detailmovies.ProductionCompanies companies) {
        productionCompanies.add(companies);
    }

    public void clear() {
        productionCompanies.clear();
    }

    public interface CompaniesListener {
        void onSourceClick(int id);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category, parent, false);
        return new Holder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tv_category.setText(productionCompanies.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return productionCompanies.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_category)
        TextView tv_category;

        public Holder(View itemView, CompaniesListener itemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mItemListener = itemListener;
        }

        @Override
        public void onClick(View view) {
            ProductionCompanies productionCompanies = getItem(getAdapterPosition());
            mItemListener.onSourceClick(Integer.parseInt(productionCompanies.getId()));
        }
    }

    private ProductionCompanies getItem(int adapterPosition) {
        return productionCompanies.get(adapterPosition);
    }
}
