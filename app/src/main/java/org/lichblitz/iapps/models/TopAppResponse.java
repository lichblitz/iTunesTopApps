package org.lichblitz.iapps.models;

import com.google.gson.annotations.SerializedName;

import org.lichblitz.iapps.util.JsonKeys;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by lichblitz on 5/05/16.
 *
 * Represents the result of the call to the itunes' api
 */
public final class TopAppResponse {

    @SerializedName(JsonKeys.ENTRY)
    ArrayList<TopApp> topApps;

    @SerializedName(JsonKeys.UPDATED)
    String lastUpdate;


    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setTopApps(ArrayList<TopApp> topApps) {
        this.topApps = topApps;
    }

    public String getLastUpdated() {
        return this.lastUpdate;
    }

    public ArrayList<TopApp> getTopApps() {
        return topApps;
    }
}
