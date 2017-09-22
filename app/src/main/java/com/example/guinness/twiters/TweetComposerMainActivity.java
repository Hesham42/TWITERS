//package com.example.guinness.twiters;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.ShareActionProvider;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import com.twitter.sdk.android.core.TwitterCore;
//import com.twitter.sdk.android.core.TwitterSession;
//import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
//import com.twitter.sdk.android.tweetcomposer.TweetComposer;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//
//
//public class TweetComposerMainActivity extends AppCompatActivity {
//
//    private static final String TAG = "TweetComposer";
//    private static final String IMAGE_TYPES = "image/*";
//    private static final int IMAGE_PICKER_CODE = 141;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        final Button tweetComposer = (Button) findViewById(R.id.tweet_composer);
//        tweetComposer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    new TweetComposer.Builder(TweetComposerMainActivity.this)
//                            .text("Tweet from TwitterKit!")
//                            .url(new URL("http://www.twitter.com"))
//                            .show();
//
//                } catch (MalformedURLException e) {
//                    Log.e(TAG, "error creating tweet intent", e);
//                }
//            }
//        });
//
//        final Button organicComposer = (Button) findViewById(R.id.organic_composer);
//        organicComposer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launchPicker();
//            }
//        });
//
//
//
//    }//end on create
//
//    void launchPicker() {
//        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType(IMAGE_TYPES);
//        startActivityForResult(Intent.createChooser(intent, "Pick an Image"), IMAGE_PICKER_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == IMAGE_PICKER_CODE && resultCode == Activity.RESULT_OK) {
//            launchComposer(data.getData());
//        }
//    }
//
//    void launchComposer(Uri uri) {
//        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
//                .getActiveSession();
//        final Intent intent = new ComposerActivity.Builder(TweetComposerMainActivity.this)
//                .session(session)
//                .image(uri)
//                .text("Tweet from TwitterKit!")
//                .hashtags("#twitter")
//                .createIntent();
//        startActivity(intent);
//    }
//}
//
