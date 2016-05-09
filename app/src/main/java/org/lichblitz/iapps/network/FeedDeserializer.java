package org.lichblitz.iapps.network;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.lichblitz.iapps.models.TopApp;
import org.lichblitz.iapps.models.TopAppResponse;
import org.lichblitz.iapps.util.JsonKeys;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lichblitz on 7/05/16.
 *
 * Deserializer for the JSON response of the itunes api
 *
 */
public final class FeedDeserializer implements JsonDeserializer<TopAppResponse>{

    private static final String TAG = FeedDeserializer.class.getCanonicalName();

    @Override
    public TopAppResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String update;
        ArrayList<TopApp> topApps = new ArrayList();
        TopAppResponse response = new TopAppResponse();


        JsonObject feed = json.getAsJsonObject().getAsJsonObject(JsonKeys.FEED);


        if(feed != null){

            update = feed.getAsJsonObject(JsonKeys.UPDATED).get(JsonKeys.LABEL).getAsString();

            //iterate for the array
            JsonArray entry = feed.getAsJsonArray(JsonKeys.ENTRY);

            for(int i = 0; i < entry.size(); i++){


                JsonObject entryObject = entry.get(i).getAsJsonObject();

                //add app into array
                topApps.add(unserializeApp(entryObject));
            }


            response.setLastUpdate(update);
            response.setTopApps(topApps);

        }

        return response;
    }

    /**
     * Unserialize an app JsonObject
     * @param entryObject: The JsonObject in the jsonresponse
     * @return {@linkplain TopApp} with the application data
     */
    private TopApp unserializeApp(JsonObject entryObject){
        TopApp topApp = new TopApp();
        //app id
        topApp.setAppId(entryObject.getAsJsonObject(JsonKeys.ID)
                .getAsJsonObject(JsonKeys.ATTRIBUTE)
                .get(JsonKeys.IM_ID)
                .getAsString());

        //app name
        topApp.setAppName(entryObject.getAsJsonObject(JsonKeys.NAME)
                .get(JsonKeys.LABEL)
                .getAsString());

        //app summary
        topApp.setSummary(entryObject.getAsJsonObject(JsonKeys.SUMMARY)
                .get(JsonKeys.LABEL)
                .getAsString());

        //app category
        JsonObject jsonCategory = entryObject.getAsJsonObject(JsonKeys.CATEGORY)
                .getAsJsonObject(JsonKeys.ATTRIBUTE);

        topApp.setCategory(jsonCategory.get(JsonKeys.LABEL)
                .getAsString());

        topApp.setCategoryId(jsonCategory.get(JsonKeys.IM_ID)
                .getAsInt());

        //app copyright
        topApp.setCopyright(entryObject.getAsJsonObject(JsonKeys.RIGHTS)
                .get(JsonKeys.LABEL)
                .getAsString());

        //app href
        topApp.setHref(entryObject.getAsJsonObject(JsonKeys.LINK)
                .getAsJsonObject(JsonKeys.ATTRIBUTE)
                .get(JsonKeys.HREF)
                .getAsString());

        JsonArray jsonImages =  entryObject.getAsJsonArray(JsonKeys.IMAGE);
        topApp.setImagesData(unserializeImages(jsonImages));

        return topApp;
    }


    /**
     * Unsearialize an array of images data from the jsonresponse into a hashmap
     * @param jsonImages
     * @return {@linkplain HashMap} with the images' data
     */
    private HashMap<Integer, String> unserializeImages(JsonArray jsonImages){
        HashMap<Integer, String> appImages = new HashMap();

        for(int i = 0; i < jsonImages.size(); i++){
            JsonObject jsonImage = jsonImages.get(i).getAsJsonObject();

            int height = jsonImage.getAsJsonObject(JsonKeys.ATTRIBUTE)
                        .get(JsonKeys.HEIGHT).getAsInt();

            String url = jsonImage.get(JsonKeys.LABEL).getAsString();

            appImages.put(height, url);


        }

        return appImages;
    }
}
