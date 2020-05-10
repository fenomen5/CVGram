package com.barkov.ais.cvgram.dataadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barkov.ais.cvgram.R;
import com.barkov.ais.cvgram.entity.Message;
import com.barkov.ais.cvgram.services.DataServiceProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThreadMessagesAdapter extends BaseHttpAdapter {

    Context mContext;
    protected  List<Message> messages;

    public ThreadMessagesAdapter(int receiverId, Context context) {

        mContext = context;
        getData(receiverId);
    }

    @Override
    public int getCount() {

        return messages.size() ;
    }

    @Override
    public Object getItem(int position)
    {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View messageView = convertView;

        if (messageView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            messageView = li.inflate(R.layout.messages_list_item, parent, false);
        }
        final Message message = messages.get(position);
        TextView lblSender = messageView.findViewById(R.id.lblCvTitle);
        lblSender.setText(message.getSenderName());

        TextView lblReciever = messageView.findViewById(R.id.lblRegion);
        lblReciever.setText(message.getRecieverName());

        SimpleDateFormat spf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        TextView lblDate = messageView.findViewById(R.id.lblIndustry);
        lblDate.setText(spf.format(message.getSendAt()));

        TextView lblMessageText = messageView.findViewById(R.id.lblSkills);
        lblMessageText.setText(""+message.getMessage());

        final int pos = position;

        return messageView;
    }

    @Override
    public boolean getData(Integer...params)
    {
        messages = new ArrayList<Message>();
        setFetching();

        DataServiceProvider dataProvider = new DataServiceProvider(mContext);

        if (!dataProvider.isOnline()) {
            dataProvider.displayNoInternetConnection();
            return false;
        }

        try {
            dataProvider.getThreadMessages(params[0], this);
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
            messages = (List<Message>) result.get("items");
        }

        setReady();
        this.notifyDataSetChanged();
    }
}
