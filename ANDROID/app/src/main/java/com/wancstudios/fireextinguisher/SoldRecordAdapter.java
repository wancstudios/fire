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


public class SoldRecordAdapter extends RecyclerView.Adapter<SoldRecordAdapter.ItemListViewHolder>
{
    private Context context;
   public ArrayList<SoldContainer> data;

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    public SoldRecordAdapter(Context context, ArrayList<SoldContainer> data)
    {
         this.context=context;
         this.data = data;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.container_soldrecord,parent,false);
        return new ItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListViewHolder holder, int position)
    {
        holder.othername.setText("Sold to : "+data.get(position).othername);
        holder.item_name.setText(data.get(position).item_name +" X " + data.get(position).item_quantity);
        holder.item_amount.setText("Total Amount: ₹"+data.get(position).item_amount);
        holder.date.setText(data.get(position).date);
        holder.balance.setText("Balance : ₹"+data.get(position).balance);
        holder.profit.setText("Profit : ₹"+data.get(position).profit);
        holder.Item_ID.setText("ID = "+data.get(position).Id);
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
        TextView item_name,Item_ID,item_amount,othername,date,balance,profit;
        ImageView image;
        public ItemListViewHolder(View itemview)
        {
            super(itemview);
            item_name = itemView.findViewById(R.id.soldrecord_item_name);
            othername = itemview.findViewById(R.id.soldrecord_customer_name);
            item_amount = itemView.findViewById(R.id.soldrecord_total_amount);
            image = itemView.findViewById(R.id.soldrecord_image);
            date = itemview.findViewById(R.id.soldrecord_date);
            balance = itemview.findViewById(R.id.sold_Balancerecord);
            Item_ID = itemview.findViewById(R.id.sold_item_id);
            profit = itemview.findViewById(R.id.soldrecord_profit);
        }
    }
}
