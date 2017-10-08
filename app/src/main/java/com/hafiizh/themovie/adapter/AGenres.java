package com.hafiizh.themovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hafiizh.themovie.R;
import com.hafiizh.themovie.model.detailmovies.Genre;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HAFIIZH on 9/5/2017.
 */

public class AGenres extends RecyclerView.Adapter<AGenres.Holder> {
    private List<Genre> genres;
    private GenresListener mItemListener;

    public AGenres(GenresListener itemListener) {
        genres = new ArrayList<>();
        mItemListener = itemListener;
    }

    public void addGenres(Genre genre) {
        genres.add(genre);
    }

    public void clear() {
        genres.clear();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category, parent, false);
        return new Holder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tv_category.setText(genres.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_category)
        TextView tv_category;

        public Holder(View itemView, GenresListener itemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mItemListener = itemListener;
        }

        @Override
        public void onClick(View view) {
            Genre genre = getItem(getAdapterPosition());
            mItemListener.onSourceClick(Integer.parseInt(genre.getId()));
        }
    }

    private Genre getItem(int adapterPosition) {
        return genres.get(adapterPosition);
    }

    public interface GenresListener {
        void onSourceClick(int id);
    }
}
