package com.webxzen.ridersapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by behestee on 9/22/17.
 */

public class RetrofitService {

    private static OkHttpClient.Builder httpClient;
    private static Retrofit.Builder builder;

    public static <S> S createService(Class<S> serviceClass, String serverURL, boolean withHeader) {

        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        if(withHeader) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    final Request request = chain.request().newBuilder()
                            .addHeader("X-API-KEY", "e74d0b5234937e14f566bec332707104fd79e834")
                            .build();

                    return chain.proceed(request);
                }
            });
        }

        builder = new Retrofit.Builder()
                        .baseUrl(serverURL)
                        .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

}
