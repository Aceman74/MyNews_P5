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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.aceman.mynews.R;
import com.aceman.mynews.data.api.NewsStream;
import com.aceman.mynews.data.models.shared.SharedDoc;
import com.aceman.mynews.data.models.shared.SharedObservable;
import com.aceman.mynews.ui.news.adapters.SharedAdapter;
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
public class SportsFragment extends FragmentsBase {
    @BindView(R.id.sports_fragment_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.spinner_sports)
    ProgressBar mProgressBar;
    Disposable mDisposable;
    List<SharedDoc> mResponse;
    SharedAdapter mAdapter;
    @BindView(R.id.layout_check_connexion)
    LinearLayout mCheckConnexion;
    @BindView(R.id.layout_no_result)
    LinearLayout mNoResult;
    @BindView(R.id.retry_btn)
    Button mRetryBtn;

    public SportsFragment() {
    }

    public static SportsFragment newInstance() {
        return (new SportsFragment());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sports, container, false);
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
        mResponse = new ArrayList<>();
        mAdapter = new SharedAdapter(mResponse, Glide.with(this), getContext()) {
        };
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    public void executeHttpRequestWithRetrofit() {
        if (isOnline()) {
            mProgressBar.setVisibility(View.VISIBLE);
            mCheckConnexion.setVisibility(View.GONE);

            this.mDisposable = NewsStream.streamGetSports().subscribeWith(new DisposableObserver<SharedObservable>() {
                @Override
                public void onNext(SharedObservable details) {
                    Log.e("CARS_Next", "On Next");
                    mProgressBar.setVisibility(View.GONE);
                    updateUI(details);

                }

                @Override
                public void onError(Throwable e) {
                    Log.e("CARS_Error", "On Error" + Log.getStackTraceString(e));
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onComplete() {
                    Log.e("CARS_Complete", "On Complete !!");

                }
            });
        } else {
            mProgressBar.setVisibility(View.GONE);
            mCheckConnexion.setVisibility(View.VISIBLE);
            retryBtnClick();
        }
    }

    public void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    public void updateUI(SharedObservable details) {
        mResponse.clear();
        mResponse.addAll(details.getSharedResponse().getSharedDocs());
        mAdapter.notifyDataSetChanged();
        RecyclerAnimation.runLayoutAnimation(mRecyclerView);
        ifNoResult(details);
    }
}
