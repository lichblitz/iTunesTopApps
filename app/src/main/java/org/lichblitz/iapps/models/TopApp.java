package org.lichblitz.iapps.models;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by lichblitz on 5/05/16.
 *
 * Represents an application from the top list of the appstore.
 */
public final class TopApp implements Serializable {

    private String appId;
    private String appName;
    private String summary;
    private String category;
    private int categoryId;
    private String copyright;
    private String href;
    private HashMap<Integer, String> imagesData;



    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public HashMap<Integer, String> getImagesData() {
        return imagesData;
    }

    public void setImagesData(HashMap<Integer, String> imagesData) {
        this.imagesData = imagesData;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}