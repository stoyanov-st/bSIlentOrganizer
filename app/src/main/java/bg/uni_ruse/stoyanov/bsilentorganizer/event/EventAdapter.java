package bg.uni_ruse.stoyanov.bsilentorganizer.event;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import bg.uni_ruse.stoyanov.bsilentorganizer.R;

/**
 * Created by stoyanovst on 9/4/17.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Event event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.events_list_item_layout, parent, false);
        }

        TextView eventTitle = convertView.findViewById(R.id.eventTitle);
        TextView eventDate = convertView.findViewById(R.id.eventDate);

        ImageView flagView = convertView.findViewById(R.id.importantFlag);

        if (event != null) {
            eventTitle.setText(event.getEventName());
            eventDate.setText(event.getTimestamp());

            if (event.getImportanceFlag()) {
                flagView.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }
}
