package bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by stoyanovst on 9/7/17.
 */

public class EndSilentModeService extends Service implements RingMode {

    static int SERVICE_ID = 420;

    @Override
    public boolean setRingerMode() {
        try {
            AudioManger audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (setRingerMode()) {

        Toast.makeText(getApplicationContext(), "Silent Mode End", Toast.LENGTH_SHORT).show();

        return START_STICKY;
        } else return false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
