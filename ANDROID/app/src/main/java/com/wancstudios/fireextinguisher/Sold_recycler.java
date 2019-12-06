package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sold_recycler extends AppCompatActivity {

    public String url = "http://192.168.0.112:8000/api/sold";
    RecyclerView recyclelist;
    public ArrayList<SoldContainer> soldContainers;
    public String newbalance,ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_sold);

        recyclelist = findViewById(R.id.soldrecord_recycler);
        soldContainers = new ArrayList<>();
        RequestData();
    }


    public void RequestData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(Sold_recycler.this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            String name = response.getJSONObject(i).getString("name");
                            String othername = response.getJSONObject(i).getString("customer");
                            String price_sold = response.getJSONObject(i).getString("price_sold");
                            String quantity = response.getJSONObject(i).getString("quantity");
                            String balance_required = response.getJSONObject(i).getString("balance_required");
                            String profit = response.getJSONObject(i).getString("profit");
                            String date = response.getJSONObject(i).getString("date");
                            String Id = response.getJSONObject(i).getString("id");

                            soldContainers.add(new SoldContainer(name, price_sold, quantity, othername, date, balance_required,profit,Id));
                        }
                        recyclelist.setLayoutManager(new LinearLayoutManager(Sold_recycler.this));
                        recyclelist.setAdapter(new SoldRecordAdapter(Sold_recycler.this, soldContainers));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.toString());
                    requestQueue.stop();
                }
            }
        ) {

        };
        requestQueue.add(jsonObjectRequest);
    }


    public void editbalance(View view)
    {
        LayoutInflater li = LayoutInflater.from(Sold_recycler.this);

        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
            Sold_recycler.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
            .findViewById(R.id.editTextDialogUserInput);

        final EditText Idedit = (EditText) promptsView
            .findViewById(R.id.EditID);
        // set dialog message
        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        ID = Idedit.getText().toString();
                        newbalance = userInput.getText().toString();
                        updatedatabalance();

                    }
                })
            .setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }



    public void updatedatabalance()
    {
        String url = "http://192.168.0.112:8000/api/sold/"+ID;

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(response.contains("0")){
                        Toast.makeText(getApplicationContext(),"Check your Connection",Toast.LENGTH_SHORT).show();
                       // pd.dismiss();
                    }
                    if(response.contains("1")){
                        Toast.makeText(getApplicationContext(),"Succesfully Added",Toast.LENGTH_SHORT).show();
                      //  pd.dismiss();
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
                    Toast.makeText(Sold_recycler.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("balance_paid",newbalance);
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
