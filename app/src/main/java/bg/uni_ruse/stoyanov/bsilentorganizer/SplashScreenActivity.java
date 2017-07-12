package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }
}
