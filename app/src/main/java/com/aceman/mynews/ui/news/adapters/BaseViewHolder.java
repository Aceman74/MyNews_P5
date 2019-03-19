package com.aceman.mynews.ui.news.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;

/**
 * Created by Lionel JOFFRAY - on 14/03/2019.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
