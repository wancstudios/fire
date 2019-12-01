package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Manage extends AppCompatActivity {

    EditText manage_name;
    EditText manage_amount;
    EditText manage_quantity;

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);


        manage_name = findViewById(R.id.manage_name);
        manage_amount = findViewById(R.id.manage_amount);
        manage_quantity = findViewById(R.id.manage_quantity);

        pd = new ProgressDialog(this);
        pd.setMessage("updating....");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
            (this, android.R.layout.select_dialog_item, MainActivity.itemsname);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.manage_name);

        actv.setThreshold(1);
        actv.setAdapter(adapter);
    }

    public void UpdateClickbtttn(View view)
    {
        if(manage_name.getText().toString().matches("") || manage_amount.getText().toString().matches("") || manage_quantity.getText().toString().matches(""))
        {
            Toast.makeText(Manage.this, "PLEASE INSERT ALL DATA", Toast.LENGTH_SHORT).show();
        }
        else
        {
            UpdateData();
            pd.show();
        }

    }

    public void DeleteClick(View view)
    {
        if(manage_name.getText().toString().matches("") )
        {
            Toast.makeText(Manage.this, "PLEASE INSERT NAME", Toast.LENGTH_SHORT).show();
        }
        else
        {
            DeleteData();
            pd.show();
        }
    }





    public void UpdateData()
    {
        String url = "http://192.168.0.112:8000/api/item/"+manage_name.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(response.contains("0")){
                        Toast.makeText(getApplicationContext(),"Check your Connection",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                    if(response.contains("1")){
                        Toast.makeText(getApplicationContext(),"Succesfully Updated",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
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
                    Toast.makeText(Manage.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                String image = imageToString(bitmap);

                Map<String,String> params = new HashMap<>();

                params.put("price",manage_amount.getText().toString());
                params.put("quantity",manage_quantity.getText().toString());
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



    public void DeleteData()
    {
        String url = "http://192.168.0.112:8000/api/item/"+manage_name.getText().toString();

        final RequestQueue requestQueue = Volley.newRequestQueue(Manage.this);

        StringRequest request = new StringRequest(Request.Method.DELETE,
            url,
            new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("0")){
                    Toast.makeText(getApplicationContext(),"Check your Connection",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                if(response.contains("1")){
                    Toast.makeText(getApplicationContext(),"Succesfully Updated",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });
        requestQueue.add(request);
    }
}
