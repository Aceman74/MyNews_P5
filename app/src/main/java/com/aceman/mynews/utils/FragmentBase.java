/*
 * Copyright (c) 2019. Aceman. All rights reserved.
 * Developed by Aceman.
 * Data provided by The New York Times API.
 * https://developer.nytimes.com/
 */

package com.aceman.mynews.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aceman.mynews.R;
import com.aceman.mynews.data.api.RetrofitSet;

import java.util.List;

/**
 * Created by Lionel JOFFRAY - on 01/04/2019.
 *
 * <b>FragmentBase Abstract Class</> is used for setting all fragments <br>
 */
public abstract class FragmentBase extends RetrofitSet {

    public Toast mToast;

    public abstract LinearLayout getNoResultLayout();

    public abstract Button getRetryBtn();

    public abstract void getHttpRequest();

    public abstract List getMResponse();

    /**
     * Method who show a different layout if result of request is 0
     */
    public void ifNoResult() {
        //  Check if result is 0 (not checking if null)
        if (getMResponse().size() <= 0) {
            getNoResultLayout().setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method who show a retry btn if no connection or too many request
     */
    public void retryBtnClick() {
        //  Set a retry btn if no connection on request
        getRetryBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHttpRequest();
            }
        });
    }

    /**
     * Method who delay request ( requests are limited by the NYT API)
     * Should'nt happen after Retrofit cache and dispatcher settings.
     */
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

    /**
     * When API is overcharged by request, lauhch the method tooManyRefreshHandler()
     *
     * @param e error if contain http 429
     */
    public void tooManyRefresh(Throwable e) {
        if (e.toString().contains(getString(R.string.many_request))) {
            mToast = Toast.makeText(getContext(), getString(R.string.many_refresh), Toast.LENGTH_LONG);
            mToast.show();
            tooManyRefreshHandler();
        }
    }

    /**
     * Make the call in asynctask
     */
    @SuppressLint("StaticFieldLeak")
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
