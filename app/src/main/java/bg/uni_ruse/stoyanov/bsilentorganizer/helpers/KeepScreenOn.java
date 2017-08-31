package bg.uni_ruse.stoyanov.bsilentorganizer.helpers;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by stoyanovst on 8/31/17.
 */

public class KeepScreenOn {

    public static void setKeepScreenOnFlag(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void clearKeepScreenOnFlag(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
