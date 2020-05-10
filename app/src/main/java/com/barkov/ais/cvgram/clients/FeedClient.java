package com.barkov.ais.cvgram.clients;

import com.barkov.ais.cvgram.services.OnClientResponse;

import org.json.JSONObject;

import java.net.URL;

public class FeedClient extends Client{

    public FeedClient(URL url, OnClientResponse listener) {
        super(url, listener);
    }

    /**
     * Request for thread messages
     * @return
     */
    public boolean getCvsFeed()
    {

        try {
            return this.send("");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
