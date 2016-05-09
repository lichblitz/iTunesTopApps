package org.lichblitz.iapps.managers.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import org.lichblitz.iapps.R;
import org.lichblitz.iapps.app.MainApplication;
import org.lichblitz.iapps.util.DialogFactory;
import org.lichblitz.iapps.util.NetworkStatus;

/**
 * Created by lichblitz on 9/05/16.
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "RECEIVER";
    public static final String INTENT_NETWORK_RECEIVER = "org.lichblitz.iapps.managers.receivers.INTENT_NETWORK_RECEIVER";
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Intent notificationBroadcastIntent = new Intent(INTENT_NETWORK_RECEIVER);
        context.sendBroadcast(notificationBroadcastIntent);

    }

}


