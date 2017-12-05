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

public class StartSilentModeService extends Service implements RingMode{

    static int SERVICE_ID = 419;
    private boolean isVibration = false;
    private AudioManager audioManager;

    @Override
    public boolean setRingerMode() {
        
        try {
            isVibration = intent.getBooleanExtra("vibrationMode", false);
            
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(setRingMode(isVibration));
            
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        if(setRingerMode) {
            Toast.makeText(getApplicationContext(), "Silent Mode Start", Toast.LENGTH_SHORT).show();
            return START_STICKY;
        }
        else return null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int setRingMode(Boolean isVibration) {
        if (isVibration) {
            return AudioManager.RINGER_MODE_VIBRATE;
        }
        else return AudioManager.RINGER_MODE_SILENT;
    }

}
