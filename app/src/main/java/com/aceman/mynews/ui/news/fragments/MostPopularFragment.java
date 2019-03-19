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

import com.aceman.mynews.R;
import com.aceman.mynews.data.models.mostpopular.MostPopular;
import com.aceman.mynews.data.models.mostpopular.PopularResult;
import com.aceman.mynews.data.api.NewsStream;
import com.aceman.mynews.ui.news.adapters.MostPopularAdapter;
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
public class MostPopularFragment extends Fragment {
    private Disposable disposable;
    private List<PopularResult> mPopular;
    private MostPopularAdapter adapter;
    @BindView(R.id.mostpopular_fragment_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.spinner_mostpopular)
    ProgressBar mProgressBar;



    public MostPopularFragment() {
        // Required empty public constructor
    }

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);
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
        this.mPopular = new ArrayList<>();
        this.adapter = new MostPopularAdapter(this.mPopular, Glide.with(this),getContext()) {
        };
        this.mRecyclerView.setAdapter(this.adapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }
    private void executeHttpRequestWithRetrofit(){
        this.disposable = NewsStream.streamGetMostPopular(7).subscribeWith(new DisposableObserver<MostPopular>() {
            @Override
            public void onNext(MostPopular details) {
                Log.e("POPULAR_Next","On Next");
                mProgressBar.setVisibility(View.GONE);
                updateUI(details);

            }

            @Override
            public void onError(Throwable e) {
                Log.e("POPULAR_Error","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("POPULAR_Complete","On Complete !!");

            }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void updateUI(MostPopular details) {
        mPopular.clear();
        mPopular.addAll(details.getPopularResults());
        adapter.notifyDataSetChanged();
    }
}
