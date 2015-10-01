package com.example.siyer.photostream;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by siyer on 9/28/2015.
 * This Collection Fragment displays all the saved images in the
 * database and allows to browse/remove them.
 */

public class CollectionFragment extends Fragment {
    public WebView webFeedView;
    public int feedIndex = 0; //Current Image index for WebView
    public ArrayList<String> collectionUrl; //Array with all the URLs in my collection
    CollDbHelper mDbHelper; //Database Helper

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.collection_fragment, container, false);

        mDbHelper = new CollDbHelper(getActivity()); //Initializing database and reading it
        collectionUrl = mDbHelper.readDatabase();

        webFeedView = (WebView) rootview.findViewById(R.id.webView_fd);
        webFeedView.loadUrl(getString((R.string.default_img))); //Init WebView and load default
        Toast.makeText(getActivity().getBaseContext(), getString(R.string.next_prev),Toast.LENGTH_SHORT).show();
        getImageFromColl(collectionUrl);

        //OnCLick Listener for NEXT button
        final View next_but = rootview.findViewById(R.id.next_button_fd);
        next_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Various normal and edge cases
                if (collectionUrl.size()>=1) {
                    if (feedIndex < collectionUrl.size() - 1) {
                        feedIndex += 1;
                        getImageFromColl(collectionUrl);
                    } else {
                        feedIndex = 0;
                        getImageFromColl(collectionUrl);
                    }
                }else if (collectionUrl.size()==0){
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.no_img_toast),Toast.LENGTH_LONG).show();
                    webFeedView.loadUrl(getString(R.string.default_img));
                }
            }
        });

        //OnCLick Listener for REMOVE button
        final View rem_but = rootview.findViewById(R.id.remove_button_fd);
        rem_but.setOnClickListener(new View.OnClickListener() {
            @Override
            //various cases; show no image available if collectionUrl is empty
            public void onClick(View v) {
                if (collectionUrl.size()>=2) {
                    mDbHelper.deleteDatabase(collectionUrl.get(feedIndex));
                    collectionUrl.remove(feedIndex);
                    if (feedIndex>=1) {
                        feedIndex -= 1;
                    }else{
                        feedIndex = 0;
                    }
                    getImageFromColl(collectionUrl);}
                else if(collectionUrl.size()==1){
                    collectionUrl.remove(feedIndex);
                    webFeedView.loadUrl(getString(R.string.default_img));
                }else {
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.add_img),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //OnCLick Listener for PREVIOUS button
        final View prev_but = rootview.findViewById(R.id.prev_button_fd);
        prev_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionUrl.size()>=1) {
                    if (feedIndex >= 1) {
                        feedIndex -= 1;
                        getImageFromColl(collectionUrl);
                    } else {
                        feedIndex = collectionUrl.size() - 1;
                        getImageFromColl(collectionUrl);
                    }
                }else {
                    //Toast to improve UX and inform the users
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.no_img_toast),Toast.LENGTH_LONG).show();
                    webFeedView.loadUrl(getString(R.string.default_img));
                }
            }
        });

        //OnCLick Listener for changing into SEARCH FRAGMENT
        final View search_but_fd = rootview.findViewById(R.id.search_button_fd);
        search_but_fd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeToSearch();
            }
        });
        return rootview;
    }
    //Method to update WebView
    public void getImageFromColl(ArrayList<String> collectionUrl) {
        String urlToShow = collectionUrl.get(feedIndex);

        //WebView Settings
        webFeedView.setWebViewClient(new WebViewClient());
        WebSettings settings = webFeedView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        //If collectionUrl is empty, show no image
        if (urlToShow != null && !urlToShow.isEmpty()) {
            try {
                webFeedView.loadUrl(urlToShow);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
            Toast.makeText(getActivity().getBaseContext(), getString(R.string.no_img_toast),Toast.LENGTH_LONG).show();
            webFeedView.loadUrl(getString(R.string.default_img));
        }
    }
}