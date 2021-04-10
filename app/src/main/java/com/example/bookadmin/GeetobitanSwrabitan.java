package com.example.bookadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GeetobitanSwrabitan extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geetobitan_swarabitan);

        ImageView back = (android.widget.ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView opt1 = (TextView)findViewById(R.id.opt1);
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GeetobitanList.class);
                startActivity(intent);
            }
        });

        TextView opt2 = (TextView)findViewById(R.id.opt2);
        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SwarabitanList.class);
                startActivity(intent);
            }
        });
    }
}
