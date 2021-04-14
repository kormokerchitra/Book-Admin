package com.example.bookadmin;

import android.content.Intent;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class RatingList extends AppCompatActivity {
    ArrayList<BookModel> arrayList;
    ListView rating_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_list);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rating_list = (ListView) findViewById(R.id.list_rating);

        arrayList = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://192.168.100.5/bookTest/book_list.php");
            }
        });
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new BookModel(
                            productObject.getString("id"),
                            productObject.getString("book_name"),
                            productObject.getString("book_link"),
                            productObject.getString("category_id"),
                            productObject.getString("category_name"),
                            productObject.getString("rating"),
                            productObject.getString("reviewer_number")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RatingAdapter adapter = new RatingAdapter(
                    getApplicationContext(), R.layout.rating_item, arrayList
            );
            rating_list.setAdapter(adapter);
            rating_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String id = arrayList.get(i).getId();
                    String categoryID = arrayList.get(i).getCategory_id();
                    String categoryName = arrayList.get(i).getCategory_name();
                    String bookName = arrayList.get(i).getBook_name();

                    Intent intent = new Intent(getBaseContext(), RatingDetailsList.class);
                    intent.putExtra("id", id);
                    intent.putExtra("categoryID", categoryID);
                    intent.putExtra("categoryName", categoryName);
                    intent.putExtra("bookName", bookName);
                    startActivity(intent);
                }
            });
        }
    }

    private String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
