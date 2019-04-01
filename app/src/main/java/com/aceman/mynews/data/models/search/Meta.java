package com.aceman.mynews.data.models.search;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Meta {

    @SerializedName("hits")
    private Long mHits;
    @SerializedName("offset")
    private Long mOffset;
    @SerializedName("time")
    private Long mTime;

    public Long getHits() {
        return mHits;
    }

    public void setHits(Long hits) {
        mHits = hits;
    }

    public Long getOffset() {
        return mOffset;
    }

    public void setOffset(Long offset) {
        mOffset = offset;
    }

    public Long getTime() {
        return mTime;
    }

    public void setTime(Long time) {
        mTime = time;
    }

}
