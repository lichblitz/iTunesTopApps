package org.lichblitz.iapps.util;

/**
 * Created by lichblitz on 7/05/16.
 *
 * App constants base
 */
public class AppConstants {

    /** URL Constants **/
    public static final String URL_BASE = "https://itunes.apple.com";
    public static final String URL_SUFFIX = "/us/rss/topfreeapplications/";
    public static final String URL_PARAM_LIMIT = "limit={limit}";
    public static final String URL_RESULT_TYPE_JSON = "/json";
    public static final String URL = URL_BASE +  URL_SUFFIX + URL_PARAM_LIMIT + URL_RESULT_TYPE_JSON;

    /** general constants **/
    public static final String LIMIT = "limit";
    public static final String LAST_UPDATED = "lastUpdated";
}
