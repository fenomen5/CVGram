package com.barkov.ais.cvgram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.barkov.ais.cvgram.dataadapter.ThreadMessagesAdapter;

public class MessageThreadActivity extends AppCompatActivity {

    ThreadMessagesAdapter adapter;
    ListView messages;
    int receiverId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_thread);

        receiverId = getIntent().getIntExtra("receiver_id",0);
        if (receiverId != 0) {
            initMessagesList();
        }
    }

    protected void initMessagesList()
    {
        messages = findViewById(R.id.threadMessages);

        adapter = new ThreadMessagesAdapter(receiverId, this);
        messages.setAdapter(adapter);
    }
}
