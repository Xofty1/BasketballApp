package com.basketballapp.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basketballapp.R;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {

    private final List<Integer> years;
    private final OnYearClickListener listener;
    private int selectedYear = -1;

    public interface OnYearClickListener {
        void onYearClick(int year);
    }

    public TimeAdapter(List<Integer> years, OnYearClickListener listener) {
        this.years = years;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        int year = years.get(position);
        holder.yearText.setText(String.valueOf(year));

        // Выделение выбранного года
        if (year == selectedYear) {
            holder.yearText.setBackgroundResource(R.drawable.time_background_selected); // Создайте файл `year_background_selected.xml`
        } else {
            holder.yearText.setBackgroundResource(R.drawable.time_background);
        }

        holder.itemView.setOnClickListener(v -> {
            selectedYear = year;
            listener.onYearClick(year);
            notifyDataSetChanged(); // Обновляем список
        });
    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView yearText;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            yearText = itemView.findViewById(R.id.yearText);
        }
    }
}
