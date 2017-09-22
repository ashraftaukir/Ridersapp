package com.webxzen.ridersapp.api;

import com.webxzen.ridersapp.model.AuthModel;
import com.webxzen.ridersapp.model.ProfileModel;
import com.webxzen.ridersapp.model.ProfileResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by behestee on 9/22/17.
 */

public interface MyProfileAPI {

    @FormUrlEncoded
    @POST("api/v1/rider/me")
    Call<ProfileResponseModel> getProfile(
            @Header("X-ACCESS-TOKEN") String token
    );



}
