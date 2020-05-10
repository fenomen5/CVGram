package com.barkov.ais.cvgram.services.parsers;

import android.util.Log;

import com.barkov.ais.cvgram.entity.CvRssItem;
import com.barkov.ais.cvgram.entity.Region;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class CvFeedParser {

    XmlPullParser parser;
    public ArrayList parse(String obj) {
        String title ="",link ="", description="";
        int event;
        String text=null;
        ArrayList<CvRssItem> list = new ArrayList<>();

        XmlPullParserFactory xmlFactoryObject = null;
        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(obj));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        try {
            event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {

                String name=parser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(name.equals("title")){
                            title = text;
                        }
                        else if(name.equals("link")){
                            link = text;
                        }
                        else if(name.equals("description")){
                            description = text;
                        }
                        else if(name.equals("item")) {
                            list.add(new CvRssItem(title,link,description));
                        }
                        break;
                }
                event = parser.next();
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("dbg list feed", list.toString());
        return list;
    }

}
