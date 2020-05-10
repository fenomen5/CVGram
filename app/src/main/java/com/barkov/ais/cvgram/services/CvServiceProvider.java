package com.barkov.ais.cvgram.services;

import android.content.Context;
import android.util.Log;

import com.barkov.ais.cvgram.OnTaskCompleted;
import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.clients.CvClient;
import com.barkov.ais.cvgram.clients.RegisterClient;
import com.barkov.ais.cvgram.clients.Response;
import com.barkov.ais.cvgram.entity.Cv;
import com.barkov.ais.cvgram.entity.User;
import com.barkov.ais.cvgram.services.parsers.CvCreateParser;
import com.barkov.ais.cvgram.services.parsers.CvGetFullParser;
import com.barkov.ais.cvgram.services.parsers.CvGetParser;
import com.barkov.ais.cvgram.services.parsers.CvUpdateParser;
import com.barkov.ais.cvgram.services.parsers.JsonParser;
import com.barkov.ais.cvgram.services.parsers.RegionsParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CvServiceProvider extends ServiceProvider implements OnClientResponse {

    CvClient client;
    OnTaskCompleted mListener;
    String mBaseAddress;
    Context mContext;
    JsonParser mParser;

    public CvServiceProvider(Context context) {
        super(context);
        mContext = context;
        mBaseAddress = context.getResources().getString(R.string.base_url);
    }

    /**
     * Create CV
     * @param cv
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean createCv(Cv cv, OnTaskCompleted listener) throws Exception
    {
        if (cv.getTitle().length() == 0 ||
                cv.getRegionId() == 0||
                cv.getDistrictId() == 0 ||
                cv.getIndustryId() == 0 ||
                cv.getCvStatusId() == 0
        ) {
            throw new Exception("Incorrect new cv data");
        }

        URL url = null;
        try {
            url = new URL(mBaseAddress + "/cvcreate");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        client = new CvClient(url, this);
        this.mParser = new CvCreateParser();
        Session session = new Session(mContext);
        if (client.createCv(cv, session.getToken())) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    /**
     * Update a CV
     * @param cv
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean updateCv(Cv cv, OnTaskCompleted listener) throws Exception
    {
        if (cv.getId() == 0 ||
                cv.getTitle().length() == 0 ||
                cv.getRegionId() == 0||
                cv.getDistrictId() == 0 ||
                cv.getIndustryId() == 0 ||
                cv.getCvStatusId() == 0
        ) {
            throw new Exception("Incorrect new cv data");
        }

        URL url = null;
        try {
            url = new URL(mBaseAddress + "/cvupdate");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        client = new CvClient(url, this);
        this.mParser = new CvUpdateParser();
        Session session = new Session(mContext);
        if (client.updateCv(cv, session.getToken())) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    /**
     * get CV by id
     * @param cvId
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean getCv(int cvId, OnTaskCompleted listener) throws Exception
    {
        URL url = null;
        try {
            url = new URL(mBaseAddress + "/usercv");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        client = new CvClient(url, this);
        this.mParser = new CvGetParser();
        Session session = new Session(mContext);
        if (client.getCv(cvId, session.getToken())) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    /**
     * get full info CV by id
     * @param cvId
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean getFullCv(int cvId, OnTaskCompleted listener) throws Exception
    {
        URL url = null;
        try {
            url = new URL(mBaseAddress + "/getcv");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        client = new CvClient(url, this);
        this.mParser = new CvGetFullParser();
        Session session = new Session(mContext);
        if (client.getCv(cvId, session.getToken())) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    @Override
    public void onClientResponse(Response response) {
        Log.d("dbg ", response.getRawResponse() );
        HashMap <String, Object> hm = new HashMap<>();
        JSONObject jsonObj = null;

        jsonObj = response.getJsonResponse();
        if (jsonObj != null) {
            ArrayList list = mParser.parse(jsonObj);
            hm.put("items", list);
        }

        if (hm.get("items") != null && ((ArrayList) hm.get("items")).size() > 0) {
            hm.put("success", "true");
            hm.put("type", mParser.getClass().getName());
        } else {
            hm.put("success", "false");
        }

        this.mListener.onTaskCompleted(hm);
    }
}
