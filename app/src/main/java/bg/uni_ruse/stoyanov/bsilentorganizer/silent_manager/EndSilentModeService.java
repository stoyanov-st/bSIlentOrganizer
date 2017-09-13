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

public class EndSilentModeService extends Service {

    static int SERVICE_ID = 420;
    private AudioManager audioManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        Toast.makeText(getApplicationContext(), "Silent Mode End", Toast.LENGTH_SHORT).show();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
