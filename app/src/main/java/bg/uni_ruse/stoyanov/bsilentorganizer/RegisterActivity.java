package bg.uni_ruse.stoyanov.bsilentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.greendao.database.Database;


import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private String email = "";
    private String initToken = "";
    RequestQueue queue;
    UserDao userDao;
    final String url = "http://10.0.2.2:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        queue = Volley.newRequestQueue(this);
        email = getEmail();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "/init",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        initToken = response;
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

        findViewById(R.id.registerButton).setOnClickListener(this);
    }
    private String getEmail() {
        if (getIntent().getExtras() != null) return getIntent().getStringExtra("email");

        return null;
    }

    @Override
    public void onClick(View view) {

        EditText usernameInput = findViewById(R.id.userNameRegisterInputBox);
        EditText passInput = findViewById(R.id.passwordRegisterInputBox);
        EditText passConfirmInput = findViewById(R.id.passwordConfirmRegisterInputBox);
        final String username = usernameInput.getText().toString().trim();
        final String password = passInput.getText().toString().trim();
        final String passwordConfirm = passConfirmInput.getText().toString().trim();

        String usernamePattern = "[A-Za-z0-9]{3,12}";
        if (username.matches(usernamePattern)){
            String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
            if (password.matches(passwordPattern)) {
                if (password.equals(passwordConfirm))  {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/register",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i(getLocalClassName(), response);
                                    userDao = MainActivity.getDaoSession().getUserDao();
                                    userDao.insert(new User());
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d(getLocalClassName(), "User registration error");
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                    }) {
                            @Override
                            protected Map<String, String> getParams() {
                              Map<String, String> params = new HashMap<>();
                                params.put("token", initToken);
                                params.put("email", email);
                                params.put("username", username);
                                params.put("password", passEncrypt(password));

                                return params;
                            }
                    };
                    queue.add(stringRequest);
                }
                else Toast.makeText(getApplicationContext(), "Passwords Don't match", Toast.LENGTH_SHORT).show();
            }
            else  Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(getApplicationContext(), "Invalid Username", Toast.LENGTH_SHORT).show();

    }


    private String passEncrypt(String pass) {
           return new String(Base64.encode(pass.getBytes(), Base64.DEFAULT));
    }
}
