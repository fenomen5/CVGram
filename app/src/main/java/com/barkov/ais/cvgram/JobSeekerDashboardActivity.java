package com.barkov.ais.cvgram;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.ListView;


import com.barkov.ais.cvgram.dataadapter.MessageThreadsAdapter;

public class JobSeekerDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    MessageThreadsAdapter messageThreadsAdapter;
    ListView messageThreads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_dashboard);
        Toolbar toolbar = (Toolbar)findViewById(R.id.jobseekertoolbar);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0,8,10,0);

        messageThreads = findViewById(R.id.messageThreads);
        messageThreadsAdapter = new MessageThreadsAdapter(this);

        messageThreads.setAdapter(messageThreadsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.jobseekerdash, menu);;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_addcv:
                intent = new Intent(this, AddCvActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_cvlist:
                intent = new Intent(this, CvListActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
