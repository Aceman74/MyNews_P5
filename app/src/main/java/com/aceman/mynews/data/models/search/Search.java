package com.aceman.mynews.data.models.search;

import com.google.gson.annotations.SerializedName;


public class Search {

    @SerializedName("copyright")
    private String mCopyright;
    @SerializedName("response")
    private SearchResponse mSearchResponse;
    @SerializedName("status")
    private String mStatus;

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String copyright) {
        mCopyright = copyright;
    }

    public SearchResponse getSearchResponse() {
        return mSearchResponse;
    }

    public void setSearchResponse(SearchResponse searchResponse) {
        mSearchResponse = searchResponse;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
