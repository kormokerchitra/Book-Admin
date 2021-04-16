package com.example.bookadmin;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CategoryEdit extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_edit);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String id = getIntent().getStringExtra("category_id");
        String name = getIntent().getStringExtra("category_name");

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        EditText category_text = (EditText) findViewById(R.id.category_text);
        category_text.setText(name);
        TextView edit_category = (TextView) findViewById(R.id.edit);
        edit_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream is = null;
                String category = category_text.getText().toString();
                if (category.equals("")) {
                    Toast.makeText(CategoryEdit.this, "category field is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("id", id));
                    nameValuePairs.add(new BasicNameValuePair("category", category));

                    try {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(IpConfig.ip + "bookTest/edit_category.php");
                        //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpClient.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        Toast.makeText(CategoryEdit.this, is + "", Toast.LENGTH_SHORT).show();
                        String msg = "Category Updated Successfully";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent(getBaseContext(), CategoryList.class);
                        startActivity(intent);
                    } catch (ClientProtocolException e) {
                        Log.e("ClientProtocol", "Log_tag");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.e("Log_tag", "IOException");
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
