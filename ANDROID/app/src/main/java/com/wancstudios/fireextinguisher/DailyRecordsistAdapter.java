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
        View view = inflater.inflate(R.layout.container_daily_records,parent,false);
        return new ItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListViewHolder holder, int position)
    {
        holder.item_name.setText(data.get(position).item_name);
        holder.item_amount.setText(data.get(position).item_amount);
        holder.item_quantity.setText(data.get(position).item_quantity);
        holder.othername.setText(data.get(position).otherName);
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
        TextView item_name,item_amount,item_quantity,othername,date;
        ImageView image;
        public ItemListViewHolder(View itemview)
        {
            super(itemview);
            item_name = itemView.findViewById(R.id.item_name);
            othername = itemview.findViewById(R.id.CustomerName_daily);
            item_amount = itemView.findViewById(R.id.item_amount);
            item_quantity = itemView.findViewById(R.id.item_quantity);
            image = itemView.findViewById(R.id.Item_image);
            date = itemview.findViewById(R.id.date_daily);
        }
    }
}
