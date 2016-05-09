package org.lichblitz.iapps.managers.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.lichblitz.iapps.models.TopApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.lichblitz.iapps.managers.sqlite.TopAppsContract.TopAppEntry;
import static org.lichblitz.iapps.managers.sqlite.TopAppsContract.AppImageEntry;

/**
 * Created by lichblitz on 7/05/16.
 *
 * The DB lord manager for the win!
 */
public final class TopAppsDbManager {

    private static SQLiteDatabase mDatabase;
    private static TopAppsDbHelper mDbHelper;
    private Context mContext;

    /**
     * TopAppsManager constructor
     * @param context
     */
    public TopAppsDbManager(Context context){
        this.mContext = context;
        this.mDbHelper = new TopAppsDbHelper(context);
    }

    /**
     * Sets database to read mode
     */
    private void setReadDb(){
        mDatabase = mDbHelper.getReadableDatabase();
    }

    /**
     * Sets database to write mode
     */
    private void setWriteDb(){
        mDatabase = mDbHelper.getWritableDatabase();

    }

    /**
     * Insert a row app into the TopApps table
     * @param app {@linkplain TopApp} object
     * @return {@linkplain Float} with the inserted ID
     */
    public float  insert(TopApp app){
        //sets to write
        setWriteDb();

        ContentValues values = new ContentValues();
        values.put(TopAppEntry.COLUMN_APP_NAME, app.getAppName());
        values.put(TopAppEntry.COLUMN_APP_ID, app.getAppId());
        values.put(TopAppEntry.COLUMN_APP_SUMMARY, app.getSummary());
        values.put(TopAppEntry.COLUMN_APP_CATEGORY, app.getCategory());
        values.put(TopAppEntry.COLUMN_APP_CATEGORY_ID, app.getCategoryId());
        values.put(TopAppEntry.COLUMN_APP_COPYRIGHT, app.getCopyright());
        values.put(TopAppEntry.COLUMN_APP_HREF, app.getHref());


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = mDatabase.insert(
                TopAppsContract.TopAppEntry.TABLE_NAME,
                null,
                values);

        //insert the images
        insert(app.getImagesData(), app.getAppId());

        return newRowId;
    }

    /**
     * Inserts images data into the AppImages table
     * @param images Hashmap of images data
     * @param appId the app id
     */
    public void insert(HashMap<Integer, String> images, String appId){
        setWriteDb();



        Iterator iterator = images.entrySet().iterator();

        while(iterator.hasNext()){
            Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) iterator.next();
            ContentValues values = new ContentValues();

            values.put(AppImageEntry.COLUMN_APP_ID, appId);
            values.put(AppImageEntry.COLUMN_IMAGE_HEIGHT, entry.getKey());
            values.put(AppImageEntry.COLUMN_IMAGE_URL, entry.getValue());

            long newRowId;
            newRowId = mDatabase.insert(
                    AppImageEntry.TABLE_NAME,
                    null,
                    values);

        }

    }

    /**
     * Delete all the data in the tables
     */
    public void deleteAllData(){
        setWriteDb();

        mDatabase.execSQL(TopAppsDbHelper.SQL_CLEAR_ENTRY_TOPAPPS);
        mDatabase.execSQL(TopAppsDbHelper.SQL_CLEAR_ENTRY_IMAGES);
    }

    /**
     * Get a list of categories
     * @return
     */
    public HashMap<Integer, String> getAll(){



        //set readable db
        setReadDb();


        String[] projection = {
                TopAppEntry.COLUMN_APP_CATEGORY_ID,
                TopAppEntry.COLUMN_APP_CATEGORY

        };

        String sortOrder = TopAppEntry.COLUMN_APP_CATEGORY + " DESC";


        Cursor cursor = mDatabase.query(
                TopAppEntry.TABLE_NAME,                   // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                TopAppEntry.COLUMN_APP_CATEGORY_ID,       // group by
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        HashMap<Integer, String> categories = new HashMap();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            categories.put(cursor.getInt(0), cursor.getString(1));

            cursor.moveToNext();

        }


        return categories;
    }

    /**
     * Get all the apps in the database
     * @return
     */
    public ArrayList<TopApp> getAllApps(){


        //set readable db
        setReadDb();


        String[] projection = {
                TopAppEntry.COLUMN_APP_NAME,
                TopAppEntry.COLUMN_APP_SUMMARY,
                TopAppEntry.COLUMN_APP_COPYRIGHT,
                TopAppEntry.COLUMN_APP_ID,
                TopAppEntry.COLUMN_APP_HREF,
                TopAppEntry.COLUMN_APP_CATEGORY_ID,
                TopAppEntry.COLUMN_APP_CATEGORY

        };

        String sortOrder = TopAppEntry._ID + " ASC";


        Cursor cursor = mDatabase.query(
                TopAppEntry.TABLE_NAME,                   // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,       // group by
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        ArrayList<TopApp> topApps = new ArrayList();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TopApp topApp = new TopApp();

            topApp.setAppName(cursor.getString(0));
            topApp.setSummary(cursor.getString(1));
            topApp.setCopyright(cursor.getString(2));
            topApp.setAppId(cursor.getString(3));
            topApp.setHref(cursor.getString(4));
            topApp.setCategoryId(cursor.getInt(5));
            topApp.setCategory(cursor.getString(6));
            topApp.setImagesData(getAppImages(topApp.getAppId()));
            topApps.add(topApp);
            cursor.moveToNext();

        }



        return topApps;
    }

    /**
     * Get the hashmap of images for a given app id
     * @param appId
     * @return
     */
    public HashMap<Integer, String> getAppImages(String appId){
        setReadDb();

        String[] projection = {
                AppImageEntry.COLUMN_IMAGE_HEIGHT,
                AppImageEntry.COLUMN_IMAGE_URL


        };

        String selection =  AppImageEntry.COLUMN_APP_ID + " = ?";



        String[] selectionArgs = {
                appId
        };
        String sortOrder = AppImageEntry._ID + " ASC";

        Cursor cursor = mDatabase.query(
                AppImageEntry.TABLE_NAME,                   // The table to query
                projection,                               // The columns to return
                selection,                                     // The columns for the WHERE clause
                selectionArgs,                                     // The values for the WHERE clause
                null,       // group by
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        cursor.moveToFirst();

        HashMap<Integer, String> images = new HashMap<>();
        while (!cursor.isAfterLast()){
            Log.d("Tag", "getAppImages: "+cursor.getInt(0));
            images.put(cursor.getInt(0), cursor.getString(1));

            cursor.moveToNext();
        }


        return images;
    }
}
