package org.lichblitz.iapps.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.lichblitz.iapps.models.TopAppResponse;
import org.lichblitz.iapps.util.AppConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lichblitz on 7/05/16.
 */
public final class ApiAdapter {

    private static ApiService API_SERVICE;

    private static Gson gson = initGson();

    private static Gson initGson() {
        if(gson==null){
            gson= new GsonBuilder().registerTypeAdapter(TopAppResponse.class,new FeedDeserializer()).create();
        }
        return gson;
    }

    public static ApiService getApiService(){

        if(API_SERVICE == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            API_SERVICE = retrofit.create(ApiService.class);
        }

        return API_SERVICE;
    }
}
