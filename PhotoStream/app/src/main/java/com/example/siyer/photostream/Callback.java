package com.example.siyer.photostream;

import java.util.ArrayList;

/**
 * Created by siyer on 9/24/2015.
 * Interface between the search fragment and the http handler (searchgoogle)
 * to return the array of URLs returned by Google
 */
public interface Callback {
    void resultsCallback(ArrayList<String> urlArray);
}
