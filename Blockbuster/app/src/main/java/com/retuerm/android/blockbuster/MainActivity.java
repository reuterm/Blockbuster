package com.retuerm.android.blockbuster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.retuerm.android.blockbuster.Utility.FetchMovieList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void showToast(String m) {
        Toast.makeText(this, m, Toast.LENGTH_LONG);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_most_popular:
                new FetchMovieList(new FetchMovieList.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Toast.makeText(MainActivity.this, output, Toast.LENGTH_LONG).show();
                    }
                }).execute("popular");
                return true;
            case R.id.action_sort_top_rated:
                new FetchMovieList(new FetchMovieList.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Toast.makeText(MainActivity.this, output, Toast.LENGTH_LONG).show();
                    }
                }).execute("top_rated");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
