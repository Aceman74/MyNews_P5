package com.aceman.mynews.data.models.search;

import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("firstname")
    private String mFirstname;
    @SerializedName("lastname")
    private String mLastname;
    @SerializedName("middlename")
    private Object mMiddlename;
    @SerializedName("organization")
    private String mOrganization;
    @SerializedName("qualifier")
    private Object mQualifier;
    @SerializedName("rank")
    private Long mRank;
    @SerializedName("role")
    private String mRole;
    @SerializedName("title")
    private Object mTitle;

    public String getFirstname() {
        return mFirstname;
    }

    public void setFirstname(String firstname) {
        mFirstname = firstname;
    }

    public String getLastname() {
        return mLastname;
    }

    public void setLastname(String lastname) {
        mLastname = lastname;
    }

    public Object getMiddlename() {
        return mMiddlename;
    }

    public void setMiddlename(Object middlename) {
        mMiddlename = middlename;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public void setOrganization(String organization) {
        mOrganization = organization;
    }

    public Object getQualifier() {
        return mQualifier;
    }

    public void setQualifier(Object qualifier) {
        mQualifier = qualifier;
    }

    public Long getRank() {
        return mRank;
    }

    public void setRank(Long rank) {
        mRank = rank;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public Object getTitle() {
        return mTitle;
    }

    public void setTitle(Object title) {
        mTitle = title;
    }

}
