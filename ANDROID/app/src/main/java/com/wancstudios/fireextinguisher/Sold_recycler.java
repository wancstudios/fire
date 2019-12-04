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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sold_recycler extends AppCompatActivity {

    public String url = "http://192.168.0.112:8000/api/sold";
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

                            soldContainers.add(new SoldContainer(name, price_sold, quantity, othername, date, balance_required,profit));
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
