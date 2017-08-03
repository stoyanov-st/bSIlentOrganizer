package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    UserDao userDao;
    private AudioManager audioManager;
    private int ringMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userDao = MainActivity.getDaoSession().getUserDao();

        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.FullName.isNotNull()).limit(1);
        List<User> users = qb.list();
        String text = users.get(0).getFullName();

        EditText editText = (EditText) findViewById(R.id.testBox);
        editText.setText(text);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        Switch silentSwitch = (Switch) findViewById(R.id.switch1);
        if(checkIfPhoneIsSilent()) {
            silentSwitch.setChecked(true);
        }
        silentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        });

        Button silentMode = (Button) findViewById(R.id.button2);
        silentMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfPhoneIsSilent()) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
            }
        });
    }

    private boolean checkIfPhoneIsSilent() {
        int ringerMode = audioManager.getRingerMode();
        return ringerMode == AudioManager.RINGER_MODE_SILENT;
    }

    private int getSavedRingMode() {
        //TODO: Restore prefs method
        return 0;
    }
}
