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
import com.aceman.mynews.data.api.NewsStream;
import com.aceman.mynews.data.models.mostpopular.MostPopular;
import com.aceman.mynews.data.models.mostpopular.PopularResult;
import com.aceman.mynews.ui.news.adapters.MostPopularAdapter;
import com.aceman.mynews.utils.RecyclerAnimation;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

/**
 * Created by Lionel JOFFRAY.
 * <p>
 * <b>Most Popular Fragment</> Makes and show his category results, extends <b>BaseFragment</b> <br>
 */
public class MostPopularFragment extends BaseFragment {
    @BindView(R.id.mostpopular_fragment_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.spinner_mostpopular)
    ProgressBar mProgressBar;
    @BindView(R.id.layout_check_connexion)
    LinearLayout mCheckConnexion;
    @BindView(R.id.layout_no_result)
    LinearLayout mNoResult;
    @BindView(R.id.retry_btn)
    Button mRetryBtn;
    private Disposable disposable;
    private List<PopularResult> mPopular;
    private MostPopularAdapter adapter;


    public MostPopularFragment() {
    }

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
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
        return mPopular;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);
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
        mPopular = new ArrayList<>();
        adapter = new MostPopularAdapter(mPopular, Glide.with(this), getContext()) {
        };
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
    }

    /**
     * Execute the request using Retrofit if isOnline() is true.
     * Different errors are handled here: if no internet, if result is 0 articles, if too many API request.
     */
    private void executeHttpRequestWithRetrofit() {
        if (isOnline()) {
            mProgressBar.setVisibility(View.VISIBLE);
            mCheckConnexion.setVisibility(View.GONE);

            this.disposable = NewsStream.getInstance().streamGetMostPopular(7).subscribeWith(new DisposableObserver<MostPopular>() {
                @Override
                public void onNext(MostPopular details) {
                    Timber.tag("POPULAR_Next").i("On Next");
                    mProgressBar.setVisibility(View.GONE);
                    updateUI(details);  //  Update RecyclerView
                }

                @Override
                public void onError(Throwable e) {
                    Timber.tag("POPULAR_Error").e("On Error%s", Log.getStackTraceString(e));
                    mProgressBar.setVisibility(View.GONE);
                    //  tooManyRefresh() method is not necessary here, no call limitation
                }

                @Override
                public void onComplete() {
                    Timber.tag("POPULAR_Complete").i("On Complete !!");
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

    /**
     * Update the view after every request
     *
     * @param details data from response
     */
    private void updateUI(MostPopular details) {
        mPopular.clear();
        mPopular.addAll(details.getPopularResults());
        adapter.notifyDataSetChanged();
        RecyclerAnimation.runLayoutAnimation(mRecyclerView);
        ifNoResult();   //  If result is 0, show a screen
    }
}
