package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{

    private GoogleApiClient mGoogleApiClient;
    private final int RC_SIGN_IN = 9001;
    private CallbackManager mFacebookCallbackManager;
    private String TAG = MainActivity.class.getCanonicalName();
    private SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        checkIfUserIsLoggedIn();

        //On Click listener for register button
        Button registerButton = (Button) findViewById(R.id.nextButton);
        registerButton.setOnClickListener(this);

        // Set the dimensions of the Google sign-in button.
        SignInButton mGoogleSignInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        mGoogleSignInButton.setSize(SignInButton.SIZE_WIDE);
        mGoogleSignInButton.setColorScheme(SignInButton.COLOR_AUTO);
        mGoogleSignInButton.setOnClickListener(this);


        //Facebook Login
        mFacebookCallbackManager = CallbackManager.Factory.create();
        LoginButton mFacebookLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
        mFacebookLoginButton.setOnClickListener(this);
        mFacebookLoginButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                //TODO: Use the Profile class to get information about the current user.
                Profile profile = Profile.getCurrentProfile();
                storeUserId(profile.getId());
                new User(profile.getId(),
                        profile.getFirstName(),
                        profile.getLastName(),
                        profile.getName(),
                        profile.getProfilePictureUri(50,50).toString());
                handleSignInResult(new Callable<Void>() {
                   @Override
                    public Void call() throws Exception {
                       LoginManager.getInstance().logOut();
                       return null;
                   }
                });
            }

            @Override
            public void onCancel() {
                handleSignInResult(null);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, error.getMessage());
                handleSignInResult(null);
            }
        });
    }

    /*
    * Checks if the user is already logged
    * if so redirects to home activity
    */
    private void checkIfUserIsLoggedIn() {


        String userId = preferences.getString("userId", null);

        if (userId != null) {
            handleSignInResult(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    return null;
                }
            });
        }

    }


    /*
    * Handling sign in results
    * Redirects to home activity
    */
    private void handleSignInResult(Callable<Void> logout) {
        if (logout == null){
            Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    private void signInWithGoogle() {

        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signInWithFB() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    //Hide soft keyboard when tapped outside of editViеws
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_sign_in_button:
                signInWithGoogle();
                break;
            case R.id.fb_login_button:
                signInWithFB();
                break;
            case R.id.nextButton:
                registerNewUser();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                final GoogleApiClient client = mGoogleApiClient;
                GoogleSignInAccount acct = result.getSignInAccount();
                storeUserId(acct.getId());
                handleSignInResult(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        if (client != null) {
                            Auth.GoogleSignInApi.signOut(client).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(@NonNull Status status) {
                                            Log.d(MainActivity.class.getCanonicalName(), status.getStatusMessage());
                                        }
                                    }
                            );
                        }
                        return null;
                    }
                });
            }
            else {
                handleSignInResult(null);
            }
        } else mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    /*
    * Stores userId for auto login on next application start
    * */
    private void storeUserId(String userId) {
        preferences.edit().putString("userId", userId).apply();
    }


    /*
    * Validating email
    * and redirect to registration activity
    * */
    private void registerNewUser() {
        EditText emailInput = (EditText) findViewById(R.id.emailInputBox);
        String email = emailInput.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
        if (email.matches(emailPattern)) {
            startActivity(new Intent(this, RegisterActivity.class).putExtra("email", email));
        }
        else Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
    }
}