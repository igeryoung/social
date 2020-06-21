package com.example.social.Image;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    private List<ItemModel> items; //Stores a list containing name, city, age and pic
    //constructor
    public CardStackAdapter(List<ItemModel> items) {
        this.items = items;
    }
    //return View Holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }
    //Bind ViewHolder with item position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }
    //return Item count
    @Override
    public int getItemCount() {
        return items.size();
    }

    // bind xml items with data
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, city, age;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            city = itemView.findViewById(R.id.item_age);
            age = itemView.findViewById(R.id.item_city);
        }

        void setData(ItemModel data) {
            Picasso.get()
                    .load(data.getImage())
                    .fit()
                    .centerCrop()
                    .into(image);
            name.setText(data.getName());
            city.setText(data.getCity());
            age.setText(data.getAge());
        }
    }
    // return items
    public List<ItemModel> getItems() {
        return items;
    }
    // set items
    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
}
