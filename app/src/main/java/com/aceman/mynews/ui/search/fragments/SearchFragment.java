package com.aceman.mynews.ui.search.fragments;


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

import com.aceman.mynews.R;
import com.aceman.mynews.data.api.NewsStream;
import com.aceman.mynews.data.models.search.Doc;
import com.aceman.mynews.data.models.search.Search;
import com.aceman.mynews.ui.news.adapters.SearchAdapter;
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
public class SearchFragment extends Fragment {
    public Disposable disposable;
    public List<Doc> mSearch;
    public SearchAdapter adapter;
    @BindView(R.id.search_fragment_imagez_view)
    ImageView mNoResult;
    @BindView(R.id.search_fragment_recyclerview)
    RecyclerView mRecyclerView;
    String mSearchQuery = null;
    String mBeginDate = null;
    String mEndDate = null;
    String mCategorie = null;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return (new SearchFragment());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        bundleStrings();
        configureRecyclerView();
        executeHttpRequestWithRetrofit();
        return view;
    }

    public void bundleStrings() {
        try {
            Bundle searchStrings = getArguments();

            mBeginDate = searchStrings.getString("fromDatePicker");
            mEndDate = searchStrings.getString("toDatePicker");
            mSearchQuery = searchStrings.getString("query");
            mCategorie = searchStrings.getString("categories");
        } catch (Exception e) {
            Log.e("SEARCH BUNDLE ERROR", "Mauvaise redirection, STRINGS NULL");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    public void configureRecyclerView() {
        this.mSearch = new ArrayList<>();
        this.adapter = new SearchAdapter(this.mSearch, Glide.with(this), getContext()) {
        };
        this.mRecyclerView.setAdapter(this.adapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    public void executeHttpRequestWithRetrofit() {
        this.disposable = NewsStream.streamGetSearch(mBeginDate, mEndDate, mSearchQuery, mCategorie).subscribeWith(new DisposableObserver<Search>() {
            @Override
            public void onNext(Search details) {
                Log.e("SEARCH_Next", "On Next");
                Log.d("SEARCH OBSERVABLE", "from: " + mBeginDate + " to: " + mEndDate + " query: " + mSearchQuery + " categorie: " + mCategorie);
                updateUI(details);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("SEARCH_Error", "On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("SEARCH_Complete", "On Complete !!");
            }
        });
    }

    public void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }


    public void updateUI(Search details) {
        mSearch.clear();
        mSearch.addAll(details.getSearchResponse().getDocs());
        adapter.notifyDataSetChanged();
        if (mSearch.isEmpty()) {
            mNoResult.setVisibility(View.VISIBLE);
        }
    }
}
