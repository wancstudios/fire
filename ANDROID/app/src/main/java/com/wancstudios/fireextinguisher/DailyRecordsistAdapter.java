package com.wancstudios.fireextinguisher;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class DailyRecordsistAdapter extends RecyclerView.Adapter<DailyRecordsistAdapter.ItemListViewHolder>
{
    private Context context;
   public ArrayList<DailyRecordsContainer> data;

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    public DailyRecordsistAdapter(Context context, ArrayList<DailyRecordsContainer> data)
    {
         this.context=context;
         this.data = data;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.container_daily_record,parent,false);
        return new ItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListViewHolder holder, int position)
    {
        if(data.get(position).type.equals("sold"))
        {
            holder.othername.setText("SOLD TO : "+data.get(position).otherName);
        }
        else
        {
            holder.othername.setText("BOUGHT FROM : "+data.get(position).otherName);
        }
        holder.item_name.setText(data.get(position).item_name +" X " + data.get(position).item_quantity);
        holder.item_amount.setText("₹"+data.get(position).item_amount);
        holder.date.setText(data.get(position).date);
        storageRef.child(data.get(position).item_name+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri)
            {
                Glide.with(context).load(uri).into(holder.image);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ItemListViewHolder extends RecyclerView.ViewHolder
    {
        TextView item_name,item_amount,othername,date;
        ImageView image;
        public ItemListViewHolder(View itemview)
        {
            super(itemview);
            item_name = itemView.findViewById(R.id.itemname_daily);
            othername = itemview.findViewById(R.id.CustomerName_daily);
            item_amount = itemView.findViewById(R.id.amount_daily);
            image = itemView.findViewById(R.id.image_daily);
            date = itemview.findViewById(R.id.date_daily);
        }
    }
}
