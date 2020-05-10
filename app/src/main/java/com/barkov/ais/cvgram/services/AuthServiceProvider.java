package com.barkov.ais.cvgram.services;

import android.content.Context;

import com.barkov.ais.cvgram.OnTaskCompleted;
import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.clients.AuthClient;
import com.barkov.ais.cvgram.clients.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class AuthServiceProvider extends ServiceProvider implements OnClientResponse {

    AuthClient client;
    OnTaskCompleted mListener;
    Context mContext;
    public AuthServiceProvider(Context context) {
        super(context);
        mContext = context;
        String baseAddress = context.getResources().getString(R.string.base_url);

        URL url = null;
        try {
            url = new URL(baseAddress + "/login");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        client = new AuthClient(url, this);
    }

    /**
     * Login user
     * @param login
     * @param password
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean login(String login, String password, OnTaskCompleted listener) throws Exception
    {
        if (login.length() == 0 || password.length() == 0) {
            throw new Exception("Login or password are empty");
        }

        if (client.Login(login, password)) {
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
                hm.put("token", jsonObj.getString("token"));
                hm.put("user_type", jsonObj.getString("user_type"));
                hm.put("user_id", jsonObj.getString("user_id"));
            }

            if ( hm.get("token") !=null && hm.get("token").length() >0 ) {
                Session session = new Session(this.mContext);
                session.setToken(hm.get("token"));
                session.setUserId(Integer.valueOf(hm.get("user_id")));

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
