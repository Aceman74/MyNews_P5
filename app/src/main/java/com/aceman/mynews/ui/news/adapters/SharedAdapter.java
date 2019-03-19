package com.aceman.mynews.ui.news.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aceman.mynews.R;
import com.aceman.mynews.data.models.shared.SharedDoc;
import com.aceman.mynews.data.models.shared.Multimedium;
import com.aceman.mynews.ui.navigations.activities.WebviewActivity;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lionel JOFFRAY - on 14/03/2019.
 */
public class SharedAdapter extends RecyclerView.Adapter<BaseViewHolder> {

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
    private List<SharedDoc> mSharedDocs;
    private List<Multimedium> mMultimedia;
    private RequestManager glide;
    private  Context mContext;


    public interface Listener {
    }

    public SharedAdapter(List<SharedDoc> listResult, RequestManager glide, Context context ) {
        this.mSharedDocs = listResult;
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
        updateWithFreshInfo(this.mSharedDocs.get(position), this.glide);

    }

    public void updateWithFreshInfo(final SharedDoc item, RequestManager glide) {
        this.mTitle.setText(item.getHeadline().getMain());
        this.mCategorie.setText(item.getSectionName());
        this.mDate.setText(item.getPubDate());


            try{
                glide   .asDrawable()
                        .load(item.getMultimedia().get(2).getUrl())
                        .into(mImageView);
            }catch (Exception e){
                Log.e("ImagesShared","Loading error");
            }




        this.mItemListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CLICK ITEM","Ca marche?");
                Intent webView = new Intent(mContext, WebviewActivity.class);
                webView.putExtra("articleUrl",item.getWebUrl());
                mContext.startActivity(webView);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.mSharedDocs.size();
    }

    public Multimedium media (int position) {
        return this.mMultimedia.get(position);
    }
}