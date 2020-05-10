package com.barkov.ais.cvgram.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Session {

    private Context mContext;

    public Session(Context context) {
        this.mContext = context;
    }

    /**
     * Retrieving session token
     * @return
     */
    public String getToken()
    {

        SharedPreferences preferences =
                this.mContext.getSharedPreferences(this.mContext.getPackageName(), Context.MODE_PRIVATE);

        return preferences.getString("session", "");
    }

    /**
     * Save session token
     * @param token
     * @return
     */
    public boolean setToken(String token)
    {
        Log.d("dbg", "session:" + token);
        SharedPreferences preferences =
                this.mContext.getSharedPreferences(this.mContext.getPackageName(), Context.MODE_PRIVATE);

        preferences.edit().putString("session", token).commit();

        return true;
    }

    /**
     * Save session userId
     * @param userId
     * @return
     */
    public boolean setUserId(int userId)
    {
        Log.d("dbg", "userId:" + userId);
        SharedPreferences preferences =
                this.mContext.getSharedPreferences(this.mContext.getPackageName(), Context.MODE_PRIVATE);

        preferences.edit().putString("userId", "" + userId).commit();

        return true;
    }

    /**
     * Get session userId
     * @return
     */
    public int getUserId()
    {
        SharedPreferences preferences =
                this.mContext.getSharedPreferences(this.mContext.getPackageName(), Context.MODE_PRIVATE);

        return Integer.valueOf(preferences.getString("userId", "0"));

    }
}
