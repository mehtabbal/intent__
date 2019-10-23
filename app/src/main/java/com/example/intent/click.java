package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class click extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);

        WebView wView = (WebView) findViewById(R.id.wView);


        Intent i = getIntent();
        String load = i.getStringExtra("link");

        String url = "https://www.reddit.com"+load;
        Log.d("mess", String.valueOf(load));

        wView.getSettings().setJavaScriptEnabled(true);
        wView.setWebViewClient(new WebViewClient());
        wView.loadUrl(url);


    }
}
