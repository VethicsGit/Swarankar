package com.swarankar.Activity.Utils;

        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by softeaven on 6/8/2017.
 */

public class APIClient1 {

    public static final String BASE_URL1= "";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL1).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
