package com.aceman.mynews.data.models.search;

import com.google.gson.annotations.SerializedName;


public class Headline {

    @SerializedName("content_kicker")
    private String mContentKicker;
    @SerializedName("kicker")
    private String mKicker;
    @SerializedName("main")
    private String mMain;
    @SerializedName("name")
    private Object mName;
    @SerializedName("print_headline")
    private String mPrintHeadline;
    @SerializedName("seo")
    private Object mSeo;
    @SerializedName("sub")
    private Object mSub;

    public String getContentKicker() {
        return mContentKicker;
    }

    public void setContentKicker(String contentKicker) {
        mContentKicker = contentKicker;
    }

    public String getKicker() {
        return mKicker;
    }

    public void setKicker(String kicker) {
        mKicker = kicker;
    }

    public String getMain() {
        return mMain;
    }

    public void setMain(String main) {
        mMain = main;
    }

    public Object getName() {
        return mName;
    }

    public void setName(Object name) {
        mName = name;
    }

    public String getPrintHeadline() {
        return mPrintHeadline;
    }

    public void setPrintHeadline(String printHeadline) {
        mPrintHeadline = printHeadline;
    }

    public Object getSeo() {
        return mSeo;
    }

    public void setSeo(Object seo) {
        mSeo = seo;
    }

    public Object getSub() {
        return mSub;
    }

    public void setSub(Object sub) {
        mSub = sub;
    }

}
