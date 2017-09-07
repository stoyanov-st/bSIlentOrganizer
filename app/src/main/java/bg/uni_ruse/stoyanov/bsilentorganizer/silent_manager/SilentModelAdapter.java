package bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;
import bg.uni_ruse.stoyanov.bsilentorganizer.R;

import static bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager.SilentModelList.getSilentModels;

/**
 * Created by stoyanovst on 9/6/17.
 */

public class SilentModelAdapter extends ArrayAdapter<SilentModel> implements CompoundButton.OnCheckedChangeListener {
    private SilentModel silentModel;

    public SilentModelAdapter(@NonNull Context context, ArrayList<SilentModel> silentModels) {
        super(context, 0, silentModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        silentModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.silent_mode_ilst_item_layout,
                            parent,
                            false);
        }

        String timeStamp = silentModel.getStartHours()
                + ":"
                + (silentModel.getStartMinutes() < 10 ? "0" + silentModel.getStartMinutes() : silentModel.getStartMinutes() )
                + " - "
                + silentModel.getEndHours()
                + ":"
                + (silentModel.getEndMinutes() < 10 ? "0" + silentModel.getEndMinutes() : silentModel.getEndMinutes() );

        Switch silentSwitch = convertView.findViewById(R.id.silent_switch);
        TextView daysTextView = convertView.findViewById(R.id.days_text_view);

        silentSwitch.setText(timeStamp);
        silentSwitch.setOnCheckedChangeListener(this);
        daysTextView.setText(silentModel.getWeekDays().getDays());

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.delete_item, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        SilentModelDao silentModelDao = MainActivity.getDaoSession().getSilentModelDao();
                        silentModelDao.delete(silentModel);
                        clear();
                        addAll(getSilentModels(getContext()));
                        notifyDataSetChanged();
                        return true;
                    }
                });
                return true;
            }
        });

        return convertView;
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        Intent startIntent = new Intent(getContext(), StartSilentModeService.class);
        startIntent.putExtra("vibrationMode", silentModel.getVibrationMode());
        PendingIntent startPendingIntent = PendingIntent.getService(getContext(), 0 ,startIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent endIntent = new Intent(getContext(), EndSilentModeService.class);
        PendingIntent endPendingIntent = PendingIntent.getService(getContext(), 0, endIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (b) {
            if (silentModel.getWeekDays().getSelectedDays().isEmpty()) {
                Calendar startService = setStartCalendar(silentModel);
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        startService.getTimeInMillis(),
                        startPendingIntent);

                Calendar endService = setEndCalendar(silentModel);
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        endService.getTimeInMillis(),
                        endPendingIntent);
            }
            else {
                for (int day : silentModel.getWeekDays().getSelectedDays()) {

                    Calendar startService = setStartCalendar(silentModel);
                    startService.setFirstDayOfWeek(Calendar.MONDAY);
                    startService.set(Calendar.DAY_OF_WEEK, day);

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                            startService.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY * 7,
                            startPendingIntent);

                    Calendar endService = setEndCalendar(silentModel);
                    endService.set(Calendar.DAY_OF_WEEK, day);

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                            endService.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY * 7,
                            endPendingIntent);
                }
            }
        }
        else {
            if (alarmManager != null) {
                alarmManager.cancel(startPendingIntent);
                alarmManager.cancel(endPendingIntent);
                getContext().startService(endIntent);
            }
        }
    }


    private Calendar setStartCalendar(SilentModel silentModel) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, silentModel.getStartHours());
        calendar.set(Calendar.MINUTE, silentModel.getStartMinutes());
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }

    private Calendar setEndCalendar(SilentModel silentModel) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, silentModel.getEndHours());
        calendar.set(Calendar.MINUTE, silentModel.getEndMinutes());
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }
}
