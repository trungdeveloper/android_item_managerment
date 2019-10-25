package com.example.itemmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.itemmanagement.R;
import com.example.itemmanagement.model.Item;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public List<Item> items;
    public static OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
//    public MyAdapter(List<Item> items) {
//        this.items = items;
//    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textName;
        public TextView textQuantity;
        Button btnDelete;
        Button btnUpdate;

        public MyViewHolder(View v) {
            super(v);
            textName = v.findViewById(R.id.name);
            textQuantity = v.findViewById(R.id.quantity);
            btnDelete = v.findViewById(R.id.delete);
            btnUpdate = v.findViewById(R.id.update);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDeleteClick(getAdapterPosition());
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onUpdateClick(getAdapterPosition());
                }
            });
        }
    }



    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_screen, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textName.setText(""+items.get(position).getName());
        holder.textQuantity.setText(""+items.get(position).getQuantity());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(items == null)
            return 0;
        return items.size();
    }

    public interface OnItemClickListener {
        void onUpdateClick(int position);

        void onDeleteClick(int position);
    }


}