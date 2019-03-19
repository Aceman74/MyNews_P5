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
import com.aceman.mynews.data.models.search.Doc;
import com.aceman.mynews.ui.navigations.activities.WebviewActivity;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lionel JOFFRAY - on 14/03/2019.
 */
public class SearchAdapter extends RecyclerView.Adapter<BaseViewHolder> {
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
    private List<Doc> mSearchResult;
    private RequestManager glide;
    private Context mContext;

    public interface Listener {
    }

    public SearchAdapter(List<Doc> listResult, RequestManager glide, Context context) {
        this.mSearchResult = listResult;
        this.glide = glide;
        this.mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_item, parent, false);
        ButterKnife.bind(this, view);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        updateWithFreshInfo(this.mSearchResult.get(position), this.glide);
    }

    public void updateWithFreshInfo(final Doc item, RequestManager glide) {
        this.mTitle.setText(item.getHeadline().getMain());
        this.mCategorie.setText(item.getSectionName());
        this.mDate.setText(item.getPubDate());
        try{
            glide   .asDrawable()
                    .load(item.getMultimedia().get(0).getUrl())
                    .into(mImageView);

        }catch (Exception e){
            Log.e("ImagesShared","Loading error");
        }
        this.mItemListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CLICK ITEM", "Ca marche?");
                Intent webView = new Intent(mContext, WebviewActivity.class);
                webView.putExtra("articleUrl", item.getWebUrl());
                mContext.startActivity(webView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mSearchResult.size();
    }

    public Doc mStories(int position) {
        return this.mSearchResult.get(position);
    }
}