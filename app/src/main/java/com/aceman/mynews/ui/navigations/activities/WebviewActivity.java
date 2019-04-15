package com.aceman.mynews.ui.navigations.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.aceman.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lionel JOFFRAY.
 * <p>
 * <b>WebviewActivity</> who show articles in a webview
 */
public class WebviewActivity extends AppCompatActivity {
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        this.configureToolbar();
        String article = getIntent().getStringExtra("UrlWebview");
        mWebView.loadUrl(article);
        mWebView.getSettings().setJavaScriptEnabled(true);
    }

    /**
     * Set the toolbar
     */
    private void configureToolbar() {
        //Set the toolbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
