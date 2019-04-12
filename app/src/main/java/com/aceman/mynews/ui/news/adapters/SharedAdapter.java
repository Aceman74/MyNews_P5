package com.aceman.mynews.ui.news.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aceman.mynews.R;
import com.aceman.mynews.data.models.shared.SharedDoc;
import com.aceman.mynews.ui.navigations.activities.WebviewActivity;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lionel JOFFRAY - on 14/03/2019.
 *
 * <b>Shared Adapter</b> for his API Call response
 */
public class SharedAdapter extends RecyclerView.Adapter<SharedAdapter.MyViewHolder> {

    private final List<SharedDoc> mSharedDocs;
    private final RequestManager glide;
    private final Context mContext;


    public SharedAdapter(List<SharedDoc> listResult, RequestManager glide, Context context) {
        this.mSharedDocs = listResult;
        this.glide = glide;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View sharedView = inflater.inflate(R.layout.fragment_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(sharedView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        updateWithFreshInfo(this.mSharedDocs.get(position), this.glide, holder);
        setFadeAnimation(holder.itemView);

    }

    /**
     * Update RecyclerView with list info, handle click on item position with webview intent
     *
     * @param item   Article in the list
     * @param glide  use for get image of article
     * @param holder view holder
     */
    private void updateWithFreshInfo(final SharedDoc item, RequestManager glide, final MyViewHolder holder) {

        holder.mTitle.setText(item.getHeadline().getMain());
        if (item.getHeadline().getMain().isEmpty()) {
            holder.mTitle.setText(R.string.embedded_video);
        }
        holder.mCategorie.setText(item.getSectionName());
        holder.mDate.setText(item.getPubDate().substring(0, 10)); // Get the date without hour
        if (item.getMultimedia().isEmpty()) { //  Check empty media
            setThumbnail(item, holder); //  Set categorie thumb if not in media
        } else {


            try {
                holder.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP); // resize large image
                glide.asDrawable()
                        .load(item.getMultimedia().get(1).getUrl()) //  Base URL added in Data
                        .apply(RequestOptions.fitCenterTransform()) //  Adapt to placeholder size
                        .into(holder.mImageView);
            } catch (Exception e) {
                Log.e("ImagesShared", "Loading error");
            }

        }

        holder.mItemListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //  Webview intent
                Intent webView = new Intent(mContext, WebviewActivity.class);
                webView.putExtra("UrlWebview", item.getWebUrl());
                mContext.startActivity(webView);
                Animation onClick = AnimationUtils.loadAnimation(mContext, R.anim.click_anim);
                holder.mItemListener.startAnimation(onClick);
            }
        });

    }

    /**
     * Set custom Thumbnail in case of No Image in article
     *
     * @param item   get item
     * @param holder viewholder
     */
    private void setThumbnail(SharedDoc item, MyViewHolder holder) {
        String categorie = item.getSectionName();
        switch (categorie) {
            case "Business Day":
                holder.mImageView.setImageResource(R.drawable.business_thumb);
                break;
            case "Food":
                holder.mImageView.setImageResource(R.drawable.food_thumb);
                break;
            case "Movies":
                holder.mImageView.setImageResource(R.drawable.movies_thumb);
                break;
            case "Sports":
                holder.mImageView.setImageResource(R.drawable.sports_thumb);
                break;
            case "Technology":
                holder.mImageView.setImageResource(R.drawable.tech_thumb);
                break;
            case "Travel":
                holder.mImageView.setImageResource(R.drawable.travel_thumb);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.mSharedDocs.size();
    }

    private void setFadeAnimation(View view) {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        view.startAnimation(anim);
    }

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

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}