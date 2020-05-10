package com.barkov.ais.cvgram.dataadapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barkov.ais.cvgram.entity.UserType;
import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.services.DataServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserTypesAdapter extends BaseHttpAdapter {

    Context mContext;
    protected  List<UserType> userTypes;

    public UserTypesAdapter(Context context) {

        mContext = context;
        getData();
    }

    @Override
    public int getCount() {

        return userTypes.size() ;
    }

    @Override
    public Object getItem(int position)
    {
        return userTypes.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        UserTypeHolder holder;
        View userTypeView = convertView;

        if (userTypeView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            userTypeView = li.inflate(R.layout.usertype_spinner, parent, false);
            holder = new UserTypeHolder();
            holder.lblUserTypeName = userTypeView.findViewById(R.id.lblTypeTitle);
            userTypeView.setTag(holder);

        } else
            holder = (UserTypeHolder) userTypeView.getTag();{
    }

        UserType userType = userTypes.get(position);
        Log.d("dbg",userType.getName()+ " " + userType.getId());
        holder.lblUserTypeName.setText(userType.getName());
        return userTypeView;
    }

    class UserTypeHolder {
        private TextView lblUserTypeId;
        private TextView lblUserTypeName;
    }

    @Override
    public boolean getData(Integer...params)
    {
        userTypes = new ArrayList<UserType>();
        setFetching();

        DataServiceProvider dataProvider = new DataServiceProvider(mContext);

        if (!dataProvider.isOnline()) {
            dataProvider.displayNoInternetConnection();
            return false;
        }

        try {
            dataProvider.getUserTypes(this);
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        } finally {
            setReady();
        }

        return true;
    }

    @Override
    public void onTaskCompleted(HashMap result)
    {
        if (result.get("success").equals("true")) {
            userTypes = (List<UserType>) result.get("items");
        }

        setReady();
        this.notifyDataSetChanged();
    }
}
