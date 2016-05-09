package org.lichblitz.iapps.app;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by Carlos Cortés on 30/12/2015.
 */

public class MainApplication extends Application {

    /**
     * Application context.
     */

    private static Context context;



    /**
     * Return App global {@linkplain android.content.Context}.
     *
     * @return {@linkplain android.content.Context} global context.
     */

    public static Context getContext() {

        return context;

    }


    /**
     * Return a string for a given resource.
     *
     * @param resource String resource.
     * @return {@linkplain java.lang.String} for the given resource.
     */

    public static String getStringFromId(int resource) {

        return getContext().getString(resource);

    }

    public static String getStringFromId(int resource, Object... params){
        return getContext().getString(resource, params);

    }


    /**
     * Get a resource for a given int resource.
     * @param resource int resource.
     * @return {@linkplain java.lang.Integer} for the given resource.
     */

    public static int getIntFromId(int resource) {

        return getContext().getResources().getInteger(resource);

    }


    /**
     * Get a color for a given int.
     * @param resource int resource.
     * @return {@linkplain java.lang.Integer} for the given resource.
     */
    public static int getColorFromId(int resource){
        return getContext().getResources().getColor(resource);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Stetho.initializeWithDefaults(this);

    }
}