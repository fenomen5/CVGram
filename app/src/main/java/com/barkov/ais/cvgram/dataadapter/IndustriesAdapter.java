package com.barkov.ais.cvgram.dataadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.entity.District;
import com.barkov.ais.cvgram.entity.Industry;
import com.barkov.ais.cvgram.entity.Region;
import com.barkov.ais.cvgram.services.DataServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class IndustriesAdapter extends BaseHttpAdapter {

    Context mContext;
    protected  List<Industry> industries;

    public IndustriesAdapter(Context context) {

        mContext = context;
        getData();
    }

    @Override
    public int getCount() {

        return industries.size() ;
    }

    @Override
    public Object getItem(int position)
    {
        return industries.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        IndustriesHolder holder;
        View industriesView = convertView;

        if (industriesView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            industriesView = li.inflate(R.layout.usertype_spinner, parent, false);
            holder = new IndustriesHolder();
            ((IndustriesHolder) holder).districtName = industriesView.findViewById(R.id.lblTypeTitle);
            industriesView.setTag(holder);

        } else
            holder = (IndustriesHolder) industriesView.getTag();{
    }

        Industry industry = industries.get(position);
        Log.d("dbg",industry.getName()+ " " + industry.getId());
        (holder).districtName.setText(industry.getName());
        return industriesView;
    }

    class IndustriesHolder {
        private TextView lblUserTypeId;
        private TextView districtName;
    }

    @Override
    public boolean getData(Integer...params)
    {
        industries = new ArrayList<Industry>();
        setFetching();

        DataServiceProvider dataProvider = new DataServiceProvider(mContext);

        if (!dataProvider.isOnline()) {
            dataProvider.displayNoInternetConnection();
            return false;
        }

        try {
            Log.d("dbg", "request regions");
            dataProvider.getIndustries(this);
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
            industries = (List<Industry>) result.get("items");
            if (this.getEmptyValue()) {
                industries.add(0,new Industry(0,""));
            }

        }

        setReady();
        this.notifyDataSetChanged();
    }
}
