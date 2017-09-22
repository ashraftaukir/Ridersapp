package com.webxzen.ridersapp.util;

/**
 * Created by behestee on 9/22/17.
 */

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;

import java8.util.Optional;

public class JSONUtils {

    /**
     * Open a json file from raw and construct as class using Gson.
     *
     * @param resources
     * @param resId
     * @param classType
     * @param <T>
     * @return
     */
    public static <T> Optional<T> getJsonFileAsClass(final Resources resources, final int resId, final Class<T> classType) {

        InputStream resourceReader = resources.openRawResource(resId);
        Writer writer = new StringWriter();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceReader, "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
            }
            return Optional.of(constructUsingGson(classType, writer.toString()));
        } catch (Exception e) {
//            Crashlytics.logException(e);
            Log.d("JSON Exception", e.toString());
        } finally {
            try {
                resourceReader.close();
            } catch (Exception e) {
                Log.d("JSON Exception", e.toString());
            }
        }
        return Optional.empty();
    }

    /**
     * Build an object from the specified JSON resource using Gson.
     *
     * @param type The type of the object to build.
     * @return An object of type T, with member fields populated using Gson.
     */
    private static <T> T constructUsingGson(final Class<T> type, final String jsonString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, type);
    }
}
