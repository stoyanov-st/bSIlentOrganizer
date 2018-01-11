package bg.uni_ruse.stoyanov.bsilentorganizer.helpers;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by stoyanovst on 9/5/17.
 */

public class ToolbarTitle {

    public static void setToolbarTitle(AppCompatActivity activity, T titleResource) {
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle(titleResource);
        }
    }
}
