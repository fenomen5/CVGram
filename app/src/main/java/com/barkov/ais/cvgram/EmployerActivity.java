package com.barkov.ais.cvgram;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.barkov.ais.cvgram.dataadapter.CvFeedAdapter;
import com.barkov.ais.cvgram.dataadapter.MessageThreadsAdapter;
import com.barkov.ais.cvgram.entity.CvRssItem;

import java.util.List;

public class EmployerActivity extends AppCompatActivity {

    CvFeedAdapter adapter;
    ListView cvsfeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);

        Toolbar toolbar = (Toolbar)findViewById(R.id.employertoolbar);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0,8,10,0);

        cvsfeed = findViewById(R.id.rss_cvs);
        adapter = new CvFeedAdapter(this);

        cvsfeed.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.employerdash, menu);;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_searchcv:
                intent = new Intent(this, EmployerCvSearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_messages:
                intent = new Intent(this, EmployerMessagesActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
