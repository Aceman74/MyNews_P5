package com.aceman.mynews.ui.news.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
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
 */
public class SharedAdapter extends RecyclerView.Adapter<SharedAdapter.MyViewHolder> {

    private List<SharedDoc> mSharedDocs;
    private RequestManager glide;
    private Context mContext;


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

    public void updateWithFreshInfo(final SharedDoc item, RequestManager glide, MyViewHolder holder) {

        holder.mTitle.setText(item.getHeadline().getMain());
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
                Log.i("CLICK ITEM", "Ca marche?");
                Intent webView = new Intent(mContext, WebviewActivity.class);
                webView.putExtra("UrlWebview", item.getWebUrl());
                mContext.startActivity(webView);
            }
        });

    }

    public void setThumbnail(SharedDoc item, MyViewHolder holder) {
        String categorie = item.getSectionName();
        switch (categorie) {
            case "Business":
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
            ButterKnife.bind(this, view);
        }
    }
    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(200);
        view.startAnimation(anim);
    }
}