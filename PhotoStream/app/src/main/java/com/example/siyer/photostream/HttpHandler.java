package com.example.siyer.photostream;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Handles all the http requests
public class HttpHandler {
    RequestQueue searchqueue;
    final String API_KEY = "AIzaSyB3HVwkKcTxnx0RTOKnSpgKVICbO_tgFCc";
    final String CSE_ID = "003595003396096289737:n_6yb0ixa4k";
    SearchFragment fragment;

    public HttpHandler(Context context,SearchFragment fragment) {
        //Request queue for search
        searchqueue = Volley.newRequestQueue(context);
        this.fragment = fragment;
    }

    public void searchGoogle(String searchQuery, final Callback callback) {
        String url = "https://www.googleapis.com/customsearch/v1?key="+ API_KEY +"&cx="+CSE_ID;
        searchQuery = searchQuery.replaceAll(" ", "+");
        String furl = url+"&q="+searchQuery+"&searchType=image";
        //Log.d("FINAL URL", furl);
        //url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyB3HVwkKcTxnx0RTOKnSpgKVICbO_tgFCc&cx=003595003396096289737:n_6yb0ixa4k&q=dog&searchType=image";
        final ArrayList<String> urlList = new ArrayList<String>();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                furl,
                new JSONObject(),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        String item = "items";
                        try {
                            Log.d("Debug","going to get Array");
                            JSONArray urlJson = response.getJSONArray(item);
                            Log.d("Debug","got it");
                            Log.d("List", urlJson.toString());
                            for(int i = 0; i < response.length(); i++){
                                Log.d("List after", urlJson.getJSONObject(i).getString("link"));
                                urlList.add(urlJson.getJSONObject(i).getString("link"));
                            }
                            callback.resultsCallback(urlList);
                            Log.d("List after", urlList.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                    }
                }
        );
        searchqueue.add(request);

    }
}


