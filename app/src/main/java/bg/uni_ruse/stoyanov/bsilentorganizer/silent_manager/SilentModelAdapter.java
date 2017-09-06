package bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;


import java.util.ArrayList;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;
import bg.uni_ruse.stoyanov.bsilentorganizer.R;

import static bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager.SilentModelList.getSilentModels;

/**
 * Created by stoyanovst on 9/6/17.
 */

public class SilentModelAdapter extends ArrayAdapter<SilentModel> {

    public SilentModelAdapter(@NonNull Context context, ArrayList<SilentModel> silentModels) {
        super(context, 0, silentModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final SilentModel silentModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.silent_mode_ilst_item_layout, parent, false);
        }

        String timeStamp = silentModel.getStartHours()
                + ":"
                + silentModel.getStartMinutes()
                + " - "
                + silentModel.getEndHours()
                + ":"
                + silentModel.getEndMinutes();

        Switch silentSwitch = convertView.findViewById(R.id.silent_switch);
        TextView daysTextView = convertView.findViewById(R.id.days_text_view);

        silentSwitch.setText(timeStamp);

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
}
