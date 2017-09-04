package bg.uni_ruse.stoyanov.bsilentorganizer.qr;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import bg.uni_ruse.stoyanov.bsilentorganizer.R;

import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.CheckCameraPermissions.checkPermission;
import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.KeepScreenOn.clearKeepScreenOnFlag;
import static bg.uni_ruse.stoyanov.bsilentorganizer.helpers.KeepScreenOn.setKeepScreenOnFlag;


public class QRScannerFragment extends Fragment {

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    public QRScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setKeepScreenOnFlag(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qrscanner, container, false);


        final SurfaceView cameraPreview = view.findViewById(R.id.camera_preview);
        final TextView barcodeText = view.findViewById(R.id.barcode_text);

        barcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(getContext(), barcodeDetector)
                .setRequestedPreviewSize(350, 350)
                .setAutoFocusEnabled(true)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    checkPermission(getActivity());
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                barcodeDetector.release();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    barcodeText.post(new Runnable() {
                        @Override
                        public void run() {
                            barcodeText.setText(barcodes.valueAt(0).displayValue);
                            Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(500);

                        }
                    });
                }
            }
        });

        return view;
    }

}
