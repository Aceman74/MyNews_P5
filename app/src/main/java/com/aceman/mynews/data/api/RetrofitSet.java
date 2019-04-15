/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.data.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

import java.io.IOException;
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
 * Created by Lionel JOFFRAY - on 13/04/2019.
 * <p>
 * This configure the <b>Retrofit</b> request by adding a cache system, online check and dispatcher to avoid too many requests.
 */
@SuppressWarnings("deprecation")
public abstract class RetrofitSet extends Fragment {
    /**
     * Interceptor for caching request for 2 minutes.
     */
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

    /**
     * Boolean method to check if device is connected to internet.
     *
     * @return network status
     */
    public boolean isOnline() {
        //    Check internet connectivity of the device
        ConnectivityManager cm =
                (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Setting Retrofit for all API request, with all param (cache and dispatcher)
     *
     * @return retrofit
     */
    public Retrofit setRetrofit() {
        //  Setting retrofit with Interceptor and dispatcher too
        //  With dispatcher max request, still for 429
        Dispatcher dispatcherMaxR = new Dispatcher();
        dispatcherMaxR.setMaxRequests(2);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
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
}
