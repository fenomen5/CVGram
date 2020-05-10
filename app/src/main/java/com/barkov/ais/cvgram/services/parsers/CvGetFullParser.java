package com.barkov.ais.cvgram.services.parsers;

import com.barkov.ais.cvgram.entity.Cv;
import com.barkov.ais.cvgram.entity.CvFull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CvGetFullParser implements JsonParser{

    @Override
    public ArrayList parse(JSONObject obj) {
        ArrayList<CvFull> list = new ArrayList<>();
        try {
            JSONObject jsonObject = obj.getJSONObject("items");

                CvFull cv = new CvFull(
                        jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        jsonObject.getInt("region"),
                        jsonObject.getString("region_name"),
                        jsonObject.getInt("district"),
                        jsonObject.getString("district_name"),
                        jsonObject.getInt("industry"),
                        jsonObject.getString("industry_name"),
                        jsonObject.getString("skills"),
                        jsonObject.getInt("status"),
                        jsonObject.getString("status_name"),
                        jsonObject.getString("updated")
                );
                list.add(cv);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

}
