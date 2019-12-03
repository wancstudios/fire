package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sold_recycler extends AppCompatActivity {

    public String url = "http://192.168.0.112:8000/api/dailyData";
    RecyclerView recyclelist;
    public ArrayList<SoldContainer> soldContainers;
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray arraynames = response.names();
                        for (int i = 0; i < arraynames.length(); i++) {
                            {
                                JSONArray arraydata = response.getJSONArray(arraynames.getString(i));
                                for (int j=0 ; j < arraydata.length();j++)
                                {
                                    String name = arraydata.getJSONObject(j).getString("name");
                                    String othername = arraydata.getJSONObject(j).getString("customer");
                                    String quantity = arraydata.getJSONObject(j).getString("quantity");
                                    String price = arraydata.getJSONObject(j).getString("price_sold");
                                    String date = arraydata.getJSONObject(j).getString("date");
                                    String balance = arraydata.getJSONObject(j).getString("balance_required");
                                    String profit = arraydata.getJSONObject(j).getString("profit");


                                    soldContainers.add(new SoldContainer(name,price,quantity,othername,date,balance,profit));
                                }
                            }
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
}
