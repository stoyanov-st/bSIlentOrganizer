package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.facebook.login.widget.ProfilePictureView;

import org.greenrobot.greendao.query.QueryBuilder;


import java.util.List;

public class HomeActivity extends AppCompatActivity {
    UserDao userDao;
    private AudioManager audioManager;
    private int ringMode;
    private Toolbar toolbar;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String[] drawerListItems;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userDao = MainActivity.getDaoSession().getUserDao();

        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.FullName.isNotNull()).limit(1);
        List<User> users = qb.list();
        User user = users.get(0);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(user.getFullName());
        drawerListItems = getResources().getStringArray(R.array.navigation_list);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.navigation, drawerListItems));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        ProfilePictureView profilePictureView = findViewById(R.id.fb_profile_picture);
        profilePictureView.setProfileId(user.getUserId());


        ringMode = getSavedRingMode();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        Switch silentSwitch = findViewById(R.id.switch1);
        if(checkIfPhoneIsSilent()) {
            silentSwitch.setChecked(true);
        }
        silentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    audioManager.setRingerMode(ringMode);
                }
                else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        });


        Switch vibrationModeSwitch = findViewById(R.id.vibrationModeSwitch);
        if (ringMode == AudioManager.RINGER_MODE_VIBRATE) {
            vibrationModeSwitch.setChecked(true);
        }
        vibrationModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ringMode = AudioManager.RINGER_MODE_VIBRATE;
                    if (checkIfPhoneIsSilent()) {
                        setPhoneSilent();
                    }
                }
                else {
                    ringMode = AudioManager.RINGER_MODE_SILENT;
                    if (checkIfPhoneIsSilent()) {
                        setPhoneSilent();
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("silentModePrefs", Context.MODE_PRIVATE);

        sharedPreferences.edit()
                .putInt("ringMode", ringMode)
                .apply();
    }

    private boolean checkIfPhoneIsSilent() {
        return audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT || audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE;
    }

    private void setPhoneSilent() {
        audioManager.setRingerMode(ringMode);
    }

    private int getSavedRingMode() {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("silentModePrefs", Context.MODE_PRIVATE);

        return sharedPreferences.getInt("ringMode", 0);
    }
}

class DrawerItemClickListener implements ListView.OnItemClickListener{

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

