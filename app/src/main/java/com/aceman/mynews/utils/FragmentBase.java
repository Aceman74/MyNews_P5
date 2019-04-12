/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aceman.mynews.R;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aceman.mynews.ui.navigations.activities.MainActivity.mCache;

/**
 * Created by Lionel JOFFRAY - on 01/04/2019.
 *
 * <b>FragmentBase Abstract Class</> is used for making all asynchronous request API for News and handle multiple request<br>
 */
@SuppressWarnings("deprecation")
public abstract class FragmentBase extends Fragment {

    public Toast mToast;

    public abstract LinearLayout getNoResultLayout();

    public abstract Button getRetryBtn();

    public abstract void getHttpRequest();

    public abstract List getMResponse();

    public void ifNoResult() {
        //  Check if result is 0 (not checking if null)
        if (getMResponse().size() <= 0) {
            getNoResultLayout().setVisibility(View.VISIBLE);
        }
    }

    public void retryBtnClick() {
        //  Set a retry btn if no connection on request
        getRetryBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHttpRequest();
            }
        });
    }

    public boolean isOnline() {
        //    Check internet connectivity of the devise
        ConnectivityManager cm =
                (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void tooManyRefreshHandler() {
        //  Wait for 15 sec if too many request and try again if still error 429
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

    public final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //  Create an Interceptor to  cache all request and avoid Too many request error
            Response originalResponse = chain.proceed(chain.request());
            if (isOnline()) {
                int maxAge = 120; // read from cache for 2 minutes
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    public final Interceptor delayInterceptor = new Interceptor() {
        //  Second interceptor to delay request, again for avoiding error 429
        @Override
        public Response intercept(Chain chain) throws IOException {
            SystemClock.sleep(1000);
            return chain.proceed(chain.request());
        }
    };

    public Retrofit setRetrofit() {
        //  Setting retrofit with Interceptor and dispatcher too
        //  With dispatcher max request, still for 429
        Dispatcher dispatcherMaxR = new Dispatcher();
        dispatcherMaxR.setMaxRequests(2);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(delayInterceptor)
                .dispatcher(dispatcherMaxR)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
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
        //  Make request in dedicated thread
        @Override
        protected String doInBackground(String... params) {
            getHttpRequest();
            Log.d("Async", "Executed!");
            return null;
        }
    }
}
