package bg.uni_ruse.stoyanov.bsilentorganizer.silent_manager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by stoyanovst on 9/6/17.
 */

public class StartSilentModeService extends Service {

    static int SERVICE_ID = 419;
    private boolean vibrationMode = false;
    private AudioManager audioManager;
    private int ringMode;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        vibrationMode = intent.getBooleanExtra("vibrationMode", false);
        setRingMode(vibrationMode);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(ringMode);

        Toast.makeText(getApplicationContext(), "Silent Mode Start", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setRingMode(Boolean vibrationMode) {
        if (vibrationMode) {
            ringMode = AudioManager.RINGER_MODE_VIBRATE;
        }
        else ringMode = AudioManager.RINGER_MODE_SILENT;
    }

}
