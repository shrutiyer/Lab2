package com.example.siyer.photostream;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {
    public int urlIndex = 0;
    public ArrayList<String> urlList;
    public WebView webImgView;
    ArrayList<String> collectionURL = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        final View search_but = rootview.findViewById(R.id.search_button);//Listener for search button
        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText search_word = (EditText) getActivity().findViewById(R.id.search_text);//Text to be searched
                final String search_word_string = search_word.getText().toString();
                HttpHandler searchHandler = new HttpHandler(SearchFragment.this.getActivity(), SearchFragment.this);
                searchHandler.searchGoogle(search_word_string, new Callback() {
                    @Override
                    public void resultsCallback(ArrayList<String> urlArray) {
                        urlList = urlArray;
                        getImageFromURL();
                    }
                });
            }
        });

        final View next_but = rootview.findViewById(R.id.next_button);
        next_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(urlIndex<urlList.size()-1){
                    urlIndex += 1;}
                else{
                    urlIndex = 0;
                }
                Log.d("Index next changed",String.valueOf(urlIndex));

                getImageFromURL();
            }
        });

        final View prev_but = rootview.findViewById(R.id.prev_button);
        prev_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(urlIndex>=1){
                    urlIndex -= 1;}
                else{
                    urlIndex = urlList.size()-1;
                }
                Log.d("Index prev changed",String.valueOf(urlIndex));
                getImageFromURL();
            }
        });

        final View add_but = rootview.findViewById(R.id.add_button);
        add_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!collectionURL.contains(urlList.get(urlIndex))) {
                    collectionURL.add(urlList.get(urlIndex));
                    Log.d("collection", collectionURL.toString());
                }
            }
        });

        final View coll_but = rootview.findViewById(R.id.coll_button);
        coll_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeToColl();
            }
        });

        webImgView = (WebView) rootview.findViewById(R.id.webView);
        webImgView.loadUrl("https://upload.wikimedia.org/wikipedia/commons/7/70/Solid_white.svg");
        return rootview;
    }

    public void getImageFromURL() {
        String urlToShow = urlList.get(urlIndex);
        webImgView.setWebViewClient(new WebViewClient());
        WebSettings settings = webImgView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        Log.d("Search wv Index", urlToShow);
        try {
            webImgView.loadUrl(urlToShow);
            Log.i("PRINTER", urlToShow);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("PRINTER", "WebView failed..." + "\t" + urlToShow);
        }
    }
    public ArrayList<String> getList() {
        Log.d("In getList", collectionURL.toString());
        return collectionURL;
    }

}

