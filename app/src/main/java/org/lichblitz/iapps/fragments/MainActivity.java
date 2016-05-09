package org.lichblitz.iapps.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.lichblitz.iapps.R;
import org.lichblitz.iapps.app.MainApplication;
import org.lichblitz.iapps.managers.receivers.NetworkBroadcastReceiver;
import org.lichblitz.iapps.managers.sqlite.TopAppsDbManager;
import org.lichblitz.iapps.models.TopApp;
import org.lichblitz.iapps.util.DialogFactory;
import org.lichblitz.iapps.util.NetworkStatus;
import org.lichblitz.iapps.views.adapters.AppAdapter;
import org.lichblitz.iapps.views.adapters.CategoryAdapter;

import java.util.ArrayList;

/**
 * Created by lichblitz on 4/05/16.
 */
public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    private static final String TAG = MainActivity.class.getName();

    private RecyclerView mListCategories;
    private RecyclerView mListApps;


    private boolean isTablet = false;

    private String[] mCategories;
    private int[] mCategoriesId;
    private CategoryAdapter mCategoryAdapter;
    private AppAdapter mAppAdapter;
    private TopAppsDbManager mDbManager;

    public static ArrayList<TopApp> topApps;
    private BroadcastReceiver mNetworkReceiver;

    private final DialogInterface.OnClickListener listener = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbManager = new TopAppsDbManager(this);
        isTablet = getResources().getBoolean(R.bool.isTablet);

        if(isTablet)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mCategories = getIntent().getStringArrayExtra(SplashActivity.CAT_NAMES);
        mCategoriesId = getIntent().getIntArrayExtra(SplashActivity.CAT_IDS);

        mListCategories = (RecyclerView) findViewById(R.id.list_categories);

        mListCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mCategoryAdapter = new CategoryAdapter(this, mCategories, mCategoriesId, new CategoryAdapter.CategoryListener() {
            @Override
            public void categorySelectedCallback(int selected) {


                if(selected ==  0){
                    mAppAdapter.addAll(topApps);

                }else{
                    ArrayList<TopApp> arrayTemp = new ArrayList<>();

                    for(TopApp app : topApps){
                        if(app.getCategoryId() == selected){
                            arrayTemp.add(app);
                        }
                    }

                    mAppAdapter.addAll(arrayTemp);

                }
            }
        });


        mListCategories.setAdapter(mCategoryAdapter);

        mListApps = (RecyclerView) findViewById(R.id.list_apps);

        if(isTablet){
            mListApps.setLayoutManager(new GridLayoutManager(this, 6));
        }else{
            mListApps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }

        topApps = mDbManager.getAllApps();
        Log.d(TAG, "onCreate: " + topApps.get(0).getImagesData().get(100));
        mAppAdapter = new AppAdapter(this, topApps , isTablet);

        mListApps.setAdapter(mAppAdapter);

        mNetworkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(NetworkStatus.isOnline(context))
                    DialogFactory.getInfoDialog(context,
                            MainApplication.getStringFromId(R.string.error_network_online_title),
                            MainApplication.getStringFromId(R.string.alert_network_enabled),
                            listener);
                else
                    DialogFactory.getInfoDialog(context,
                            MainApplication.getStringFromId(R.string.error_network_offline_title),
                            MainApplication.getStringFromId(R.string.alert_network_disabled),
                            listener);
            }
        };


    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(mNetworkReceiver, new IntentFilter(NetworkBroadcastReceiver.INTENT_NETWORK_RECEIVER));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mNetworkReceiver);
    }
}
