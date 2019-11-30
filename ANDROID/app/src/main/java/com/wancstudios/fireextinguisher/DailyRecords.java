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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DailyRecords extends AppCompatActivity {

    public String url = "http://192.168.0.112:8000/api/dailyData";
    RecyclerView recyclelist;
    public ArrayList<DailyRecordsContainer> Dailyrecordlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_dailyrecords);

        recyclelist = findViewById(R.id.Dailyrecordrecycle);
        Dailyrecordlist = new ArrayList<>();
        RequestData();
    }

    public void RequestData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(DailyRecords.this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
//                            String name = response.getJSONObject(i).getString("name");
//                            String othername = response.getJSONObject(i).getString("customer");
//                            String quantity = response.getJSONObject(i).getString("quantity");
//                            String price = response.getJSONObject(i).getString("price_sold");
//                            String date = response.getJSONObject(i).getString("date");
//
//                            Dailyrecordlist.add(new DailyRecordsContainer(name,price,quantity,othername,date));
                        }
                        recyclelist.setLayoutManager(new LinearLayoutManager(DailyRecords.this));
                        recyclelist.setAdapter(new DailyRecordsistAdapter(DailyRecords.this, Dailyrecordlist));
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
