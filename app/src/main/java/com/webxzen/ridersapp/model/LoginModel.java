package com.webxzen.ridersapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by behestee on 9/22/17.
 */

public class LoginModel implements Parcelable{
    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("expires_in")
    public long expiresIn;
    @SerializedName("token_type")
    public String tokenType;
    public String scope;
    @SerializedName("refresh_token")
    public String refreshToken;

    protected LoginModel(Parcel in) {
        accessToken = in.readString();
        expiresIn = in.readLong();
        tokenType = in.readString();
        scope = in.readString();
        refreshToken = in.readString();
    }

    public LoginModel() {

    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(accessToken);
        parcel.writeLong(expiresIn);
        parcel.writeString(tokenType);
        parcel.writeString(scope);
        parcel.writeString(refreshToken);
    }
}
