package com.barkov.ais.cvgram.services.parsers;

import com.barkov.ais.cvgram.entity.CvStatus;
import com.barkov.ais.cvgram.entity.MessageThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessageThreadsParser implements JsonParser{

    @Override
    public ArrayList parse(JSONObject obj) {
        ArrayList<MessageThread> list = new ArrayList<>();
        try {
            JSONArray jsArray = obj.getJSONArray("items");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i=0; i< jsArray.length(); i++) {
                JSONObject jsonObject=jsArray.getJSONObject(i);
                try {
                    list.add(new MessageThread(
                            jsonObject.getInt("sentFrom"),
                            jsonObject.getInt("sentTo"),
                            jsonObject.getString("senderName"),
                            jsonObject.getString("receiverName"),
                            jsonObject.getInt("counter"),
                            sdf.parse(jsonObject.getString("lastDate"))
                    ));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

}
