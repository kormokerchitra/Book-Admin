package com.example.bookadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    TextView submit;
    boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        submit = (TextView) findViewById(R.id.submit_user);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                String pass = password.getText().toString();

                if (em.equals("")) {
                    Toast.makeText(MainActivity.this, "Email field is empty!", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Password field is empty!", Toast.LENGTH_SHORT).show();
                } else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, IpConfig.ip + "bookTest/login.php",
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    String res = response.toString();
                                    //Creating a shared preference
                                    if (res.equals("success")) {
                                        Intent intent = new Intent(getBaseContext(), HomePage.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //You can handle error here if you want
                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            //Adding parameters to request
                            params.put("email", em);
                            params.put("password", pass);

                            //returning parameter
                            return params;
                        }
                    };

                    //Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}