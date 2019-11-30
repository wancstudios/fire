package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity
{
    public static ArrayList<String> itemsname;
    public static String url =  "http://192.168.0.112:8000/api/item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemsname = new ArrayList<>();

        RequestData();
    }


    public void AddItem(View view)
    {
        Intent intent = new Intent(MainActivity.this, Additem.class);
        startActivity(intent);
    }
    public  void Sold(View view)
    {
        Intent intent = new Intent(MainActivity.this, Sold.class);
        startActivity(intent);
    }

    public  void Buy(View view)
    {
        Intent intent = new Intent(MainActivity.this, Buy.class);
        startActivity(intent);
    }

    public  void ManageItem(View view)
    {
        Intent intent = new Intent(MainActivity.this, Manage.class);
        startActivity(intent);
    }


    public  void DailyrecordsBttn(View view)
    {
        Intent intent = new Intent(MainActivity.this, DailyRecords.class);
        startActivity(intent);
    }

    public  void ItemListBttn(View view)
    {
        Intent intent = new Intent(MainActivity.this, ItemList.class);
        startActivity(intent);
    }

    public void RequestData()
    {
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET,url,null,
            new Response.Listener<JSONArray>(){
                @Override
                public void onResponse(JSONArray response) {
                    try
                    {
                        for(int i = 0; i<response.length();i++ )
                        {
                            String ans =  response.getJSONObject(i).getString("name");
                            itemsname.add(ans);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error",error.toString());
                    requestQueue.stop();
                }
            }
        ){

        };
        requestQueue.add(jsonObjectRequest);
    }
}
