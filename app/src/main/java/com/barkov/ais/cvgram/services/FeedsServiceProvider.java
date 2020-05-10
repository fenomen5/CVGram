package com.barkov.ais.cvgram.services;

import android.content.Context;
import android.util.Log;

import com.barkov.ais.cvgram.OnTaskCompleted;
import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.clients.DataClient;
import com.barkov.ais.cvgram.clients.FeedClient;
import com.barkov.ais.cvgram.clients.Response;
import com.barkov.ais.cvgram.services.parsers.CvFeedParser;
import com.barkov.ais.cvgram.services.parsers.CvStatusesParser;
import com.barkov.ais.cvgram.services.parsers.CvsParser;
import com.barkov.ais.cvgram.services.parsers.DistrictsParser;
import com.barkov.ais.cvgram.services.parsers.IndustriesParser;
import com.barkov.ais.cvgram.services.parsers.JsonParser;
import com.barkov.ais.cvgram.services.parsers.MessageThreadsParser;
import com.barkov.ais.cvgram.services.parsers.RegionsParser;
import com.barkov.ais.cvgram.services.parsers.ThreadMessagesParser;
import com.barkov.ais.cvgram.services.parsers.UserTypesParser;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class FeedsServiceProvider extends ServiceProvider implements OnClientResponse {

    FeedClient mDataClient;
    OnTaskCompleted mListener;
    String mBaseAddress;
    CvFeedParser mParser;
    Context mContext;
    public FeedsServiceProvider(Context context) {
        super(context);
        mContext = context;
        mBaseAddress = context.getResources().getString(R.string.feed_url);
    }

    /**
     * Get regions
     * @param listener
     * @return
     * @throws Exception
     */
    public boolean getCVFeed(OnTaskCompleted listener) throws Exception
    {
        URL url = null;
        try {
            url = new URL(mBaseAddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mDataClient = new FeedClient(url, this);
        this.mParser = new CvFeedParser();
        if (mDataClient.getCvsFeed()) {
            this.mListener = listener;
            return true;
        }

        return false;
    }

    @Override
    public void onClientResponse(Response response) {

        HashMap <String, Object> hm = new HashMap<>();
        String data = null;
        Log.d("dbg onClientResp datase", response.toString());

        data = response.getRawResponse();
        if (data != null) {
            ArrayList list = mParser.parse(data);
            hm.put("items", list);
        }

        if (hm.get("items") != null && ((ArrayList) hm.get("items")).size() > 0) {
            hm.put("success", "true");
        } else {
            hm.put("success", "false");
        }

        this.mListener.onTaskCompleted(hm);
    }
}
