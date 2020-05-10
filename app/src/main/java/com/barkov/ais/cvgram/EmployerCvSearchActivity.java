package com.barkov.ais.cvgram;

import android.app.Dialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.barkov.ais.cvgram.dataadapter.CvsAdapter;
import com.barkov.ais.cvgram.dataadapter.DistrictsAdapter;
import com.barkov.ais.cvgram.dataadapter.EmployerCvsAdapter;
import com.barkov.ais.cvgram.dataadapter.IndustriesAdapter;
import com.barkov.ais.cvgram.dataadapter.RegionsAdapter;
import com.barkov.ais.cvgram.entity.District;
import com.barkov.ais.cvgram.entity.Industry;
import com.barkov.ais.cvgram.entity.Region;

import java.util.HashMap;

public class EmployerCvSearchActivity extends AppCompatActivity {

    RegionsAdapter regionsAdapter;
    IndustriesAdapter industriesAdapter;
    Button btnSetFilters;
    EmployerCvsAdapter adapter;
    ListView cvsList;
    Dialog dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_cv_search);

        Toolbar toolbar = (Toolbar)findViewById(R.id.employertoolbar);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0,8,10,0);

        adapter = new EmployerCvsAdapter(this);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                cvsList.invalidateViews();
                cvsList.refreshDrawableState();
            }
        });
        cvsList = findViewById(R.id.foundCvs);
        cvsList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.employersearchcv, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_params:
                displaySearchDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Display serch CV dialog
     */
    public void displaySearchDialog()
    {
        dlg = new Dialog(this);
        dlg.setTitle("Query parameters");
        dlg.setContentView(R.layout.dialog_search_cv);

        EditText txtQueryName, txtQuerySkills;
        Spinner spnRegion, spnDistrict, spnIndustry;

        txtQueryName = dlg.findViewById(R.id.txtSearchParamTitle);
        txtQuerySkills = dlg.findViewById(R.id.txtSearchParamSkills);

        spnRegion = dlg.findViewById(R.id.spnSearchParamRegion);
        regionsAdapter = new RegionsAdapter(this);
        regionsAdapter.setEmptyValue();
        spnRegion.setAdapter(regionsAdapter);

        spnIndustry = dlg.findViewById(R.id.spnSearchParamIndustry);
        industriesAdapter = new IndustriesAdapter(this);
        industriesAdapter.setEmptyValue();
        spnIndustry.setAdapter(industriesAdapter);

        final HashMap<String,Object> searchParams = new HashMap<String, Object>();

        btnSetFilters = dlg.findViewById(R.id.btnSetCvFilters);

        final EditText queryName = txtQueryName;
        final EditText querySkills = txtQuerySkills;
        final Spinner region = spnRegion;
        final Spinner industry = spnIndustry;

        btnSetFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchParams.put("title",queryName.getText());
                searchParams.put("region",((Region)region.getSelectedItem()).getId());
                searchParams.put("industry",((Industry)industry.getSelectedItem()).getId());
                searchParams.put("skills",querySkills.getText());

                dlg.hide();
                searchCvs(searchParams);
            }
        });

        dlg.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dlg != null) {
            dlg.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dlg != null) {
            dlg.dismiss();
        }
    }

    /**
     * Search CVs by parameters
     * @param hm
     */
    private void searchCvs(HashMap<String,Object> hm)
    {
        adapter.setFilters(hm);
        adapter.getData();
    }
}
