package com.example.pageacceuil;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

public class DataAdapter extends RecyclerView.Adapter<MyViewHolder>{

Context context;

    private final ListData listData;

    public DataAdapter(Context context,ListData listData) {
        this.context = context;
        this.listData=listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.blocdata,parent,false));

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DecimalFormat a = new DecimalFormat("##.###");
        holder.temp.setText(a.format(listData.recup_data(position).getTemperature()) + "°C");
        holder.humi.setText(a.format(listData.recup_data(position).getHumidite()) + "%");
        holder.co2.setText(a.format(listData.recup_data(position).getCO2()) + "%");
        holder.o2.setText(a.format(listData.recup_data(position).getO2()) + "%");
        holder.lux.setText(a.format(listData.recup_data(position).getLight()) + "lux");
        holder.temps.setText((listData.recup_data(position).getTemps()));
    }

    @Override
    public int getItemCount() {
        return listData.list_size();
    }
}
