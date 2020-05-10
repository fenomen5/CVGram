package com.barkov.ais.cvgram.services.parsers;

import org.json.JSONObject;

import java.util.ArrayList;

public interface JsonParser {

    public ArrayList parse(JSONObject obj);
}
