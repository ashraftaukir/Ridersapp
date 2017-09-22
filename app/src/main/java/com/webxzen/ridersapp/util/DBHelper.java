package com.webxzen.ridersapp.util;

import android.content.Context;

import com.webxzen.ridersapp.model.LoginModel;

import io.paperdb.Paper;

/**
 * Created by behestee on 9/22/17.
 */

public class DBHelper {

    public static final String DB_VERSION = "version";
    public static final String DB_LOGIN = "login";
    public static final String DB_LOCATIONS = "locations";
    public static final String DB_CURRENT_LOCATIONS = "curr_location";

    public static void init(Context context){
        Paper.init(context);
    }

//    public static void saveVersion(ArrayList<VersionModel> version){
//        try {
//            Paper.book().write(DB_VERSION, version);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static ArrayList<VersionModel> getVersions() {
//        try {
//            return Paper.book().read(DB_VERSION, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static boolean saveLogin(LoginModel loginModel){
        try {
            Paper.book().write(DB_LOGIN, loginModel);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static LoginModel getSavedLogin() {
        try {
            return Paper.book().read(DB_LOGIN);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void remove(String key) {
        try {
            Paper.book().delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void destroy(){
        Paper.book().destroy();
    }

}
