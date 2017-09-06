package bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import bg.uni_ruse.stoyanov.bsilentorganizer.R;

/**
 * Created by stoyanovst on 9/6/17.
 */

public class NewSilentModelDialogFragment extends DialogFragment {

    private AlertDialog alertDialog;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.DialogStyle));
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.layout_silent_mode, null))
            .setTitle(R.string.new_silent_mode)
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            })
            .setPositiveButton(R.string.add_silent_mode, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    TimePicker startTimePicker = alertDialog.findViewById(R.id.start_time_picker);
                    TimePicker endTimePicker = alertDialog.findViewById(R.id.end_time_picker);
                    ToggleButton monToggle = alertDialog.findViewById(R.id.mon_toggle);
                    ToggleButton tueToggle = alertDialog.findViewById(R.id.tue_toggle);
                    ToggleButton wedToggle = alertDialog.findViewById(R.id.wed_toggle);
                    ToggleButton thuToggle = alertDialog.findViewById(R.id.thu_toggle);
                    ToggleButton friToggle = alertDialog.findViewById(R.id.fri_toggle);
                    ToggleButton satToggle = alertDialog.findViewById(R.id.sat_toggle);
                    ToggleButton sunToggle = alertDialog.findViewById(R.id.sun_toggle);
                    Switch vibrationModeSwitch = alertDialog.findViewById(R.id.vibrationModeSwitch);

                    Intent intent = new Intent();

                    intent.putExtra("startHour", startTimePicker.getCurrentHour());
                    intent.putExtra("startMinutes", startTimePicker.getCurrentMinute());
                    intent.putExtra("endHour", endTimePicker.getCurrentHour());
                    intent.putExtra("endMinutes", endTimePicker.getCurrentMinute());
                    intent.putExtra("mon", monToggle.isChecked());
                    intent.putExtra("tur", tueToggle.isChecked());
                    intent.putExtra("wed", wedToggle.isChecked());
                    intent.putExtra("thu", thuToggle.isChecked());
                    intent.putExtra("fri", friToggle.isChecked());
                    intent.putExtra("sat", satToggle.isChecked());
                    intent.putExtra("sun", sunToggle.isChecked());
                    intent.putExtra("vibrationMode", vibrationModeSwitch.isChecked());

                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                }
            });

        alertDialog = builder.show();

        TabHost tabhost = alertDialog.findViewById(R.id.time_picker_tab);
        tabhost.setup();
        TabHost.TabSpec ts = tabhost.newTabSpec("tag1");
        ts.setContent(R.id.start);
        ts.setIndicator("Start");
        tabhost.addTab(ts);

        ts = tabhost.newTabSpec("tag2");
        ts.setContent(R.id.end);
        ts.setIndicator("End");
        tabhost.addTab(ts);

        TimePicker startTimePicker = alertDialog.findViewById(R.id.start_time_picker);
        startTimePicker.setIs24HourView(true);
        TimePicker endTimePicker = alertDialog.findViewById(R.id.end_time_picker);
        endTimePicker.setIs24HourView(true);

        return alertDialog;
    }
}
