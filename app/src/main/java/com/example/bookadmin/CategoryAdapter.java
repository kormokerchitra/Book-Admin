package com.example.bookadmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<CategoryModel> {

    ArrayList<CategoryModel> products;
    Context context;
    int resource;

    public CategoryAdapter(Context context, int resource, ArrayList<CategoryModel> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.category_item, null, true);

        }
        CategoryModel product = getItem(position);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView category = (TextView) convertView.findViewById(R.id.title); //type casting
        String category_title = product.getCategory();
        category.setText(category_title);

        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryEdit.class);
                intent.putExtra("category_id", product.getId());
                intent.putExtra("category_name", product.getCategory());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //add this line
                context.startActivity(intent);
            }
        });
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream is = null;

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", product.getId()));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://192.168.100.5/bookTest/delete_category.php");
                    //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    String msg = "Category Deleted Successfully";
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, CategoryList.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //add this line
                    context.startActivity(intent);
                } catch (ClientProtocolException e) {
                    Log.e("ClientProtocol", "Log_tag");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("Log_tag", "IOException");
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }
}