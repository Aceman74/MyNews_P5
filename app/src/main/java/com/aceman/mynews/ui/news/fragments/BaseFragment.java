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
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aceman.mynews.R;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

/**
 * Created by Lionel JOFFRAY - on 01/04/2019.
 */
@SuppressWarnings("deprecation")
public abstract class BaseFragment extends Fragment {

    public abstract LinearLayout getNoResultLayout();
    public abstract Button getRetryBtn();
    public abstract void getHttpRequest();
    public abstract List getMResponse();

    public void ifNoResult() {
        if (getMResponse().size() <= 0) {
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
                (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public class AsyncRetrofitRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            getHttpRequest();
            Log.d("Async","Executed!");
            return null;
        }
    }
}