package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.greenrobot.greendao.query.QueryBuilder;

import bg.uni_ruse.stoyanov.bsilentorganizer.user.User;
import bg.uni_ruse.stoyanov.bsilentorganizer.user.UserDao;

import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.SocialId.getUserId;
import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.ToolbarTitle.setToolbarTitle;
import static bg.uni_ruse.stoyanov.bsilentorganizer.user.UserFullName.getUserFullName;

public class HomeFragment extends Fragment {

    public static String TAG = HomeFragment.class.getCanonicalName();
    private AudioManager audioManager;
    private String userFullName;
    private Switch silentSwitch;
    private int ringMode;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) throws java.lang.NullPointerException {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        userFullName = getUserFullName(getContext());
        setToolbar();
        //Silent mode toggle setup
        silentSwitch = view.findViewById(R.id.switch1);

        ringMode = getSavedRingMode();

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

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

        return view;
    }


    private boolean checkIfPhoneIsSilent() {
        return audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT || audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE;
    }

    private void setPhoneSilent() {
        audioManager.setRingerMode(ringMode);
    }

    private int getSavedRingMode() {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext()
                .getSharedPreferences("silentModePrefs", Context.MODE_PRIVATE);

        return sharedPreferences.getInt("ringMode", 0);
    }
    private void setToolbar() {

        setToolbarTitle((AppCompatActivity) getActivity(), userFullName);
    }

}
