
package com.aceman.mynews.data.models.shared;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SharedResponse {

    @SerializedName("docs")
    private List<SharedDoc> mSharedDocs;
    @SerializedName("meta")
    private Meta mMeta;

    public List<SharedDoc> getSharedDocs() {
        return mSharedDocs;
    }

    public void setSharedDocs(List<SharedDoc> sharedDocs) {
        mSharedDocs = sharedDocs;
    }

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta meta) {
        mMeta = meta;
    }

}
