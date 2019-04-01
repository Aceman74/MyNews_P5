/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.ui.news.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aceman.mynews.data.models.mostpopular.MostPopular;
import com.aceman.mynews.data.models.shared.SharedObservable;
import com.aceman.mynews.data.models.topstories.TopStories;

/**
 * Created by Lionel JOFFRAY - on 01/04/2019.
 */
public abstract class FragmentsBase extends Fragment {

    public abstract LinearLayout getNoResultLayout();

    public abstract Button getRetryBtn();

    public abstract void getHttpRequest();


    public void ifNoResult(TopStories details) {
        if (details.getTopStorieResults().size() <= 0) {
            getNoResultLayout().setVisibility(View.VISIBLE);
        }
    }

    public void ifNoResult(SharedObservable details) {
        if (details.getSharedResponse().getSharedDocs().size() <= 0) {
            getNoResultLayout().setVisibility(View.VISIBLE);
        }
    }

    public void ifNoResult(MostPopular details) {
        if (details.getPopularResults().size() <= 0) {
            getNoResultLayout().setVisibility(View.VISIBLE);
        }
    }

    public void retryBtnClick() {
        getRetryBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHttpRequest();
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
