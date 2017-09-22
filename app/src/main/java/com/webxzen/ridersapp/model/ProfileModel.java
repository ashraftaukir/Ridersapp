package com.webxzen.ridersapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by behestee on 9/23/17.
 */

public class ProfileModel {

    public String photo;
    public String name;
    @SerializedName("curr_rating")
    public String currRating;
    public String email;
    public String phone;
    public String address;
}
