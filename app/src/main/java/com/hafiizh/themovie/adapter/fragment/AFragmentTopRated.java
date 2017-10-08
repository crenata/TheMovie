package com.hafiizh.themovie.adapter.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hafiizh.themovie.R;
import com.hafiizh.themovie.model.toprated.TopRatedSource;
import com.hafiizh.themovie.utilities.Constants;
import com.lid.lib.LabelImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HAFIIZH on 9/12/2017.
 */

public class AFragmentTopRated extends RecyclerView.Adapter<AFragmentTopRated.Holder> {
    private List<TopRatedSource> ratedSources;
    private AFTListener listener;

    public AFragmentTopRated(AFTListener listener) {
        ratedSources = new ArrayList<>();
        this.listener = listener;
    }

    public void addTopRated(TopRatedSource source) {
        ratedSources.add(source);
    }

    public void clear() {
        ratedSources.clear();
    }

    public interface AFTListener {
        void onSourceClick(int id);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_movies, parent, false);
        return new Holder(v, listener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        TopRatedSource source = ratedSources.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(Constants.IMAGE.W154 + source.getPoster_path())
                .error(R.drawable.error)
                .into(holder.iv_image_list_all_movies);
        holder.iv_image_list_all_movies.setLabelText(source.getVote_average());
        holder.iv_image_list_all_movies.setLabelTextStyle(1);
        holder.iv_image_list_all_movies.setLabelTextSize(70);
        holder.iv_image_list_all_movies.setLabelHeight(30);
        holder.tv_list_all_movies.setText(source.getTitle());
    }

    @Override
    public int getItemCount() {
        return ratedSources.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_image_list_all_movies)
        LabelImageView iv_image_list_all_movies;
        @BindView(R.id.tv_list_all_movies)
        TextView tv_list_all_movies;

        public Holder(View itemView, AFTListener mListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            listener = mListener;
        }

        @Override
        public void onClick(View view) {
            TopRatedSource source = getItem(getAdapterPosition());
            listener.onSourceClick(Integer.parseInt(source.getId()));
        }
    }

    private TopRatedSource getItem(int adapterPosition) {
        return ratedSources.get(adapterPosition);
    }
}
