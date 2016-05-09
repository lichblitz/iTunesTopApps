package org.lichblitz.iapps.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.lichblitz.iapps.R;
import org.lichblitz.iapps.app.MainApplication;
import org.lichblitz.iapps.managers.receivers.NetworkBroadcastReceiver;
import org.lichblitz.iapps.models.TopApp;
import org.lichblitz.iapps.util.DialogFactory;
import org.lichblitz.iapps.util.NetworkStatus;

/**
 * Created by lichblitz on 9/05/16.
 */
public class AppActivity extends AppCompatActivity implements DialogInterface.OnClickListener{
    public static final String TOP_APP = "topApp";
    private Button mBtnHref;
    private Button mBtnBack;
    private ImageView mIvAppImage;
    private TextView mTvAppName;
    private TextView mTvAppRights;
    private TextView mTvAppSummary;
    private TopApp topApp;
    private BroadcastReceiver mNetworkReceiver;

    private final DialogInterface.OnClickListener listener = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        topApp = (TopApp) getIntent().getSerializableExtra(TOP_APP);
        if(topApp == null){
            finish();
        }
        mBtnHref = (Button) findViewById(R.id.btn_href);
        mBtnBack = (Button) findViewById(R.id.btn_back);

        mIvAppImage = (ImageView) findViewById(R.id.app_image);
        mTvAppName = (TextView) findViewById(R.id.app_title);
        mTvAppRights = (TextView) findViewById(R.id.app_copyright);
        mTvAppSummary = (TextView) findViewById(R.id.app_summary);


        /** Button listeners **/
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBtnHref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(topApp.getHref());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });


        Picasso.with(this)
                .load(topApp.getImagesData().get(100))
                .into(mIvAppImage);


        mTvAppName.setText(topApp.getAppName());
        mTvAppRights.setText(topApp.getCopyright());
        mTvAppSummary.setText(topApp.getSummary());
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
