package com.barkov.ais.cvgram.clients;

import org.json.JSONException;
import org.json.JSONObject;

public class Response {

    private int code;
    private boolean success;
    private String rawResponse;
    private String from;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public JSONObject getJsonResponse() {

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(this.rawResponse);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return jsonObject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", success=" + success +
                ", rawResponse='" + rawResponse + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}
