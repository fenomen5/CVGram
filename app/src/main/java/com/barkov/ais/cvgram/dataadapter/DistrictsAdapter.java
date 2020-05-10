package com.barkov.ais.cvgram.dataadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.entity.District;
import com.barkov.ais.cvgram.entity.Region;
import com.barkov.ais.cvgram.services.DataServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DistrictsAdapter extends BaseHttpAdapter {

    Context mContext;
    int postValue;
    protected  List<District> districts;

    public DistrictsAdapter(Context context) {

        mContext = context;
        districts = new LinkedList<>();
    }

    @Override
    public int getCount() {

        return districts.size() ;
    }

    @Override
    public Object getItem(int position)
    {
        return districts.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        DistrictHolder holder;
        View districtView = convertView;

        if (districtView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            districtView = li.inflate(R.layout.usertype_spinner, parent, false);
            holder = new DistrictHolder();
            ((DistrictHolder) holder).districtName = districtView.findViewById(R.id.lblTypeTitle);
            districtView.setTag(holder);

        } else
            holder = (DistrictHolder) districtView.getTag();{
    }

        District district = districts.get(position);
        Log.d("dbg",district.getName()+ " " + district.getId());
        ((DistrictHolder) holder).districtName.setText(district.getName());
        return districtView;
    }

    class DistrictHolder {
        private TextView lblUserTypeId;
        private TextView districtName;
    }

    @Override
    public boolean getData(Integer...params)
    {
        districts = new ArrayList<District>();
        setFetching();

        DataServiceProvider dataProvider = new DataServiceProvider(mContext);

        if (!dataProvider.isOnline()) {
            dataProvider.displayNoInternetConnection();
            setReady();
            return false;
        }
        if (params[0] == 0) {
            setReady();
            return false;
        }
        try {
            Log.d("dbg", "request regions");
            dataProvider.getDistricts(params[0], this);
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
            districts = (List<District>) result.get("items");
        }

        setReady();
        this.notifyDataSetChanged();
    }

    public void setPostValue(int itemdId)
    {
        this.postValue = itemdId;
    }
}
