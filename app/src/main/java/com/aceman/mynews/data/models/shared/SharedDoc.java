
package com.aceman.mynews.data.models.shared;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SharedDoc {

    @SerializedName("byline")
    private Byline mByline;
    @SerializedName("document_type")
    private String mDocumentType;
    @SerializedName("headline")
    private Headline mHeadline;
    @SerializedName("keywords")
    private List<Keyword> mKeywords;
    @SerializedName("lead_paragraph")
    private String mLeadParagraph;
    @SerializedName("multimedia")
    private List<Multimedium> mMultimedia;
    @SerializedName("news_desk")
    private String mNewsDesk;
    @SerializedName("print_page")
    private String mPrintPage;
    @SerializedName("pub_date")
    private String mPubDate;
    @SerializedName("score")
    private Double mScore;
    @SerializedName("section_name")
    private String mSectionName;
    @SerializedName("snippet")
    private String mSnippet;
    @SerializedName("source")
    private String mSource;
    @SerializedName("type_of_material")
    private String mTypeOfMaterial;
    @SerializedName("uri")
    private String mUri;
    @SerializedName("web_url")
    private String mWebUrl;
    @SerializedName("word_count")
    private Long mWordCount;
    @SerializedName("_id")
    private String m_id;


    public Byline getByline() {
        return mByline;
    }

    public void setByline(Byline byline) {
        mByline = byline;
    }

    public String getDocumentType() {
        return mDocumentType;
    }

    public void setDocumentType(String documentType) {
        mDocumentType = documentType;
    }

    public Headline getHeadline() {
        return mHeadline;
    }

    public void setHeadline(Headline headline) {
        mHeadline = headline;
    }

    public List<Keyword> getKeywords() {
        return mKeywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        mKeywords = keywords;
    }

    public String getLeadParagraph() {
        return mLeadParagraph;
    }

    public void setLeadParagraph(String leadParagraph) {
        mLeadParagraph = leadParagraph;
    }

    public List<Multimedium> getMultimedia() {
        return mMultimedia;
    }

    public void setMultimedia(List<Multimedium> multimedia) {
        mMultimedia = multimedia;
    }

    public String getNewsDesk() {
        return mNewsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        mNewsDesk = newsDesk;
    }

    public String getPrintPage() {
        return mPrintPage;
    }

    public void setPrintPage(String printPage) {
        mPrintPage = printPage;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public void setPubDate(String pubDate) {
        mPubDate = pubDate;
    }

    public Double getScore() {
        return mScore;
    }

    public void setScore(Double score) {
        mScore = score;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public void setSectionName(String sectionName) {
        mSectionName = sectionName;
    }

    public String getSnippet() {
        return mSnippet;
    }

    public void setSnippet(String snippet) {
        mSnippet = snippet;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public String getTypeOfMaterial() {
        return mTypeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        mTypeOfMaterial = typeOfMaterial;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public void setWebUrl(String webUrl) {
        mWebUrl = webUrl;
    }

    public Long getWordCount() {
        return mWordCount;
    }

    public void setWordCount(Long wordCount) {
        mWordCount = wordCount;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
