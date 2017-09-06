package bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;
import bg.uni_ruse.stoyanov.bsilentorganizer.R;

import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.SocialId.getUserId;
import static bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager.SilentModelList.getSilentModels;

public class SilentManagerFragment extends Fragment {

    private SilentModelDao silentModelDao;
    private WeekDaysDao weekDaysDao;
    private ArrayAdapter<SilentModel> silentModelArrayAdapter;
    private ArrayList<SilentModel> silentModelsList;

    public SilentManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        silentModelDao = MainActivity.getDaoSession().getSilentModelDao();
        weekDaysDao = MainActivity.getDaoSession().getWeekDaysDao();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_silent_manager, container, false);

        FloatingActionButton newSilentModelButton = view.findViewById(R.id.new_silent_model_button);
        newSilentModelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new NewSilentModelDialogFragment();
                newFragment.setTargetFragment(SilentManagerFragment.this, 1);
                newFragment.show(getFragmentManager(), "NewSilentMode");
            }
        });

        ListView listView = view.findViewById(R.id.silent_mode_list);
        silentModelsList = new ArrayList<>();
        silentModelArrayAdapter = new SilentModelAdapter(getContext(), silentModelsList);

        listView.setAdapter(silentModelArrayAdapter);
        silentModelArrayAdapter.notifyDataSetChanged();
        silentModelsList.addAll(getSilentModels(getContext()));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                WeekDays weekDays = new WeekDays(null,
                        data.getBooleanExtra("mon", false),
                        data.getBooleanExtra("tue", false),
                        data.getBooleanExtra("wed", false),
                        data.getBooleanExtra("thu", false),
                        data.getBooleanExtra("fri", false),
                        data.getBooleanExtra("sat", false),
                        data.getBooleanExtra("sun", false));

                weekDaysDao.insert(weekDays);

                SilentModel silentModel = new SilentModel(null,
                        getUserId(getActivity()),
                        weekDays.getId(),
                        data.getIntExtra("startHour", 0),
                        data.getIntExtra("startMinutes", 0),
                        data.getIntExtra("endHour", 0),
                        data.getIntExtra("endMinutes", 0),
                        data.getBooleanExtra("vibrationMode", false));

                silentModelDao.insertOrReplace(silentModel);


                silentModelArrayAdapter.clear();
                silentModelArrayAdapter.addAll(getSilentModels(getContext()));
                silentModelArrayAdapter.notifyDataSetChanged();
            }
        }
    }
}
