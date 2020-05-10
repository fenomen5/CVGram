package com.barkov.ais.cvgram.services.parsers;

import android.util.Log;

import com.barkov.ais.cvgram.entity.Message;
import com.barkov.ais.cvgram.entity.MessageThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ThreadMessagesParser implements JsonParser{

    @Override
    public ArrayList parse(JSONObject obj) {
        ArrayList<Message> list = new ArrayList<>();
        try {
            JSONArray jsArray = obj.getJSONArray("items");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            for (int i=0; i< jsArray.length(); i++) {
                JSONObject jsonObject=jsArray.getJSONObject(i);

                try {
                    Log.d("dbg", ""+sdf.parse(jsonObject.getString("date")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    list.add(new Message(
                            jsonObject.getString("senderName"),
                            jsonObject.getString("receiverName"),
                            jsonObject.getString("body"),
                            sdf.parse(jsonObject.getString("date"))
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
