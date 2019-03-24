
package com.aceman.mynews.data.models.mostpopular;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class MostPopular {

    @SerializedName("copyright")
    private String mCopyright;
    @SerializedName("num_results")
    private Long mNumResults;
    @SerializedName("results")
    private List<PopularResult> mPopularResults;
    @SerializedName("status")
    private String mStatus;

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String copyright) {
        mCopyright = copyright;
    }

    public Long getNumResults() {
        return mNumResults;
    }

    public void setNumResults(Long numResults) {
        mNumResults = numResults;
    }

    public List<PopularResult> getPopularResults() {
        return mPopularResults;
    }

    public void setPopularResults(List<PopularResult> popularResults) {
        mPopularResults = popularResults;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
