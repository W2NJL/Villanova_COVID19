package com.w2njl.VillanovaCovid19;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Templatesvilla on 7/30/2018.
 *
 */


public class ConnectionDetector {

    private Context _context;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            //noinspection deprecation
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (NetworkInfo anInfo : info)
                if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }

        }
        return false;
    }

}
