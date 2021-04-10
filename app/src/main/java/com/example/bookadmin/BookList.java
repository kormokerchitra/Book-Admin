package com.example.bookadmin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

public class BookList extends AppCompatActivity {
    ArrayList<BookModel> arrayList;
    ListView book_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklist);

        ImageView back = (android.widget.ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), BookAdd.class);
                startActivity(intent);
            }
        });

        book_list = (ListView) findViewById(R.id.list_book);

        arrayList = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://192.168.100.6/bookTest/book_list.php");
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
            BookAdapter adapter = new BookAdapter(
                    getApplicationContext(), R.layout.book_item, arrayList
            );
            book_list.setAdapter(adapter);
//            category_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    String id = arrayList.get(i).getId();
//                    String category = arrayList.get(i).getCategory();
//
//                    Intent intent = new Intent(getBaseContext(), CategoryBooksPage.class);
//                    intent.putExtra("id", id);
//                    intent.putExtra("category", category);
//                    startActivity(intent);
//                }
//            });
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
