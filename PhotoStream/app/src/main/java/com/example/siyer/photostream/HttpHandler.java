package com.example.siyer.photostream;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Handles all the http requests
public class HttpHandler {
    RequestQueue searchqueue;
    final String API_KEY = "AIzaSyB3HVwkKcTxnx0RTOKnSpgKVICbO_tgFCc";
    final String CSE_ID = "003595003396096289737:n_6yb0ixa4k";

    public HttpHandler(Context context) {
        //Request queue for search
        searchqueue = Volley.newRequestQueue(context);
    }

    public void searchGoogle(String searchQuery, final Callback callback) {
        String url = "https://www.googleapis.com/customsearch/v1?key="+ API_KEY +"&cx="+CSE_ID;
        searchQuery = searchQuery.replaceAll(" ", "+");
        url = url+"&q="+searchQuery+"&searchType=image";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new JSONObject(),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        List<String> urlList = new ArrayList<String>();
                        //JSONObject res = new JSONObject(response)
                        String item = "items";
                        JSONArray urlJson = response.getJSONArray(item);
                        callback.resultsCallback(new ArrayList<String>());
                        Log.d("Response", response.toString());
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
        return;
    }
}


