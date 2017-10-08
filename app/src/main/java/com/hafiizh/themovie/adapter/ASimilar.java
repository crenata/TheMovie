package com.hafiizh.themovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hafiizh.themovie.R;
import com.hafiizh.themovie.model.similar.SimilarSource;
import com.hafiizh.themovie.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HAFIIZH on 9/9/2017.
 */

public class ASimilar extends RecyclerView.Adapter<ASimilar.Holder> {
    List<SimilarSource> sourceList;
    private SimilarListener similarListener;

    public ASimilar(SimilarListener similarListener) {
        sourceList = new ArrayList<>();
        this.similarListener = similarListener;
    }

    public void addSimilar(SimilarSource source) {
        sourceList.add(source);
    }

    public void clear() {
        sourceList.clear();
    }

    public interface SimilarListener {
        void onSourceClick(int id);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
        return new Holder(v, similarListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        SimilarSource source = sourceList.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(Constants.IMAGE.W154 + source.getPoster_path())
                .error(R.drawable.bg_list_category)
                .into(holder.iv_image_movie);
        holder.tv_name_movie.setText(source.getTitle());
        holder.tv_vote_average.setText(source.getVote_average());
        holder.tv_release_date.setText(source.getRelease_date());
    }

    @Override
    public int getItemCount() {
        return sourceList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_image_movie)
        ImageView iv_image_movie;
        @BindView(R.id.tv_name_movie)
        TextView tv_name_movie;
        @BindView(R.id.tv_vote_average)
        TextView tv_vote_average;
        @BindView(R.id.tv_release_date)
        TextView tv_release_date;

        public Holder(View itemView, SimilarListener mSimilarListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            similarListener = mSimilarListener;
        }

        @Override
        public void onClick(View view) {
            SimilarSource source = getItem(getAdapterPosition());
            similarListener.onSourceClick(Integer.parseInt(source.getId()));
        }
    }

    private SimilarSource getItem(int adapterPosition) {
        return sourceList.get(adapterPosition);
    }
}
