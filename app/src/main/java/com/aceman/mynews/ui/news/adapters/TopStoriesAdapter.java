/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.ui.news.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aceman.mynews.R;
import com.aceman.mynews.data.models.topstories.TopStorieResult;
import com.aceman.mynews.ui.navigations.activities.WebviewActivity;
import com.bumptech.glide.RequestManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Lionel JOFFRAY - on 14/03/2019.
 *
 * <b>TopStories Adapter</b> for his API Call response
 */
public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.MyViewHolder> {

    private final List<TopStorieResult> mTopStories;
    private final RequestManager glide;
    private final Context mContext;

    public TopStoriesAdapter(List<TopStorieResult> listTopStorieResult, RequestManager glide, Context context) {
        this.mTopStories = listTopStorieResult;
        this.glide = glide;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View topView = inflater.inflate(R.layout.fragment_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(topView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        updateWithFreshInfo(this.mTopStories.get(position), this.glide, holder);
        setFadeAnimation(holder.itemView);
    }

    /**
     * Update RecyclerView with list info, handle click on item position with webview intent
     *
     * @param item   Article in the list
     * @param glide  use for get image of article
     * @param holder view holder
     */
    private void updateWithFreshInfo(final TopStorieResult item, RequestManager glide, final MyViewHolder holder) {

        holder.mTitle.setText(item.getTitle());
        holder.setCategroie = item.getSection();

        if (!item.getSubsection().isEmpty()) {
            holder.setCategroie = holder.setCategroie + " -  " + item.getSubsection();
        }
        holder.mCategorie.setText(holder.setCategroie);
        holder.mDate.setText(item.getPublishedDate().substring(0, 10));
        if (item.getMultimedia().isEmpty()) {            //  Check empty media
            holder.mImageView.setImageResource(R.drawable.newyorktimes_thumb);
        } else {
            try {
                glide.asDrawable()
                        .load(item.getMultimedia().get(1).getUrl())
                        .into(holder.mImageView);
            } catch (Exception e) {
                Timber.tag("Image_TopStories").e("Loading error");
            }
        }
        holder.mItemListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webView = new Intent(mContext, WebviewActivity.class);
                webView.putExtra("UrlWebview", item.getUrl());
                mContext.startActivity(webView);
                Animation onClick = AnimationUtils.loadAnimation(mContext, R.anim.click_anim);
                holder.mItemListener.startAnimation(onClick);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mTopStories.size();
    }

    private void setFadeAnimation(View view) {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        view.startAnimation(anim);
    }

    /**
     * View Hoodler using ButterKnife
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fragment_main_item_title)
        TextView mTitle;
        @BindView(R.id.fragment_main_item_categorie)
        TextView mCategorie;
        @BindView(R.id.fragment_main_item_date)
        TextView mDate;
        @BindView(R.id.fragment_main_item_image)
        ImageView mImageView;
        @BindView(R.id.item_id)
        LinearLayout mItemListener;
        String setCategroie;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}