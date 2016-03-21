package com.yuyakaido.android.genesis.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.yuyakaido.android.genesis.R;

import java.io.Serializable;

/**
 * Created by yuyakaido on 3/13/16.
 */
public class WebViewActivity extends BaseActivity {

    private static final String ARGS_WEB_VIEW_SPEC = "ARGS_WEB_VIEW_SPEC";

    public static class WebViewSpec implements Serializable {
        public String url;
    }

    public static Intent createIntent(Context context, String url) {
        WebViewSpec spec = new WebViewSpec();
        spec.url = url;
        return createIntent(context, spec);
    }

    private static Intent createIntent(Context context, WebViewSpec spec) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(ARGS_WEB_VIEW_SPEC, spec);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        WebViewSpec spec = (WebViewSpec) intent.getSerializableExtra(ARGS_WEB_VIEW_SPEC);

        WebView webView = (WebView) findViewById(R.id.activity_web_view_web_view);
        webView.loadUrl(spec.url);
    }

}
