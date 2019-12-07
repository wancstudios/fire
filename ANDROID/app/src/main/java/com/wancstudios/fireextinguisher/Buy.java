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
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Buy extends AppCompatActivity {

    EditText buy_name;
    EditText vendor_name;
    EditText buy_amount;
    EditText buy_quantity;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        buy_name = findViewById(R.id.buy_name);
        vendor_name = findViewById(R.id.vendor_name);
        buy_amount = findViewById(R.id.buy_amount);
        buy_quantity = findViewById(R.id.buy_quantity);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
            (this, android.R.layout.select_dialog_item, MainActivity.itemsname);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.buy_name);

        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }



    public void insertbuy(View view)
    {
        if(buy_name.getText().toString().matches("") || vendor_name.getText().toString().matches("") || buy_amount.getText().toString().matches("")|| buy_quantity.getText().toString().matches(""))
        {

            Toast.makeText(Buy.this, "PLEASE INSERT ALL DATA", Toast.LENGTH_SHORT).show();
        }
        else
        {
            pd.show();
            postData();
        }
    }

    public void postData()
    {
        String url = "http://192.168.0.112:8000/api/buy";

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
                    Toast.makeText(Buy.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",buy_name.getText().toString());
                params.put("vender",vendor_name.getText().toString());
                params.put("quantity",buy_quantity.getText().toString());
                params.put("price_buy",buy_amount.getText().toString());
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
