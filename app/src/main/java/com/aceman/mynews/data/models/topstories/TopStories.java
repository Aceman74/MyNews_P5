package com.aceman.mynews.data.models.topstories;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopStories {

    @SerializedName("copyright")
    private String mCopyright;
    @SerializedName("last_updated")
    private String mLastUpdated;
    @SerializedName("num_results")
    private Long mNumResults;
    @SerializedName("results")
    private List<TopStorieResult> mTopStorieResults;
    @SerializedName("section")
    private String mSection;
    @SerializedName("status")
    private String mStatus;

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String copyright) {
        mCopyright = copyright;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        mLastUpdated = lastUpdated;
    }

    public Long getNumResults() {
        return mNumResults;
    }

    public void setNumResults(Long numResults) {
        mNumResults = numResults;
    }

    public List<TopStorieResult> getTopStorieResults() {
        return mTopStorieResults;
    }

    public void setTopStorieResults(List<TopStorieResult> topStorieResults) {
        mTopStorieResults = topStorieResults;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
