package com.example.bookadmin;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BookAdd extends AppCompatActivity {
    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Uri to store the image uri
    private Uri filePath;
    EditText catName, bookName;
    TextView add;
    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookadd);

        ImageView back = (android.widget.ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView gallery = (android.widget.ImageView) findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        requestStoragePermission();

        catName = (EditText) findViewById(R.id.category_text);
        bookName = (EditText) findViewById(R.id.book_name);
        add = (TextView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMultipart();
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    public void uploadMultipart() {
        //getting name for the image
        String book = bookName.getText().toString().trim();
        String category = catName.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, IpConfig.ip + "bookTest/category_finder.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        String res = response.toString();
                        Toast.makeText(BookAdd.this, res, Toast.LENGTH_SHORT).show();
                        //Creating a shared preference
                        if (res.contains("list")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(res);
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject productObject = jsonArray.getJSONObject(i);

                                    id = productObject.getString("id");
                                    Toast.makeText(BookAdd.this, id, Toast.LENGTH_SHORT).show();

                                }
                                //getting the actual path of the image
                                String path = FilePath.getPath(BookAdd.this, filePath);

                                if (path == null) {

                                    Toast.makeText(BookAdd.this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
                                } else {
                                    //Uploading code
                                    try {
                                        String uploadId = UUID.randomUUID().toString();

                                        //Creating a multi part request
                                        new MultipartUploadRequest(BookAdd.this, uploadId, IpConfig.ip + "bookTest/file_upload.php")
                                                .addFileToUpload(path, "pdf") //Adding file
                                                .addParameter("book_name", book) //Adding text parameter to the request
                                                .addParameter("category_id", id) //Adding text parameter to the request
                                                .addParameter("category_name", category) //Adding text parameter to the request
//                        .addParameter("name", category) //Adding text parameter to the request
//                        .setNotificationConfig(new UploadNotificationConfig())
                                                .setMaxRetries(5)
                                                .startUpload();
                                        Toast.makeText(BookAdd.this, "Uploaded successfully!", Toast.LENGTH_SHORT).show();//Starting the upload

                                        Intent intent = new Intent(getBaseContext(), BookList.class);
                                        startActivity(intent);

                                    } catch (Exception exc) {
                                        Toast.makeText(BookAdd.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(BookAdd.this, "Failed to get category!", Toast.LENGTH_SHORT).show();
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
                params.put("name", category);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(BookAdd.this);
        requestQueue.add(stringRequest);


    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
//            Toast.makeText(this, filePath + "", Toast.LENGTH_SHORT).show();
        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
