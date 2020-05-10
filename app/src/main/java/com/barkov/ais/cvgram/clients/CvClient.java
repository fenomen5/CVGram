package com.barkov.ais.cvgram.clients;

import com.barkov.ais.cvgram.entity.Cv;
import com.barkov.ais.cvgram.services.OnClientResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class CvClient extends Client{

    public CvClient(URL url, OnClientResponse listener) {
        super(url, listener);
    }

    /**
     * Send create CV request
     * @param cv
     * @return
     */
    public boolean createCv(Cv cv, String token)
    {
        final JSONObject cvobj = new JSONObject();

        try {
            cvobj.put("title", cv.getTitle());
            cvobj.put("region_id", cv.getRegionId());
            cvobj.put("district_id", cv.getDistrictId());
            cvobj.put("industry_id", cv.getIndustryId());
            cvobj.put("skills", cv.getSkills());
            cvobj.put("status", cv.getCvStatusId());
            cvobj.put("token", token);
            String json = cvobj.toString(1);

            return this.send(json);

        } catch (JSONException je) {
            je.printStackTrace();
        }

        return false;
    }

    /**
     * Send update CV request
     * @param cv
     * @return
     */
    public boolean updateCv(Cv cv, String token)
    {
        final JSONObject cvobj = new JSONObject();

        try {
            cvobj.put("cvid", cv.getId());
            cvobj.put("title", cv.getTitle());
            cvobj.put("region_id", cv.getRegionId());
            cvobj.put("district_id", cv.getDistrictId());
            cvobj.put("industry_id", cv.getIndustryId());
            cvobj.put("skills", cv.getSkills());
            cvobj.put("status", cv.getCvStatusId());
            cvobj.put("token", token);
            String json = cvobj.toString(1);

            return this.send(json);

        } catch (JSONException je) {
            je.printStackTrace();
        }

        return false;
    }

    /**
     * Send getCv request
     * @param cvid
     * @return
     */
    public boolean getCv(int cvId, String token)
    {
        final JSONObject cvobj = new JSONObject();

        try {
            cvobj.put("cvid", cvId);
            cvobj.put("token", token);
            String json = cvobj.toString(1);

            return this.send(json);

        } catch (JSONException je) {
            je.printStackTrace();
        }

        return false;
    }
}
