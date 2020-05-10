package com.barkov.ais.cvgram.services.parsers;

import com.barkov.ais.cvgram.entity.Cv;
import com.barkov.ais.cvgram.entity.CvInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CvGetParser implements JsonParser{

    @Override
    public ArrayList parse(JSONObject obj) {
        ArrayList<Cv> list = new ArrayList<>();
        try {
            JSONObject jsonObject = obj.getJSONObject("items");

                Cv cv = new Cv(
                        jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        jsonObject.getInt("region"),
                        jsonObject.getInt("district"),
                        jsonObject.getInt("industry"),
                        jsonObject.getString("skills"),
                        jsonObject.getInt("status")
                );
                list.add(cv);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

}
