package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

public class DailyRecords extends AppCompatActivity {

    public String url = "http://fireextinguisher.xyz/api/dailyData";
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
                                    String price = arraydata.getJSONObject(j).getString("price");
                                    String date = arraydata.getJSONObject(j).getString("date");
                                    String type = arraydata.getJSONObject(j).getString("type");


                                    Dailyrecordlist.add(new DailyRecordsContainer(name,price,quantity,othername,date,type));
                                }
                            }
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
