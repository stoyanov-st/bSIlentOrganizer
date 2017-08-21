package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.widget.ProfilePictureView;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONObject;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    UserDao userDao;
    private AudioManager audioManager;
    private int ringMode;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userDao = MainActivity.getDaoSession().getUserDao();

        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.FullName.isNotNull()).limit(1);
        List<User> users = qb.list();
        User user = users.get(0);

        ProfilePictureView profilePictureView = findViewById(R.id.fb_profile_picture);
        profilePictureView.setProfileId(user.getUserId());

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(user.getFullName());

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
