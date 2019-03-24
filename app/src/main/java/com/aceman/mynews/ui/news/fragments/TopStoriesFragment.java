package com.aceman.mynews.ui.news.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aceman.mynews.R;
import com.aceman.mynews.data.models.topstories.TopStorieResult;
import com.aceman.mynews.data.models.topstories.TopStories;
import com.aceman.mynews.data.api.NewsStream;
import com.aceman.mynews.ui.news.adapters.TopStoriesAdapter;
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
public class TopStoriesFragment extends Fragment  {
    public Disposable disposable;
    public List<TopStorieResult> mTopStories;

    public TopStoriesAdapter adapter;
    @BindView(R.id.topstories_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.spinner_topstories)
    ProgressBar mProgressBar;


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
        ButterKnife.bind(this,view);
        mProgressBar.setVisibility(View.VISIBLE);
        configureRecyclerView();
        executeHttpRequestWithRetrofit();
        return view ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void configureRecyclerView(){
        this.mTopStories = new ArrayList<>();
        this.adapter = new TopStoriesAdapter(this.mTopStories, Glide.with(this), getContext()) {
        };
        this.mRecyclerView.setAdapter(this.adapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    private void executeHttpRequestWithRetrofit(){
        this.disposable = NewsStream.streamGetTopStories("&page=1").subscribeWith(new DisposableObserver<TopStories>() {
            @Override
            public void onNext(TopStories details) {
                Log.e("TOP_Next","On Next");
                updateUI(details);
                mProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onError(Throwable e) {
                Log.e("TOP_Error","On Error"+Log.getStackTraceString(e));
                mProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onComplete() {
                Log.e("TOP_Complete","On Complete !!");
            }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }



    private void updateUI(TopStories details) {
        mTopStories.clear();
        mTopStories.addAll(details.getTopStorieResults());
        adapter.notifyDataSetChanged();
    }
}
