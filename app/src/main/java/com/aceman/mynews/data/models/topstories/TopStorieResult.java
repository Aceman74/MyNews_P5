
package com.aceman.mynews.data.models.topstories;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TopStorieResult {

    @SerializedName("abstract")
    private String mAbstract;
    @SerializedName("byline")
    private String mByline;
    @SerializedName("created_date")
    private String mCreatedDate;
    @SerializedName("item_type")
    private String mItemType;
    @SerializedName("kicker")
    private String mKicker;
    @SerializedName("multimedia")
    private List<Multimedium> mMultimedia;
    @SerializedName("published_date")
    private String mPublishedDate;
    @SerializedName("section")
    private String mSection;
    @SerializedName("short_url")
    private String mShortUrl;
    @SerializedName("subsection")
    private String mSubsection;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("updated_date")
    private String mUpdatedDate;
    @SerializedName("url")
    private String mUrl;

    public String getAbstract() {
        return mAbstract;
    }

    public void setAbstract(String isAbstract) {
        mAbstract = isAbstract;
    }

    public String getByline() {
        return mByline;
    }

    public void setByline(String byline) {
        mByline = byline;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public String getItemType() {
        return mItemType;
    }

    public void setItemType(String itemType) {
        mItemType = itemType;
    }

    public String getKicker() {
        return mKicker;
    }

    public void setKicker(String kicker) {
        mKicker = kicker;
    }

    public List<Multimedium> getMultimedia() {
        return mMultimedia;
    }

    public void setMultimedia(List<Multimedium> multimedia) {
        mMultimedia = multimedia;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        mPublishedDate = publishedDate;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public String getShortUrl() {
        return mShortUrl;
    }

    public void setShortUrl(String shortUrl) {
        mShortUrl = shortUrl;
    }

    public String getSubsection() {
        return mSubsection;
    }

    public void setSubsection(String subsection) {
        mSubsection = subsection;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUpdatedDate() {
        return mUpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        mUpdatedDate = updatedDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
