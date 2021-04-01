package com.yoska.demo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{


    JSONArray jsonArray;
    Context context;

    public RecyclerAdapter(JSONArray jsonArray, Context context) {
        this.jsonArray = jsonArray;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name,flavour,price,image_url;

        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);

            name = jsonObject.getString("name");
            flavour = jsonObject.getString("flavour");
            price = jsonObject.getString("price");
            image_url = jsonObject.getString("image");

            Picasso.with(context).load(image_url).placeholder(R.drawable.loader).error(R.drawable.loader).into(holder.image);

            //clickListener on particular list_item click
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ShowDetails.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("image", image_url);
                    intent.putExtra("name", name);
                    intent.putExtra("flavour", flavour);
                    intent.putExtra("price",price);
                    context.startActivity(intent);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,flavour,price;
        ImageView image;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }

    }
}
