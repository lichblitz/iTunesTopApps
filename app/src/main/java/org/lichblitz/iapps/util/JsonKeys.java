package org.lichblitz.iapps.util;

/**
 * Created by lichblitz on 5/05/16.
 *
 * Contains all the necessary JsonKeys for parcing the json response from the appstore api
 *
 */
public final class JsonKeys {

    /* General json keys */
    public static final String FEED = "feed";
    public static final String ENTRY = "entry";
    public static final String UPDATED = "updated"; //last updated
    public static final String LABEL = "label";
    public static final String ATTRIBUTE = "attributes";

    /* App json keys */
    public static final String NAME = "im:name";
    public static final String IMAGE = "im:image";
    public static final String SUMMARY = "summary";
    public static final String RIGHTS = "rights";
    public static final String CATEGORY = "category";
    public static final String ID = "id";
    public static final String IM_ID = "im:id";
    public static final String LINK = "link";
    public static final String HREF = "href";
    public static final String HEIGHT = "height";

    /** Avoid instantiation */
    private JsonKeys(){}

}
