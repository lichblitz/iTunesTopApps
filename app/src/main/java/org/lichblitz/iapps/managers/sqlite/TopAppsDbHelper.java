package org.lichblitz.iapps.managers.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static org.lichblitz.iapps.managers.sqlite.TopAppsContract.TopAppEntry;
import static org.lichblitz.iapps.managers.sqlite.TopAppsContract.AppImageEntry;

/**
 * Created by lichblitz on 7/05/16.
 *
 * SQLiteOpenHelper that creates the DB
 */
public final class TopAppsDbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "topapps.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    /** Create topApps table **/
    public static final String SQL_CREATE_ENTRY_TOPAPPS =
            "CREATE TABLE " + TopAppEntry.TABLE_NAME + " (" +
                    TopAppEntry._ID + INT_TYPE + " PRIMARY KEY," +
                    TopAppEntry.COLUMN_APP_ID + TEXT_TYPE + COMMA_SEP +
                    TopAppEntry.COLUMN_APP_NAME + TEXT_TYPE + COMMA_SEP +
                    TopAppEntry.COLUMN_APP_SUMMARY + TEXT_TYPE + COMMA_SEP +
                    TopAppEntry.COLUMN_APP_CATEGORY + TEXT_TYPE + COMMA_SEP +
                    TopAppEntry.COLUMN_APP_CATEGORY_ID + INT_TYPE + COMMA_SEP +
                    TopAppEntry.COLUMN_APP_COPYRIGHT + TEXT_TYPE + COMMA_SEP +
                    TopAppEntry.COLUMN_APP_HREF + TEXT_TYPE +
                    " )";

    /** Create appImages table **/
    public static final String SQL_CREATE_ENTRY_IMAGES =
            "CREATE TABLE " + AppImageEntry.TABLE_NAME + " (" +
                    AppImageEntry._ID + INT_TYPE + " PRIMARY_KEY," +
                    AppImageEntry.COLUMN_APP_ID + TEXT_TYPE + COMMA_SEP +
                    AppImageEntry.COLUMN_IMAGE_HEIGHT + INT_TYPE + COMMA_SEP +
                    AppImageEntry.COLUMN_IMAGE_URL + TEXT_TYPE +
                    " )";

    /** Drop the tables **/

    public static final String SQL_DELETE_ENTRY_TOPAPPS =
            "DROP TABLE IF EXIST " + TopAppEntry.TABLE_NAME;
    public static final String SQL_DELETE_ENTRY_IMAGES =
            "DROP TABLE IF EXIST " + AppImageEntry.TABLE_NAME;

    /** Clear tables **/
    public static final String SQL_CLEAR_ENTRY_TOPAPPS =
            "DELETE FROM " + TopAppEntry.TABLE_NAME;
    public static final String SQL_CLEAR_ENTRY_IMAGES =
            "DELETE FROM " + AppImageEntry.TABLE_NAME;
    /**
     * Constructor
     * @param context
     */
    public TopAppsDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRY_TOPAPPS);
        db.execSQL(SQL_CREATE_ENTRY_IMAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRY_TOPAPPS);
        db.execSQL(SQL_DELETE_ENTRY_IMAGES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
