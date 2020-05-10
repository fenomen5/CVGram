package com.barkov.ais.cvgram.services;

import android.content.Context;
import android.util.Log;

import com.barkov.ais.cvgram.OnTaskCompleted;
import com.barkov.ais.cvgram.R;

import com.barkov.ais.cvgram.clients.RegisterClient;
import com.barkov.ais.cvgram.clients.Response;
import com.barkov.ais.cvgram.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class RegisterServiceProvider extends ServiceProvider implements OnClientResponse {

    RegisterClient client;
    OnTaskCompleted mListener;

    public RegisterServiceProvider(Context context) {
        super(context);

        String baseAddress = context.getResources().getString(R.string.base_url);

        URL url = null;
        try {
            url = new URL(baseAddress + "/register");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        client = new RegisterClient(url, this);
    }

    /**
     * Register user
     * @param user
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean register(User user, OnTaskCompleted listener) throws Exception
    {
        if (user.getFirst_name().length() == 0 ||
                user.getLogin().length() == 0 ||
                user.getEmail().length() == 0 ||
                user.getPassword().length() == 0
        ) {
            throw new Exception("Incorrect register data");
        }

        if (client.register(user)) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    @Override
    public void onClientResponse(Response response) {

        HashMap <String, String> hm = new HashMap<>();
        JSONObject jsonObj = null;
        try {
            jsonObj = response.getJsonResponse();
            if (jsonObj != null) {
                hm.put("registered", jsonObj.getString("registered"));
            }

            if ( hm.get("registered") !=null && hm.get("registered").equals("true")) {
                hm.put("success", "true");
            } else {
                hm.put("success", "false");
            }

        } catch (JSONException e) {

            hm.put("success", "false");
            e.printStackTrace();
        }

        this.mListener.onTaskCompleted(hm);
    }
}
