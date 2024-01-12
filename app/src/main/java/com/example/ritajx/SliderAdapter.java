package com.example.ritajx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class SliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int WEATHER_TYPE = 0;
    public static int NEWS_TYPE = 1;
    public static int Birzeit_Cover = 2;
    public static int Birzeit_Cover2 = 3;

    // Custom listener interface
    public interface OnItemClickListener {
        void onItemClick(int position, int viewType);
    }

    private OnItemClickListener listener; // Custom listener
    // Add other types as constants if needed

    private List<Integer> items; // This list will hold the types of items.

    public SliderAdapter(List<Integer> items) {
        this.items = items; // Initialize with a list of item types
    }

    @Override
    public int getItemViewType(int position) {
        // Return the type based on the position or your logic
        return items.get(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == Birzeit_Cover) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.birzeit, parent, false);
            return new NewsViewHolder(view);
        }
        if (viewType == NEWS_TYPE) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gazagenocide, parent, false);
                return new NewsViewHolder(view);
        } if (viewType == WEATHER_TYPE){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather, parent, false);
                return new WeatherViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.birzeitcard, parent, false);
            return new WeatherViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(position, viewType);
            }
        });
        if (viewType == WEATHER_TYPE) {
            // Bind weather data to the weather view holder
            // ((WeatherViewHolder) holder).bind(weatherData);
        } else {
            // Bind news data to the news view holder
            // ((NewsViewHolder) holder).bind(newsData);
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    // Define ViewHolder for weather
    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        // Define weather view components

        WeatherViewHolder(View itemView) {
            super(itemView);
            // Initialize weather view components
        }
    }

    // Define ViewHolder for news
    static class NewsViewHolder extends RecyclerView.ViewHolder {
        // Define news view components

        NewsViewHolder(View itemView) {
            super(itemView);
            // Initialize news view components
        }
    }
    // Method to set the custom listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
