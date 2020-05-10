package com.barkov.ais.cvgram.dataadapter;

import android.widget.BaseAdapter;

import com.barkov.ais.cvgram.OnTaskCompleted;

public abstract class BaseHttpAdapter extends BaseAdapter implements OnTaskCompleted {

    private boolean isReady;
    private boolean emptyValue;
    /**
     * Get status
     * @return
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * Set fetching status
     */
    public void setFetching() {
        isReady = false;
    }

    /**
     * Set ready status
     */
    public void setReady() {
        isReady = true;
    }

    /**
     * Add empty value to a list
     */
    public void setEmptyValue()
    {
        emptyValue = true;
    }

    protected boolean getEmptyValue()
    {
        return emptyValue;
    }
    public abstract boolean getData(Integer...params);
}
