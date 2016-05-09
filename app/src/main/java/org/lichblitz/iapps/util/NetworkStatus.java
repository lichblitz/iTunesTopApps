package org.lichblitz.iapps.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by lichblitz on 7/05/16.
 */
public final class NetworkStatus {

    /**
     * Get the status of the internet connection
     * @param context: {@linkplain Context} of the application
     * @return {@linkplain boolean}
     */
    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        return (netInfo != null && netInfo.isConnected());

    }
}
