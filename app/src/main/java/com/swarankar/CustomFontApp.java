package com.swarankar;

import android.app.Application;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CustomFontApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "Verdana.ttf");

    }
}
