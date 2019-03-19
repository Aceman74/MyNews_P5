package com.aceman.mynews.ui.navigations.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.aceman.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewActivity extends AppCompatActivity {
@BindView(R.id.webview)
    WebView mWebView;
@BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        this.configureToolbar();
        String article = getIntent().getStringExtra("articleUrl");
        mWebView.loadUrl(article);
    }

    private void configureToolbar(){
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
