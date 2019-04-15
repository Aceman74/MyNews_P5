/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aceman.mynews.ui.navigations.activities.MainActivity.mCache;

/**
 * Created by Lionel JOFFRAY - on 15/04/2019.
 */
public class TestUtils {

    public static Retrofit setRetrofitForTesting() {

        Dispatcher dispatcherMaxR = new Dispatcher();
        dispatcherMaxR.setMaxRequests(2);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .dispatcher(dispatcherMaxR)
                .cache(mCache)
                .build();

        Retrofit mTestingRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/svc/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return mTestingRetrofit;
    }
}
