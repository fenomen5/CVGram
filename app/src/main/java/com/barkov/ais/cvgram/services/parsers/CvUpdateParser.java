package com.barkov.ais.cvgram.services.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CvUpdateParser implements JsonParser{

    @Override
    public ArrayList parse(JSONObject obj) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            HashMap<String, String> hm = new HashMap<>();
            if (obj != null) {
                hm.put("updated", obj.getString("updated"));
                hm.put("cv_id", obj.getString("cv_id"));
            }
            list.add(hm);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

}
