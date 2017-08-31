package bg.uni_ruse.stoyanov.bsilentorganizer.helpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by stoyanovst on 8/31/17.
 */

@TargetApi(Build.VERSION_CODES.M)
public class CheckCameraPermissions {

    public static void checkPermission(Activity activity) {
        int hasCameraPermission = activity.checkSelfPermission(android.Manifest.permission.CAMERA);
        if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.CAMERA},
                    1);
        }
    }
}
