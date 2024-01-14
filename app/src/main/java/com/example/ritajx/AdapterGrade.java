package com.example.ritajx;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ritajx.R;

import java.util.List;

public class AdapterGrade extends RecyclerView.Adapter<AdapterGrade.MyViewHolder> {
    private List<String> items; // This list should contain your data

    // Provide a reference to the views for each data item
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView; // Example view
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textViewCourseName); // Initialize your views here
        }
    }

    // Initialize the dataset of the Adapter
    public AdapterGrade(List<String> myDataset) {
        items = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public AdapterGrade.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view from the XML layout
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grade_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Set item views based on your views and data model
        String item = items.get(position);
        holder.textView.setText(item);
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return items.size();
    }
}
