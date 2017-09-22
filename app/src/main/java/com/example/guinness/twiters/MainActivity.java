package com.example.guinness.twiters;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;


public class MainActivity extends AppCompatActivity {

    TwitterLoginButton loginButton;
    private String socailId = "";
    private String socialType = "";
    String email;
    private TwitterLoginButton twitterLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        //Add callback to the button
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //If login succeeds passing the Calling the login method and passing Result object
                login(result);
            }

            @Override
            public void failure(TwitterException exception) {
                //If failure occurs while login handle it here
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

    }

    private void login(Result<TwitterSession> result) {
        //Creating a twitter session with result's data
        final TwitterSession session = result.data;

        //Getting the username from session
        socailId = "" + session.getUserId();
        socialType = "twitter";

        Log.d("twitterlogId", socailId);

        //Getting the account service of the user logged in
        Call<User> call = Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false);
        call.enqueue(new Callback<User>() {
            @Override
            public void failure(TwitterException e) {
                //If any error occurs handle it here
                e.printStackTrace();
            }

            @Override
            public void success(Result<User> userResult) {
                //If it succeeds creating a User object from userResult.data
                final User user = userResult.data;

                Log.d("twitterimg", user.profileImageUrl);

                final String photoUrlOriginalSize = userResult.data.profileImageUrl.replace("_normal", "");
                Log.d("twitterimg", photoUrlOriginalSize);

                Log.d("twitterimg", user.name);
                email = userResult.data.email;
                if (email == null) {
                    TwitterAuthClient authClient = new TwitterAuthClient();
                    authClient.requestEmail(session, new Callback<String>() {
                        @Override
                        public void success(Result<String> result) {
                            // Do something with the result, which provides the email address
                            Log.d("twittermail", "" + result.data);
                            email = result.data;



                        }

                        @Override
                        public void failure(TwitterException exception) {
                            // Do something on failure
                            exception.printStackTrace();
                        }
                    });
                }

            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        callbackManager.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

}


