package com.surtymar.vbadge.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.surtymar.vbadge.Activities.MainActivity;

/**
 * Created by Anass on 12/07/2017.
 */

public class ConnectivityCheck {

    private Context context;


    public ConnectivityCheck(Context context) {
        this.context = context;
    }


    public boolean isConnected() {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }

        return false;
    }

}
