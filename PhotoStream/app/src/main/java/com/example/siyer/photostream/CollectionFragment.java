package com.example.siyer.photostream;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

/**
 * Created by siyer on 9/28/2015.
 */

public class CollectionFragment extends Fragment {
    public WebView webFeedView;
    public int feedIndex =0;
    public ArrayList<String> collectionUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.collection_fragment, container, false);

        Log.d("Init feedIndex", String.valueOf(feedIndex));
        MainActivity activity = (MainActivity) getActivity();
        collectionUrl = activity.collectionUrl;

        Log.d("Init List", activity.collectionUrl.toString());
        final View next_but = rootview.findViewById(R.id.next_button_fd);
        next_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click next", collectionUrl.toString());
                if (feedIndex < collectionUrl.size() - 1) {
                    feedIndex += 1;
                } else {
                    feedIndex = 0;
                }
                Log.d("Index next changed", String.valueOf(feedIndex));

                getImageFromColl(collectionUrl);
            }
        });

        final View rem_but = rootview.findViewById(R.id.remove_button_fd);
        rem_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionUrl.size()>=2) {
                    collectionUrl.remove(feedIndex);
                    feedIndex -=1;
                    getImageFromColl(collectionUrl);}
                else{
                    webFeedView.loadUrl("http://www.clickertraining.com/files/u1/lab_puppy_250.jpg");
                }
            }
        });

        final View prev_but = rootview.findViewById(R.id.prev_button_fd);
        prev_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedIndex>=1){
                    feedIndex -= 1;}
                else{
                    feedIndex = collectionUrl.size()-1;
                }
                Log.d("Index prev changed",String.valueOf(feedIndex));
                getImageFromColl(collectionUrl);
            }
        });

        final View search_but_fd = rootview.findViewById(R.id.search_button_fd);
        search_but_fd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeToSearch();
            }
        });

        webFeedView = (WebView) rootview.findViewById(R.id.webView_fd);
        webFeedView.loadUrl("http://www.clickertraining.com/files/u1/lab_puppy_250.jpg");
        Log.d("Before WV loads", collectionUrl.toString());
        return rootview;
    }
    public void getImageFromColl(ArrayList<String> collectionUrl) {
        Log.d("Inside Image Coll",String.valueOf(feedIndex));
        String urlToShow = collectionUrl.get(feedIndex);
        Log.i("PRINTER", "FeedFragment : updateWebView about to run...");
        webFeedView.setWebViewClient(new WebViewClient());
        WebSettings settings = webFeedView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        Log.d("Search wv Index", urlToShow);
        try {
            webFeedView.loadUrl(urlToShow);
            Log.i("PRINTER", urlToShow);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("PRINTER", "WebView failed..." + "\t" + urlToShow);
        }
    }
}