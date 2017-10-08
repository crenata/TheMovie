package com.hafiizh.themovie.adapter.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hafiizh.themovie.R;
import com.hafiizh.themovie.model.nowplaying.NowPlayingSource;
import com.hafiizh.themovie.utilities.Constants;
import com.lid.lib.LabelImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HAFIIZH on 9/3/2017.
 */

public class AFragmentNowPlaying extends RecyclerView.Adapter<AFragmentNowPlaying.Holder> {
    private List<NowPlayingSource> mSources;
    private FragmentNowPlayingListener mItemListener;

    public AFragmentNowPlaying(FragmentNowPlayingListener itemListener) {
        mSources = new ArrayList<>();
        mItemListener = itemListener;
    }

    public void clear() {
        mSources.clear();
    }

    public void addNowPlaying(NowPlayingSource source) {
        mSources.add(source);
    }

    public interface FragmentNowPlayingListener {
        void onSourceClick(int id);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_movies, parent, false);
        return new Holder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        NowPlayingSource source = mSources.get(position);
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
        return mSources.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_image_list_all_movies)
        LabelImageView iv_image_list_all_movies;
        @BindView(R.id.tv_list_all_movies)
        TextView tv_list_all_movies;

        public Holder(View itemView, FragmentNowPlayingListener itemListener) {
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
