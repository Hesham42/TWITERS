//package com.example.guinness.twiters;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.twitter.sdk.android.core.Callback;
//import com.twitter.sdk.android.core.Result;
//import com.twitter.sdk.android.core.Twitter;
//import com.twitter.sdk.android.core.TwitterException;
//import com.twitter.sdk.android.core.TwitterSession;
//import com.twitter.sdk.android.core.identity.TwitterAuthClient;
//import com.twitter.sdk.android.core.identity.TwitterLoginButton;
//import com.twitter.sdk.android.core.models.User;
//import com.crashlytics.android.Crashlytics;
//import java.util.Arrays;
//
//import retrofit2.Call;
//
///**
// * Created by guinness on 26/07/17.
// */
//
//public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
//
//    // UI references.
//
//
//    TwitterLoginButton twitterLoginButton;
//
//    private View mProgressView;
//    private View mLoginFormView;
//
//    private String email = "";
//    private String socailId = "";
//    private String socialType = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
//        //Add callback to the button
//        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
//            @Override
//            public void success(Result<TwitterSession> result) {
//                //If login succeeds passing the Calling the login method and passing Result object
//                login(result);
//            }
//
//            @Override
//            public void failure(TwitterException exception) {
//                //If failure occurs while login handle it here
//                Log.d("TwitterKit", "Login with Twitter failure", exception);
//            }
//        });
//
//        cacheFile = new CacheFile(LoginActivity.this);
//
//        initView();
//    }
//
//
//
//    //The login function accepting the result object
//    public void login(Result<TwitterSession> result) {
//        //Creating a twitter session with result's data
//        final TwitterSession session = result.data;
//
//        //Getting the username from session
//        socailId = "" + session.getUserId();
//        socialType = "twitter";
//
//        Log.d("twitterlogId", socailId);
//
//        //Getting the account service of the user logged in
//        Call<User> call = Twitter.getApiClient(session).getAccountService()
//                .verifyCredentials(true, false);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void failure(TwitterException e) {
//                //If any error occurs handle it here
//            }
//
//            @Override
//            public void success(Result<User> userResult) {
//                //If it succeeds creating a User object from userResult.data
//                final User user = userResult.data;
//
//                Log.d("twitterimg", user.profileImageUrl);
//
//                final String photoUrlOriginalSize = userResult.data.profileImageUrl.replace("_normal", "");
//                Log.d("twitterimg", photoUrlOriginalSize);
//
//                Log.d("twitterimg", user.name);
//                email = userResult.data.email;
//                if (email == null) {
//                    TwitterAuthClient authClient = new TwitterAuthClient();
//                    authClient.requestEmail(session, new Callback<String>() {
//                        @Override
//                        public void success(Result<String> result) {
//                            // Do something with the result, which provides the email address
//                            Log.d("twittermail", "" + result.data);
//                            email = result.data;
//
//                            socialUserModel = new SocialUserModel(user.name, "", "",
//                                    photoUrlOriginalSize, email, socailId, socialType);
//
//                            if (!email.equals("") && email != null) {
//                                // social request here !
//                                checkUserMail(socialUserModel.getUserEmail(), socailId, socialType);
//                            }
//                        }
//
//                        @Override
//                        public void failure(TwitterException exception) {
//                            // Do something on failure
//                        }
//                    });
//                }
//
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View view) {
//        int id = view.getId();
//        if (id == R.id.email_sign_in_button) {
//            // login btn pressed
//            if (CheckInternetConnection.isNetworkAvailable(LoginActivity.this)) {
//                onLoginPressed();
//            } else {
//                Toast.makeText(LoginActivity.this, R.string.check_internet_connection,
//                        Toast.LENGTH_LONG).show();
//            }
//        } else if (id == R.id.createaccount) {
//            // create new account , go to register activity
//            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
//        } else if (id == R.id.resetpassword) {
//            // reset password pressed , go to reset password activity
//            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
//        }
//
//    }
//
//    private void onLoginPressed() {
//
//        if (!validateEmail()) {
//            return;
//        }
//
//        if (!validatePassword()) {
//            return;
//        }
//
//        // login request here !
//        final String mail = mEmailView.getText().toString();
//        String password = mPasswordView.getText().toString();
//
//        RequestQueue requestQueue = AppController.getInstance().getRequestQueue();
//        HashMap<String, String> loginMapData = new HashMap<String, String>();
//        loginMapData.put("email", mail);
//        loginMapData.put("password", password);
//
//        showProgress(true);
//        CustomObjectRequest loginObjectRequest = new CustomObjectRequest(Request.Method.POST, Const.LOGIN_URL,
//                loginMapData, new Response.Listener<JSONObject>() {
//
//
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    // getting response
//                    Log.d("loginres", response.toString());
//                    if (response.has("success")) {
//                        if (response.getBoolean("success") == false) {
//                            Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
//                        }
//                        showProgress(false);
//                        return;
//                    }
//                    if (response.getInt("status") == 200) {
//                        JSONObject userObject = response.getJSONObject("data");
//
//                        parseResult(userObject);
//
//
//                    } else {
//                        Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_LONG).show();
//                    }
//
//                    showProgress(false);
//                } catch (Exception e) {
//                    Log.d("parseerr", e.toString());
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                showProgress(false);
//                Log.d("error", error.toString());
//            }
//        });
//        loginObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(loginObjectRequest);
//    }
//
//    private void parseResult(JSONObject userObject) {
//        String userId = null;
//        try {
//            userId = userObject.getString("id");
//
//            String userName = userObject.getString("username");
//            String firstName = userObject.getString("first_name");
//            String lastName = userObject.getString("last_name");
//            String mobile = userObject.getString("mobile");
//            String mail = userObject.getString("email");
//            String age = userObject.getString("age");
//
//            String userImage = "";
//            if (userObject.has("photo")) {
//                JSONObject photo = userObject.getJSONObject("photo");
//                userImage = photo.getString("url");
//                Log.d("userImageCached", userImage);
//            }
//            JSONObject tokenObject = userObject.getJSONObject("auth_token");
//            String token = tokenObject.getString("auth_token");
//
//            JSONObject parentObj = userObject.getJSONObject("parent");
//            String parentMobile = parentObj.getString("mobile");
//
//            AppController.cacheFile.insertKey("parent_mobile", parentMobile);
//
//            AppController.cacheFile.insertKey("user_token", token);
//            AppController.cacheFile.insertKey("user_id", userId);
//            AppController.cacheFile.insertKey("user_name", userName);
//            AppController.cacheFile.insertKey("first_name", firstName);
//            AppController.cacheFile.insertKey("last_name", lastName);
//            AppController.cacheFile.insertKey("user_mail", mail);
//            AppController.cacheFile.insertKey("user_image", userImage);
//            AppController.cacheFile.insertKey("user_age", age);
//            AppController.cacheFile.insertKey("user_mobile", mobile);
//            AppController.cacheFile.insertKey("logged", "1");
//
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // email validate
//    private boolean validateEmail() {
//        String email = mEmailView.getText().toString().trim();
//
//        if (email.isEmpty() || !isValidEmail(email)) {
//            mEmailView.setError(getString(R.string.err_msg_usermail));
//            requestFocus(mEmailView);
//            return false;
//        }
//
//        return true;
//    }
//
//    private boolean isValidEmail(String email) {
//        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }
//
//    private boolean validatePassword() {
//        if (mPasswordView.getText().toString().trim().isEmpty()) {
//            mPasswordView.setError(getString(R.string.err_msg_password));
//            requestFocus(mPasswordView);
//            return false;
//        }
//        return true;
//    }
//
//    private void requestFocus(View view) {
//        if (view.requestFocus()) {
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        }
//    }
//
//    /**
//     * Shows the progress UI and hides the login form.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void checkUserMail(String email, String socialId, String socialType) {
//        if (!CheckInternetConnection.isNetworkAvailable(LoginActivity.this)) {
//            Toast.makeText(LoginActivity.this, getResources().getString(R.string.check_internet_connection)
//                    , Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.MyDialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Loading ...");
//        progressDialog.show();
//
//        RequestQueue requestQueue = AppController.getInstance().getRequestQueue();
//        HashMap<String, String> friendsMap = new HashMap<String, String>();
//        friendsMap.put("usermail", email);
//        friendsMap.put("social_id", socialId);
//        friendsMap.put("social_type", socialType);
//        final CustomObjectRequest mailObjectRequest = new CustomObjectRequest(Request.Method.POST, Const.SOCAIL_REGISTER_URL,
//                friendsMap, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    Log.d("userRes", response.toString());
//                    // check if the user already found
//                    if (response.getInt("status") == 200) {
//                        JSONObject dataObj = response.getJSONObject("data");
//                        parseResult(dataObj);
//                    } else if (response.getInt("status") == 422) {
//                        // add else here if user not found go to continue screen
//                        Intent intent = new Intent(LoginActivity.this, ContinueRegisterSocailActivity.class);
//                        intent.putExtra("user_object", socialUserModel);
//                        startActivity(intent);
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    progressDialog.dismiss();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (error instanceof TimeoutError) {
//                    Toast.makeText(LoginActivity.this, R.string.weak_internet_connection
//                            , Toast.LENGTH_LONG).show();
//                }
//                Log.d("userRes", error.toString());
//                progressDialog.dismiss();
//            }
//        });
//        mailObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(mailObjectRequest);
//    }
//}