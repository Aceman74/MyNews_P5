package com.aceman.mynews.ui.search.fragments;


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
import com.aceman.mynews.data.models.shared.SharedDoc;
import com.aceman.mynews.data.models.shared.SharedObservable;
import com.aceman.mynews.ui.news.adapters.SharedAdapter;
import com.aceman.mynews.ui.news.fragments.BaseFragment;
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
 * <b>Search Fragment</> get the search activity bundle to make api call for user <br>
 */
public class SearchFragment extends BaseFragment {
    @BindView(R.id.spinner_search)
    ProgressBar mProgressBar;
    @BindView(R.id.layout_check_connexion)
    LinearLayout mCheckConnexion;
    @BindView(R.id.layout_no_result)
    LinearLayout mNoResult;
    @BindView(R.id.retry_btn)
    Button mRetryBtn;
    @BindView(R.id.search_fragment_recyclerview)
    RecyclerView mRecyclerView;
    private Disposable disposable;
    private List<SharedDoc> mSearch;
    private SharedAdapter adapter;
    private String mSearchQuery = null;
    private String mBeginDate = null;
    private String mEndDate = null;
    private String mCategorie = null;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        return (new SearchFragment());
    }

    /**
     * On creation, load the strings from SearchActivity to set the recyclerview
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        bundleStrings();    //  Get Search data
        configureRecyclerView();
        executeHttpRequestWithRetrofit();
        return view;
    }

    /**
     * String bundle from SearchActivity with query, date and category(ies)
     */
    private void bundleStrings() {
        try {
            Bundle searchStrings = getArguments();
            mBeginDate = Objects.requireNonNull(searchStrings).getString("fromDatePicker");
            mEndDate = searchStrings.getString("toDatePicker");
            mSearchQuery = searchStrings.getString("query");
            mCategorie = searchStrings.getString("categories");
        } catch (Exception e) {
            Timber.tag("SEARCH_BUNDLE_ERROR").e("STRINGS NULL");
        }
    }

    /**
     * Destroy disposable on quit
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    /**
     * Set the recyclerview
     */
    private void configureRecyclerView() {
        this.mSearch = new ArrayList<>();
        this.adapter = new SharedAdapter(this.mSearch, Glide.with(this), getContext()) {
        };
        this.mRecyclerView.setAdapter(this.adapter);
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

            this.disposable = NewsStream.getInstance().streamGetSearch(mBeginDate, mEndDate, mSearchQuery, mCategorie).subscribeWith(new DisposableObserver<SharedObservable>() {
                @Override
                public void onNext(SharedObservable details) {
                    mProgressBar.setVisibility(View.GONE);
                    Timber.tag("SEARCH_Next").i("On Next");
                    Timber.tag("SEARCH_OBSERVABLE").i("from: " + mBeginDate + " to: " + mEndDate + " query: " + mSearchQuery + " categorie: " + mCategorie);
                    updateUI(details);
                }

                @Override
                public void onError(Throwable e) {
                    Timber.tag("SEARCH_Error").e("On Error%s", Log.getStackTraceString(e));
                    mProgressBar.setVisibility(View.GONE);
                    tooManyRefresh(e);  //  When user makes too many API call (shouldn't happen with BaseFragment Dispatcher fix)
                }

                @Override
                public void onComplete() {
                    Timber.tag("SEARCH_Complete").i("On Complete !!");
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
    private void updateUI(SharedObservable details) {
        mSearch.clear();
        mSearch.addAll(details.getSharedResponse().getSharedDocs());
        adapter.notifyDataSetChanged();
        ifNoResult();   //  If result is 0, show a screen
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
        return mSearch;
    }
}
