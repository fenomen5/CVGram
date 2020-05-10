package com.barkov.ais.cvgram.dataadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barkov.ais.cvgram.MessageThreadActivity;
import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.entity.MessageThread;
import com.barkov.ais.cvgram.services.DataServiceProvider;
import com.barkov.ais.cvgram.services.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageThreadsAdapter extends BaseHttpAdapter {

    Context mContext;
    protected  List<MessageThread> messageThreads;

    public MessageThreadsAdapter(Context context) {

        mContext = context;
        getData();
    }

    @Override
    public int getCount() {

        return messageThreads.size() ;
    }

    @Override
    public Object getItem(int position)
    {
        return messageThreads.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View messageThreadsView = convertView;

        if (messageThreadsView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            messageThreadsView = li.inflate(R.layout.message_threads_list_item, parent, false);
        }
        final MessageThread thread = messageThreads.get(position);
        TextView lblSender = messageThreadsView.findViewById(R.id.lblCvTitle);
        lblSender.setText(thread.getSenderName());

        TextView lblReciever = messageThreadsView.findViewById(R.id.lblRegion);
        lblReciever.setText(thread.getRecieverName());

        SimpleDateFormat spf = null;
        try {
            spf = new SimpleDateFormat("dd/MM/yyyy");
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView lblDate = messageThreadsView.findViewById(R.id.lblIndustry);
        lblDate.setText(spf.format(thread.getLastDate()).toString());

        TextView lblMessages = messageThreadsView.findViewById(R.id.lblSkills);
        lblMessages.setText(""+thread.getCounter());

        final int pos = position;

        messageThreadsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageThreadActivity.class);
                Session session = new Session(mContext);
                int userId = session.getUserId();
                int addresser;

                if (userId != thread.getSendTo()) {
                    addresser = thread.getSendTo();
                } else {
                    addresser = thread.getSendFrom();
                }

                intent.putExtra("receiver_id", addresser);

                mContext.startActivity(intent);
            }
        });
        return messageThreadsView;
    }

    @Override
    public boolean getData(Integer...params)
    {
        messageThreads = new ArrayList<MessageThread>();
        setFetching();

        DataServiceProvider dataProvider = new DataServiceProvider(mContext);

        if (!dataProvider.isOnline()) {
            dataProvider.displayNoInternetConnection();
            return false;
        }

        try {
            dataProvider.getMessageThreads(this);
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
            messageThreads = (List<MessageThread>) result.get("items");
        }

        setReady();
        this.notifyDataSetChanged();
    }
}
