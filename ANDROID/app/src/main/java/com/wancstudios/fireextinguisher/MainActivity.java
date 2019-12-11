package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> itemsname;
    public static String url = "http://fireextinguisher.xyz/api/item";

    TextView itemcount, soldcount, buycount,soldamount,buyamount,profitmain,Monthname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemsname = new ArrayList<>();
        itemcount = findViewById(R.id.RecordItemCount);
        Monthname = findViewById(R.id.month);
        soldcount = findViewById(R.id.RecordSoldCount);
        buycount = findViewById(R.id.RecordBoughtCount);
        profitmain = findViewById(R.id.ProfitAmountMAin);
        soldamount = findViewById(R.id.soldAmount);
        buyamount = findViewById(R.id.boughtAmount);
        RequestData();
        Itemcount();
        DataCounts();
        isInternetOn();
        Amount();
        MonthName();
    }


    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
            (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet
//
//            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }



    public void AddItem(View view) {
        Intent intent = new Intent(MainActivity.this, Additem.class);
        startActivity(intent);
        Itemcount();
        Amount();
        isInternetOn();
        DataCounts();
    }

    public void Sold(View view) {
        Intent intent = new Intent(MainActivity.this, Sold.class);
        startActivity(intent);
        Itemcount();
        isInternetOn();
        Amount();
        DataCounts();
    }

    public void Sold_records(View view) {
        Intent intent = new Intent(MainActivity.this, Sold_recycler.class);
        startActivity(intent);
        Itemcount();
        Amount();
        isInternetOn();
        DataCounts();
    }

    public void Buy(View view) {
        Intent intent = new Intent(MainActivity.this, Buy.class);
        startActivity(intent);
        Itemcount();
        DataCounts();
        Amount();
        isInternetOn();
    }

    public void ManageItem(View view) {
        Intent intent = new Intent(MainActivity.this, Manage.class);
        startActivity(intent);
        Itemcount();
        DataCounts();
        Amount();
        isInternetOn();
    }


    public void DailyrecordsBttn(View view) {
        Intent intent = new Intent(MainActivity.this, DailyRecords.class);
        startActivity(intent);
        Itemcount();
        DataCounts();
        isInternetOn();
        Amount();
    }

    public void ItemListBttn(View view) {
        Intent intent = new Intent(MainActivity.this, ItemList.class);
        startActivity(intent);
        Itemcount();
        DataCounts();
        isInternetOn();
        Amount();
    }

    public void RequestData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            String ans = response.getJSONObject(i).getString("name");
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
                    Log.e("Error", error.toString());
                    requestQueue.stop();
                }
            }
        ) {

        };
        requestQueue.add(jsonObjectRequest);
    }


    public void DataCounts() {
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://fireextinguisher.xyz/api/data", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {

                            soldcount.setText(response.getJSONObject("itemSold").getString("lastMonthItemSold"));
                            buycount.setText(response.getJSONObject("itemBuy").getString("lastMonthItemBuy"));
                        }
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


    public void Itemcount() {
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest request = new StringRequest(Request.Method.GET, "http://fireextinguisher.xyz/api/itemCount", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                itemcount.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        requestQueue.add(request);
    }


    public void Amount() {
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://fireextinguisher.xyz/api/data", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {

                            soldamount.setText("₹"+response.getJSONObject("SoldAmount").getString("lastMonthSoldAmount"));
                            buyamount.setText("₹"+response.getJSONObject("BuyAmount").getString("lastMonthBuyAmount"));

                            profitmain.setText("Profit: ₹"+response.getJSONObject("profit").getString("lastMonthProfit"));
                        }
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



    public void MonthName() {
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://fireextinguisher.xyz/api/data", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Monthname.setText(response.getString("MonthName").toUpperCase());

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
