package org.lichblitz.iapps.network;

import org.lichblitz.iapps.models.TopAppResponse;
import org.lichblitz.iapps.util.AppConstants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lichblitz on 7/05/16.
 */
public interface ApiService {

    @GET(AppConstants.URL)
    Call<TopAppResponse> getTopApps(@Path("limit") int limit);

}
