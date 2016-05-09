package org.lichblitz.iapps.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.lichblitz.iapps.R;
import org.lichblitz.iapps.fragments.AppActivity;
import org.lichblitz.iapps.models.TopApp;

import java.util.ArrayList;

/**
 * Created by lichblitz on 8/05/16.
 */
public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppHolder> {

    private Context context;
    private ArrayList<TopApp> topApps;
    private boolean isTablet;



    public AppAdapter(Context context, ArrayList<TopApp> apps, boolean isTablet){
        this.context = context;
        this.topApps = apps;
        this.isTablet = isTablet;
    }

    /**
     * Replace the arraylist of the topapps
     * @param apps
     */
    public void addAll(ArrayList<TopApp> apps){
        this.topApps = apps;
        notifyDataSetChanged();
    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(isTablet)
            itemView = LayoutInflater.from(context).inflate(R.layout.item_app_tablet, parent, false);
        else
            itemView = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false);

        return new AppHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(AppHolder holder, int position) {
        TopApp app = topApps.get(position);
        holder.setApp(app);
        holder.setAppName((position+1) + ". " + app.getAppName());
        holder.setAppRights(app.getCopyright());
        if(isTablet)
            holder.setAppImage(context,app.getImagesData().get(100));
        else
            holder.setAppImage(context, app.getImagesData().get(100));


    }

    @Override
    public int getItemCount() {
        return topApps.size();
    }

    /**
     * App holder
     */
    public static final class AppHolder extends RecyclerView.ViewHolder{

        ImageView appImage;
        TextView appName;
        TextView appRights;
        TopApp app;
        View view;
        Context context;

        public AppHolder(View itemView, final Context context) {
            super(itemView);
            appImage = (ImageView) itemView.findViewById(R.id.app_image);
            appName = (TextView) itemView.findViewById(R.id.app_title);
            appRights = (TextView) itemView.findViewById(R.id.app_copyright);
            view = itemView;
            this.context = context;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, AppActivity.class);
                    intent.putExtra(AppActivity.TOP_APP, app);

                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.anim_item_app);
                    view.startAnimation(anim);

                    context.startActivity(intent);
                    ((AppCompatActivity)context).overridePendingTransition(R.anim.anim_left_in, R.anim.anim_left_out);

                }
            });


        }

        public void setApp(TopApp app) {
            this.app = app;
        }

        public void setAppName(String name) {
            this.appName.setText(name);
        }

        public void setAppRights(String appRights) {
            this.appRights.setText(appRights);
        }

        public void setAppImage(Context context, String url) {

            Picasso.with(context)
                    .load(url)
                    .into(appImage);

            Animation anim = AnimationUtils.loadAnimation(context, R.anim.anim_item_app_image);
            appImage.startAnimation(anim);


        }

    }


}
