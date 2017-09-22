package com.webxzen.ridersapp.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.util.Argument;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by behestee on 9/22/17.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.wv_main)
    WebView wvMain;

    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        showBackButton(true);

        link = getIntent().getStringExtra(Argument.LINK);
        setTitle(getIntent().getStringExtra(Argument.TITLE));
//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        wvMain.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Do something on page loading started
                // Visible the progressbar
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url){
                // Do something when page loading finished
//                Toast.makeText(mContext,"Page Loaded.",Toast.LENGTH_SHORT).show();
            }

        });

        /*
            WebView
                A View that displays web pages. This class is the basis upon which you can roll your
                own web browser or simply display some online content within your Activity. It uses
                the WebKit rendering engine to display web pages and includes methods to navigate
                forward and backward through a history, zoom in and out, perform text searches and more.

            WebChromeClient
                 WebChromeClient is called when something that might impact a browser UI happens,
                 for instance, progress updates and JavaScript alerts are sent here.
        */
        wvMain.setWebChromeClient(new WebChromeClient(){
            /*
                public void onProgressChanged (WebView view, int newProgress)
                    Tell the host application the current progress of loading a page.

                Parameters
                    view : The WebView that initiated the callback.
                    newProgress : Current page loading progress, represented by an integer
                        between 0 and 100.
            */
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                progressBar.setProgress(newProgress);
                if(newProgress == 100){
                    // Hide the progressbar
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        wvMain.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && wvMain.canGoBack()) {
                    wvMain.goBack();
                    return true;
                }
                return false;
            }
        });

        // Enable the javascript
        wvMain.getSettings().setJavaScriptEnabled(true);
        // Render the web page
        wvMain.loadUrl(link);

    }
}
