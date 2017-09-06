package bg.uni_ruse.stoyanov.bsilentorganizer.helpers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;
import bg.uni_ruse.stoyanov.bsilentorganizer.R;

/**
 * Created by stoyanovst on 9/6/17.
 */

public class RedirectMainActivity {

    public static void goToMainActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        sharedPreferences.edit()
                .clear()
                .apply();
        Toast.makeText(activity.getApplicationContext(), R.string.logout, Toast.LENGTH_SHORT).show();
        activity.finish();
    }
}
