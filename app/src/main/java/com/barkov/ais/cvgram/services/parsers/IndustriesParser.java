package com.barkov.ais.cvgram.services.parsers;

import com.barkov.ais.cvgram.entity.Industry;
import com.barkov.ais.cvgram.entity.Region;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IndustriesParser implements JsonParser{

    @Override
    public ArrayList parse(JSONObject obj) {
        ArrayList<Industry> list = new ArrayList<>();
        try {
            JSONArray jsArray = obj.getJSONArray("items");

            for (int i=0; i< jsArray.length(); i++) {
                JSONObject jsonObject=jsArray.getJSONObject(i);
                list.add(new Industry(jsonObject.getInt("id"),
                        jsonObject.getString("name")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

}
