package com.aceman.mynews.ui.news.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.aceman.mynews.R;
import com.aceman.mynews.data.api.NewsStream;
import com.aceman.mynews.data.models.topstories.TopStorieResult;
import com.aceman.mynews.data.models.topstories.TopStories;
import com.aceman.mynews.ui.news.adapters.TopStoriesAdapter;
import com.aceman.mynews.utils.RecyclerAnimation;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends Fragment {
    Disposable disposable;
    List<TopStorieResult> mTopStories;
    TopStoriesAdapter adapter;
    @BindView(R.id.topstories_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.spinner_topstories)
    ProgressBar mProgressBar;
    @BindView(R.id.layout_check_connexion)
    LinearLayout mCheckConnexion;
    @BindView(R.id.layout_no_result)
    LinearLayout mNoResult;
    @BindView(R.id.retry_btn)
    Button mRetryBtn;


    public TopStoriesFragment() {
        // Required empty public constructor

    }

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        mProgressBar.setVisibility(View.VISIBLE);
        configureRecyclerView();
        executeHttpRequestWithRetrofit();
        isOnline();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    public void configureRecyclerView() {
        this.mTopStories = new ArrayList<>();
        this.adapter = new TopStoriesAdapter(this.mTopStories, Glide.with(this), getContext()) {
        };
        this.mRecyclerView.setAdapter(this.adapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }


    public void executeHttpRequestWithRetrofit() {

        if (isOnline()) {
            mProgressBar.setVisibility(View.VISIBLE);
            mCheckConnexion.setVisibility(View.GONE);
            mNoResult.setVisibility(View.GONE);

            this.disposable = NewsStream.streamGetTopStories().subscribeWith(new DisposableObserver<TopStories>() {
                @Override
                public void onNext(TopStories details) {
                    Log.e("TOP_Next", "On Next");
                    updateUI(details);
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("TOP_Error", "On Error" + Log.getStackTraceString(e));
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onComplete() {
                    Log.e("TOP_Complete", "On Complete !!");
                }
            });

        } else {
            mProgressBar.setVisibility(View.GONE);
            mCheckConnexion.setVisibility(View.VISIBLE);
            mRetryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    executeHttpRequestWithRetrofit();
                }
            });

        }
    }

    public void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void updateUI(TopStories details) {
        mTopStories.clear();
        mTopStories.addAll(details.getTopStorieResults());
        adapter.notifyDataSetChanged();
        RecyclerAnimation.runLayoutAnimation(mRecyclerView);
        ifNoResult(details);
    }

    private void ifNoResult(TopStories details) {
        if (details.getTopStorieResults().size() <= 0) {
            mNoResult.setVisibility(View.VISIBLE);
        }
    }

    private void retryBtnClick() {
        mRetryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeHttpRequestWithRetrofit();
            }
        });
    }
}
