package com.barkov.ais.cvgram.dataadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.entity.CvStatus;
import com.barkov.ais.cvgram.entity.Industry;
import com.barkov.ais.cvgram.services.DataServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatusesAdapter extends BaseHttpAdapter {

    Context mContext;
    protected  List<CvStatus> statuses;

    public StatusesAdapter(Context context) {

        mContext = context;
        getData();
    }

    @Override
    public int getCount() {

        return statuses.size() ;
    }

    @Override
    public Object getItem(int position)
    {
        return statuses.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        StatusesHolder holder;
        View statusesView = convertView;

        if (statusesView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            statusesView = li.inflate(R.layout.usertype_spinner, parent, false);
            holder = new StatusesHolder();
            ((StatusesHolder) holder).districtName = statusesView.findViewById(R.id.lblTypeTitle);
            statusesView.setTag(holder);

        } else
            holder = (StatusesHolder) statusesView.getTag();{
    }

        CvStatus status = statuses.get(position);
        Log.d("dbg",status.getName()+ " " + status.getId());
        (holder).districtName.setText(status.getName());
        return statusesView;
    }

    class StatusesHolder {
        private TextView lblUserTypeId;
        private TextView districtName;
    }

    @Override
    public boolean getData(Integer...params)
    {
        statuses = new ArrayList<CvStatus>();
        setFetching();

        DataServiceProvider dataProvider = new DataServiceProvider(mContext);

        if (!dataProvider.isOnline()) {
            dataProvider.displayNoInternetConnection();
            return false;
        }

        try {
            Log.d("dbg", "request regions");
            dataProvider.getCvStatuses(this);
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
            statuses = (List<CvStatus>) result.get("items");
        }

        setReady();
        this.notifyDataSetChanged();
    }
}
