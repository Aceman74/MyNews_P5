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
public class TravelFragment extends Fragment {
    @BindView(R.id.travel_fragment_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.spinner_travel)
    ProgressBar mProgressBar;
    Disposable mDisposable;
    List<SharedDoc> mResponse;
    SharedAdapter mAdapter;
    @BindView(R.id.layout_check_connexion)
    LinearLayout mCheckConnexion;
    @BindView(R.id.retry_btn)
    Button mRetryBtn;

    public TravelFragment() {
    }

    public static TravelFragment newInstance() {
        return (new TravelFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel, container, false);
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
        this.mResponse = new ArrayList<>();
        this.mAdapter = new SharedAdapter(this.mResponse, Glide.with(this), getContext()) {
        };
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    public void executeHttpRequestWithRetrofit() {
        if(isOnline()){
        mProgressBar.setVisibility(View.VISIBLE);
        mCheckConnexion.setVisibility(View.GONE);

        this.mDisposable = NewsStream.streamGetTravel().subscribeWith(new DisposableObserver<SharedObservable>() {
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
        }else {
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
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void updateUI(SharedObservable details) {
        mResponse.clear();
        mResponse.addAll(details.getSharedResponse().getSharedDocs());
        mAdapter.notifyDataSetChanged();
        RecyclerAnimation.runLayoutAnimation(mRecyclerView);
    }
}
