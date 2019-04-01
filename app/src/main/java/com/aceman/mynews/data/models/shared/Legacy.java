package com.aceman.mynews.data.models.shared;

import com.google.gson.annotations.SerializedName;

public class Legacy {

    @SerializedName("xlarge")
    private String mXlarge;
    @SerializedName("xlargeheight")
    private Long mXlargeheight;
    @SerializedName("xlargewidth")
    private Long mXlargewidth;

    public String getXlarge() {
        return mXlarge;
    }

    public void setXlarge(String xlarge) {
        mXlarge = xlarge;
    }

    public Long getXlargeheight() {
        return mXlargeheight;
    }

    public void setXlargeheight(Long xlargeheight) {
        mXlargeheight = xlargeheight;
    }

    public Long getXlargewidth() {
        return mXlargewidth;
    }

    public void setXlargewidth(Long xlargewidth) {
        mXlargewidth = xlargewidth;
    }

}
