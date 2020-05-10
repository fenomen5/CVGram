package com.barkov.ais.cvgram.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ServiceProvider {

    private Context mContext;

    public ServiceProvider(Context context) {
        this.mContext = context;
    }

    public boolean isOnline()
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public void displayNoInternetConnection()
    {
        Toast.makeText(mContext, "No Internet connection available", Toast.LENGTH_LONG).show();
    }
}
