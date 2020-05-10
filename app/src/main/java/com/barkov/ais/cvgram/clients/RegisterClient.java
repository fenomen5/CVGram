package com.barkov.ais.cvgram.clients;

import com.barkov.ais.cvgram.entity.User;
import com.barkov.ais.cvgram.services.OnClientResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class RegisterClient extends Client{

    public RegisterClient(URL url, OnClientResponse listener) {
        super(url, listener);
    }

    /**
     * Send register request
     * @param user
     * @return
     */
    public boolean register(User user)
    {
        final JSONObject auth = new JSONObject();

        try {
            auth.put("name", user.getFirst_name());
            auth.put("email", user.getEmail());
            auth.put("phone", user.getPhone());
            auth.put("user_type", ""+user.getUserType());
            auth.put("login", user.getLogin());
            auth.put("password", user.getPassword());
            auth.put("repeat_password", user.getPassword());

            String json = auth.toString(1);

            return this.send(json);

        } catch (JSONException je) {
            je.printStackTrace();
        }

        return false;
    }
}
