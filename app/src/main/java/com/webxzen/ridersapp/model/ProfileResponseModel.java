package com.webxzen.ridersapp.model;

import android.provider.ContactsContract;

import com.google.gson.annotations.SerializedName;

/**
 * Created by behestee on 9/22/17.
 */

public class ProfileResponseModel {
    public int status;
    public String message;
    public Result result;

    public class Result{
        public String error;
        @SerializedName("error_description")
        public String errorDescription;
        public ProfileModel profile;
    }
}
