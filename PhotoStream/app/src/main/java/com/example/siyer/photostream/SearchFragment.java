package com.example.siyer.photostream;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        final View search_but = rootview.findViewById(R.id.search_button);//Listener for search button
        EditText search_word = (EditText) rootview.findViewById(R.id.search_text);//Text to be searched
        final String search_word_string = search_word.getText().toString();
        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpHandler searchHandler = new HttpHandler(SearchFragment.this.getActivity());
                searchHandler.searchGoogle(search_word_string, new Callback() {
                    @Override
                    public void resultsCallback(ArrayList<String> urlArray) {

                    }
                });
            }
        });
        return rootview;
    }
}

