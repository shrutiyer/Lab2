package com.example.siyer.photostream;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> collectionUrl = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment transaction for switching between fragments
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SearchFragment fragment = new SearchFragment();
        fragmentTransaction.add(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //Changes to the SEARCH fragment from the COLLECTION
    public void changeToSearch() {
        FragmentTransaction fTrac = getSupportFragmentManager().beginTransaction();
        SearchFragment fragment = new SearchFragment();
        fTrac.replace(R.id.fragment, fragment);
        fTrac.commit();
    }
    //Changes to the COLLECTION fragment from the SEARCH fragment
    public void changeToColl()  {
        FragmentTransaction fTrac = getSupportFragmentManager().beginTransaction();
        CollectionFragment fragment = new CollectionFragment();
        fTrac.replace(R.id.fragment, fragment);
        fTrac.commit();
    }
}
