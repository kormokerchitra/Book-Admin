package com.example.bookadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        TextView opt1 = (TextView)findViewById(R.id.opt1);
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CategoryList.class);
                startActivity(intent);
            }
        });

        TextView opt2 = (TextView)findViewById(R.id.opt2);
        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), BookList.class);
                startActivity(intent);
            }
        });

        TextView opt3 = (TextView)findViewById(R.id.opt3);
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GeetobitanSwrabitan.class);
                startActivity(intent);
            }
        });

        TextView opt4 = (TextView)findViewById(R.id.opt4);
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), UserList.class);
                startActivity(intent);
            }
        });

        TextView opt5 = (TextView)findViewById(R.id.opt5);
        opt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RatingList.class);
                startActivity(intent);
            }
        });
    }
}
