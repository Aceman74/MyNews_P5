package com.aceman.mynews.ui.news.fragments;


import android.os.Bundle;
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
import com.aceman.mynews.data.api.NewYorkTimesService;
import com.aceman.mynews.data.api.NewsStream;
import com.aceman.mynews.data.models.topstories.TopStorieResult;
import com.aceman.mynews.data.models.topstories.TopStories;
import com.aceman.mynews.ui.news.adapters.TopStoriesAdapter;
import com.aceman.mynews.utils.FragmentBase;
import com.aceman.mynews.utils.RecyclerAnimation;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


/**
 * Created by Lionel JOFFRAY.
 * <p>
 * <b>Top Stories Fragment</> Makes and show his category results, extends <b>FragmentBase</b> <br>
 */
public class TopStoriesFragment extends FragmentBase {
    private Disposable disposable;
    private List<TopStorieResult> mTopStories;
    private TopStoriesAdapter adapter;
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
    }

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    @Override
    public LinearLayout getNoResultLayout() {
        return mNoResult;
    }

    @Override
    public Button getRetryBtn() {
        return mRetryBtn;
    }

    @Override
    public void getHttpRequest() {
        executeHttpRequestWithRetrofit();
    }

    @Override
    public List getMResponse() {
        return mTopStories;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        mProgressBar.setVisibility(View.VISIBLE);
        isOnline(); //  Check internet connection
        new asyncRetrofitRequest().execute("request");  //  Make the request call
        configureRecyclerView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeWhenDestroy();
    }

    private void configureRecyclerView() {
        mTopStories = new ArrayList<>();
        adapter = new TopStoriesAdapter(mTopStories, Glide.with(this), getContext()) {
        };
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
    }


    private void executeHttpRequestWithRetrofit() {

        if (isOnline()) {
            mProgressBar.setVisibility(View.VISIBLE);
            mCheckConnexion.setVisibility(View.GONE);
            mNoResult.setVisibility(View.GONE);


            NewYorkTimesService newsStream = setRetrofit().create(NewYorkTimesService.class);
            this.disposable = NewsStream.streamGetTopStories(newsStream).subscribeWith(new DisposableObserver<TopStories>() {
                @Override
                public void onNext(TopStories details) {
                    Log.e("TOP_Next", "On Next");
                    updateUI(details);  //  Update RecyclerView
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
                    //  tooManyRefresh() method is not necessary here, no call limitation
                }
            });

        } else {
            mProgressBar.setVisibility(View.GONE);
            mCheckConnexion.setVisibility(View.VISIBLE);
            retryBtnClick();    //  If no connection, show refresh btn
        }
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void updateUI(TopStories details) {
        mTopStories.clear();
        mTopStories.addAll(details.getTopStorieResults());
        adapter.notifyDataSetChanged();
        RecyclerAnimation.runLayoutAnimation(mRecyclerView);
        ifNoResult();   //  If result is 0, show a screen
    }
}
