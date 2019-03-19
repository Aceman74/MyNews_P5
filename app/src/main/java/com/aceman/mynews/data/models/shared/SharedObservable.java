
package com.aceman.mynews.data.models.shared;

import com.google.gson.annotations.SerializedName;

public class SharedObservable {

    @SerializedName("copyright")
    private String mCopyright;
    @SerializedName("response")
    private SharedResponse mSharedResponse;
    @SerializedName("status")
    private String mStatus;

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String copyright) {
        mCopyright = copyright;
    }

    public SharedResponse getSharedResponse() {
        return mSharedResponse;
    }

    public void setSharedResponse(SharedResponse sharedResponse) {
        mSharedResponse = sharedResponse;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
