package com.webxzen.ridersapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by behestee on 9/22/17.
 *
 * This class will process all preferences
 */

public class PreferenceHelper {

    public static final String LOGIN_AGENT_IC = "login_user_ic";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String REFRESH_USER = "refresh_user";

    private SharedPreferences preferences;

    /**
     * Creating preferences object
     * @param context
     */
    public PreferenceHelper(Context context) {
        preferences = (preferences == null) ?
                context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
                : preferences;
    }

    /**
     * Save any String preferences based on key
     * @param key
     * @param value
     */
    public void save(String key, String value){
        preferences.edit().putString(key, value).commit();
    }

    /**
     * Save any int value based on key
     * @param key
     * @param value
     */
    public void save(String key, int value){
        preferences.edit().putInt(key, value).commit();
    }

    /**
     * Save any boolean value based on key
     * @param key
     * @param value
     */
    public void save(String key, boolean value){
        preferences.edit().putBoolean(key, value).commit();
    }

    /**
     * Return string value based on key (default null)
     * @param key
     * @return
     */
    public String getStringValue(String key){
        return preferences.getString(key, null);
    }

    /**
     * Return int value based on key (default 0)
     * @param key
     * @return
     */
    public int getIntValue(String key){
        return preferences.getInt(key, 0);
    }

    /**
     * Return boolean value based on key (default false)
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key){
        return preferences.getBoolean(key, false);
    }

}
