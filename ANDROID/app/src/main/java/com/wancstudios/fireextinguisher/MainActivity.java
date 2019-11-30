package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
