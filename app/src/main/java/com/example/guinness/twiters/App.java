package com.example.guinness.twiters;

import android.app.Application;


import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by guinness on 27/07/17.
 */

public class App extends Application{


    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY)
                , getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET));
        Fabric.with(this, new Twitter(authConfig));
    }
}
