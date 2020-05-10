package com.barkov.ais.cvgram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.barkov.ais.cvgram.dataadapter.CvsAdapter;

public class CvListActivity extends AppCompatActivity {

    ListView cvsList;
    CvsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_list);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new CvsAdapter(this);
        cvsList = findViewById(R.id.userCvs);
        cvsList.setAdapter(adapter);
    }
}
