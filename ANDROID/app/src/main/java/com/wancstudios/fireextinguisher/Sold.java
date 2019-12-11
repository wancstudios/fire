package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sold extends AppCompatActivity {


    EditText sold_name;
    EditText customer_name;
    EditText sold_amount;
    EditText sold_quantity;
    EditText sold_amount_paid;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold);

        sold_name = findViewById(R.id.sold_name);
        customer_name= findViewById(R.id.custome_name);
        sold_amount = findViewById(R.id.sold_amount);
        sold_amount_paid = findViewById(R.id.sold_amount_paid);
        sold_quantity = findViewById(R.id.sold_quantity);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
            (this, android.R.layout.select_dialog_item, MainActivity.itemsname);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.sold_name);

        actv.setThreshold(1);
        actv.setAdapter(adapter);
    }


    public void insertSold(View view)
    {

        if (sold_name.getText().toString().matches("") || customer_name.getText().toString().matches("") || sold_amount.getText().toString().matches("") || sold_quantity.getText().toString().matches(""))
        {
            Toast.makeText(Sold.this, "PLEASE INSERT ALL DATA", Toast.LENGTH_SHORT).show();
        }
        else {
            pd.show();
            postData();
        }
    }




    public void postData()
    {
        String url = "http://fireextinguisher.xyz/api/sold";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(response.contains("0")){
                        Toast.makeText(getApplicationContext(),"Check your Connection",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                    if(response.contains("1")){
                        Toast.makeText(getApplicationContext(),"Succesfully Added",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                    if(response.contains("-1")){
                        Toast.makeText(getApplicationContext(),"Less Quantity Left",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                    if(response.contains("-2")){
                        Toast.makeText(getApplicationContext(),"Item Not Found",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Sold.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                String image = imageToString(bitmap);

                Map<String,String> params = new HashMap<>();
                params.put("name",sold_name.getText().toString());
                params.put("customer",customer_name.getText().toString());
                params.put("quantity",sold_quantity.getText().toString());
                params.put("price_sold",sold_amount.getText().toString());
                params.put("balance_paid",sold_amount_paid.getText().toString());
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
