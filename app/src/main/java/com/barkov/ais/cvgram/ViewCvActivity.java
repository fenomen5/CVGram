package com.barkov.ais.cvgram;

import android.database.DataSetObserver;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.barkov.ais.cvgram.dataadapter.DistrictsAdapter;
import com.barkov.ais.cvgram.dataadapter.IndustriesAdapter;
import com.barkov.ais.cvgram.dataadapter.RegionsAdapter;
import com.barkov.ais.cvgram.dataadapter.StatusesAdapter;
import com.barkov.ais.cvgram.entity.Cv;
import com.barkov.ais.cvgram.entity.CvFull;
import com.barkov.ais.cvgram.entity.CvStatus;
import com.barkov.ais.cvgram.entity.District;
import com.barkov.ais.cvgram.entity.Industry;
import com.barkov.ais.cvgram.entity.Region;
import com.barkov.ais.cvgram.google.map.ScrollGoogleMap;
import com.barkov.ais.cvgram.services.CvServiceProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewCvActivity extends AppCompatActivity implements OnTaskCompleted,
        View.OnClickListener, OnMapReadyCallback {

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    TextView title, place, industry, skills;
    Button btnBack;
    int cvId;
    ScrollGoogleMap clubMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cv);
        cvId = getIntent().getIntExtra("cv_id",0);

        title = findViewById(R.id.lblCvTitle);
        place = findViewById(R.id.lblPlace);
        industry = findViewById(R.id.lblIndustry);
        skills = findViewById(R.id.lblSkills);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        clubMap = findViewById(R.id.mapViewCvPlace);
        clubMap.onCreate(mapViewBundle);
        clubMap.getMapAsync(this);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        setCvValues();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        clubMap.onSaveInstanceState(mapViewBundle);
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
            cvProvider.getFullCv(cvId, this);
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }

        return  true;
    }

    @Override
    public void onTaskCompleted(HashMap result) {
        CvFull cvData;
        Log.d("dbg result", result.toString());
        if (((String) result.get("type")).contains("CvGetFullParser")) {
            if (result.get("success").equals("true")) {
                ArrayList items = (ArrayList) result.get("items");
                cvData = (CvFull) items.get(0);
                setCvFields(cvData);
                clubMap.getMapAsync(this);

            }
        }
    }

    /**
     * Set cv fields values
     * @param cv
     */
    private void setCvFields(CvFull cv)
    {
        Log.d("dbg", cv.toString());
        title.setText(cv.getTitle());
        skills.setText(cv.getSkills());
        place.setText(cv.getRegionName()+" " + cv.getDistrictName());
        industry.setText(cv.getIndustryName());
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMinZoomPreference(12);
        googleMap.setIndoorEnabled(true);

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setAllGesturesEnabled(true);
        setMarker(googleMap);
    }

    protected void setMarker(GoogleMap gMap)
    {
        List<Address> addressList;
        Geocoder geoCoder = new Geocoder(this);
        try {
            addressList = geoCoder.getFromLocationName(place.getText().toString(),1);
            //addressList = geoCoder.getFromLocationName("Auckland papakura",1);
            if(addressList.size() > 0) {

                LatLng ny = new LatLng(addressList.get(0).getLatitude(),addressList.get(0).getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(ny);
                gMap.addMarker(markerOptions);

                gMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
                /*p = new GeoPoint( (int) (addresses.get(0).getLatitude() * 1E6),
                        (int) (addresses.get(0).getLongitude() * 1E6));

                controller.animateTo(p);
                controller.setZoom(12);

                MapOverlay mapOverlay = new MapOverlay();
                List<Overlay> listOfOverlays = map.getOverlays();
                listOfOverlays.clear();
                listOfOverlays.add(mapOverlay);

                map.invalidate();
                txtsearch.setText("");*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*LatLng ny = new LatLng(club.getLongitude(), club.getLatitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ny);
        gMap.addMarker(markerOptions);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(ny));*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        clubMap.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clubMap.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clubMap.onResume();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        clubMap.onDestroy();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        clubMap.onStop();
    }

}
