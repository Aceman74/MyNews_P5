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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.aceman.mynews.R;
import com.aceman.mynews.data.api.NewsStream;
import com.aceman.mynews.data.models.shared.SharedDoc;
import com.aceman.mynews.data.models.shared.SharedObservable;
import com.aceman.mynews.ui.news.adapters.SharedAdapter;
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
public class TechnologyFragment extends Fragment {
    private Disposable mDisposable;
    private List<SharedDoc> mResponse;
    private SharedAdapter mAdapter;
    @BindView(R.id.no_internet_tech)
    ImageView mNoInternet;
    @BindView(R.id.tech_fragment_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.spinner_tech)
    ProgressBar mProgressBar;


    public TechnologyFragment() {
        // Required empty public constructor
    }

    public static TechnologyFragment newInstance() {
        return (new TechnologyFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tech, container, false);
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
        this.mResponse = new ArrayList<>();
        this.mAdapter = new SharedAdapter(this.mResponse, Glide.with(this),getContext()) {
        };
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    private void executeHttpRequestWithRetrofit(){
        this.mDisposable = NewsStream.streamGetTech().subscribeWith(new DisposableObserver<SharedObservable>() {
            @Override
            public void onNext(SharedObservable details) {
                Log.e("TECH_Next","On Next");
                mProgressBar.setVisibility(View.GONE);
                updateUI(details);

            }

            @Override
            public void onError(Throwable e) {

                Log.e("TECH_Error","On Error "+Log.getStackTraceString(e));
                mProgressBar.setVisibility(View.GONE);


            }

            @Override
            public void onComplete() {
                Log.e("TECH_Complete","On Complete !!");

            }
        });
    }
    private void disposeWhenDestroy(){
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    private void updateUI(SharedObservable details) {
        mResponse.clear();
        mResponse.addAll(details.getSharedResponse().getSharedDocs());
        mAdapter.notifyDataSetChanged();
    }
}
