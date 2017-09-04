package bg.uni_ruse.stoyanov.bsilentorganizer.calendar;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;
import bg.uni_ruse.stoyanov.bsilentorganizer.R;
import bg.uni_ruse.stoyanov.bsilentorganizer.event.Event;
import bg.uni_ruse.stoyanov.bsilentorganizer.event.EventAdapter;
import bg.uni_ruse.stoyanov.bsilentorganizer.event.EventDao;
import bg.uni_ruse.stoyanov.bsilentorganizer.event.NewEventDialogFragment;

import static bg.uni_ruse.stoyanov.bsilentorganizer.event.EventList.getEventsByDate;


public class CalendarFragment extends Fragment implements AdapterView.OnItemClickListener, CalendarView.OnDateChangeListener, View.OnClickListener {

    private String TAG = CalendarFragment.class.getCanonicalName();
    private EventDao eventDao;
    private ArrayList<Event> eventList;
    private EventAdapter arrayAdapter;
    private TextView selectedDateTextView;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private Long currentDate;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventDao = MainActivity.getDaoSession().getEventDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.addEvent);
        floatingActionButton.setOnClickListener(this);

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);

        currentDate = calendarView.getDate();

        selectedDateTextView = view.findViewById(R.id.selectedDate);
        selectedDateTextView.setText(simpleDateFormat.format(currentDate));

        ListView listView = view.findViewById(R.id.eventList);
        eventList = new ArrayList<>();
        arrayAdapter = new EventAdapter(getContext(), eventList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);

        eventList.addAll(getEventsByDate(currentDate));

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Long eventId = null;
                String eventTitle = data.getStringExtra("eventTitle");
                boolean isImportant = data.getBooleanExtra("eventImportant", false);
                Long timestamp = data.getLongExtra("eventTimestamp", 0);

                Event event = new Event(eventId,
                        eventTitle,
                        currentDate,
                        timestamp,
                        isImportant);

                eventDao.insertOrReplace(event);

                getCurrentEvents();

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
        arrayAdapter.addAll(getEventsByDate(calendarView.getDate()));
        selectedDateTextView = getView().findViewById(R.id.selectedDate);
        currentDate = calendarView.getDate();
        selectedDateTextView.setText(simpleDateFormat.format(currentDate));

        getCurrentEvents();
    }

    @Override
    public void onClick(View view) {
        DialogFragment newFragment = new NewEventDialogFragment();
        newFragment.setTargetFragment(this, 1);
        newFragment.show(getFragmentManager(), "NewEvent");
    }

    private void getCurrentEvents() {
        arrayAdapter.clear();
        arrayAdapter.addAll(getEventsByDate(currentDate));
        arrayAdapter.notifyDataSetChanged();
    }
}
