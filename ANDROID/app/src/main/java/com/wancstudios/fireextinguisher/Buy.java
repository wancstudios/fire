package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Buy extends AppCompatActivity {

    EditText buy_name;
    EditText vendor_name;
    EditText buy_amount;
    EditText buy_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        buy_name = findViewById(R.id.buy_name);
        vendor_name = findViewById(R.id.vendor_name);
        buy_amount = findViewById(R.id.buy_amount);
        buy_quantity = findViewById(R.id.buy_quantity);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//            (this, android.R.layout.select_dialog_item, DatabaseHelper.itemsname);
//        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.buy_name);
//
//        actv.setThreshold(1);//will start working from first character
//        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }



    public void insertbuy(View view)
    {
        if(buy_name.getText().toString().matches("") || vendor_name.getText().toString().matches("") || buy_amount.getText().toString().matches("")|| buy_quantity.getText().toString().matches(""))
        {

            Toast.makeText(Buy.this, "PLEASE INSERT ALL DATA", Toast.LENGTH_SHORT).show();
        }
        else
        {
//            boolean done= MyDB.insertBuy(nameBuy.getText().toString(),Vendor.getText().toString(),Integer.parseInt(AmountBuy.getText().toString()),Integer.parseInt(QuantityBuy.getText().toString()));
//            if(done)
//            {
                Toast.makeText(Buy.this, "SUCCESSFULL INSERTED", Toast.LENGTH_SHORT).show();
//            }

        }

    }
}
