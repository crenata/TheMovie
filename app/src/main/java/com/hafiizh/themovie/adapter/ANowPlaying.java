package com.hafiizh.themovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hafiizh.themovie.R;
import com.hafiizh.themovie.model.nowplaying.NowPlayingSource;
import com.hafiizh.themovie.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HAFIIZH on 9/2/2017.
 */

public class ANowPlaying extends RecyclerView.Adapter<ANowPlaying.Holder> {
    private List<NowPlayingSource> mSources;
    private NowPlayingListener mItemListener;

    public ANowPlaying(NowPlayingListener itemListener) {
        mSources = new ArrayList<>();
        mItemListener = itemListener;
    }

    public void addNowPlaying(NowPlayingSource source) {
        mSources.add(source);
    }

    public void clear() {
        mSources.clear();
    }

    public interface NowPlayingListener {
        void onSourceClick(int id);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
        return new Holder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        NowPlayingSource source = mSources.get(position);
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
        return mSources.size();
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

        public Holder(View itemView, NowPlayingListener itemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mItemListener = itemListener;
        }

        @Override
        public void onClick(View view) {
            NowPlayingSource source = getItem(getAdapterPosition());
            mItemListener.onSourceClick(Integer.parseInt(source.getId()));
        }
    }

    private NowPlayingSource getItem(int adapterPosition) {
        return mSources.get(adapterPosition);
    }
}
