package com.wancstudios.bodyfuel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainMenu extends AppCompatActivity {

    DatabaseHelper MyDB;
    private EditText name;
    private EditText amount;
    private EditText quantity;
    private TextView ItemCount;
    private TextView SoldCOunt;
    private TextView BuyCount;
    private TextView SoldCountText;
    private TextView BuyCountText;
    private TextView TodayDate;
    private TextView ProfitText;
    public ArrayList<DataContainer> data;
    public ArrayList<DataContainerRecords> dataRecords;


    String Apiurl = "https://c83d8339.ngrok.io/api/data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        MyDB = new DatabaseHelper(this);

        name = (EditText)findViewById(R.id.name);
        amount = (EditText)findViewById(R.id.amount);
        quantity = (EditText)findViewById(R.id.quantity);
        ItemCount = findViewById(R.id.ItemCount);
        SoldCOunt = findViewById(R.id.SOLDCOUNT);
        BuyCount = findViewById(R.id.BUYCOUNT);
        TodayDate = findViewById(R.id.date);
        SoldCountText=findViewById(R.id.soldcounttext);
        ProfitText = findViewById(R.id.profittext);
        BuyCountText = findViewById(R.id.BuyCountText);

        datafill();
    }





      public void Ite