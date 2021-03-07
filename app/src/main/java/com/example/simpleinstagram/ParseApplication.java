package com.example.simpleinstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("aVyUcvGRZGVLJMW7qFM1XqeBtMoLJG0E2iN9yGYh")
                .clientKey("NNVU7KNIh27O6RtrMt5MpVc7eWPoFLymVtFKCIBe")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
