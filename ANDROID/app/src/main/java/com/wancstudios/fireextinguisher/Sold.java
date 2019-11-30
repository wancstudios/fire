package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Sold extends AppCompatActivity {


    EditText sold_name;
    EditText customer_name;
    EditText sold_amount;
    EditText sold_quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold);

        sold_name = findViewById(R.id.sold_name);
        customer_name= findViewById(R.id.custome_name);
        sold_amount = findViewById(R.id.sold_amount);
        sold_quantity = findViewById(R.id.sold_quantity);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
            (this, android.R.layout.select_dialog_item, MainActivity.itemsname);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.sold_name);

        actv.setThreshold(1);
        actv.setAdapter(adapter);
    }


    public void insertSold(View view)
    {

        if (sold_name.getText().toString().matches("") || customer_name.getText().toString().matches("") || sold_amount.getText().toString().matches("") || sold_quantity.getText().toString().matches(""))
        {
            Toast.makeText(Sold.this, "PLEASE INSERT ALL DATA", Toast.LENGTH_SHORT).show();
        }
        else {
//            MyDB.insertSold(nameSold.getText().toString(), Customer.getText().toString(), Integer.parseInt(AmountSold.getText().toString()), Integer.parseInt(QuantitySold.getText().toString()));
            Toast.makeText(Sold.this, "SUCCESSFULL INSERTED", Toast.LENGTH_SHORT).show();
        }
    }
}
