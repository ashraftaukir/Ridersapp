package com.webxzen.ridersapp.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by behestee on 9/22/17.
 */

public class Utils {

    public static long convertDate(String dateTime){

        long timeMilis = System.currentTimeMillis();

        try {
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateParser.parse(dateTime);
            timeMilis = date.getTime();
        }catch (ParseException pe){

        }

        return timeMilis;
    }

    public static String getFormalDateFull(String dateTime){

        String formalDate = "";

        try {
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateParser.parse(dateTime);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm a, dd/MM/yyyy");
            formalDate = dateFormatter.format(date);

        }catch (ParseException pe){

        }

        return formalDate;
    }

    public static String getFormalDate(String dateTime){

        String formalDate = "";

        try {
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateParser.parse(dateTime);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            formalDate = dateFormatter.format(date);

        }catch (ParseException pe){

        }

        return formalDate;
    }

    public static long getDiffHour(String endTime, String startTime){

        long endTimeL = convertDate(endTime);
        long startTimeL = convertDate(startTime);

        long diff = endTimeL - startTimeL;

        return TimeUnit.MILLISECONDS.toHours(diff);
    }

    public static String getFormattedAmount(float amount){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        return "RM "+ df.format(amount);
    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }

    }

    public static String loadJSONFromRaw(Context context, int fileId) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(fileId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String getCurrentYear(int nextDay){
        SimpleDateFormat formatServerDate = new SimpleDateFormat("yyyy");

        Calendar today = Calendar.getInstance();

        if(nextDay > 0)
            today.add(Calendar.DATE, nextDay);

        return formatServerDate.format(today.getTime());
    }

    public static String getCurrentMonth(int nextDay){
        SimpleDateFormat formatServerDate = new SimpleDateFormat("MM");

        Calendar today = Calendar.getInstance();

        if(nextDay > 0)
            today.add(Calendar.DATE, nextDay);

        return formatServerDate.format(today.getTime());
    }

    public static String getDeviceId(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

}
