package bg.uni_ruse.stoyanov.bsilentorganizer.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

/**
 * Created by stoyanovst on 8/29/17.
 */

public class SocialId {

    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("userId", "UserID");
    }
}
