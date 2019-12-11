package com.wancstudios.fireextinguisher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Additem extends AppCompatActivity {


    private static final int GALLERY_REQUEST_CODE = 1 ;

    private EditText add_name;
    private EditText add_amount;
    public ImageView add_image;
    private EditText add_quantity;
    boolean done;
    public Uri selectedImage;


    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://fireextinguisher-f330e.appspot.com/");
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        add_name = (EditText)findViewById(R.id.add_name);
        add_amount = (EditText)findViewById(R.id.add_amount);
        add_image = findViewById(R.id.add_image);
        add_quantity = (EditText)findViewById(R.id.add_quantity);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");
    }

    public void SubmitClick(View view)
    {
        done = false;
        if(add_name.getText().toString().matches("") || add_amount.getText().toString().matches("") || add_quantity.getText().toString().matches("") || selectedImage == null)
        {

            Toast.makeText(Additem.this, "PLEASE INSERT ALL DATA", Toast.LENGTH_SHORT).show();
        }
        else
        {
            pd.show();
            postData();

        }
    }


    public void imageClick(View view)
    {   Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Additem.RESULT_OK)
            switch (requestCode)
            {
                case GALLERY_REQUEST_CODE:
                    selectedImage = data.getData();
                    add_image.setImageURI(selectedImage);
                    break;
            }
    }


    public void postData()
    {
        String url = "http://fireextinguisher.xyz/api/item";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(response.contains("0")){
                        Toast.makeText(getApplicationContext(),"Item Already Exist",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                    if(response.contains("1")){

                        StorageReference childRef = storageRef.child(add_name.getText().toString() +".jpg");
                        UploadTask uploadTask = childRef.putFile(selectedImage);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(),"Succesfully Added",Toast.LENGTH_SHORT).show();
                                done = true;
                                pd.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(Additem.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null) {
                        Log.d("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                    }
                    if (error instanceof TimeoutError) {
                        Log.e("Volley", "TimeoutError");
                    }else if(error instanceof NoConnectionError){
                        Log.d("Volley", "NoConnectionError");
                    } else if (error instanceof AuthFailureError) {
                        Log.d("Volley", "AuthFailureError");
                    } else if (error instanceof ServerError) {
                        Log.d("Volley", "ServerError");
                    } else if (error instanceof NetworkError) {
                        Log.d("Volley", "NetworkError");
                    } else if (error instanceof ParseError) {
                        Log.d("Volley", "ParseError");
                    }
                    Log.e("Error",error.toString());
                    Toast.makeText(Additem.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                String image = imageToString(bitmap);

                Map<String,String> params = new HashMap<>();
                params.put("name",add_name.getText().toString());
                params.put("price",add_amount.getText().toString());
                params.put("quantity",add_quantity.getText().toString());
                return params;
            }
        };


        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}
