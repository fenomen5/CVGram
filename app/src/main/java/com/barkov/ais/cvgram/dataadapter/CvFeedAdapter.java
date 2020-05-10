package com.barkov.ais.cvgram.dataadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barkov.ais.cvgram.MessageThreadActivity;
import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.entity.CvRssItem;
import com.barkov.ais.cvgram.entity.MessageThread;
import com.barkov.ais.cvgram.services.DataServiceProvider;
import com.barkov.ais.cvgram.services.FeedsServiceProvider;
import com.barkov.ais.cvgram.services.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CvFeedAdapter extends BaseHttpAdapter {

    Context mContext;
    protected  List<CvRssItem> cvRssItems;

    public CvFeedAdapter(Context context) {

        mContext = context;
        getData();
    }

    @Override
    public int getCount() {

        return cvRssItems.size() ;
    }

    @Override
    public Object getItem(int position)
    {
        return cvRssItems.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View cvRssFeedsView = convertView;

        if (cvRssFeedsView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            cvRssFeedsView = li.inflate(R.layout.cvs_feeds_item, parent, false);
        }
        final CvRssItem feedItem = cvRssItems.get(position);
        TextView lblTitle = cvRssFeedsView.findViewById(R.id.lblfeeditemtitle);
        lblTitle.setText(feedItem.getTitle());

        TextView lblDescritpion = cvRssFeedsView.findViewById(R.id.lblfeeditemdescription);
        lblDescritpion.setText(feedItem.getDescription());

        final int pos = position;

        return cvRssFeedsView;
    }

    @Override
    public boolean getData(Integer...params)
    {
        cvRssItems = new ArrayList<CvRssItem>();
        setFetching();

        FeedsServiceProvider dataProvider = new FeedsServiceProvider(mContext);

        if (!dataProvider.isOnline()) {
            dataProvider.displayNoInternetConnection();
            return false;
        }

        try {
            dataProvider.getCVFeed(this);
        } catch (Exception e) {
            e.printStackTrace();
            setReady();
            return  false;
        }

        return true;
    }

    @Override
    public void onTaskCompleted(HashMap result)
    {
        if (result.get("success").equals("true")) {
            cvRssItems = (List<CvRssItem>) result.get("items");
        }

        setReady();
        this.notifyDataSetChanged();
    }
}
