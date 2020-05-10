package com.barkov.ais.cvgram;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.barkov.ais.cvgram.services.AuthServiceProvider;
import com.barkov.ais.cvgram.services.CvServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditCvActivity extends AppCompatActivity implements View.OnClickListener,
        OnTaskCompleted {

    Spinner regionsSpinner, districtsSpinner, industriesSpinner, cvStatusesSpinner;
    RegionsAdapter regionsAdapter;
    DistrictsAdapter districtsAdapter;
    IndustriesAdapter industriesAdapter;
    StatusesAdapter statusesAdapter;
    Button btnCreate;
    EditText cvTitle, cvSkills;
    int cvId;
    int districtId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cv);
        cvId = getIntent().getIntExtra("cv_id",0);
        initForm();
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
        btnCreate.setText("Update");
        btnCreate.setOnClickListener(this);

        setCvValues();
    }


    /**
     * Set up saved parameters
     */
    private boolean setCvValues()
    {
        CvServiceProvider cvProvider = new CvServiceProvider(this);

        if (!cvProvider.isOnline()) {
            Log.d("dbg", "No internet connection");
            cvProvider.displayNoInternetConnection();
            return false;
        }

        try {
            cvProvider.getCv(cvId, this);
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }

        return  true;
    }

    @Override
    public void onClick(View v) {
        updateCv();
    }

    private void setCvFields(Cv cv)
    {
        cvTitle.setText(cv.getTitle());
        cvSkills.setText(cv.getSkills());

        for (int i=0; i<regionsSpinner.getAdapter().getCount(); i++ ) {

            if (((Region)regionsSpinner.getAdapter().getItem(i)).getId() == cv.getRegionId()) {
                regionsSpinner.setSelection(i);
            }
        }

        this.districtId = cv.getDistrictId();
        districtsAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                if (districtId > 0) {
                    for (int i=0; i<districtsSpinner.getAdapter().getCount(); i++ ) {

                        if (((District)districtsSpinner.getAdapter().getItem(i)).getId() == districtId) {
                            districtsSpinner.setSelection(i);
                            districtId = -1;
                        }
                    }
                }

            }
        });


        for (int i=0; i<industriesSpinner.getAdapter().getCount(); i++ ) {

            if (((Industry)industriesSpinner.getAdapter().getItem(i)).getId() == cv.getIndustryId()) {
                industriesSpinner.setSelection(i);
            }
        }

        for (int i = 0; i < cvStatusesSpinner.getAdapter().getCount(); i++) {

            if (((CvStatus) cvStatusesSpinner.getAdapter().getItem(i)).getId() == cv.getCvStatusId()) {
                cvStatusesSpinner.setSelection(i);
            }
        }

    }

    /**
     * Update an existing CV
     */
    private void updateCv()
    {
        Cv cv = new Cv(cvId,
                cvTitle.getText().toString(),
                ((Region)regionsSpinner.getSelectedItem()).getId(),
                ((District)districtsSpinner.getSelectedItem()).getId(),
                ((Industry)industriesSpinner.getSelectedItem()).getId(),
                cvSkills.getText().toString(),
                ((CvStatus)cvStatusesSpinner.getSelectedItem()).getId());

        CvServiceProvider provider = new CvServiceProvider(this);

        try {
            provider.updateCv(cv, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("dbg updated CV", cv.toString());
    }

    @Override
    public void onTaskCompleted(HashMap result) {
        Cv cvData;

        if (((String)result.get("type")).contains("CvUpdateParser")) {

            if (result.get("success").equals("true")) {
                finish();
            } else {
                displayInvalidCvCreation();
            }
        }

        if (((String) result.get("type")).contains("CvGetParser")) {
            if (result.get("success").equals("true")) {
                ArrayList items = (ArrayList) result.get("items");
                cvData = (Cv) items.get(0);
                setCvFields(cvData);
            }
        }
    }

    private void displayInvalidCvCreation()
    {
        Toast.makeText(this, "Unable to update cv", Toast.LENGTH_LONG).show();
    }
}
