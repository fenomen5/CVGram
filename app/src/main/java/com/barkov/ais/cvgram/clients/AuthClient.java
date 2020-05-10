package com.barkov.ais.cvgram.clients;

import com.barkov.ais.cvgram.services.OnClientResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class AuthClient extends Client{

    public AuthClient(URL url, OnClientResponse listener) {
        super(url, listener);
    }

    /**
     * Send login request
     * @param login
     * @param password
     * @return
     */
    public boolean Login(String login, String password)
    {
        final JSONObject auth = new JSONObject();

        try {
            auth.put("login", login);
            auth.put("password", password);

            String json = auth.toString(1);

            return this.send(json);

        } catch (JSONException je) {
            je.printStackTrace();
        }

        return false;
    }
}
