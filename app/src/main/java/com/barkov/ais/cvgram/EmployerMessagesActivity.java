package com.barkov.ais.cvgram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.barkov.ais.cvgram.dataadapter.MessageThreadsAdapter;

public class EmployerMessagesActivity extends AppCompatActivity {

    MessageThreadsAdapter messageThreadsAdapter;
    ListView messageThreads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_messages);

        messageThreads = findViewById(R.id.employerMessageThreads);
        messageThreadsAdapter = new MessageThreadsAdapter(this);

        messageThreads.setAdapter(messageThreadsAdapter);
    }
}
