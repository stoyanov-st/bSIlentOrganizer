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
        if (getIntent().getExtras() != null) {
           text = getIntent().getParcelableExtra("profile").toString();

        }

        EditText editText = (EditText) findViewById(R.id.testBox);
        editText.setText(text);
    }

}
