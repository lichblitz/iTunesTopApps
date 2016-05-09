package org.lichblitz.iapps.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.lichblitz.iapps.R;
import org.lichblitz.iapps.app.MainApplication;
import org.lichblitz.iapps.managers.sqlite.TopAppsDbManager;
import org.lichblitz.iapps.models.TopApp;
import org.lichblitz.iapps.models.TopAppResponse;
import org.lichblitz.iapps.network.ApiAdapter;
import org.lichblitz.iapps.util.AppConstants;
import org.lichblitz.iapps.util.DialogFactory;
import org.lichblitz.iapps.util.NetworkStatus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lichblitz on 7/05/16.
 */
public class SplashActivity extends AppCompatActivity implements Callback<TopAppResponse> {


    private static final String TAG = SplashActivity.class.getCanonicalName();
    public static final String CAT_NAMES = "catNames";
    public static final String CAT_IDS = "catIds";
    public static final String FIRST_START = "firstStart";

    private TopAppsDbManager mDbManager;
    private SharedPreferences mUserPreferences;
    private boolean isFirstLaunch = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Search options on the user preferences **/
        mUserPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isFirstLaunch = mUserPreferences.getBoolean(FIRST_START, true);
        mDbManager = new TopAppsDbManager(this);

        if(isFirstLaunch) {

            //No internet connection
            if(!NetworkStatus.isOnline(this)){
                DialogFactory.getInfoDialog(this,
                        MainApplication.getStringFromId(R.string.error_network_offline_title),
                        MainApplication.getStringFromId(R.string.error_network_offline_message),
                        new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
            }else{
                searchApps();
            }


        }else{
            //No internet connection , no first launch
            //

            if(!NetworkStatus.isOnline(this)){
                //load data from  sqlite
                startMainActivity();
            }else{
                searchApps();
            }
        }



    }

    @Override
    public void onResponse(Call<TopAppResponse> call, Response<TopAppResponse> response) {
        TopAppResponse topAppResponse = response.body();
        if(topAppResponse != null){


            if(isFirstLaunch){

                for(TopApp app : topAppResponse.getTopApps()){
                    mDbManager.insert(app);
                }

            }else{
                if(mUserPreferences.getString(AppConstants.LAST_UPDATED, "lastUpdated")
                        .equals(topAppResponse.getLastUpdated())){
                    mDbManager.deleteAllData();

                    for(TopApp app : topAppResponse.getTopApps()){
                        mDbManager.insert(app);
                    }
                }

            }

            mUserPreferences.edit().putBoolean(FIRST_START, false).commit();
            mUserPreferences.edit().putString(AppConstants.LAST_UPDATED,
                    topAppResponse.getLastUpdated()).commit();

            startMainActivity();
        }
    }

    @Override
    public void onFailure(Call<TopAppResponse> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
    }


    /**
     * Calls the api for top apps
     */
    private void searchApps(){
        int limit = mUserPreferences.getInt(AppConstants.LIMIT, 40);
        /** search for list updates **/
        Call<TopAppResponse> topAppResponseCall = ApiAdapter.getApiService().getTopApps(limit);
        topAppResponseCall.enqueue(this);
    }

    /**
     * Starts the main activity of the application
     */
    private void startMainActivity(){

        HashMap<Integer, String> categories = mDbManager.getAll();


        String[] catNames = new String[categories.size()];
        int[] catIds = new int[categories.size()];

        Iterator<Map.Entry<Integer, String>> iterator = categories.entrySet().iterator();

        int counter = 0;

        while(iterator.hasNext()){
            Map.Entry<Integer, String> entry = iterator.next();

            catNames[counter] = entry.getValue();
            catIds[counter] = entry.getKey();

            counter++;
        }


        Intent intentLauncher = new Intent(this, MainActivity.class);

        intentLauncher.putExtra(SplashActivity.CAT_IDS, catIds);
        intentLauncher.putExtra(SplashActivity.CAT_NAMES, catNames);

        startActivity(intentLauncher);
        overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);

        finish();

    }
}
