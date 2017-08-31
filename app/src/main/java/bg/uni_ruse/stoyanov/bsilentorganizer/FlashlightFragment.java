package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.CheckCameraPermissions.checkPermission;
import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.KeepScreenOn.clearKeepScreenOnFlag;
import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.KeepScreenOn.setKeepScreenOnFlag;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FlashlightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FlashlightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("deprecation")
public class FlashlightFragment extends Fragment {

    ToggleButton flashToggle;
    private Camera camera;
    private Camera.Parameters cameraParameters;
    private OnFragmentInteractionListener mListener;
    private boolean backgroundUse = false;

    public FlashlightFragment() {
        // Required empty public constructor
    }

    public static FlashlightFragment newInstance() {
        return new FlashlightFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setKeepScreenOnFlag(getActivity());
        checkPermission(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flashlight, container, false);

        flashToggle = view.findViewById(R.id.flash_light_toggle);
        Switch backgroundModeSwitch = view.findViewById(R.id.use_in_bg_switch);

        flashToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            turnFlashOn();
                        }
                    }).start();
                }
                else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    turnFlashOff();
                                } catch (IOException e) {
                                    camera.release();
                                    camera = Camera.open();
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                }
            }
        });

        backgroundModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                backgroundUse = b;
            }
        });

        backgroundModeSwitch.setChecked(getSavedBackgroundMode());

        return view;
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
        clearKeepScreenOnFlag(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();

        if (!backgroundUse && camera != null) {
            flashToggle = getActivity().findViewById(R.id.flash_light_toggle);
            flashToggle.setChecked(false);
        }


        SharedPreferences sharedPreferences = getActivity().getApplicationContext()
                .getSharedPreferences("flashLightPrefs", Context.MODE_PRIVATE);

        sharedPreferences.edit()
                .putBoolean("backgroundMode", backgroundUse)
                .apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (camera != null)
            camera.release();
    }

    private void turnFlashOn(){
        camera = Camera.open();
        cameraParameters = camera.getParameters();
        cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(cameraParameters);
        camera.startPreview();

    }

    private void turnFlashOff() throws IOException {
        camera.reconnect();
        cameraParameters = camera.getParameters();
        cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(cameraParameters);
        camera.stopPreview();
        camera.release();
    }

    private boolean getSavedBackgroundMode() {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext()
                .getSharedPreferences("flashLightPrefs", Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean("backgroundMode", false);
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
        void onFragmentInteraction(Uri uri);
    }
}
