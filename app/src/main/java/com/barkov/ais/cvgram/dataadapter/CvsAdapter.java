package com.barkov.ais.cvgram.dataadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barkov.ais.cvgram.EditCvActivity;
import com.barkov.ais.cvgram.MessageThreadActivity;
import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.entity.Cv;
import com.barkov.ais.cvgram.entity.CvInfo;
import com.barkov.ais.cvgram.entity.MessageThread;
import com.barkov.ais.cvgram.services.DataServiceProvider;
import com.barkov.ais.cvgram.services.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CvsAdapter extends BaseHttpAdapter {

    Context mContext;
    protected  List<CvInfo> cvs;

    public CvsAdapter(Context context) {

        mContext = context;
        getData();
    }

    @Override
    public int getCount() {

        return cvs.size() ;
    }

    @Override
    public Object getItem(int position)
    {
        return cvs.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View cvsView = convertView;

        if (cvsView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            cvsView = li.inflate(R.layout.cvs_list_item, parent, false);
        }
        final CvInfo cv = cvs.get(position);
        TextView lblTitle = cvsView.findViewById(R.id.lblCvTitle);
        lblTitle.setText(cv.getTitle());

        TextView lblRegion = cvsView.findViewById(R.id.lblRegion);
        lblRegion.setText(cv.getRegion());

        TextView lblDistrict = cvsView.findViewById(R.id.lblDistrict);
        lblDistrict.setText(cv.getDistrict());

        TextView lblSkills = cvsView.findViewById(R.id.lblSkills);
        lblSkills.setText(""+cv.getSkills());

        final int pos = position;

        cvsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditCvActivity.class);
                Session session = new Session(mContext);

                intent.putExtra("cv_id", cv.getId());

                mContext.startActivity(intent);
            }
        });
        return cvsView;
    }

    @Override
    public boolean getData(Integer...params)
    {
        cvs = new ArrayList<CvInfo>();
        setFetching();

        DataServiceProvider dataProvider = new DataServiceProvider(mContext);

        if (!dataProvider.isOnline()) {
            dataProvider.displayNoInternetConnection();
            return false;
        }

        try {
            dataProvider.getCvs(this);
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
            cvs = (List<CvInfo>) result.get("items");
        }

        setReady();
        this.notifyDataSetChanged();
    }
}
