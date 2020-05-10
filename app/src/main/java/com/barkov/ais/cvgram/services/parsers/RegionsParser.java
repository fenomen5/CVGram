package com.barkov.ais.cvgram.services.parsers;

import android.content.Context;
import android.util.Log;

import com.barkov.ais.cvgram.OnTaskCompleted;
import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.clients.DataClient;
import com.barkov.ais.cvgram.clients.Response;
import com.barkov.ais.cvgram.entity.Region;
import com.barkov.ais.cvgram.entity.UserType;
import com.barkov.ais.cvgram.services.OnClientResponse;
import com.barkov.ais.cvgram.services.ServiceProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RegionsParser implements JsonParser{

    @Override
    public ArrayList parse(JSONObject obj) {
        ArrayList<Region> list = new ArrayList<>();
        try {
            JSONArray jsArray = obj.getJSONArray("items");

            for (int i=0; i< jsArray.length(); i++) {
                JSONObject jsonObject=jsArray.getJSONObject(i);
                list.add(new Region(jsonObject.getInt("id"),
                        jsonObject.getString("name")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

}
