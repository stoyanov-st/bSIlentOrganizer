package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String text = "";
        String text1 = "";
        if (getIntent().getExtras() != null) {
            text = getIntent().getStringExtra("profileName");
            text1 = getIntent().getStringExtra("profilePicture");

        }
        EditText editText = (EditText) findViewById(R.id.testBox);
        editText.setText(text);
        EditText editText1 = (EditText) findViewById(R.id.testBox1);
        editText1.setText(text1);
    }

}
