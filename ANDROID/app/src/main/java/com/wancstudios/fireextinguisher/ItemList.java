package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity {

    public String url = "http://192.168.0.112:8000/api/item";


    RecyclerView recyclelist;
    public ArrayList<ItemContainer> ItemContainerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_item);
        recyclelist = findViewById(R.id.itemListRecycle);
        ItemContainerList = new ArrayList<>();
        RequestData();

    }


    public void RequestData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(ItemList.this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            String name = response.getJSONObject(i).getString("name");
                            String price = response.getJSONObject(i).getString("price");
                            String quantity = response.getJSONObject(i).getString("quantity");
                            ItemContainerList.add(new ItemContainer(name, price, quantity));
                        }
                        recyclelist.setLayoutManager(new LinearLayoutManager(ItemList.this));
                        recyclelist.setAdapter(new ItemListAdapter(ItemList.this, ItemContainerList));
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
