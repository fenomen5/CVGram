package com.barkov.ais.cvgram.clients;

import com.barkov.ais.cvgram.services.OnClientResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

public class DataClient extends Client{

    public DataClient(URL url, OnClientResponse listener) {
        super(url, listener);
    }

    /**
     * Get user types
     * @return
     */
    public boolean getUserTypes()
    {
        try {
            String json = "";
            return this.send(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get regions
     * @return
     */
    public boolean getRegions()
    {
        final JSONObject data = new JSONObject();

        try {
            String json = "";
            return this.send(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get districts
     * @return
     */
    public boolean getDistricts(int regionId)
    {
        final JSONObject data = new JSONObject();

        try {
            data.put("region_id", regionId);

            String json = data.toString(1);
            return this.send(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Request for data
     * @return
     */
    public boolean getUserThreads(String token)
    {
        final JSONObject params = new JSONObject();

        try {
            params.put("token", token);
            String json = params.toString(1);
            return this.send(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Request for thread messages
     * @return
     */
    public boolean getThreadMessages(String token, int receiver)
    {
        final JSONObject params = new JSONObject();

        try {
            params.put("token", token);
            params.put("receiver", receiver);
            String json = params.toString(1);
            return this.send(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Request for cvs info
     * @return
     */
    public boolean getCvsInfo(String token)
    {
        final JSONObject params = new JSONObject();

        try {
            params.put("token", token);
            String json = params.toString(1);
            return this.send(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Request for cvs info
     * @return
     */
    public boolean searchCvsInfo(String token, HashMap<String,Object> queryParams)
    {
        final JSONObject params = new JSONObject();

        try {
            if (queryParams.get("title") != null ) {
                params.put("cvtitle", queryParams.get("title"));
            }
            if (queryParams.get("skills") != null ) {
                params.put("cvskill", queryParams.get("skills"));
            }
            if (queryParams.get("region") != null ) {
                params.put("region", queryParams.get("region"));
            }
            if (queryParams.get("industry") != null ) {
                params.put("industry", queryParams.get("industry"));
            }

            params.put("token", token);
            String json = params.toString(1);
            return this.send(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Request for data
     * @return
     */
    public boolean getData()
    {
        try {
            String json = "";
            return this.send(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
