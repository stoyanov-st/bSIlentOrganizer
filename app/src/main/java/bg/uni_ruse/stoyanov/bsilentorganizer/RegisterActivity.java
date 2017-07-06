package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private String email = getEmail();
    private String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    private String usernamePattern = "^(?=.*[A-Za-z0-9])+(?:[_-][A-Za-z0-9]+){3,12}$";
    private String initToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.registerButton).setOnClickListener(this);
    }
    private String getEmail() {
        if (getIntent().getExtras() != null) return getIntent().getStringExtra("email");

        return null;
    }

    @Override
    public void onClick(View view) {

        String username = findViewById(R.id.userNameRegisterInputBox).toString().trim();
        String password = findViewById(R.id.passwordRegisterInputBox).toString().trim();
        String passwordConfirm = findViewById(R.id.passwordConfirmRegisterInputBox).toString().trim();

        if (username.matches(usernamePattern)){
            if (password.matches(passwordPattern)) {
                if (password == passwordConfirm)  {
                    new UserDTO(email, username, password, initToken);
                }
            }
            else  Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(getApplicationContext(), "Invalid Username", Toast.LENGTH_SHORT).show();

    }

}
