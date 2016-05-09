package org.lichblitz.iapps.managers.sqlite;

import android.provider.BaseColumns;


/**
 * Created by lichblitz on 7/05/16.
 *
 * Contract for the tables in the sqlite db
 */
public final class TopAppsContract{

    private TopAppsContract(){}

    /**
     * Table TopApps
     */
    public static abstract class TopAppEntry implements BaseColumns{
        public static final String TABLE_NAME = "topApps";
        public static final String COLUMN_APP_ID = "appId";
        public static final String COLUMN_APP_NAME = "name";
        public static final String COLUMN_APP_SUMMARY = "summary";
        public static final String COLUMN_APP_CATEGORY = "category";
        public static final String COLUMN_APP_CATEGORY_ID = "categoryId";
        public static final String COLUMN_APP_COPYRIGHT = "rights";
        public static final String COLUMN_APP_HREF = "href";

    }

    /**
     * Table Images
     */
    public static abstract class AppImageEntry implements BaseColumns{
        public static final String TABLE_NAME = "appImages";
        public static final String COLUMN_IMAGE_HEIGHT = "height";
        public static final String COLUMN_IMAGE_URL = "url";
        public static final String COLUMN_APP_ID = "appId";
    }
}
