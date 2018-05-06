package com.example.int_systems.gmb_app.GMB_services;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.int_systems.gmb_app.R;

import java.util.List;

public class notificationAdapter extends RecyclerView.Adapter <notificationAdapter.ViewHolder> {
    public notificationAdapter(Context mCtx, List<Mynotifications> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Mynotifications> productList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.notify, null);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(notificationAdapter.ViewHolder holder, int position) {
        Mynotifications product = productList.get(position);

        //binding the data with the viewholder views
        holder.notifyTitle.setText(product.getTitle());
        holder.nofi_cation.setText(product.getDescription());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notifyTitle, nofi_cation;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            nofi_cation = (TextView) itemView.findViewById(R.id.notiText);
            notifyTitle = (TextView) itemView.findViewById(R.id.forthtext);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });
        }
    }


}

