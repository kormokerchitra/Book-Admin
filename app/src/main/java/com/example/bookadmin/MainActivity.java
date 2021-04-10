package com.example.bookadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        submit=(TextView)findViewById(R.id.submit_user);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                String pass = password.getText().toString();

                if(em.equals("")){
                    Toast.makeText(MainActivity.this, "Email field is empty!", Toast.LENGTH_SHORT).show();
                }else if(pass.equals("")){
                    Toast.makeText(MainActivity.this, "Password field is empty!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getBaseContext(), HomePage.class);
                    startActivity(intent);
                }
            }
        });
    }
}