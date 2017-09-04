package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

public class HomeFragment extends Fragment {

    private AudioManager audioManager;
    private int ringMode;
    private Switch silentSwitch, vibrationModeSwitch;

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

        //Silent mode toggle setup
        silentSwitch = view.findViewById(R.id.switch1);
        vibrationModeSwitch = view.findViewById(R.id.vibrationModeSwitch);

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

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getActivity().getApplicationContext()
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
        SharedPreferences sharedPreferences = getActivity().getApplicationContext()
                .getSharedPreferences("silentModePrefs", Context.MODE_PRIVATE);

        return sharedPreferences.getInt("ringMode", 0);
    }

}
