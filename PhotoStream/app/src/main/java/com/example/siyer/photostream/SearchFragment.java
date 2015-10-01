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
 * Search Fragment that lets you search on Google for images,
 * browse through them, and add the ones that you liked
 * to a collection database. These images can bee viewed from
 * the Collection fragment
 */
public class SearchFragment extends Fragment {
    public int urlIndex = 0;
    public ArrayList<String> urlList; //List of search results
    public WebView webImgView;
    public ArrayList<String> collectionURL; //List of added images
    CollDbHelper mDbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_main, container, false);

        MainActivity activity = (MainActivity)getActivity();
        collectionURL = activity.collectionUrl; //get the list from the main activity

        ////OnCLick Listener for SEARCH button
        final View search_but = rootview.findViewById(R.id.search_button);//Listener for search button
        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText search_word = (EditText) getActivity().findViewById(R.id.search_text);//Text to be searched
                final String search_word_string = search_word.getText().toString();

                //Handling the search and returning the results
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
        //OnCLick Listener for PREV button
        final View next_but = rootview.findViewById(R.id.next_button);
        next_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlList.size()>0) {
                    if (urlIndex < urlList.size() - 1) {
                        urlIndex += 1;
                    } else {
                        urlIndex = 0;
                    }
                    getImageFromURL();
                }
            }
        });
        //OnCLick Listener for PREV button
        final View prev_but = rootview.findViewById(R.id.prev_button);
        prev_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlList.size() > 0) {
                    if (urlIndex >= 1) {
                        urlIndex -= 1;
                    } else {
                        urlIndex = urlList.size() - 1;
                    }
                    getImageFromURL();
                }
            }
        });
        //OnCLick Listener for ADD button
        final View add_but = rootview.findViewById(R.id.add_button);
        add_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!collectionURL.contains(urlList.get(urlIndex))) {
                    mDbHelper = new CollDbHelper(getActivity().getBaseContext()); //initialize database
                    mDbHelper.writeDatabase(urlList.get(urlIndex));

                    collectionURL.add(urlList.get(urlIndex));//Add it to collection
                    Toast.makeText(getActivity().getBaseContext(), "Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getBaseContext(), "Already in the collection", Toast.LENGTH_LONG).show();
                }
            }
        });
        //OnCLick Listener for changing into COLLECTION fragment
        final View coll_but = rootview.findViewById(R.id.coll_button);
        coll_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeToColl();
            }
        });
        //Initializing WebView
        webImgView = (WebView) rootview.findViewById(R.id.webView);
        webImgView.loadUrl(getString(R.string.default_img));
        return rootview;
    }

    //Handles updating the WebView
    public void getImageFromURL() {
        String urlToShow = urlList.get(urlIndex);
        webImgView.setWebViewClient(new WebViewClient());
        WebSettings settings = webImgView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        
        try {
            webImgView.loadUrl(urlToShow);
            Log.i("PRINTER", urlToShow);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("PRINTER", "WebView failed..." + "\t" + urlToShow);
        }
    }

}

