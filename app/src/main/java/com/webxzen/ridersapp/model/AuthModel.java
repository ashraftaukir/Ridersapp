package com.webxzen.ridersapp.model;

import com.google.gson.annotations.SerializedName;



public class AuthModel {
    public boolean success;
    public String message;
    public Result result;


    public class Result{
        public String error;
        @SerializedName("error_description")
        public String errorDescription;
        public LoginModel login;
    }
}
