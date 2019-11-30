package com.wancstudios.fireextinguisher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Additem extends AppCompatActivity {


    private static final int GALLERY_REQUEST_CODE = 1 ;

    private EditText add_name;
    private EditText add_amount;
    public ImageView add_image;
    private EditText add_quantity;
    boolean done;
    public Uri selectedImage;


    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://fireextinguisher-f330e.appspot.com/");
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        add_name = (EditText)findViewById(R.id.add_name);
        add_amount = (EditText)findViewById(R.id.add_amount);
        add_image = findViewById(R.id.add_image);
        add_quantity = (EditText)findViewById(R.id.add_quantity);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");
    }

    public void SubmitClick(View view)
    {
        done = false;
        if(add_name.getText().toString().matches("") || add_amount.getText().toString().matches("") || add_quantity.getText().toString().matches("") || selectedImage == null)
        {

            Toast.makeText(Additem.this, "PLEASE INSERT ALL DATA", Toast.LENGTH_SHORT).show();
        }
        else
        {
            pd.show();
            StorageReference childRef = storageRef.child(add_name.getText().toString() +".jpg");
            UploadTask uploadTask = childRef.putFile(selectedImage);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    Toast.makeText(Additem.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    done = true;
//                    MyDB.insertRecord(name.getText().toString(), Integer.parseInt(add_amount.getText().toString()), Integer.parseInt(quantity.getText().toString()));
//                    Toast.makeText(AddItem.this, "SUCCESSFUL INSERTED", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(Additem.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    public void imageClick(View view)
    {   Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Additem.RESULT_OK)
            switch (requestCode)
            {
                case GALLERY_REQUEST_CODE:
                    selectedImage = data.getData();
                    add_image.setImageURI(selectedImage);
                    break;
            }
    }
}
