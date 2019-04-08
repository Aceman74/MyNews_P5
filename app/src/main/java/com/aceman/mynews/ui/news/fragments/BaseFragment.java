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
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aceman.mynews.R;

import java.util.List;
import java.util.Objects;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aceman.mynews.ui.navigations.activities.MainActivity.mCache;

/**
 * Created by Lionel JOFFRAY - on 01/04/2019.
 */
@SuppressWarnings("deprecation")
public abstract class BaseFragment extends Fragment {

    Toast mToast;

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

    public void tooManyRefreshHandler(){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getHttpRequest();
            }
        }, 15000);
    }

    public void tooManyRefresh(Throwable e) {
        if (e.toString().contains(getString(R.string.many_request))) {
                mToast = Toast.makeText(getContext(), getString(R.string.many_refresh), Toast.LENGTH_LONG);
                mToast.show();
            tooManyRefreshHandler();
        }
    }

public Retrofit setRetrofit(){

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cache(mCache)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();

return retrofit;

}
    public class asyncRetrofitRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            getHttpRequest();
            Log.d("Async", "Executed!");
            return null;
        }
    }

}
