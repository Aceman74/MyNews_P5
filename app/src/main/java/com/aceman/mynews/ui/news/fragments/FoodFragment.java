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
import com.aceman.mynews.data.models.shared.SharedDoc;
import com.aceman.mynews.data.models.shared.SharedObservable;
import com.aceman.mynews.ui.news.adapters.SharedAdapter;
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
 * <b>Food Fragment</> Makes and show his category results, extends <b>FragmentBase</b> <br>
 */
public class FoodFragment extends FragmentBase {
    @BindView(R.id.food_fragment_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.spinner_food)
    ProgressBar mProgressBar;
    @BindView(R.id.layout_check_connexion)
    LinearLayout mCheckConnexion;
    @BindView(R.id.layout_no_result)
    LinearLayout mNoResult;
    @BindView(R.id.retry_btn)
    Button mRetryBtn;
    private Disposable mDisposable;
    private List<SharedDoc> mResponse;
    private SharedAdapter mAdapter;

    public FoodFragment() {
    }

    public static FoodFragment newInstance() {
        return (new FoodFragment());
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
        return mResponse;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        ButterKnife.bind(this, view);
        mProgressBar.setVisibility(View.VISIBLE);
        isOnline(); //  Check internet connection
        new asyncRetrofitRequest().execute("request");  //  Make the request call
        configureRecyclerView();
        return view;
    }

    /**
     * Destroy disposable on destroy
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    /**
     * Set the recyclerview with list result
     */
    private void configureRecyclerView() {
        this.mResponse = new ArrayList<>();
        this.mAdapter = new SharedAdapter(this.mResponse, Glide.with(this), getContext()) {
        };
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
    }

    /**
     * Execute the request using Retrofit if isOnline() is true.
     * Different errors are handled here: if no internet, if result is 0 articles, if too many API request.
     */
    private void executeHttpRequestWithRetrofit() {
        if (isOnline()) {
            mProgressBar.setVisibility(View.VISIBLE);
            mCheckConnexion.setVisibility(View.GONE);

            NewYorkTimesService newsStream = setRetrofit().create(NewYorkTimesService.class);
            this.mDisposable = NewsStream.streamGetFood(newsStream).subscribeWith(new DisposableObserver<SharedObservable>() {
                @Override
                public void onNext(SharedObservable details) {
                    Log.e("FOOD_Next", "On Next");
                    mProgressBar.setVisibility(View.GONE);
                    updateUI(details);  //  Update RecyclerView
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("FOOD_Error", "On Error" + Log.getStackTraceString(e));
                    mProgressBar.setVisibility(View.GONE);
                    tooManyRefresh(e);  //  When user makes too many API call (shouldn't happen with FragmentBase Dispatcher fix)
                }

                @Override
                public void onComplete() {
                    Log.e("FOOD_Complete", "On Complete !!");
                }
            });
        } else {
            mProgressBar.setVisibility(View.GONE);
            mCheckConnexion.setVisibility(View.VISIBLE);
            retryBtnClick();    //  If no connection, show refresh btn
        }
    }

    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }

    /**
     * Update the view after every request
     *
     * @param details data from response
     */
    private void updateUI(SharedObservable details) {
        mResponse.clear();
        mResponse.addAll(details.getSharedResponse().getSharedDocs());
        mAdapter.notifyDataSetChanged();
        RecyclerAnimation.runLayoutAnimation(mRecyclerView);
        ifNoResult();   //  If result is 0, show a screen
    }

}
