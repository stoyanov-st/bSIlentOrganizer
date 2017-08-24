package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.login.widget.ProfilePictureView;

import org.greenrobot.greendao.query.QueryBuilder;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener{
    UserDao userDao;

    private Toolbar toolbar;
    private NavigationView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    private User user;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userDao = MainActivity.getDaoSession().getUserDao();

        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.FullName.isNotNull()).limit(1);
        List<User> users = qb.list();
        user = users.get(0);
        String userId = getUserId();

        //Load Home Fragment
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameContent, HomeFragment.newInstance()).commit();

        // Toolbar and NavigationDrawer Setup
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(user.getFullName());
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.left_drawer);
        drawerList.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(drawerList)) {
                    drawerLayout.closeDrawer(drawerList, true);
                }
                else {
                    drawerLayout.openDrawer(drawerList, true);
                }
            }
        });

        //Navigation Menu onClickListener
        drawerList.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Fragment fragment = null;
                        Class fragmentClass;

                        switch (menuItem.getItemId()) {
                            case R.id.home_view:
                                fragmentClass = HomeFragment.class;
                                break;
                            default:
                                fragmentClass = HomeFragment.class;
                        }

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }

                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit();

                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();

                        return true;
                    }
                });

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        //Google profile picture
       if (user.getImageUrl().contains("google")){
           try {

               new DownloadGProfilePicture(this).execute(new URL(user.getImageUrl()));
           } catch (MalformedURLException e) {
               e.printStackTrace();
           }
       } else {
           //Facebook profile picture
           ProfilePictureView profilePictureView = findViewById(R.id.fb_profile_picture);
           profilePictureView.setVisibility(View.VISIBLE);
           profilePictureView.setProfileId(userId);
       }
    }

    private static class DownloadGProfilePicture extends AsyncTask<URL, Void, Bitmap> {
        Object activity;

        DownloadGProfilePicture(Activity activity) {
             this.activity = activity;
        }

        private Object getActivity() {
            return activity;
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {
            Bitmap googleProfilePicture = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                googleProfilePicture = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(inputStream), 50, 55, false);
                connection.disconnect();
            } catch ( IOException ioe) {
                ioe.printStackTrace();
            }

            return googleProfilePicture;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Activity mActivity = (Activity) getActivity();
            ImageView googleImageView = mActivity.findViewById(R.id.g_profile_picture);
            googleImageView.setVisibility(View.VISIBLE);
            googleImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPostCreate(Bundle savedInstancesState) {
        super.onPostCreate(savedInstancesState);

        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(drawerList)) {
            drawerLayout.closeDrawers();
        }
        else super.onBackPressed();
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString("userId", "UserID");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}