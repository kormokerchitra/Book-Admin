package com.example.bookadmin;

import android.app.Activity;
import android.content.Context;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<UserModel> {

    ArrayList<UserModel> products;
    Context context;
    int resource;

    public UserAdapter(Context context, int resource, ArrayList<UserModel> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.user_item, null, true);

        }
        UserModel product = getItem(position);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView name = (TextView) convertView.findViewById(R.id.title); //type casting
        String full_name = product.getFullname();
        name.setText(full_name);

        TextView email = (TextView) convertView.findViewById(R.id.email); //type casting
        String email_text = product.getEmail();
        email.setText(email_text);

        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream is = null;

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", product.getId()));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(IpConfig.ip + "bookTest/delete_user.php");
                    //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    String msg = "User Deleted Successfully";
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, UserList.class);
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

    ;
}