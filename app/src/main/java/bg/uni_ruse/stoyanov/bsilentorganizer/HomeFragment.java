package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import bg.uni_ruse.stoyanov.bsilentorganizer.event.Event;
import bg.uni_ruse.stoyanov.bsilentorganizer.event.EventAdapter;
import bg.uni_ruse.stoyanov.bsilentorganizer.event.EventDao;
import bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager.EndSilentModeService;

import static bg.uni_ruse.stoyanov.bsilentorganizer.event.EventList.getEventsByDate;
import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.ToolbarTitle.setToolbarTitle;
import static bg.uni_ruse.stoyanov.bsilentorganizer.user.UserFullName.getUserFullName;

public class HomeFragment extends Fragment {

    public static String TAG = HomeFragment.class.getCanonicalName();
    private AudioManager audioManager;
    private String userFullName;
    private int ringMode;
    private EventDao eventDao;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Switch thirtySwitch;
    private Switch hourSwitch;
    private final int CODE_THIRTY_SWITCH = 1;
    private final int CODE_HOUR_SWITCH = 2;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);

        eventDao = MainActivity.getDaoSession().getEventDao();
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) throws java.lang.NullPointerException {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        userFullName = getUserFullName(getContext());
        setToolbar();
        //Silent mode toggle setup
        Switch silentSwitch = view.findViewById(R.id.switch_silence);
        thirtySwitch = view.findViewById(R.id.switch_30min);
        hourSwitch = view.findViewById(R.id.switch_1h);
        Switch vibrationMode = view.findViewById(R.id.switch_vibration);
        ringMode = getSavedRingMode();
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        if(checkIfPhoneIsSilent()) {
            silentSwitch.setChecked(true);
        }

        silentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setPhoneSilent();
                }
                else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    alarmManager.cancel(pendingIntent);
                }
            }
        });
        thirtySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    hourSwitch.setChecked(false);
                    setPhoneSilent();
                    setSilentService(AlarmManager.INTERVAL_HALF_HOUR, CODE_THIRTY_SWITCH);
                }
                else {
                    alarmManager.cancel(pendingIntent);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        });
        hourSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    thirtySwitch.setChecked(false);
                    setPhoneSilent();
                    setSilentService(AlarmManager.INTERVAL_HOUR, CODE_HOUR_SWITCH);
                }
                else {
                    alarmManager.cancel(pendingIntent);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        });
        if (ringMode == AudioManager.RINGER_MODE_VIBRATE) {
            vibrationMode.setChecked(true);
        }
        vibrationMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b) {
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


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = simpleDateFormat.format(Calendar.getInstance().getTimeInMillis());
        ListView listView = view.findViewById(R.id.today_events);
        listView.addHeaderView(inflater.inflate(R.layout.layout_home_list_header, null, false));
        ArrayList<Event> eventList = new ArrayList<>();
        ArrayAdapter<Event> arrayAdapter = new EventAdapter(getContext(), eventList);
        listView.setAdapter(arrayAdapter);
        eventList.addAll(getEventsByDate(getContext(), currentDate));

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

    private void setSilentService(Long interval, int intervalFlag) {
        Intent intent = new Intent(getContext(), EndSilentModeService.class);
        pendingIntent = PendingIntent.getService(getContext(), intervalFlag, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                Calendar.getInstance().getTimeInMillis() + interval,
                pendingIntent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SharedPreferences sharedPreferences = getActivity().getApplicationContext()
                .getSharedPreferences("silentModePrefs", Context.MODE_PRIVATE);

        sharedPreferences.edit()
                .putInt("ringMode", ringMode)
                .putBoolean("thirtySwitch", thirtySwitch.isChecked())
                .putBoolean("hourSwitch", hourSwitch.isChecked())
                .apply();
    }
}
