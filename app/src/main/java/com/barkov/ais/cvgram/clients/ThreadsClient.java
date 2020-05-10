package com.barkov.ais.cvgram.clients;

import com.barkov.ais.cvgram.entity.Cv;
import com.barkov.ais.cvgram.services.OnClientResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class ThreadsClient extends Client{

    public ThreadsClient(URL url, OnClientResponse listener) {
        super(url, listener);
    }

    /**
     * Send register request
     * @param cv
     * @return
     */
    public boolean createCv(Cv cv, String token)
    {
        final JSONObject cvobj = new JSONObject();

        try {
            cvobj.put("token", token);
            String json = cvobj.toString(1);

            return this.send(json);

        } catch (JSONException je) {
            je.printStackTrace();
        }

        return false;
    }
}
