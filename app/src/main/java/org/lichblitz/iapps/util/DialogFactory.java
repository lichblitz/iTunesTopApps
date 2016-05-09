package org.lichblitz.iapps.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import org.lichblitz.iapps.R;

import static android.content.DialogInterface.OnClickListener;

/**
 * Created by lichblitz on 8/05/16.
 */
public final class DialogFactory {

    private static int IC_ICON = R.drawable.ic_alert_dialog;

    /**
     * Creates an alert dialog just to show some info, only have yes option
     * @param context: the context for the dialog
     * @param title: the title
     * @param message: the message
     * @param listener: the yes listener
     */
    public static void getInfoDialog(Context context,
                                     String title,
                                     String message,
                                     OnClickListener listener){

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, listener)
                .setIcon(IC_ICON)
                .show();
    }

    /**
     * Shows an Alert dialog with yes/no options
     * @param context: The context for the dialog
     * @param title: The title
     * @param message: The message
     * @param yesListener: Listener when user hits yes
     * @param noListener: listener when user hits no
     */
    public static void getYesNoDialog(Context context,
                                      String title,
                                      String message,
                                      OnClickListener yesListener,
                                      OnClickListener noListener){

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, yesListener)
                .setNegativeButton(android.R.string.no, noListener)
                .setIcon(IC_ICON)
                .show();
    }
}
