package com.example.guinness.twiters.tweetcomposer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

/**
 * Created by guinness on 25/07/17.
 */

public class TweetFailureReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            final Intent retryIntent
                    = intentExtras.getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);
            Log.e("TweetFailureReceiver", retryIntent.toString());
        }
    }
}