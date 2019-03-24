
package com.aceman.mynews.data.models.search;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchResponse {

    @SerializedName("docs")
    private List<Doc> mDocs;
    @SerializedName("meta")
    private Meta mMeta;

    public List<Doc> getDocs() {
        return mDocs;
    }

    public void setDocs(List<Doc> docs) {
        mDocs = docs;
    }

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta meta) {
        mMeta = meta;
    }

}
