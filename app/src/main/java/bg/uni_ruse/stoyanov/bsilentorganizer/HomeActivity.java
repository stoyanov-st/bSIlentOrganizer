package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.content.res.Configuration;
import android.os.Bundle;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.greendao.query.QueryBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import bg.uni_ruse.stoyanov.bsilentorganizer.calendar.CalendarFragment;
import bg.uni_ruse.stoyanov.bsilentorganizer.flashlight.FlashlightFragment;
import bg.uni_ruse.stoyanov.bsilentorganizer.helpers.DownloadGProfilePicture;
import bg.uni_ruse.stoyanov.bsilentorganizer.note.NotesFragment;
import bg.uni_ruse.stoyanov.bsilentorganizer.profile.ProfileFragment;
import bg.uni_ruse.stoyanov.bsilentorganizer.qr.QRScannerFragment;
import bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager.SilentManagerFragment;
import bg.uni_ruse.stoyanov.bsilentorganizer.user.User;
import bg.uni_ruse.stoyanov.bsilentorganizer.user.UserDao;

import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.RedirectMainActivity.goToMainActivity;
import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.SocialId.getUserId;

public class HomeActivity extends AppCompatActivity {

    private UserDao userDao;
    private Toolbar toolbar;
    private NavigationView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    private User user;
    private View headerView;
    private GoogleApiClient mGoogleApiClient;
    private boolean backPressed = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String userId = getUserId(getApplicationContext());
        userDao = MainActivity.getDaoSession().getUserDao();

        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.SocialId.like(userId));
        List<User> users = qb.list();
        user = users.get(0);

        //Load Home Fragment
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameContent, new HomeFragment()).commit();

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
                        Class fragmentClass = null;

                        switch (menuItem.getItemId()) {
                            case R.id.home_view:
                                fragmentClass = HomeFragment.class;
                                break;
                            case R.id.profile_view:
                                fragmentClass = ProfileFragment.class;
                                break;
                            case R.id.silent_mode_view:
                                fragmentClass = SilentManagerFragment.class;
                                break;
                            case R.id.notes_view:
                                fragmentClass = NotesFragment.class;
                                break;
                            case R.id.calendar_view:
                                fragmentClass = CalendarFragment.class;
                                break;
                            case R.id.qr_view:
                                fragmentClass = QRScannerFragment.class;
                                break;
                            case R.id.flashlight_view:
                                fragmentClass = FlashlightFragment.class;
                                break;
                            case R.id.logout:
                                logout();
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

        //Navigation Header setup
        headerView = drawerList.getHeaderView(0);

        TextView headerNavText = headerView.findViewById(R.id.fullNameNav);
        headerNavText.setText(user.getFullName());

        //Google profile picture
       if (user.isGoogleProfile()){
           try {
               new DownloadGProfilePicture(this).execute(new URL(user.getImageUrl()));
               ImageView gProfilePic = toolbar.findViewById(R.id.g_profile_picture);
               gProfilePic.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       try {
                           fragmentManager = getSupportFragmentManager();
                           Fragment profileFragment = (Fragment) ProfileFragment.class.newInstance();
                           fragmentManager.beginTransaction().replace(R.id.frameContent, profileFragment).commit();
                           drawerList.setCheckedItem(R.id.profile_view);
                       } catch (InstantiationException | IllegalAccessException e) {
                           e.printStackTrace();
                       }
                   }
               });

           } catch (MalformedURLException e) {
               e.printStackTrace();
           }
       } else {
           //Facebook profile picture
           ProfilePictureView profilePictureNavView = headerView.findViewById(R.id.fb_profile_picture_nav);
           profilePictureNavView.setProfileId(userId);
           FrameLayout frameLayout = headerView.findViewById(R.id.frame_layout_nav);
           frameLayout.setVisibility(View.VISIBLE);
           ProfilePictureView profilePictureView = findViewById(R.id.fb_profile_picture);
           profilePictureView.setVisibility(View.VISIBLE);
           profilePictureView.setProfileId(userId);
           profilePictureView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   try {
                       fragmentManager = getSupportFragmentManager();
                       Fragment profileFragment = (Fragment) ProfileFragment.class.newInstance();
                       fragmentManager.beginTransaction().replace(R.id.frameContent, profileFragment ).commit();
                       drawerList.setCheckedItem(R.id.profile_view);
                   } catch (InstantiationException | IllegalAccessException e) {
                       e.printStackTrace();
                   }
               }
           });

       }
    }

    private void logout() {
        if (user.isGoogleProfile()){
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
        } else {
            LoginManager.getInstance().logOut();
        }
        goToMainActivity(this);
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
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
        else if (!(fragmentManager.findFragmentById(R.id.frameContent) instanceof HomeFragment)) {
            try {
                Fragment homeFragment = (Fragment) HomeFragment.class.newInstance();
                fragmentManager.beginTransaction().replace(R.id.frameContent, homeFragment).commit();
                drawerList.setCheckedItem(R.id.home_view);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        else if (!backPressed){
            Toast.makeText(getApplicationContext(), "Press Back button again to exit.", Toast.LENGTH_SHORT).show();
            backPressed = true;
        }
        else {
            System.exit(0);
        }
    }
}