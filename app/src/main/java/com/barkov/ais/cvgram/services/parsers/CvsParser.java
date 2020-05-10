package com.barkov.ais.cvgram.services.parsers;

import android.util.Log;

import com.barkov.ais.cvgram.entity.Cv;
import com.barkov.ais.cvgram.entity.CvInfo;
import com.barkov.ais.cvgram.entity.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CvsParser implements JsonParser{

    @Override
    public ArrayList parse(JSONObject obj) {
        ArrayList<CvInfo> list = new ArrayList<>();
        try {
            JSONArray jsArray = obj.getJSONArray("items");

            for (int i=0; i< jsArray.length(); i++) {
                JSONObject jsonObject=jsArray.getJSONObject(i);

                CvInfo cv = new CvInfo(
                        jsonObject.getString("name"),
                        jsonObject.getString("region"),
                        jsonObject.getString("district"),
                        jsonObject.getString("status"),
                        jsonObject.getString("skills"),
                        jsonObject.optInt("views")
                );
                cv.setId(jsonObject.getInt("id"));
                list.add(cv);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

}
