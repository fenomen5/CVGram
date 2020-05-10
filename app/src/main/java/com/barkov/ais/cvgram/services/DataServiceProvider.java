package com.barkov.ais.cvgram.services;

import android.content.Context;
import android.util.Log;

import com.barkov.ais.cvgram.OnTaskCompleted;
import com.barkov.ais.cvgram.R;

import com.barkov.ais.cvgram.clients.DataClient;
import com.barkov.ais.cvgram.clients.Response;

import com.barkov.ais.cvgram.services.parsers.CvStatusesParser;
import com.barkov.ais.cvgram.services.parsers.CvsParser;
import com.barkov.ais.cvgram.services.parsers.DistrictsParser;
import com.barkov.ais.cvgram.services.parsers.IndustriesParser;
import com.barkov.ais.cvgram.services.parsers.JsonParser;
import com.barkov.ais.cvgram.services.parsers.MessageThreadsParser;
import com.barkov.ais.cvgram.services.parsers.RegionsParser;
import com.barkov.ais.cvgram.services.parsers.ThreadMessagesParser;
import com.barkov.ais.cvgram.services.parsers.UserTypesParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DataServiceProvider extends ServiceProvider implements OnClientResponse {

    DataClient mDataClient;
    OnTaskCompleted mListener;
    String mBaseAddress;
    JsonParser mParser;
    Context mContext;

    public DataServiceProvider(Context context) {
        super(context);
        mContext = context;
        mBaseAddress = context.getResources().getString(R.string.base_url);
    }

    /**
     * Get user types
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean getUserTypes(OnTaskCompleted listener) throws Exception
    {

        URL url = null;
        try {
            url = new URL(mBaseAddress + "/usertypes");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mDataClient = new DataClient(url, this);
        this.mParser = new UserTypesParser();
        if (mDataClient.getUserTypes()) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    /**
     * Get regions
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean getRegions(OnTaskCompleted listener) throws Exception
    {
        URL url = null;
        try {
            url = new URL(mBaseAddress + "/regions");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mDataClient = new DataClient(url, this);
        this.mParser = new RegionsParser();
        if (mDataClient.getRegions()) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    /**
     * Get districts
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean getDistricts(int regionId, OnTaskCompleted listener) throws Exception
    {
        URL url = null;
        try {
            url = new URL(mBaseAddress + "/districts");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mDataClient = new DataClient(url, this);

        this.mParser = new DistrictsParser();
        if (mDataClient.getDistricts(regionId)) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    /**
     * Get industries
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean getIndustries(OnTaskCompleted listener) throws Exception
    {
        URL url = null;
        try {
            url = new URL(mBaseAddress + "/industries");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mDataClient = new DataClient(url, this);
        this.mParser = new IndustriesParser();
        if (mDataClient.getData()) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    /**
     * Get available statuses of CV
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean getCvStatuses(OnTaskCompleted listener) throws Exception
    {
        URL url = null;
        try {
            url = new URL(mBaseAddress + "/cvstatuses");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mDataClient = new DataClient(url, this);
        this.mParser = new CvStatusesParser();
        if (mDataClient.getData()) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    /**
     * Get messages threads
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean getMessageThreads(OnTaskCompleted listener) throws Exception
    {
        URL url = null;
        try {
            url = new URL(mBaseAddress + "/threads");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mDataClient = new DataClient(url, this);
        this.mParser = new MessageThreadsParser();
        Session session = new Session(mContext);
        if (mDataClient.getUserThreads(session.getToken())) {
            this.mListener = listener;
            return true;
        }

        return false;
    }


    /**
     * Get thread messages
     * @param listener
     * @param receiver
     * @return
     * @throws Exception
     */
    public boolean getThreadMessages(int receiver, OnTaskCompleted listener) throws Exception
    {
        URL url = null;
        try {
            url = new URL(mBaseAddress + "/thread");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mDataClient = new DataClient(url, this);
        this.mParser = new ThreadMessagesParser();
        Session session = new Session(mContext);
        if (mDataClient.getThreadMessages(session.getToken(),receiver)) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    /**
     * Get user CVs
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean getCvs(OnTaskCompleted listener) throws Exception
    {
        URL url = null;
        try {
            url = new URL(mBaseAddress + "/usercvs");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mDataClient = new DataClient(url, this);
        this.mParser = new CvsParser();
        Session session = new Session(mContext);
        if (mDataClient.getCvsInfo(session.getToken())) {
            this.mListener = listener;
            return true;
        }

        return false;
    }


    /**
     * Get user CVs
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean searchCvs(HashMap<String,Object> hm, OnTaskCompleted listener) throws Exception
    {
        URL url = null;
        try {
            url = new URL(mBaseAddress + "/searchcvs");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mDataClient = new DataClient(url, this);
        this.mParser = new CvsParser();
        Session session = new Session(mContext);
        if (mDataClient.searchCvsInfo(session.getToken(), hm)) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    @Override
    public void onClientResponse(Response response) {

        HashMap <String, Object> hm = new HashMap<>();
        JSONObject jsonObj = null;
        Log.d("dbg onClientResp datase", response.toString());

        jsonObj = response.getJsonResponse();
        if (jsonObj != null) {
            ArrayList list = mParser.parse(jsonObj);
            hm.put("items", list);
        }

        if (hm.get("items") != null && ((ArrayList) hm.get("items")).size() > 0) {
            hm.put("success", "true");
        } else {
            hm.put("success", "false");
        }

        this.mListener.onTaskCompleted(hm);
    }
}
