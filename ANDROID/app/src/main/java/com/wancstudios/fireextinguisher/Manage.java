package com.wancstudios.fireextinguisher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Manage extends AppCompatActivity {

    EditText manage_name;
    EditText manage_amount;
    EditText manage_quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);


        manage_name = findViewById(R.id.manage_name);
        manage_amount = findViewById(R.id.manage_amount);
        manage_quantity = findViewById(R.id.manage_quantity);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//            (this, android.R.layout.select_dialog_item, DatabaseHelper.itemsname);
//        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.manage_name);
//
//        actv.setThreshold(1);
//        actv.setAdapter(adapter);
    }

    public void UpdateClickbtttn(View view)
    {
        if(manage_name.getText().toString().matches("") || manage_amount.getText().toString().matches("") || manage_quantity.getText().toString().matches(""))
        {
            Toast.makeText(Manage.this, "PLEASE INSERT ALL DATA", Toast.LENGTH_SHORT).show();
        }
        else
        {
//            MyDB.updateRecords(name.getText().toString(), Integer.parseInt(amount.getText().toString()), Integer.parseInt(quantity.getText().toString()));
            Toast.makeText(Manage.this, "SUCCESSFULL UPDATED", Toast.LENGTH_SHORT).show();

        }

    }

    public void DeleteClick(View view)
    {
        if(manage_name.getText().toString().matches("") )
        {
            Toast.makeText(Manage.this, "PLEASE INSERT NAME", Toast.LENGTH_SHORT).show();
        }
        else
        {
//            boolean deleted =  MyDB.deleteRecords(name.getText().toString());
//            if(deleted)
//            {
                Toast.makeText(Manage.this, "SUCCESSFULL DELETED", Toast.LENGTH_SHORT).show();
//            }
        }
    }

}
