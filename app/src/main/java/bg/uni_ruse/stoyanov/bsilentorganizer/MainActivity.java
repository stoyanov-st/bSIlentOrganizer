package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void loginWithOrRegister(View view) {
        Intent intent = new Intent(this, loginWithOrRegister.class);
        startActivity(intent);
                
    }
}
