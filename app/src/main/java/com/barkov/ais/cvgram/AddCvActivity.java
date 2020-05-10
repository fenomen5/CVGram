package com.barkov.ais.cvgram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.barkov.ais.cvgram.dataadapter.DistrictsAdapter;
import com.barkov.ais.cvgram.dataadapter.IndustriesAdapter;
import com.barkov.ais.cvgram.dataadapter.RegionsAdapter;
import com.barkov.ais.cvgram.dataadapter.StatusesAdapter;
import com.barkov.ais.cvgram.entity.Cv;
import com.barkov.ais.cvgram.entity.CvStatus;
import com.barkov.ais.cvgram.entity.District;
import com.barkov.ais.cvgram.entity.Industry;
import com.barkov.ais.cvgram.entity.Region;
import com.barkov.ais.cvgram.services.CvServiceProvider;

import java.util.HashMap;

public class AddCvActivity extends AppCompatActivity implements View.OnClickListener,
        OnTaskCompleted {

    Spinner regionsSpinner, districtsSpinner, industriesSpinner, cvStatusesSpinner;
    RegionsAdapter regionsAdapter;
    DistrictsAdapter districtsAdapter;
    IndustriesAdapter industriesAdapter;
    StatusesAdapter statusesAdapter;
    Button btnCreate;
    EditText cvTitle, cvSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cv);
        initForm();
        //createCv();
    }

    private void initForm()
    {
        cvTitle = findViewById(R.id.txtTitle);
        cvSkills = findViewById(R.id.meditSkills);

        regionsSpinner = findViewById(R.id.spnRegions);

        regionsAdapter = new RegionsAdapter(this);
        regionsSpinner.setAdapter(regionsAdapter);

        regionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int regionId = ((Region)parent.getSelectedItem()).getId();
                districtsAdapter.getData(regionId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        districtsSpinner = findViewById(R.id.spnDistricts);

        districtsAdapter = new DistrictsAdapter(this);
        districtsSpinner.setAdapter(districtsAdapter);

        industriesSpinner = findViewById(R.id.spnIndustries);

        industriesAdapter = new IndustriesAdapter(this);
        industriesSpinner.setAdapter(industriesAdapter);

        cvStatusesSpinner = findViewById(R.id.spnCvStatuses);

        statusesAdapter = new StatusesAdapter(this);
        cvStatusesSpinner.setAdapter(statusesAdapter);

        btnCreate = findViewById(R.id.btnCreateCv);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        createCv();
    }

    /**
     * Create a new CV
     */
    private void createCv()
    {
        Cv cv = new Cv(0,
                cvTitle.getText().toString(),
                ((Region)regionsSpinner.getSelectedItem()).getId(),
                ((District)districtsSpinner.getSelectedItem()).getId(),
                ((Industry)industriesSpinner.getSelectedItem()).getId(),
                cvSkills.getText().toString(),
                ((CvStatus)cvStatusesSpinner.getSelectedItem()).getId());

       /* Cv cv = new Cv(0,
                "Web developer",
                1,
                1,
                12,
                "MSSQL; Java; Cordova",
                1);*/

        CvServiceProvider provider = new CvServiceProvider(this);

        try {
            provider.createCv(cv, this);
        } catch (Exception e) {
            e.printStackTrace();
            displayInvalidCvCreation();
        }

        Log.d("dbg CV", cv.toString());
    }

    @Override
    public void onTaskCompleted(HashMap result)
    {
        Log.d("dbg onTaskCompleted", result.toString());
        if (result.get("success").equals("true")) {

            Intent intent = new Intent(this, JobSeekerDashboardActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        displayInvalidCvCreation();
    }

    private void displayInvalidCvCreation()
    {
        Toast.makeText(this, "Unable to create cv", Toast.LENGTH_LONG).show();
    }
}
