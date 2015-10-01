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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by siyer on 9/28/2015.
 */

public class CollectionFragment extends Fragment {
    public WebView webFeedView;
    public int feedIndex = 0; //Current Image index for WebView
    public ArrayList<String> collectionUrl; //Array with all the URLs in my collection
    CollDbHelper mDbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.collection_fragment, container, false);

        mDbHelper = new CollDbHelper(getActivity());
        collectionUrl = mDbHelper.readDatabase();
        webFeedView = (WebView) rootview.findViewById(R.id.webView_fd);
        webFeedView.loadUrl(getString((R.string.default_img)));
        Toast.makeText(getActivity().getBaseContext(), "Click Next and Prev to browse",Toast.LENGTH_SHORT).show();
        Log.d("Init feedIndex", String.valueOf(feedIndex));
        Log.d("Current Image", collectionUrl.get(feedIndex));
        getImageFromColl(collectionUrl);

        final View next_but = rootview.findViewById(R.id.next_button_fd);
        next_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click next", collectionUrl.toString());
                if (collectionUrl.size()>=1) {
                    if (feedIndex < collectionUrl.size() - 1) {
                        feedIndex += 1;
                        getImageFromColl(collectionUrl);
                    } else {
                        feedIndex = 0;
                        getImageFromColl(collectionUrl);
                    }
                }else if (collectionUrl.size()==0){
                    Toast.makeText(getActivity().getBaseContext(), "No images to display",Toast.LENGTH_LONG).show();//TEST
                    webFeedView.loadUrl(getString(R.string.default_img));//TEST
                }
            }
        });

        final View rem_but = rootview.findViewById(R.id.remove_button_fd);
        rem_but.setOnClickListener(new View.OnClickListener() {
            @Override
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
                    webFeedView.loadUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/2000px-No_image_available.svg.png");
                }else {
                    Toast.makeText(getActivity().getBaseContext(), "Add images from Search",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                    Toast.makeText(getActivity().getBaseContext(), "No images to display",Toast.LENGTH_LONG).show();//TEST
                    webFeedView.loadUrl(getString(R.string.default_img));//TEST
                }
            }
        });

        final View search_but_fd = rootview.findViewById(R.id.search_button_fd);
        search_but_fd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeToSearch();
            }
        });

        return rootview;
    }
    public void getImageFromColl(ArrayList<String> collectionUrl) {
        String urlToShow = collectionUrl.get(feedIndex);

        webFeedView.setWebViewClient(new WebViewClient());
        WebSettings settings = webFeedView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        if (urlToShow != null && !urlToShow.isEmpty()) {
            try {
                webFeedView.loadUrl(urlToShow);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
            Toast.makeText(getActivity().getBaseContext(), "No images to display",Toast.LENGTH_LONG).show();//TEST
            webFeedView.loadUrl(getString(R.string.default_img));//TEST
        }
    }
}