package com.aceman.mynews.ui.news.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Lionel JOFFRAY - on 14/03/2019.
 */
public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.MyViewHolder> {

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

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    public List<TopStorieResult> mTopStories;
    public RequestManager glide;
    Context mContext;

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
    }

    public void updateWithFreshInfo(final TopStorieResult item, RequestManager glide, MyViewHolder holder) {

        holder.mTitle.setText(item.getTitle());
        holder.mCategorie.setText(item.getSection());
        holder.mDate.setText(item.getPublishedDate().substring(0, 10));
        if (item.getMultimedia().isEmpty()) {            //  Check empty media
            holder.mImageView.setImageResource(R.drawable.newyorktimes_thumb);
        } else {
            try {
                glide.asDrawable()
                        .load(item.getMultimedia().get(0).getUrl())
                        .into(holder.mImageView);
            } catch (Exception e) {
                Log.e("ImagesTopStories", "Loading error");
            }
        }
        holder.mItemListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CLICK ITEM", "Ca marche?");
                Intent webView = new Intent(mContext, WebviewActivity.class);
                webView.putExtra("articleUrl", item.getUrl());
                mContext.startActivity(webView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mTopStories.size();
    }

}