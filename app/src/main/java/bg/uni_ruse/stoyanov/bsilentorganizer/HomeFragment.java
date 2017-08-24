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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private AudioManager audioManager;
    private int ringMode;
    private Switch silentSwitch, vibrationModeSwitch;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        return new HomeFragment();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
