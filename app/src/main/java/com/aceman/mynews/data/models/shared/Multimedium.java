package com.aceman.mynews.data.models.shared;

import com.google.gson.annotations.SerializedName;

public class Multimedium {

    @SerializedName("caption")
    private Object mCaption;
    @SerializedName("credit")
    private Object mCredit;
    @SerializedName("crop_name")
    private String mCropName;
    @SerializedName("height")
    private Long mHeight;
    @SerializedName("legacy")
    private Legacy mLegacy;
    @SerializedName("rank")
    private Long mRank;
    @SerializedName("subType")
    private String mSubType;
    @SerializedName("subtype")
    private String mSubtype;
    @SerializedName("type")
    private String mType;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("width")
    private Long mWidth;

    public Object getCaption() {
        return mCaption;
    }

    public void setCaption(Object caption) {
        mCaption = caption;
    }

    public Object getCredit() {
        return mCredit;
    }

    public void setCredit(Object credit) {
        mCredit = credit;
    }

    public String getCropName() {
        return mCropName;
    }

    public void setCropName(String cropName) {
        mCropName = cropName;
    }

    public Long getHeight() {
        return mHeight;
    }

    public void setHeight(Long height) {
        mHeight = height;
    }

    public Legacy getLegacy() {
        return mLegacy;
    }

    public void setLegacy(Legacy legacy) {
        mLegacy = legacy;
    }

    public Long getRank() {
        return mRank;
    }

    public void setRank(Long rank) {
        mRank = rank;
    }

    public String getSubType() {
        return mSubType;
    }

    public void setSubType(String subType) {
        mSubType = subType;
    }

    public String getSubtype() {
        return mSubtype;
    }

    public void setSubtype(String subtype) {
        mSubtype = subtype;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUrl() {
        String baseUrl = "https://static01.nyt.com/";

        return baseUrl + mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Long getWidth() {
        return mWidth;
    }

    public void setWidth(Long width) {
        mWidth = width;
    }

}
