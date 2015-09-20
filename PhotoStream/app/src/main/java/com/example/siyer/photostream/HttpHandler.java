package com.example.siyer.photostream;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by siyer on 9/17/2015.
 */
public class HttpHandler {

    public RequestQueue queue;

    public HttpHandler(Context context) {
        queue = Volley.nevRequestQueue(context);
    }

    public void searchSpotify(String searchQuery) {
        String query = searchQuery.replaceAll(" ", "+");

        String url = "https://api.spotify.com/v1/seaarch";
        url = url + "?q=" + query;
        url = url + "&type=track";

        JSONObjectRequest request = new JsonObjectRequest(
                Request.
        )
    }
}
