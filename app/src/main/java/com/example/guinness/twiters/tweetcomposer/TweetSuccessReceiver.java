package com.example.guinness.twiters.tweetcomposer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

/**
 * Created by guinness on 25/07/17.
 */

public class TweetSuccessReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Long tweetId = intentExtras.getLong(TweetUploadService.EXTRA_TWEET_ID);
            Toast.makeText(context, "Success TweetId " + Long.toString(tweetId),
                    Toast.LENGTH_SHORT).show();
        }
    }
}