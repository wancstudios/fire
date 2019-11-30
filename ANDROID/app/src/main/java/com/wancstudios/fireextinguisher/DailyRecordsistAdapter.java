package com.wancstudios.fireextinguisher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class DailyRecordsistAdapter extends RecyclerView.Adapter
{
    private Context context;

    public DailyRecordsistAdapter(Context context)
    {
         this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.container_daily_records,parent,false);
        return new ItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount() {
        return 0;
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
