package com.barkov.ais.cvgram.dataadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.entity.Region;
import com.barkov.ais.cvgram.services.DataServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegionsAdapter extends BaseHttpAdapter {

    Context mContext;
    protected  List<Region> regions;

    public RegionsAdapter(Context context) {

        mContext = context;
        getData();
    }

    @Override
    public int getCount() {

        return regions.size() ;
    }

    @Override
    public Object getItem(int position)
    {
        return regions.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        RegionHolder holder;
        View regionView = convertView;

        if (regionView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            regionView = li.inflate(R.layout.usertype_spinner, parent, false);
            holder = new RegionHolder();
            ((RegionHolder) holder).regionName = regionView.findViewById(R.id.lblTypeTitle);
            regionView.setTag(holder);

        } else
            holder = (RegionHolder) regionView.getTag();{
    }

        Region region = regions.get(position);
        Log.d("dbg",region.getName()+ " " + region.getId());
        ((RegionHolder) holder).regionName.setText(region.getName());
        return regionView;
    }

    class RegionHolder {
        private TextView lblUserTypeId;
        private TextView regionName;
    }

    @Override
    public boolean getData(Integer...params)
    {
        regions = new ArrayList<Region>();
        setFetching();

        DataServiceProvider dataProvider = new DataServiceProvider(mContext);

        if (!dataProvider.isOnline()) {
            dataProvider.displayNoInternetConnection();
            return false;
        }

        try {
            Log.d("dbg", "request regions");
            dataProvider.getRegions(this);
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
            regions = (List<Region>) result.get("items");
            if (this.getEmptyValue()) {
                regions.add(0,new Region(0,""));
            }

        }

        setReady();
        this.notifyDataSetChanged();
    }

}
