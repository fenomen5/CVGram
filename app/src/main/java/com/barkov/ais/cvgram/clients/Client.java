package com.barkov.ais.cvgram.clients;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.barkov.ais.cvgram.services.OnClientResponse;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class Client {

    private URL mUrl;
    private String mResponse;
    private Response resp;
    private OnClientResponse mListener;

    public Client(URL url, OnClientResponse listener) {
        this.mUrl = url;
        this.mListener = listener;
        resp = new Response();
    }

    /**
     * Send request to a server
     * @param data
     * @return
     */
    protected boolean send(String data) {

        final String json = data;

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                return getServerResponse(json);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("dbg repsonse", s);
                Client.this.mListener.onClientResponse(resp);
            }
        }.execute();

        return true;
    }

    /**
     * Request server for data
     * @param json
     * @return
     */
    private String getServerResponse(String json)
    {
        Log.d("dbg", "params" + json);
        Log.d("dbg", "url" + this.mUrl);
        OutputStream out = null;
        String response = "";

        try {
            HttpURLConnection urlConn = (HttpURLConnection) this.mUrl.openConnection();

            if (this.mUrl.toString().contains("feed")) {
                urlConn.setRequestMethod("GET");
                urlConn.setDoInput(true);
            } else {
                urlConn.setRequestMethod("POST");
                urlConn.setDoOutput(true);
                urlConn.setDoInput(true);
                out = new BufferedOutputStream(urlConn.getOutputStream());

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8") );
                writer.write(json);
                writer.flush();
                writer.close();
                out.close();
            }

            Log.d("dbg", ""+urlConn.getResponseCode());
            Log.d("dbg", ""+urlConn.getResponseMessage());

            resp.setCode(urlConn.getResponseCode());

            int responseCode=urlConn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response += line;
                }
            }
            else {
                response="";
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("dbg exception", ""+e.getStackTrace());
        }

        resp.setRawResponse(response);
        return response;
    }
}
